package com.moonsky.mapper;

/**
 * @author benshaoye
 */
public interface BeanCopier<THIS, THAT> {

    /**
     * 实际获取{@code Copier}的方法
     *
     * @param fromClass 复制数据源类
     * @param toClass   目标类
     * @param <F>       源类型
     * @param <T>       目标类型
     *
     * @return 源类型至目标类型的复制器
     */
    static <F, T> BeanCopier<F, T> get(Class<F> fromClass, Class<T> toClass) {
        return Mappers.getCopier(fromClass, toClass);
    }

    /**
     * 属性复制
     * <p>
     * 此方法不会检查{@code thisObject}或{@code thatObject}是否是{@code null}
     * <p>
     * 数据源对象和目标对象应当有 setter/getter 方法，任一方没有就会忽略该属性
     *
     * @param thisObject 属性数据源
     * @param thatObject 属性将要复制到的目标对象
     *
     * @return 目标对象
     */
    default THAT unsafeCopy(THIS thisObject, THAT thatObject) { return thatObject; }

    /**
     * 属性复制
     * <p>
     * 此方法会检查{@code thisObject}或{@code thatObject}是否是{@code null},
     * <p>
     * 其中任一为{@code null}将直接返回{@code null}
     * <p>
     * 数据源对象和目标对象应当有 setter/getter 方法，任一方没有就会忽略该属性
     *
     * @param thisObject 属性数据源
     * @param thatObject 属性将要复制到的目标对象
     *
     * @return 目标对象
     */
    default THAT copy(THIS thisObject, THAT thatObject) {
        return (thisObject == null || thatObject == null) ? thatObject : unsafeCopy(thisObject, thatObject);
    }

    /**
     * 将{@code thisObject}转换为{@code thatObject}
     *
     * @param thisObject 属性数据源对象
     *
     * @return 转换目标对象
     */
    default THAT convert(THIS thisObject) {
        // return thisObject == null ? null :unsafeCopy(thisObject, new <THAT>());
        throw new UnsupportedOperationException("unknown target type of: THAT convert(Object)");
    }
}
