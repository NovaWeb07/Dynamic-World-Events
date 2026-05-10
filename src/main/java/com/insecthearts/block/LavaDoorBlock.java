
package com.insecthearts.block;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
public class LavaDoorBlock extends Block {
    public LavaDoorBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(3.0f).noOcclusion().lightLevel(s -> 15));
    }
}
