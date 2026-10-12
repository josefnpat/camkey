package com.camkey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CamKeyTest {
    @Test
    void modIdIsCamkey() {
        assertEquals("camkey", CamKey.MOD_ID);
    }

    @Test
    void parsesVersionIntoNumericParts() {
        assertArrayEquals(new int[]{1, 0, 0}, CamKey.parseVersion("1.0.0"));
        assertArrayEquals(new int[]{2, 3, 4}, CamKey.parseVersion("2.3.4"));
    }
}
