package com.insecthearts.adminblock;

import com.insecthearts.InsectHeartsMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AdminBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, InsectHeartsMod.MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InsectHeartsMod.MODID);

    public static final RegistryObject<Block> ADMIN_COMMAND_BLOCK =
            BLOCKS.register("admin_command_block", AdminCommandBlock::new);

    public static final RegistryObject<Item> ADMIN_COMMAND_BLOCK_ITEM =
            ITEMS.register("admin_command_block",
                    () -> new BlockItem(
                            ADMIN_COMMAND_BLOCK.get(),
                            new Item.Properties().stacksTo(1)
                    ));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
