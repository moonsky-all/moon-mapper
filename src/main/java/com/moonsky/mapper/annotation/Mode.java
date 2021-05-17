package com.moonsky.mapper.annotation;

/**
 * 模式，处理忽略字段时的模式
 *
 * @author benshaoye
 */
public enum Mode {
    /** 默认，没有 */
    NONE,
    /** 正向 */
    FORWARD,
    /** 反向 */
    BACKWARD,
    /** 双向 */
    ALL
}
