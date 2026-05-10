package com.insecthearts.adminblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

public class AdminCommandScreen extends AbstractContainerScreen<AdminCommandMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation("insect_hearts", "textures/gui/admin_command_gui.png");

    private EditBox commandBox;
    private CommandSuggestions suggestions;
    private Button startButton;

    public AdminCommandScreen(AdminCommandMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 248;
        this.imageHeight = 176;
    }

    @Override
    protected void init() {
        super.init();

        commandBox = new EditBox(
                this.font,
                this.leftPos + 18,
                this.topPos + 70,
                212,
                20,
                Component.literal("")
        );
        commandBox.setMaxLength(32500);
        commandBox.setBordered(true);
        commandBox.setTextColor(0xD580FF);
        commandBox.setResponder(s -> {
            if (suggestions != null) suggestions.updateCommandInfo();
        });
        addRenderableWidget(commandBox);
        setInitialFocus(commandBox);

        suggestions = new CommandSuggestions(
                Minecraft.getInstance(),
                this,
                commandBox,
                this.font,
                true,
                true,
                0,
                7,
                false,
                0xD580FF
        );

        startButton = Button.builder(
                Component.literal("START")
                        .setStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.LIGHT_PURPLE)),
                b -> executeCommand()
        ).bounds(
                this.leftPos + 18,
                this.topPos + 100,
                212,
                20
        ).build();

        addRenderableWidget(startButton);
    }

    private void executeCommand() {
        String cmd = commandBox.getValue().trim();
        if (cmd.isEmpty()) return;

        if (cmd.startsWith("/")) {
            cmd = cmd.substring(1);
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getSingleplayerServer() == null) return;

        MinecraftServer server = mc.getSingleplayerServer();
        ServerPlayer serverPlayer = server.getPlayerList().getPlayer(mc.player.getUUID());
        if (serverPlayer == null) return;

        CommandSourceStack source = serverPlayer.createCommandSourceStack()
                .withPermission(4)
                .withSuppressedOutput();

        server.getCommands().performPrefixedCommand(source, cmd);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (suggestions != null && suggestions.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_E) {
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            executeCommand();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partial, int mx, int my) {
        RenderSystem.setShaderTexture(0, BG);
        gfx.blit(BG, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        gfx.drawString(
                this.font,
                Component.literal("ADMIN COMMAND BLOCK")
                        .setStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.LIGHT_PURPLE)),
                12,
                10,
                0xFFFFFF,
                false
        );
    }

    @Override
    public void render(GuiGraphics gfx, int mx, int my, float partial) {
        renderBackground(gfx);
        super.render(gfx, mx, my, partial);
        if (suggestions != null) suggestions.render(gfx, mx, my);
    }
}
