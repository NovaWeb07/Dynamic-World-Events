package com.insecthearts.adminblock.handler;


import com.insecthearts.adminblock.AdminBlockRegistry;
import com.insecthearts.adminblock.gui.AdminCommandMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

@Mod.EventBusSubscriber(modid = "insect_hearts")
public class AdminBlockRightClickHandler {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {

        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (event.getLevel().isClientSide()) return;

        if (event.getLevel().getBlockState(event.getPos()).getBlock()
                != AdminBlockRegistry.ADMIN_COMMAND_BLOCK.get()) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();

        MenuProvider provider = new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("ADMIN COMMAND BLOCK");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
                return new AdminCommandMenu(id, inv, null);
            }
        };

        NetworkHooks.openScreen(player, provider, event.getPos());
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }
}
