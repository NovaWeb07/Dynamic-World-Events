package com.insecthearts.adminblock.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class AdminCommandParticle extends TextureSheetParticle {

    protected AdminCommandParticle(
            ClientLevel level,
            double x, double y, double z,
            double xd, double yd, double zd,
            SpriteSet sprites
    ) {
        super(level, x, y, z, xd, yd, zd);
        this.pickSprite(sprites);
        this.gravity = 0.0F;
        this.lifetime = 30;
        this.quadSize = 1.4F;
        this.alpha = 0.6F;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
