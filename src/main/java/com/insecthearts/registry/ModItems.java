package com.insecthearts.registry;


import com.insecthearts.InsectHeartsMod;
import com.insecthearts.item.CampPasteItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, InsectHeartsMod.MODID);

    public static final RegistryObject<Item> CAMP_PASTE_RED =
            ITEMS.register("camp_paste_red", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_BLUE =
            ITEMS.register("camp_paste_blue", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_GREEN =
            ITEMS.register("camp_paste_green", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_YELLOW =
            ITEMS.register("camp_paste_yellow", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_LIME =
            ITEMS.register("camp_paste_lime", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_CYAN =
            ITEMS.register("camp_paste_cyan", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_PURPLE =
            ITEMS.register("camp_paste_purple", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_MAGENTA =
            ITEMS.register("camp_paste_magenta", CampPasteItem::new);

    public static final RegistryObject<Item> CAMP_PASTE_ORANGE =
            ITEMS.register("camp_paste_orange", CampPasteItem::new);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
