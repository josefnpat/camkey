package com.camkey.command;

import com.camkey.tween.LinearTween;
import com.camkey.tween.Tween;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TweenArgumentTypeTest {
    @Test
    void parsesRegisteredTween() throws CommandSyntaxException {
        Tween tween = TweenArgumentType.tween().parse(new StringReader(LinearTween.NAME));
        assertEquals(LinearTween.NAME, tween.name());
    }

    @Test
    void rejectsUnknownTween() {
        StringReader reader = new StringReader("bogus");
        assertThrows(CommandSyntaxException.class, () -> TweenArgumentType.tween().parse(reader));
    }
}
