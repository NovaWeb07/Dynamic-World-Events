package com.insecthearts.adminblock.logic;

import com.insecthearts.adminblock.AdminBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(modid = "insect_hearts")
public class AdminBlockRedstoneHandler {

    private static int tick = 0;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tick++;
        if (tick % 5 != 0) return;

        for (ServerLevel level : event.getServer().getAllLevels()) {
            for (BlockPos pos : BlockPos.betweenClosed(
                    level.getSharedSpawnPos().offset(-48, -8, -48),
                    level.getSharedSpawnPos().offset(48, 8, 48))) {

                BlockState state = level.getBlockState(pos);
                if (state.getBlock() == AdminBlockRegistry.ADMIN_COMMAND_BLOCK.get()
                        && level.hasNeighborSignal(pos)) {

                    RandomSource r = level.random;

                    DustParticleOptions dust =
                            new DustParticleOptions(new Vector3f(1.0F, 0.2F, 0.2F), 3.5F);

                    level.sendParticles(
                            dust,
                            pos.getX() + 0.5,
                            pos.getY() + 1.0,
                            pos.getZ() + 0.5,
                            12,
                            0.35,
                            0.35,
                            0.35,
                            0.0
                    );
                }
            }
        }
    }
}
