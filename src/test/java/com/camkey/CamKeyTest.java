package com.camkey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CamKeyTest {
    @Test
    void modIdIsCamkey() {
        assertEquals("camkey", CamKey.MOD_ID);
    }
}
