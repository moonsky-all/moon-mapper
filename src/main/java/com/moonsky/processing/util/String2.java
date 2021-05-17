package com.moonsky.processing.util;

import com.moonsky.processing.wrapper.Import;

import javax.lang.model.element.TypeElement;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public enum String2 {
    ;

    public static String toSpaceString(int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    public static String dotClass(Class<?> cls) { return cls == null ? null : dotClass(cls.getCanonicalName()); }

    public static String dotClass(String classname) { return classname == null ? null : (classname + ".class"); }

    /**
     * 返回字符串中字符总数
     *
     * @param str 源字符串
     *
     * @return 字符串长度，若字符串为 null，将返回 0
     */
    public static int length(CharSequence str) { return str == null ? 0 : str.length(); }

    /**
     * 字符串是否为空
     *
     * @param string 源字符串
     *
     * @return 源字符串是 null 或长度为 0 返回 true，否则返回 false
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 字符串中是否不包含任何非空白字符
     *
     * @param str 源字符串
     *
     * @return 如果源字符串中不包含任何非空白字符则返回 true，否则返回 false
     */
    public static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) { return !isBlank(str); }

    /**
     * 验证字符串中所有字符是否存在符合验证条件的字符
     *
     * @param str    源字符串
     * @param tester 检验器
     *
     * @return 如果源字符串中至少存在一个字符符合验证器规则则返回 true，否则返回 false
     */
    public static boolean isAny(String str, IntPredicate tester) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (tester.test(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串中的所有{@code search}替换为{@code replacement}
     * <p>
     * 由于{@code replacement}中可能仍然存在{@code search}，
     * 所以返回的字符串只确保源字符串的所有{@code search}被替换，
     * 不保证返回的字符串中不在存在{@code search}
     *
     * @param input       源模板字符串
     * @param search      搜索字符串（将要被替换的字符串）
     * @param replacement 替换串（用于替换搜索字符串）
     *
     * @return 替换完成的字符串
     */
    public static String replaceAll(String input, String search, String replacement) {
        if (isEmpty(input) || isEmpty(search) || replacement == null) {
            return input;
        }
        int index = input.indexOf(search);
        if (index == -1) {
            return input;
        }
        int capacity = input.length();
        if (replacement.length() > search.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);
        int idx = 0;
        int searchLen = search.length();
        while (index >= 0) {
            sb.append(input, idx, index);
            sb.append(replacement);
            idx = index + searchLen;
            index = input.indexOf(search, idx);
        }
        sb.append(input, idx, input.length());
        return sb.toString();
    }

    /**
     * 驼峰字符串转连字符号字符串
     *
     * @param str    源驼峰字符串
     * @param hyphen 分隔符
     *
     * @return 由连字符组成的字符串
     */
    public static String camelcaseToHyphen(String str, char hyphen) {
        return camelcaseToHyphen(str, hyphen, true);
    }

    /**
     * 驼峰字符串转连字符号字符串
     *
     * @param str                     源驼峰字符串
     * @param hyphen                  分隔符
     * @param splitContinuousCapitals 是否拆分连续大写字母，如: SSSName ===> s-s-s-name 或者 sss-name
     *
     * @return 由连字符组成的字符串
     */
    public static String camelcaseToHyphen(String str, char hyphen, boolean splitContinuousCapitals) {
        return camelcaseToHyphen(str, hyphen, splitContinuousCapitals, true);
    }

    /**
     * 驼峰字符串转连字符号字符串
     *
     * @param str                     源驼峰字符串
     * @param hyphen                  分隔符
     * @param splitContinuousCapitals 是否拆分连续大写字母，如: SSSName ===> s-s-s-name 或者 sss-name
     * @param startWithLower          是否以小写字母开头，如: SSSName ===> S-S-S-Name 或者 s-s-s-name
     *
     * @return 由连字符组成的字符串
     */
    public static String camelcaseToHyphen(
        String str, char hyphen, boolean splitContinuousCapitals, boolean startWithLower
    ) {
        final int len = str == null ? 0 : str.length();
        if (len == 0) { return str; }
        boolean prevIsUpper = false, thisIsUpper;
        StringBuilder res = new StringBuilder(len + 5);
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (thisIsUpper = Character.isUpperCase(ch)) {
                ch = startWithLower ? Character.toLowerCase(ch) : ch;
                if (i == 0) {
                    res.append(ch);
                } else if (prevIsUpper) {
                    if (splitContinuousCapitals) {
                        res.append(hyphen).append(ch);
                    } else if (Character.isLowerCase(str.charAt(i + 1))) {
                        res.append(hyphen).append(ch);
                    } else {
                        res.append(ch);
                    }
                } else {
                    res.append(hyphen).append(ch);
                }
            } else if (!Character.isWhitespace(ch)) {
                res.append(ch);
            }
            prevIsUpper = thisIsUpper;
        }
        return res.toString();
    }

    /**
     * 返回字符串中第一个单词
     * <p>
     * 返回字符串中第一个连续字母组成的字符串
     * <p>
     * 如果字符串{@code string}中第一个字母是大写，那返回的就是大写，反之全是小写
     * <p>
     * 单词中不包含任何非字母字符
     *
     * @param string 源字符串
     *
     * @return 第一个单词
     */
    public static String firstWord(String string) {
        char[] chars = string.trim().toCharArray();
        StringBuilder builder = new StringBuilder(8);
        Boolean firstUpper = null;
        for (char currChar : chars) {
            if (firstUpper == null) {
                if (Char2.isUpper(currChar)) {
                    firstUpper = Boolean.TRUE;
                } else if (Char2.isLower(currChar)) {
                    firstUpper = Boolean.FALSE;
                } else {
                    continue;
                }
                builder.append(currChar);
            } else if (!Char2.isLetter(currChar)) {
                break;
            } else {
                if (firstUpper && Char2.isUpper(currChar)) {
                    builder.append(currChar);
                } else if (!firstUpper && Char2.isLower(currChar)) {
                    builder.append(currChar);
                } else {
                    break;
                }
            }
        }
        return builder.toString();
    }

    /**
     * 拆分字符串，以指定字符为分割点，拆分字符串
     *
     * @param str       源字符串
     * @param separator 分割字符
     *
     * @return 拆分后的字符串集合
     */
    public static List<String> split(CharSequence str, char separator) {
        List<String> result = new ArrayList<>();
        int length = str == null ? 0 : str.length();
        if (length != 0) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < length; ++i) {
                char ch;
                if ((ch = str.charAt(i)) == separator) {
                    result.add(builder.toString());
                    builder.setLength(0);
                } else {
                    builder.append(ch);
                }
            }

            result.add(builder.toString());
        }
        return result;
    }

    /**
     * 将首字符转为小写，通常 Java 类实例名命名为该类首字母小写后的字符串
     *
     * @param name
     *
     * @return 首字母小写后的字符串
     */
    public static String decapitalize(String name) { return Introspector.decapitalize(name); }

    /**
     * 首字母大写
     *
     * @param name
     *
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 格式化字符串，将模板中的占位符（{}）按顺序替换为指定值的字符串形式，剩余的值
     * <p>
     * 直接追加到最终字符串末尾
     *
     * @param template 字符串模板
     * @param values   有序的待替换值
     *
     * @return 替换完成后的字符串
     */
    public static String format(String template, Object... values) {
        if (template == null) {
            return null;
        }
        if (values == null || values.length == 0) {
            return template;
        }
        int startIdx = 0, idx = 0;
        final int valueLen = values.length, tempLen = template.length();
        StringBuilder builder = new StringBuilder(tempLen);
        for (int at, nextStartAt; ; ) {
            at = template.indexOf("{}", startIdx);
            if (at >= 0) {
                nextStartAt = at + 2;
                builder.append(template, startIdx, at);
                builder.append(values[idx++]);
                if (idx >= valueLen) {
                    return builder.append(template, nextStartAt, tempLen).toString();
                }
                startIdx = nextStartAt;
            } else {
                for (int i = idx; i < valueLen; i++) {
                    builder.append(values[i]);
                }
                return builder.toString();
            }
        }
    }

    /**
     * 格式化可能带有{@link Import}的数据，具体方式参考{@link #format(String, Object...)}
     * <p>
     * 但是会将{@code types}中的{@link Import}对象进行{@code import}转换
     *
     * @param importer
     * @param template
     * @param types
     *
     * @return
     */
    public static String formatImported(Importer importer, String template, Object... types) {
        if (types == null || template == null) {
            return template;
        }
        if (importer == null) {
            return format(template, types);
        }
        Object value;
        Object[] stringify = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            value = types[i];
            if (value instanceof Import<?>) {
                stringify[i] = ((Import<?>) value).toString(importer);
            } else {
                stringify[i] = String.valueOf(value);
            }
        }
        return format(template, stringify);
    }

    public static String typeFormatted(String typeTemplate, Object... types) {
        if (types == null) {
            return typeTemplate;
        }
        Object[] typesStringify = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            Object type = types[i];
            if (type instanceof Class<?>) {
                typesStringify[i] = Element2.getQualifiedName((Class<?>) type);
            } else if (type instanceof TypeElement) {
                typesStringify[i] = Element2.getQualifiedName((TypeElement) type);
            } else {
                typesStringify[i] = String.valueOf(type);
            }
        }
        return String2.format(typeTemplate, typesStringify);
    }

    public static String keyOf(String... keys) { return String.join(":", keys); }

    public static String toGetterName(String field, String type) {
        return ("boolean".equals(type) ? Const2.IS : Const2.GET) + capitalize(field);
    }

    public static String toSetterName(String field) { return Const2.SET + capitalize(field); }

    public static String deleteWhitespace(String value) {
        StringBuilder builder = new StringBuilder();
        for (char ch : value.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
}
