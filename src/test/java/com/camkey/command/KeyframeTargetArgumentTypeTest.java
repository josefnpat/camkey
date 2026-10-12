package com.camkey.command;

import com.camkey.keyframe.Keyframes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyframeTargetArgumentTypeTest {
    @Test
    void parsesPlainName() throws CommandSyntaxException {
        assertEquals("100_tree", parse("100_tree"));
    }

    @Test
    void parsesReservedTargets() throws CommandSyntaxException {
        assertEquals(Keyframes.START, parse(Keyframes.START));
        assertEquals(Keyframes.END, parse(Keyframes.END));
    }

    @Test
    void stopsAtWhitespace() throws CommandSyntaxException {
        StringReader reader = new StringReader("a b");
        assertEquals("a", KeyframeTargetArgumentType.target().parse(reader));
        assertEquals(1, reader.getCursor());
    }

    @Test
    void rejectsEmptyInput() {
        StringReader reader = new StringReader("");
        assertThrows(CommandSyntaxException.class, () -> KeyframeTargetArgumentType.target().parse(reader));
    }

    @Test
    void rejectsLeadingWhitespace() {
        StringReader reader = new StringReader(" a");
        assertThrows(CommandSyntaxException.class, () -> KeyframeTargetArgumentType.target().parse(reader));
    }

    private static String parse(String input) throws CommandSyntaxException {
        return KeyframeTargetArgumentType.target().parse(new StringReader(input));
    }
}
