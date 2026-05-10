package com.insecthearts.ability.glitchmob;

import java.util.Random;

public class GlitchEffects {

    private static final Random RAND = new Random();

    public static boolean blink() {
        return RAND.nextFloat() < 0.4f;
    }

    public static float scale() {
        return 0.4f + RAND.nextFloat() * 1.4f;
    }

    public static float rot() {
        return RAND.nextInt(360);
    }
}
