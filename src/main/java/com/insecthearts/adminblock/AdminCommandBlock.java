package com.insecthearts.adminblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class AdminCommandBlock extends Block {

    public AdminCommandBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .strength(5.0F, 1200.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }
}
