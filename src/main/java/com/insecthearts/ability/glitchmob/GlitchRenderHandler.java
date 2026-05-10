package com.insecthearts.ability.glitchmob;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "insect_hearts", value = Dist.CLIENT)
public class GlitchRenderHandler {

    private static final String[] TEAMS = {
            "glitch_red",
            "glitch_blue",
            "glitch_pink",
            "glitch_white"
    };

    @SubscribeEvent
    public static void onRender(RenderLivingEvent.Pre<?, ?> event) {
        if (Minecraft.getInstance().level == null) return;
        if (!GlitchMobTracker.isGlitched(event.getEntity().getUUID())) return;

        long t = Minecraft.getInstance().level.getGameTime();

        if (t % 10 == 0) {
            event.setCanceled(true);
            return;
        }

        float phase = Mth.sin((t % 100) / 100f * Mth.TWO_PI);
        float sx = 1.0f + phase * 1.8f;
        float sy = 1.0f - phase * 0.8f;
        float sz = 1.0f + phase * 1.8f;
        event.getPoseStack().scale(sx, sy, sz);

        if (t % 35 == 0) {
            event.getPoseStack().mulPose(Axis.YP.rotationDegrees((t * 11) % 360));
        }

        Scoreboard board = Minecraft.getInstance().level.getScoreboard();
        int idx = (int) ((t / 8) % TEAMS.length);
        String teamName = TEAMS[idx];

        PlayerTeam team = board.getPlayerTeam(teamName);
        if (team == null) {
            team = board.addPlayerTeam(teamName);
            switch (teamName) {
                case "glitch_red" -> team.setColor(net.minecraft.ChatFormatting.RED);
                case "glitch_blue" -> team.setColor(net.minecraft.ChatFormatting.AQUA);
                case "glitch_pink" -> team.setColor(net.minecraft.ChatFormatting.LIGHT_PURPLE);
                case "glitch_white" -> team.setColor(net.minecraft.ChatFormatting.WHITE);
            }
        }

        board.addPlayerToTeam(event.getEntity().getStringUUID(), team);
        event.getEntity().setGlowingTag(t % 6 < 3);

        event.getEntity().hurtTime = 1;
    }
}
