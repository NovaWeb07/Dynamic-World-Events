package com.insecthearts.logic;

import com.insecthearts.block.ModBlocks;
import com.insecthearts.client.LavaDoorKeybind;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "insect_hearts", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LavaDoorLogic {

    private static BlockPos CENTER = null;
    private static boolean vertical = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;

        if (player == null || level == null) return;

        if (LavaDoorKeybind.SPAWN_FLAT.consumeClick()) {
            if (mc.hitResult == null) return;

            CENTER = BlockPos.containing(mc.hitResult.getLocation());
            vertical = false;
            spawnFlat(level, CENTER);
            playOpen(player);
        }

        if (LavaDoorKeybind.TOGGLE.consumeClick()) {
            if (CENTER == null) return;

            if (vertical) {
                clearVertical(level, CENTER);
                spawnFlat(level, CENTER);
                playClose(player);
            } else {
                clearFlat(level, CENTER);
                spawnVertical(level, CENTER);
                playOpen(player);
            }
            vertical = !vertical;
        }
    }

    private static void spawnFlat(Level level, BlockPos c) {
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++)
                level.setBlock(c.offset(x, 0, z),
                        ModBlocks.LAVA_DOOR.get().defaultBlockState(), 3);
    }

    private static void spawnVertical(Level level, BlockPos c) {
        for (int y = 0; y < 3; y++)
            for (int x = -1; x <= 1; x++)
                level.setBlock(c.offset(x, y, 0),
                        ModBlocks.LAVA_DOOR.get().defaultBlockState(), 3);
    }

    private static void clearFlat(Level level, BlockPos c) {
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++)
                level.removeBlock(c.offset(x, 0, z), false);
    }

    private static void clearVertical(Level level, BlockPos c) {
        for (int y = 0; y < 3; y++)
            for (int x = -1; x <= 1; x++)
                level.removeBlock(c.offset(x, y, 0), false);
    }

    private static void playOpen(Player p) {
        p.level().playSound(p, p.blockPosition(),
                SoundEvents.IRON_TRAPDOOR_OPEN,
                SoundSource.PLAYERS, 1f, 0.9f);
    }

    private static void playClose(Player p) {
        p.level().playSound(p, p.blockPosition(),
                SoundEvents.IRON_TRAPDOOR_CLOSE,
                SoundSource.PLAYERS, 1f, 0.8f);
    }
}
