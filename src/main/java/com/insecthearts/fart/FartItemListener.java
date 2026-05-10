package com.insecthearts.fart;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FartItemListener {

    // External MCCreator item
    private static final ResourceLocation FART_JAR_ID =
            new ResourceLocation("moddd", "fart");

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        if (stack.getItem().builtInRegistryHolder().key().location().equals(FART_JAR_ID)) {
            // enable HOLD usage
            event.getEntity().startUsingItem(event.getHand());
        }
    }
}
