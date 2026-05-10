package com.insecthearts.ability.glitchmob;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GlitchMobTracker {

    private static final Set<UUID> GLITCHED = new HashSet<>();

    public static void add(UUID id) {
        GLITCHED.add(id);
    }

    public static boolean isGlitched(UUID id) {
        return GLITCHED.contains(id);
    }

    public static void remove(UUID id) {
        GLITCHED.remove(id);
    }
}
