package com.insecthearts.adminblock.particle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "insect_hearts",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class AdminParticleClient {

    @SubscribeEvent
    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(
                AdminParticles.ADMIN_COMMAND.get(),
                sprites -> (type, level, x, y, z, xd, yd, zd) ->
                        new AdminCommandParticle(level, x, y, z, xd, yd, zd, sprites)
        );
    }
}
