package com.camkey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CamKeyPathsTest {
    @Test
    void appendsJsonExtensionWhenMissing() {
        assertEquals("mysave.json", CamKeyPaths.saveFileName("mysave"));
    }

    @Test
    void keepsExistingJsonExtension() {
        assertEquals("mysave.json", CamKeyPaths.saveFileName("mysave.json"));
    }
}
