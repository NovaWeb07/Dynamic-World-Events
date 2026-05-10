package com.insecthearts.adminblock.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class AdminCommandMenu extends AbstractContainerMenu {

    private String command = "";

    public AdminCommandMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(AdminMenus.ADMIN_COMMAND_MENU.get(), id);
    }

    public void setCommand(String cmd) {
        this.command = cmd;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
