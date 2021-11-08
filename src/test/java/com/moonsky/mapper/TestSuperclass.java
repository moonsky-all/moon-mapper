package com.moonsky.mapper;

import com.moonsky.mapper.util.Convert;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author benshaoye
 */
public class TestSuperclass {

    public static class MethodInfo {

        final String statement;

        MethodInfo(String statement) {this.statement = statement;}

        public String getStatement() {return statement;}
    }

    public static final void printlnConvertTestAll(Class<? extends Convert> convertClass) {
        Map<String, List<String>> infosMap = new HashMap<>();
        String template = "// %s %s = %s.%s(%s);"; // {@link %s#%s(%s)}
        String convertSimpleName = convertClass.getSimpleName();
        for (Method method : convertClass.getDeclaredMethods()) {
            Class<?> returnType = method.getReturnType();
            String returnSimpleClassname = returnType.getSimpleName();
            String uncapitalizeLocalVarName = Introspector.decapitalize(returnSimpleClassname);
            Class<?> parameterClass = method.getParameterTypes()[0];
            String parameterClassname = parameterClass.getCanonicalName();

            String formatted = String.format(template,
                returnSimpleClassname,
                uncapitalizeLocalVarName,
                convertSimpleName,
                method.getName(),
                parameterClassname);
            infosMap.computeIfAbsent(method.getName(), k -> new ArrayList<>()).add(formatted);
        }
        System.out.println(padStart("", 120, '='));
        System.out.println();
        infosMap.forEach((name, infos) -> {
            System.out.println("@Test");
            System.out.println("void test" + capitalize(name) + "() {");
            if (infos.size() > 1) {
                infos.forEach(info -> {
                    System.out.println("    {");
                    System.out.println("        startingOf(\"" + info + "\");");
                    System.out.println("        long var = System.currentTimeMillis();");
                    System.out.println("    }");
                });
            } else {
                infos.forEach(info -> {
                    System.out.println("    startingOf(\"" + info + "\");");
                    System.out.println("    long var = System.currentTimeMillis();");
                });
            }
            System.out.println("}");
            System.out.println();
        });
        System.out.println();
        System.out.println(padStart("", 120, '='));
    }

    public static final String padStart(Object value, int maxLength, char padding) {
        if (value == null) {
            return null;
        }
        String str = value.toString();
        int length = str.length();
        int diff = maxLength - length;
        if (diff > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < diff; i++) {
                builder.append(padding);
            }
            return builder.append(str).toString();
        }
        return str;
    }

    public static final String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static final void startingOf(String mark) {
        System.out.println();
        System.out.println(padStart("", 150, '='));
        System.out.println(padStart("  ", 10, '>') + mark);
        System.out.println();
    }

    public TestSuperclass() {}
}
