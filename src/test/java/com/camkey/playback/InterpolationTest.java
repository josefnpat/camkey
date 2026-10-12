package com.camkey.playback;

import com.camkey.keyframe.Keyframe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpolationTest {
    private static final double POSITION_EPSILON = 0.001;
    private static final float ANGLE_EPSILON = 0.01F;

    private static final Keyframe FROM = new Keyframe("a", 1.0, 0.0, 64.0, 0.0, 10.0F, 20.0F, 30.0F, "linear");
    private static final Keyframe TO = new Keyframe("b", 1.0, 10.0, 70.0, -30.0, 0.0F, 90.0F, 0.0F, "linear");

    @Test
    void startMatchesFrom() {
        Transform result = Interpolation.interpolate(FROM, TO, 0.0F);
        assertEquals(FROM.x(), result.x(), POSITION_EPSILON);
        assertEquals(FROM.y(), result.y(), POSITION_EPSILON);
        assertEquals(FROM.z(), result.z(), POSITION_EPSILON);
        assertEquals(FROM.pitch(), result.pitch(), ANGLE_EPSILON);
        assertEquals(FROM.yaw(), result.yaw(), ANGLE_EPSILON);
        assertEquals(FROM.roll(), result.roll(), ANGLE_EPSILON);
    }

    @Test
    void endMatchesTo() {
        Transform result = Interpolation.interpolate(FROM, TO, 1.0F);
        assertEquals(TO.x(), result.x(), POSITION_EPSILON);
        assertEquals(TO.y(), result.y(), POSITION_EPSILON);
        assertEquals(TO.z(), result.z(), POSITION_EPSILON);
        assertEquals(TO.pitch(), result.pitch(), ANGLE_EPSILON);
        assertEquals(TO.yaw(), result.yaw(), ANGLE_EPSILON);
        assertEquals(TO.roll(), result.roll(), ANGLE_EPSILON);
    }

    @Test
    void midpointLerpsPosition() {
        Transform result = Interpolation.interpolate(FROM, TO, 0.5F);
        assertEquals(5.0, result.x(), POSITION_EPSILON);
        assertEquals(67.0, result.y(), POSITION_EPSILON);
        assertEquals(-15.0, result.z(), POSITION_EPSILON);
    }

    @Test
    void midpointSlerpsYaw() {
        Keyframe from = new Keyframe("a", 1.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear");
        Keyframe to = new Keyframe("b", 1.0, 0.0, 0.0, 0.0, 0.0F, 90.0F, 0.0F, "linear");
        Transform result = Interpolation.interpolate(from, to, 0.5F);
        assertEquals(45.0F, result.yaw(), ANGLE_EPSILON);
    }

    @Test
    void midpointSlerpsPitch() {
        Keyframe from = new Keyframe("a", 1.0, 0.0, 0.0, 0.0, 0.0F, 10.0F, 0.0F, "linear");
        Keyframe to = new Keyframe("b", 1.0, 0.0, 0.0, 0.0, 90.0F, 10.0F, 0.0F, "linear");
        Transform result = Interpolation.interpolate(from, to, 0.5F);
        assertEquals(45.0F, result.pitch(), ANGLE_EPSILON);
    }

    @Test
    void midpointSlerpsRoll() {
        Keyframe from = new Keyframe("a", 1.0, 0.0, 0.0, 0.0, 0.0F, 10.0F, 0.0F, "linear");
        Keyframe to = new Keyframe("b", 1.0, 0.0, 0.0, 0.0, 0.0F, 10.0F, 90.0F, "linear");
        Transform result = Interpolation.interpolate(from, to, 0.5F);
        assertEquals(45.0F, result.roll(), ANGLE_EPSILON);
    }

    @Test
    void identicalRotationsAreStable() {
        Keyframe from = new Keyframe("a", 1.0, 0.0, 0.0, 0.0, 12.0F, 34.0F, 56.0F, "linear");
        Keyframe to = new Keyframe("b", 1.0, 0.0, 0.0, 0.0, 12.0F, 34.0F, 56.0F, "linear");
        Transform result = Interpolation.interpolate(from, to, 0.5F);
        assertEquals(12.0F, result.pitch(), ANGLE_EPSILON);
        assertEquals(34.0F, result.yaw(), ANGLE_EPSILON);
        assertEquals(56.0F, result.roll(), ANGLE_EPSILON);
    }
}
