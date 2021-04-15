package com.moonsky.mapper;

/**
 * @author benshaoye
 */
public interface BeanCopier<THIS, THAT> {

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
