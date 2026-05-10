package com.insecthearts.ability.thunderstrom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "insect_hearts")
public class ThunderstromPlaceHandler {

    private static final ResourceLocation THUNDERSTROM_ID =
            ResourceLocation.fromNamespaceAndPath("moddd", "thunderstrom");

    private static final String TAG_THUNDERSTROM_STAND = "insect_thunderstrom_stand";

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();

        if (level.isClientSide) return;

        ItemStack stack = player.getItemInHand(hand);
        Item thunderItem = BuiltInRegistries.ITEM.get(THUNDERSTROM_ID);
        if (stack.isEmpty() || stack.getItem() != thunderItem) return;

        event.setCanceled(true);

        Vec3 hit = event.getHitVec().getLocation();

        ArmorStand stand = new ArmorStand(level, hit.x + 0.5, hit.y + 0.02, hit.z + 0.5);
        stand.setInvisible(true);
        stand.setNoGravity(true);
        stand.setNoBasePlate(true);
        stand.setShowArms(false);

        stand.setItemSlot(EquipmentSlot.HEAD, stack.copyWithCount(1));

        stand.getPersistentData().putBoolean(TAG_THUNDERSTROM_STAND, true);
        level.addFreshEntity(stand);

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        Level level = event.getLevel();

        if (level.isClientSide) return;
        if (!(target instanceof ArmorStand stand)) return;
        if (!stand.getPersistentData().getBoolean(TAG_THUNDERSTROM_STAND)) return;

        event.setCanceled(true);

        ItemStack stack = stand.getItemBySlot(EquipmentSlot.HEAD);
        if (!stack.isEmpty()) {
            player.getInventory().add(stack.copy());
        }

        stand.discard();
    }
}
