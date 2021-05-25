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
  <version>0.1</version>
</dependency>
```

### 二、 使用
`moon-mapper`属性值复制是基于两个同名属性的数据复制:
``` java
public class CompanyDetail {
    
    private String name;

    // other fields, setters, getters
}

// 用 MapperFor 注册映射器
@MapperFor({CompanyDetail.class})
public class EnterpriseDetail {

    // 获取映射器，这是一个简便方式
    private static final BeanMapper MAPPER = Mapper2.thisPrimary();

    private String name;

    // other fields, setters, getters

    public CompanyDetail toCompanyDetail() {
        CompanyDetail company = new CompanyDetail();
        // 使用映射器复制属性值，浅复制
        MAPPER.doForward(this, company);
        return company;
    }
}
```

### 三、 常见数据类型的自动转换
`moon-mapper`对常见的数据类型提供了默认的自动转换功能，提供默认类型转换的数据类型有：
1. 基本数据类型，如: `boolean`, `int`, `long`, `float`, `double`等;
2. 基本数据类型对应的包装类，如: `Boolean`, `Character`, `Integer` 等;
3. `java.util`, `java.sql`, `java.time`, `joda-time` 日期类型之间的互相转换;
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
    private static final BeanMapper MAPPER = Mapper2.get(UserVO.class, UserEntity.class);

    private Date birthday;

    // 基于 setter 的转换器
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
    private static final BeanMapper MAPPER = Mapper2.thisPrimary();

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

### 六、 说明
1. `moon-mapper`是基于`getter`、`setter`重载进行属性复制的，所以要复制的属性可以没有字段，但不能没有`getter`、`setter`，或者如果只有其中一个
那就只能单向映射了；
2. `com.moonsky.processing`包下有一些可以使用的类，但是不建议在实际程序中使用，这个包将来会剥离出去单独使用。
