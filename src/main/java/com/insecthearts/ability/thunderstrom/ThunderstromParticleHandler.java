package com.insecthearts.ability.thunderstrom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(modid = "insect_hearts", value = Dist.CLIENT)
public class ThunderstromParticleHandler {

    private static final ResourceLocation THUNDERSTROM_ID =
            ResourceLocation.fromNamespaceAndPath("moddd", "thunderstrom");

    private static final DustParticleOptions WHITE_GLOW =
            new DustParticleOptions(new Vector3f(1f, 1f, 1f), 0.5f);

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        long t = mc.level.getGameTime();

        for (Entity e : mc.level.entitiesForRendering()) {
            if (e instanceof ArmorStand stand &&
                stand.getPersistentData().getBoolean("insect_thunderstrom_stand")) {

                spawn(stand.getX(), stand.getY() + 0.9, stand.getZ(), t);
            }
        }

        Player p = mc.player;
        if (p == null) return;

        Item thunder = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(THUNDERSTROM_ID);

        InteractionHand activeHand = null;
        if (p.getItemInHand(InteractionHand.MAIN_HAND).getItem() == thunder) {
            activeHand = InteractionHand.MAIN_HAND;
        } else if (p.getItemInHand(InteractionHand.OFF_HAND).getItem() == thunder) {
            activeHand = InteractionHand.OFF_HAND;
        }

        if (activeHand != null) {
            // hand-relative position (no head offset)
            double side = activeHand == InteractionHand.MAIN_HAND ? 0.35 : -0.35;

            double x = p.getX() + Mth.cos((float) Math.toRadians(p.getYRot() + 90)) * side;
            double y = p.getY() + p.getBbHeight() * 0.55;
            double z = p.getZ() + Mth.sin((float) Math.toRadians(p.getYRot() + 90)) * side;

            spawn(x, y, z, t);
        }
    }

    private static void spawn(double x, double y, double z, long t) {
        float a = t * 0.15f;
        double ox = Mth.cos(a) * 0.22;
        double oz = Mth.sin(a) * 0.22;

        Minecraft.getInstance().level.addParticle(
                WHITE_GLOW,
                x + ox,
                y,
                z + oz,
                0, 0.0015, 0
        );
    }
}
