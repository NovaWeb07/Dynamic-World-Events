package com.insecthearts.client;

import com.insecthearts.InsectHeartsMod;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = InsectHeartsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LavaDoorKeybind {

    public static KeyMapping SPAWN_FLAT;
    public static KeyMapping TOGGLE;

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent e) {
        SPAWN_FLAT = new KeyMapping(
                "key.insect_hearts.spawn_flat",
                GLFW.GLFW_KEY_G,
                "key.categories.gameplay"
        );

        TOGGLE = new KeyMapping(
                "key.insect_hearts.toggle",
                GLFW.GLFW_KEY_H,
                "key.categories.gameplay"
        );

        e.register(SPAWN_FLAT);
        e.register(TOGGLE);
    }
}
