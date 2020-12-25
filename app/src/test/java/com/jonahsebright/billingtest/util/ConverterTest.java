package com.jonahsebright.billingtest.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void convert() {
        Converter<Integer, String> converter = new Converter<Integer, String>() {
            @Override
            public String convert(Integer integer) {
                return integer.toString();
            }
        };
        assertEquals("1234", converter.convert(1234));
        Converter<IntegerBox, StringBox> converter2 = new Converter<IntegerBox, StringBox>() {
            @Override
            public StringBox convert(IntegerBox integerBox) {
                return new StringBox(integerBox.integer.toString());
            }
        };
        assertThat(converter2.convert(new IntegerBox(56789)))
                .usingRecursiveComparison()
                .isEqualTo(new StringBox("56789"));
    }

    private static class IntegerBox {
        private Integer integer;

        public IntegerBox(Integer integer) {
            this.integer = integer;
        }
    }

    private static class StringBox {
        private String string;

        public StringBox(String string) {
            this.string = string;
        }
    }

    @Test
    void convertItems() {
        Converter<IntegerBox, StringBox> converter = new Converter<IntegerBox, StringBox>() {
            @Override
            public StringBox convert(IntegerBox integerBox) {
                return new StringBox(integerBox.integer.toString());
            }
        };
        ArrayList<IntegerBox> toConvert = new ArrayList<>(Arrays.asList(
                new IntegerBox(1),
                new IntegerBox(345),
                new IntegerBox(10)
        ));
        ArrayList<StringBox> expected = new ArrayList<>(Arrays.asList(
                new StringBox("1"),
                new StringBox("345"),
                new StringBox("10")
        ));
        assertThat(converter.convertItems(toConvert))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void convertItemsStatic() {
        Converter<IntegerBox, StringBox> converter = new Converter<IntegerBox, StringBox>() {
            @Override
            public StringBox convert(IntegerBox integerBox) {
                return new StringBox(integerBox.integer.toString());
            }
        };
        ArrayList<IntegerBox> toConvert = new ArrayList<>(Arrays.asList(
                new IntegerBox(1),
                new IntegerBox(345),
                new IntegerBox(10)
        ));
        ArrayList<StringBox> expected = new ArrayList<>(Arrays.asList(
                new StringBox("1"),
                new StringBox("345"),
                new StringBox("10")
        ));
        assertThat(Converter.convertItems(toConvert, converter))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}