package com.insecthearts.adminblock.handler;

import com.insecthearts.InsectHeartsMod;
import com.insecthearts.adminblock.AdminBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = InsectHeartsMod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class AdminBlockRedstoneVanillaParticle {

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        Level level = (Level) event.getLevel();
        if (level.isClientSide()) return;

        BlockPos changedPos = event.getPos();
        ServerLevel server = (ServerLevel) level;

        for (BlockPos nearby : BlockPos.betweenClosed(
                changedPos.offset(-1, -1, -1),
                changedPos.offset(1, 1, 1)
        )) {
            if (level.getBlockState(nearby).getBlock()
                    != AdminBlockRegistry.ADMIN_COMMAND_BLOCK.get()) continue;

            if (!level.hasNeighborSignal(nearby)) continue;

            double cx = nearby.getX() + 0.5;
            double cy = nearby.getY() + 1.0;
            double cz = nearby.getZ() + 0.5;

            server.sendParticles(
                    ParticleTypes.PORTAL,
                    cx, cy + 0.3, cz,
                    160,
                    1.2, 0.4, 1.2,
                    0.02
            );

            server.sendParticles(
                    ParticleTypes.REVERSE_PORTAL,
                    cx, cy + 0.6, cz,
                    120,
                    0.6, 0.6, 0.6,
                    0.0
            );

            server.sendParticles(
                    ParticleTypes.END_ROD,
                    cx, cy + 0.8, cz,
                    70,
                    0.5, 0.7, 0.5,
                    0.12
            );

            server.sendParticles(
                    ParticleTypes.CLOUD,
                    cx, cy + 0.2, cz,
                    50,
                    0.9, 0.1, 0.9,
                    0.01
            );

            server.sendParticles(
                    ParticleTypes.FLASH,
                    cx, cy + 0.6, cz,
                    1,
                    0, 0, 0,
                    0
            );

            server.playSound(
                    null, // all nearby players
                    nearby,
                    SoundEvents.BEACON_AMBIENT,
                    SoundSource.BLOCKS,
                    0.9f,   // volume
                    0.6f    // pitch (deep, heavy)
            );

            server.playSound(
                    null,
                    nearby,
                    SoundEvents.RESPAWN_ANCHOR_CHARGE,
                    SoundSource.BLOCKS,
                    0.6f,
                    0.8f
            );
        }
    }
}
