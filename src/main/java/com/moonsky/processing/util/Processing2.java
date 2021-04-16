package com.moonsky.processing.util;

import com.moonsky.processing.holder.Holders;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author benshaoye
 */
public enum Processing2 {
    ;

    public static Elements getUtils() { return Holders.INSTANCE.getUtils(); }

    public static Types getTypes() { return Holders.INSTANCE.getTypes(); }
}
