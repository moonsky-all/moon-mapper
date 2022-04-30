package com.moonsky.mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author benshaoye
 */
public interface BeanCloner<R> {

    /**
     * 将{@code source}拷贝到{@code target}里
     * <p>
     * 不会判断数据源和复制目标为 null 的情况
     *
     * @param source 数据来源
     * @param target 复制目标
     *
     * @return 目标对象
     *
     * @see #copy(Object, Object) 会判断数据源和复制目标为 null 的情况
     */
    R unsafeCopy(R source, R target);

    /**
     * 浅拷贝
     *
     * @param record 记录
     *
     * @return 拷贝后的记录
     */
    default R clone(R record) {
        // return record == null ? null : unsafeAssign(record, new <R>());
        throw new UnsupportedOperationException("unknown target type of: THAT clone(Object)");
    }

    /**
     * 将{@code source}拷贝到{@code target}里
     * <p>
     * 会判断数据源和复制目标为 null 的情况
     *
     * @param source 数据来源
     * @param target 复制目标
     *
     * @return 目标对象
     *
     * @see #unsafeCopy(Object, Object) 不会判断数据源和复制目标为 null 的情况
     */
    default R copy(R source, R target) {
        return source == null || target == null ? target : unsafeCopy(source, target);
    }

    /**
     * 浅拷贝所有项
     *
     * @param data 数据
     *
     * @return 复制后的集合数据
     */
    default List<? extends R> cloneAll(Collection<R> data) {
        return Mapper2.doMappingAll(data, this::clone);
    }
}
