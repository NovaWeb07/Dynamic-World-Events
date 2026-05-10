package com.insecthearts.ability;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class HeartConsumeAbility {

    private static final ResourceLocation HEART_ITEM_ID =
            new ResourceLocation("moddd", "hearts");

    // Multiplayer-safe cooldown (0.5 sec)
    private static final int COOLDOWN_TICKS = 10;

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        Item heartItem = ForgeRegistries.ITEMS.getValue(HEART_ITEM_ID);
        if (heartItem == null) return;
        if (stack.getItem() != heartItem) return;

        if (serverPlayer.getCooldowns().isOnCooldown(heartItem)) return;

        AttributeInstance maxHealthAttr = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) return;

        maxHealthAttr.setBaseValue(maxHealthAttr.getBaseValue() + 2.0D);
        serverPlayer.setHealth(serverPlayer.getMaxHealth());

        // consume item
        stack.shrink(1);

        // apply cooldown
        serverPlayer.getCooldowns().addCooldown(heartItem, COOLDOWN_TICKS);

        ServerLevel serverLevel = (ServerLevel) level;

        double px = serverPlayer.getX();
        double py = serverPlayer.getY() + 1.0;
        double pz = serverPlayer.getZ();


        serverLevel.sendParticles(
                ParticleTypes.HEART,
                px, py, pz,
                14,
                0.25, 0.35, 0.25,
                0.0
        );

        for (int i = 0; i < 16; i++) {
            double angle = i * (Math.PI * 2 / 16);
            double radius = 0.75;

            serverLevel.sendParticles(
                    ParticleTypes.HEART,
                    px + Math.cos(angle) * radius,
                    py + (i * 0.015),
                    pz + Math.sin(angle) * radius,
                    1,
                    0, 0, 0,
                    0
            );
        }

        serverLevel.sendParticles(
                ParticleTypes.SOUL,
                px,
                py + 0.4,
                pz,
                14,
                0.3, 0.5, 0.3,
                0.02
        );

        serverLevel.sendParticles(
                ParticleTypes.CRIT,
                px, py, pz,
                10,
                0.35, 0.45, 0.35,
                0.05
        );


        serverLevel.playSound(
                null,
                serverPlayer.blockPosition(),
                SoundEvents.PLAYER_LEVELUP,
                SoundSource.PLAYERS,
                0.9F,
                1.4F
        );

        serverLevel.playSound(
                null,
                serverPlayer.blockPosition(),
                SoundEvents.AMETHYST_BLOCK_CHIME,
                SoundSource.PLAYERS,
                0.6F,
                1.8F
        );

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}
