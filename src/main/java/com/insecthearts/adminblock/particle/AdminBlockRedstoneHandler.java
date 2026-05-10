package com.insecthearts.adminblock.particle;

import com.insecthearts.adminblock.AdminBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AdminBlockRedstoneHandler {

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor accessor = event.getLevel();
        if (!(accessor instanceof ServerLevel level)) return;

        BlockPos pos = event.getPos();

        if (level.getBlockState(pos).getBlock()
                != AdminBlockRegistry.ADMIN_COMMAND_BLOCK.get()) return;

        if (!level.hasNeighborSignal(pos)) return;

        level.sendParticles(
                AdminParticles.ADMIN_COMMAND.get(),
                pos.getX() + 0.5,
                pos.getY() + 1.1,
                pos.getZ() + 0.5,
                20,
                0.3, 0.3, 0.3,
                0.0
        );
    }
}
