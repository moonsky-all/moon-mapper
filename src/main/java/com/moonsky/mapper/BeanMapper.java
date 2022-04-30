package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;

import java.util.Collection;
import java.util.List;

/**
 * @author benshaoye
 */
public interface BeanMapper<THIS, THAT> {

    /**
     * 获取指定{@code fromClass}至{@code toClass}之间的映射器
     *
     * @param fromClass 复制数据源类（被{@link MapperFor}注解的类）
     * @param toClass   映射目标类
     * @param <F>       当前类
     * @param <T>       目标类
     *
     * @return 指定类之间的映射器
     */
    static <F, T> BeanMapper<F, T> get(Class<F> fromClass, Class<T> toClass) {
        return Mappers.getMapper(fromClass, toClass);
    }

    /**
     * null 不安全的属性映射，不会对{@code thisObject}和{@code thatObject}进行{@code null}检查
     * <p>
     * 将当前对象{@code thisObject}按属性名映射到另一个对象{@code thatObject}
     *
     * @param thisObject 属性数据源对象
     * @param thatObject 属性映射目标对象
     *
     * @return 设置属性后的目标对象 {@code thatObject}
     */
    default THAT unsafeForward(THIS thisObject, THAT thatObject) {return thatObject;}

    /**
     * null 不安全的属性映射，不会对{@code thisObject}和{@code thatObject}进行{@code null}检查
     * <p>
     * 从目标属性数据源{@code thatObject}按属性名称复制到当前对象{@code thisObject}
     *
     * @param thisObject 当前对象，属性映射目标对象
     * @param thatObject 属性数据源对象
     *
     * @return 当前对象 {@code thisObject}
     */
    default THIS unsafeBackward(THIS thisObject, THAT thatObject) {return thisObject;}

    /**
     * 将当前对象{@code thisObject}按属性名映射到另一个对象{@code thatObject}
     *
     * @param thisObject 属性数据源对象
     * @param thatObject 属性映射目标对象
     *
     * @return 设置属性后的目标对象 {@code thatObject}
     */
    default THAT doForward(THIS thisObject, THAT thatObject) {
        return (thisObject == null || thatObject == null) ? thatObject : unsafeForward(thisObject, thatObject);
    }

    /**
     * 从目标属性数据源{@code thatObject}按属性名称复制到当前对象{@code thisObject}
     *
     * @param thisObject 当前对象，属性映射目标对象
     * @param thatObject 属性数据源对象
     *
     * @return 当前对象 {@code thisObject}
     */
    default THIS doBackward(THIS thisObject, THAT thatObject) {
        return (thisObject == null || thatObject == null) ? thisObject : unsafeBackward(thisObject, thatObject);
    }

    /**
     * 以当前类为数据源，构造并复制属性到目标对象
     *
     * @param thisObject 当前类实例，属性数据源
     *
     * @return {@code THAT}类实例
     *
     * @see #unsafeForward(Object, Object)
     */
    default THAT doForward(THIS thisObject) {
        // return thisObject == null ? null : unsafeForward(thisObject, new <THAT>());
        throw new UnsupportedOperationException("unknown target type of: doForward(Object, Object)");
    }

    /**
     * 以对象{@code thatObject}为数据源，构造并复制属性到当前类实例对象
     *
     * @param thatObject 属性数据源对象
     *
     * @return {@code THIS}类实例
     *
     * @see #unsafeBackward(Object, Object)
     */
    default THIS doBackward(THAT thatObject) {
        // return thatObject == null ? null : unsafeBackward(new <THIS>(), thatObject);
        throw new UnsupportedOperationException("unknown target type of: doBackward(Object, Object)");
    }

    /**
     * 正向映射{@code thisObject}数据列表为{@code THAT}数据列表
     *
     * @param collection 数据列表
     *
     * @return 转换后的数据列表
     */
    default List<? extends THAT> doForwardAll(Collection<THIS> collection) {
        return Mapper2.doMappingAll(collection, this::doForward);
    }

    /**
     * 反向映射{@code thatObject}数据列表为{@code THIS}数据列表
     *
     * @param collection 数据列表
     *
     * @return 转换后的数据列表
     */
    default List<? extends THIS> doBackwardAll(Collection<THAT> collection) {
        return Mapper2.doMappingAll(collection, this::doBackward);
    }
}
