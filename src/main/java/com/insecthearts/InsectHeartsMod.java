package com.insecthearts;

import com.insecthearts.block.ModBlocks;
import com.insecthearts.registry.ModItems;
import com.insecthearts.adminblock.AdminBlockRegistry;
import com.insecthearts.adminblock.gui.AdminMenus;
import com.insecthearts.adminblock.handler.AdminBlockRightClickHandler;
import com.insecthearts.adminblock.particle.AdminParticles;
import com.insecthearts.adminblock.particle.AdminBlockRedstoneHandler;
import com.insecthearts.ability.glitchmob.GlitchMobCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(InsectHeartsMod.MODID)
public class InsectHeartsMod {

    public static final String MODID = "insect_hearts";

    public InsectHeartsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModBlocks.ITEMS.register(bus);
        ModItems.register(bus);

        AdminBlockRegistry.register(bus);
        AdminMenus.register(bus);

        AdminParticles.register(bus);

        MinecraftForge.EVENT_BUS.register(AdminBlockRightClickHandler.class);
        MinecraftForge.EVENT_BUS.register(AdminBlockRedstoneHandler.class);
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class CommandRegister {
        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            GlitchMobCommand.register(event.getDispatcher());
        }
    }
}
