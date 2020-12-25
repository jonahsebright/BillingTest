package com.jonahsebright.billingtest.util;

import java.util.ArrayList;
import java.util.List;

public interface Converter<From, To> {
    To convert(From from);

    default ArrayList<To> convertItems(List<From> fromArrayList){
        ArrayList<To> converted = new ArrayList<>();
        for (From from : fromArrayList) {
            converted.add(convert(from));
        }
        return converted;
    }

    static <From, To> ArrayList<To> convertItems(List<From> fromArrayList, Converter<From, To> converter){
        ArrayList<To> converted = new ArrayList<>();
        for (From from : fromArrayList) {
            converted.add(converter.convert(from));
        }
        return converted;
    }
}
