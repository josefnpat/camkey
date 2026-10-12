package com.camkey.playback;

import com.camkey.keyframe.Keyframe;
import com.camkey.tween.OutQuadTween;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaybackTest {
    private static final double EPSILON = 0.001;
    private static final double TICK = 0.05;

    private static final List<Keyframe> KEYFRAMES = List.of(
        new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
        new Keyframe("b", 2.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear")
    );

    @Test
    void playsFromFirstToLast() {
        Playback playback = new Playback();
        playback.start(KEYFRAMES, 0, Playback.NO_LIMIT);
        assertTrue(playback.playing());
        assertEquals(0.0, playback.transform().x(), EPSILON);

        tick(playback, 20);
        assertEquals(5.0, playback.transform().x(), EPSILON);
        assertTrue(playback.playing());

        tick(playback, 20);
        assertFalse(playback.playing());
        assertEquals(10.0, playback.transform().x(), EPSILON);
    }

    @Test
    void extraSecondsInterpolatesWithinSegment() {
        Playback playback = new Playback();
        playback.start(KEYFRAMES, 0, Playback.NO_LIMIT);
        assertEquals(5.0, playback.transform(1.0).x(), EPSILON);
    }

    @Test
    void limitStopsPlaybackEarly() {
        Playback playback = new Playback();
        playback.start(
            List.of(
                new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("b", 10.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear")
            ),
            0,
            1.0
        );
        tick(playback, 25);
        assertFalse(playback.playing());
    }

    @Test
    void stopEndsPlayback() {
        Playback playback = new Playback();
        playback.start(KEYFRAMES, 0, Playback.NO_LIMIT);
        playback.stop();
        assertFalse(playback.playing());
    }

    @Test
    void startingAtLastKeyframeHasNothingToPlay() {
        Playback playback = new Playback();
        playback.start(KEYFRAMES, 1, Playback.NO_LIMIT);
        assertFalse(playback.playing());
    }

    @Test
    void playsMultipleSegments() {
        Playback playback = new Playback();
        playback.start(
            List.of(
                new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("b", 1.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("c", 1.0, 10.0, 10.0, 0.0, 0.0F, 0.0F, 0.0F, "linear")
            ),
            0,
            Playback.NO_LIMIT
        );
        tick(playback, 10);
        assertEquals(5.0, playback.transform().x(), EPSILON);
        tick(playback, 10);
        assertEquals(0.0, playback.transform().y(), EPSILON);
        tick(playback, 10);
        assertEquals(5.0, playback.transform().y(), EPSILON);
        tick(playback, 10);
        assertFalse(playback.playing());
        assertEquals(10.0, playback.transform().y(), EPSILON);
    }

    @Test
    void noneTweenHoldsUntilSegmentEnd() {
        Playback playback = new Playback();
        playback.start(
            List.of(
                new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("b", 1.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "none")
            ),
            0,
            Playback.NO_LIMIT
        );
        tick(playback, 10);
        assertEquals(0.0, playback.transform().x(), EPSILON);
        tick(playback, 10);
        assertEquals(10.0, playback.transform().x(), EPSILON);
    }

    @Test
    void startsAtMiddleKeyframe() {
        Playback playback = new Playback();
        playback.start(
            List.of(
                new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("b", 1.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("c", 2.0, 20.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear")
            ),
            1,
            Playback.NO_LIMIT
        );
        assertTrue(playback.playing());
        tick(playback, 20);
        assertEquals(15.0, playback.transform().x(), EPSILON);
    }

    @Test
    void transformClampsToLastKeyframePastEnd() {
        Playback playback = new Playback();
        playback.start(KEYFRAMES, 0, Playback.NO_LIMIT);
        assertEquals(10.0, playback.transform(100.0).x(), EPSILON);
    }

    @Test
    void appliesDestinationTween() {
        Playback playback = new Playback();
        playback.start(
            List.of(
                new Keyframe("a", 0.0, 0.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, "linear"),
                new Keyframe("b", 2.0, 10.0, 0.0, 0.0, 0.0F, 0.0F, 0.0F, OutQuadTween.NAME)
            ),
            0,
            Playback.NO_LIMIT
        );
        tick(playback, 20);
        assertEquals(7.5, playback.transform().x(), EPSILON);
    }

    private static void tick(Playback playback, int ticks) {
        for (int index = 0; index < ticks; index++) {
            playback.tick(TICK);
        }
    }
}
