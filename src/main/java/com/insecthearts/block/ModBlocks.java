
package com.insecthearts.block;
import com.insecthearts.InsectHeartsMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModBlocks {
    public static final DeferredRegister<net.minecraft.world.level.block.Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, InsectHeartsMod.MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InsectHeartsMod.MODID);
    public static final RegistryObject<net.minecraft.world.level.block.Block> LAVA_DOOR =
            BLOCKS.register("lava_door", LavaDoorBlock::new);
    public static final RegistryObject<Item> LAVA_DOOR_ITEM =
            ITEMS.register("lava_door", () -> new BlockItem(LAVA_DOOR.get(), new Item.Properties()));
}
