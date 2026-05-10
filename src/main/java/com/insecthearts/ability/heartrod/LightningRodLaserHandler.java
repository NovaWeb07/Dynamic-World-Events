package com.insecthearts.ability.heartrod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = "insect_hearts")
public class LightningRodLaserHandler {

    private static final double MOB_RANGE = 8.0;
    private static final float DPS = 6.0F; // real damage per second (smooth)
    private static final int TICK_RATE = 5; // every 5 ticks

    private static int tickDivider = 0;
    private static float spinTick = 0f;

    private static final DustParticleOptions RED_GLOW =
            new DustParticleOptions(new Vector3f(1.0f, 0.15f, 0.15f), 1.2f);

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;

        tickDivider++;
        spinTick += 0.18f;
        if (tickDivider < TICK_RATE) return;
        tickDivider = 0;

        float damageThisTick = DPS * (TICK_RATE / 20f); // smooth DPS

        for (Player player : level.players()) {
            BlockPos center = player.blockPosition();

            for (BlockPos pos : BlockPos.betweenClosed(
                    center.offset(-64, -32, -64),
                    center.offset(64, 32, 64))) {

                BlockState state = level.getBlockState(pos);
                if (state.getBlock() != Blocks.LIGHTNING_ROD) continue;

                double baseX = pos.getX() + 0.5;
                double baseY = pos.getY() - 0.15;
                double baseZ = pos.getZ() + 0.5;

                double r = 0.18;
                double ox = Mth.cos(spinTick) * r;
                double oz = Mth.sin(spinTick) * r;

                level.sendParticles(
                        ParticleTypes.HEART,
                        baseX + ox, baseY, baseZ + oz,
                        1, 0, 0, 0, 0
                );

                AABB box = new AABB(pos).inflate(MOB_RANGE);
                List<LivingEntity> mobs = level.getEntitiesOfClass(
                        LivingEntity.class,
                        box,
                        e -> e.isAlive() && !(e instanceof Player)
                );
                if (mobs.isEmpty()) continue;

                level.playSound(
                        null, pos,
                        SoundEvents.BEACON_POWER_SELECT,
                        SoundSource.BLOCKS,
                        0.5f, 1.6f
                );

                Vec3 start = new Vec3(baseX, baseY, baseZ);

                for (LivingEntity mob : mobs) {

                    float hp = mob.getHealth();
                    if (hp > 0.0F) {
                        mob.setHealth(Math.max(0.0F, hp - damageThisTick));
                    }

                    Vec3 end = mob.position().add(0, mob.getBbHeight() * 0.5, 0);
                    Vec3 diff = end.subtract(start);

                    int steps = 14;
                    for (int i = 0; i <= steps; i++) {
                        double t = i / (double) steps;
                        double bend = Math.sin(t * Math.PI) * 0.35;

                        Vec3 p = start.add(diff.scale(t))
                                .add(
                                        Mth.sin((float) (t * Math.PI)) * bend,
                                        0,
                                        Mth.cos((float) (t * Math.PI)) * bend
                                );

                        level.sendParticles(
                                ParticleTypes.ELECTRIC_SPARK,
                                p.x, p.y, p.z,
                                1, 0, 0, 0, 0
                        );
                        level.sendParticles(
                                RED_GLOW,
                                p.x, p.y, p.z,
                                1, 0, 0, 0, 0
                        );
                    }
                }
            }
        }
    }
}
