package com.insecthearts.fart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(
        modid = "insect_hearts",
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public class FartJetHandler {

    private static final ResourceLocation FART_JAR_ID =
            new ResourceLocation("moddd", "fart");

    // sound ids
    private static final ResourceLocation FART_START_ID =
            new ResourceLocation("insect_hearts", "fart_start");
    private static final ResourceLocation FART_LOOP_ID =
            new ResourceLocation("insect_hearts", "fart");

    private static final SoundEvent FART_START =
            SoundEvent.createVariableRangeEvent(FART_START_ID);
    private static final SoundEvent FART_LOOP =
            SoundEvent.createVariableRangeEvent(FART_LOOP_ID);

    private static boolean wasHolding = false;
    private static int holdTicks = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        SoundManager soundManager = mc.getSoundManager();

        boolean holding =
                mc.options.keyUse.isDown()
                        && !player.getMainHandItem().isEmpty()
                        && player.getMainHandItem().getItem()
                        .builtInRegistryHolder().key().location().equals(FART_JAR_ID);

        if (!holding) {
            if (wasHolding) {
                soundManager.stop(FART_LOOP_ID, SoundSource.PLAYERS);
                soundManager.stop(FART_START_ID, SoundSource.PLAYERS);
            }
            wasHolding = false;
            holdTicks = 0;
            return;
        }

        holdTicks++;

        if (!wasHolding) {
            player.level().playSound(
                    player,
                    player.blockPosition(),
                    FART_START,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
            );
            wasHolding = true;
        }

        if (holdTicks > 8 && player.tickCount % 18 == 0) {
            player.level().playSound(
                    player,
                    player.blockPosition(),
                    FART_LOOP,
                    SoundSource.PLAYERS,
                    0.9f,
                    0.9f + player.getRandom().nextFloat() * 0.3f
            );
        }

        player.setPose(Pose.FALL_FLYING);

        Vec3 look = player.getLookAngle().normalize();
        float ramp = Mth.clamp(holdTicks / 120f, 0f, 1f);
        double speed = 0.35 + 0.25 * ramp;

        Vec3 thrust = new Vec3(
                look.x * speed,
                Mth.clamp(look.y * speed + 0.18, -0.1, 0.6),
                look.z * speed
        );

        Vec3 next = player.getDeltaMovement().scale(0.85).add(thrust);

        if (player.onGround() && next.y < 0) {
            next = new Vec3(next.x, 0.05, next.z);
        }

        player.setDeltaMovement(next);
        player.hurtMarked = true;

        for (int i = 0; i < 8; i++) {
            player.level().addParticle(
                    new DustParticleOptions(new Vector3f(0.3f, 1f, 0.3f), 1.3f),
                    player.getX() + (player.getRandom().nextDouble() - 0.5) * 0.3,
                    player.getY() - 0.45,
                    player.getZ() + (player.getRandom().nextDouble() - 0.5) * 0.3,
                    (player.getRandom().nextDouble() - 0.5) * 0.05,
                    -0.1,
                    (player.getRandom().nextDouble() - 0.5) * 0.05
            );
        }

        if (holdTicks > 100) {
            float shake = Mth.clamp((holdTicks - 100) / 60f, 0f, 2.5f);
            player.setYRot(player.getYRot()
                    + (player.getRandom().nextFloat() - 0.5f) * shake);
            player.setXRot(player.getXRot()
                    + (player.getRandom().nextFloat() - 0.5f) * shake);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == player
                && mc.options.keyUse.isDown()
                && !player.getMainHandItem().isEmpty()
                && player.getMainHandItem().getItem()
                .builtInRegistryHolder().key().location().equals(FART_JAR_ID)) {
            event.setCanceled(true);
        }
    }
}
