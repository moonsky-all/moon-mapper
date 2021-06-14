# moon-mapper
A java bean properties mapper

这是一个 Java Bean 属性映射器

**Github**: [https://github.com/moonsky-all/moon-mapper](https://github.com/moonsky-all/moon-mapper)

**Gitee**: [https://gitee.com/moonsky-all/moon-mapper](https://gitee.com/moonsky-all/moon-mapper)

### 一、 安装
``` xml
<dependency>
  <groupId>io.github.moonsky-all</groupId>
  <artifactId>moon-mapper</artifactId>
  <version>0.2</version>
</dependency>
```

### 二、 使用
`moon-mapper`属性值复制是基于两个同名属性的数据复制:

2.1. 注册映射器只需要在其中一个类上添加注解`MapperFor`指定要映射的类即可定义两个类之间的映射关系；

2.2. 获取映射器`BeanMapper mapper = Mapper2.get(Class, Class)`。

2.3. 使用映射器`mapper.doForward(obj1, obj2)`
``` java
public class UserEntity {
    
    private String name;
    
    private LocalDateTime birthday;

    // other fields, setters, getters
    // 支持 lombok
}

// 用 MapperFor 注册映射器
@MapperFor({UserEntity.class})
public class UserVO {

    // 获取映射器
    // thisPrimary 是个简便方法，只能在有 MapperFor 注解的类使用
    // 它获取的是本类与 MapperFor 中定义的第一个类之间的映射器
    static BeanMapper MAPPER = Mapper2.thisPrimary();

    private String name;

    // MappingFormat 注解是用作格式化的，可用于格式化（解析）日期和数字
    // 在非日期/数字与字符串之间的关系上会忽略掉
    // 日期格式化支持 java8-time、joda-time、Calendar、Date 等
    @Mapping(format = "yyyy-MM-dd HH:mm:ss")
    private String birthday;

    // 支持 lombok
    // other fields, setters, getters

    public CompanyDetail toCompanyDetail() {
        CompanyDetail company = new CompanyDetail();
        // 使用映射器复制属性值，浅复制
        MAPPER.doForward(this, company);
        // 反向映射，从 company 复制属性到 this
        // MAPPER.doBackward(this, company);
        return company;
    }
}
```

### 三、 常见数据类型的自动转换
`moon-mapper`对常见的数据类型提供了默认的自动转换功能，提供默认类型转换的数据类型有：
1. 基本数据类型，如: `boolean`, `int`, `long`, `float`, `double`等;
2. 基本数据类型对应的包装类，如: `Boolean`, `Character`, `Integer` 等;
3. `java.util`, `java.sql`, `java.time`, `joda-time 2.x` 日期类型之间的互相转换;
4. `java.math.BigDecimal`, `java.math.BigInteger`
5. 枚举;
6. 日期/数字与字符串之间的解析和格式化，使用注解`MappingFormat`;

当然，不同数据类型之间的转换方式各异，也并不是支持所有的转换，比如`BigDecimal`与`Date`之间的转换是不符合日常使用习惯的，这样的转换没有默认提供，类似不符合日常习惯的属性会忽略。

### 四、 基于`setter`方法重载的类型转换器
上面提到了默认的数据类型自动转换功能，但是有些情况下肯定是不能满足使用场景的，
所以另外提供了基于`setter`重载的自定义转换器，由于`java`语言特性，同名不同参数类型的方法可以重载，
当为字段提供另一个不同数据类型的`setter`方法时，这里就相当于提供了一个指定类型的数据转换器，如:
``` java
public class UserEntity {

    // 取得映射器
    static BeanMapper MAPPER = Mapper2.get(UserVO.class, UserEntity.class);

    private Date birthday;

    // 基于 setter 重载的自定义转换器
    // 基本用法中展示的是用 Mapping.format 定义格式化
    // setter重载自定义转换器优先于 Mapping.format、自动类型转换
    public void setBirthday(String birthday) {
        try {
            this.birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(birthday);
        } catch (ParseException e) {
            // 不能抛出非运行时异常
            throw new IllegalStateException(e);
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    // other fields, setters, getters
}

@MapperFor({UserEntity.class})
public class UserVO {

    // 取得映射器，这种简单方式只能在注解有 MapperFor 的类使用
    static BeanMapper MAPPER = Mapper2.thisPrimary();

    private String birthday;

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    // 基于 setter 的转换器
    public void setBirthday(Date birthday) {
        this.birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(birthday);
    }

    // other fields, setters, getters
}
```
**基于`setter`的转换器优先级高于默认类型转换**

### 五、 支持`Spring`注入
`moon-mapper`使用的是静态编译，和手写`setter`、`getter`一样，性能有保证，而且在`Spring`环境里会自动加上`Component`注解，如果所在包能被`Spring`扫描到就能使用`Spring`的一些特性，如`Autowired`

### 六、实现原理
`Mapper2`是基于**静态编译**生成的映射器，这一点`mapstruct`类似，生成的代码和手写`get/set`一样，所以性能上是有保证的。

用`@MapperFor`注册映射器后会生成两个`Copier`分别代表正向和反向的属性复制器，和一个`Mapper`映射器对前两个`Copier`做了简单封装，在`Spring`环境下都会添加注解`@Component`，这样就可以实现自动注入了。

> 提示：<br>每次需要重新编译时记得`Build -> Rebuild Project`(在IDEA中)；<br>另外`mvn clean compile`也会执行这一过程

属性复制是基于`getter`和`setter`方法进行的，所以参与复制的属性必须有`public`修饰的`setter/getter`方法，同时在处理映射关系时，会静态分析每个属性的数据类型，**相同类型的同名属性**可直接双向映射。如果是不同数据类型，**优先**使用基于`setter`重载的自定义转换器，**然后**分析是否可使用一些默认转换，比如数字之间的转换、`Date`和`long`之间的转换、日期/数字格式化等，如果能转换就按转换的规则映射，**否则**就忽略同名不同类型和不同名属性的映射。

### 八、 说明
1. `moon-mapper`是基于`getter`、`setter`重载进行属性复制的，所以要复制的属性可以没有字段，但不能没有`getter`、`setter`，或者如果只有其中一个
那就只能单向映射了；
2. 支持`joda-time 2.x`日期库；
3. 支持`lombok`；
4. 目前总体分成了`mapper`和`processing`两个包，其中`processing`包里的所有内容最好都不要用，将来这个包一定会剥离出去；
