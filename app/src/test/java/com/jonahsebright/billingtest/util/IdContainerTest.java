package com.jonahsebright.billingtest.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IdContainerTest {
    @Test
    public void canGetIdOfIdContainer() throws Exception {
        IdContainer idContainer = new IdContainer() {
            @Override
            public String getProductId() {
                return "hello";
            }
        };
        assertEquals("hello", idContainer.getProductId());
    }


    @Test
    public void canFindPositionOfIdContainerInListOfIdContainersById() throws Exception {
        IdContainer idContainer1 = () -> "hello1";
        IdContainer idContainer2 = () -> "hello2";
        IdContainer idContainer3 = () -> "world1";
        IdContainer idContainer4 = () -> "world2";
        IdContainer idContainer5 = new ImplementingIdContainer("hello world");
        ArrayList<IdContainer> idContainers = new ArrayList<>(Arrays.asList(
                idContainer1,
                idContainer2,
                idContainer3,
                idContainer4,
                idContainer5
        ));
        assertEquals(-1, IdContainer.findPositionOf("abcde", idContainers));
        assertEquals(1, IdContainer.findPositionOf("hello2", idContainers));
        assertEquals(3, IdContainer.findPositionOf("world2", idContainers));
        assertEquals(4, IdContainer.findPositionOf("hello world", idContainers));
    }

    @Test
    public void canFindIdContainerInListOfIdContainersById() throws Exception {
        IdContainer idContainer1 = () -> "hello1";
        IdContainer idContainer2 = () -> "hello2";
        IdContainer idContainer3 = () -> "world1";
        IdContainer idContainer4 = () -> "world2";
        IdContainer idContainer5 = new ImplementingIdContainer("hello world");
        ArrayList<IdContainer> idContainers = new ArrayList<>(Arrays.asList(
                idContainer1,
                idContainer2,
                idContainer3,
                idContainer4,
                idContainer5
        ));
        assertNull(IdContainer.findItem("abcde", idContainers));
        assertEquals(idContainer2, IdContainer.findItem("hello2", idContainers));
        assertEquals(idContainer4, IdContainer.findItem("world2", idContainers));
        assertEquals(idContainer5, IdContainer.findItem("hello world", idContainers));
    }

    private static class ImplementingIdContainer implements IdContainer {
        private String id;
        private int i;

        public ImplementingIdContainer(String id) {
            this.id = id;
            i = 1234;
        }

        @Override
        public String getProductId() {
            return id;
        }
    }

}