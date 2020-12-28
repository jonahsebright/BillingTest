package com.jonahsebright.billingtest.util;

public class StringUtils {
    public static String iso8601ToNormal(String inIsoFormat) {
        String[] chars = inIsoFormat.split("");
        String unitShort = chars[chars.length - 1];
        String unit = null;
        switch (unitShort) {
            case "Y":
                unit = "year";
                break;
            case "M":
                unit = "month";
                break;
            case "W":
                unit = "week";
        }
        String amount = inIsoFormat.substring(1, chars.length - 1);
        if (!amount.equals("1")) unit += "s";
        return amount + " " + unit;
    }
}
