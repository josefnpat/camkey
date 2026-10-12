package com.camkey.command;

import com.camkey.keyframe.Keyframe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyframeCommandTest {
    @Test
    void formatsTimeAndTransformWithOneDecimal() {
        Keyframe keyframe = new Keyframe("a", 1.0, 0.5, 64.0, -3.2, 10.0F, 90.0F, 0.0F, "linear");
        assertEquals("1.0 [0.5,64.0,-3.2] @ [10.0,90.0,0.0]", KeyframeCommand.formatTransform(keyframe));
    }

    @Test
    void formatsZeroTimeAndRoundsToTenth() {
        Keyframe keyframe = new Keyframe("a", 0.0, 1.26, 64.0, -3.24, 0.0F, 0.0F, 0.0F, "linear");
        assertEquals("0.0 [1.3,64.0,-3.2] @ [0.0,0.0,0.0]", KeyframeCommand.formatTransform(keyframe));
    }
}
