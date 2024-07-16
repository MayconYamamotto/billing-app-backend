package br.com.billing.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatterUtils {

    public static String format(String text, Object... args) {
        for (Object arg : args) {
            text = text.replaceFirst("\\{}", arg.toString());
        }
        return text;
    }

}
