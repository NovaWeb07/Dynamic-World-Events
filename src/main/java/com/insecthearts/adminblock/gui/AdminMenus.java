package com.insecthearts.adminblock.gui;


import com.insecthearts.InsectHeartsMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AdminMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, InsectHeartsMod.MODID);

    public static final RegistryObject<MenuType<AdminCommandMenu>> ADMIN_COMMAND_MENU =
            MENUS.register(
                    "admin_command_menu",
                    () -> IForgeMenuType.create(AdminCommandMenu::new)
            );

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
