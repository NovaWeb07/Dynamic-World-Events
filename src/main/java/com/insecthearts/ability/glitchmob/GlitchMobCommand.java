package com.insecthearts.ability.glitchmob;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GlitchMobCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("insect")
                        .then(Commands.literal("glitchmob")
                                .executes(ctx -> {
                                    ServerLevel level = ctx.getSource().getLevel();
                                    Vec3 pos = ctx.getSource().getPosition();
                                    AABB box = new AABB(pos, pos).inflate(12);

                                    for (Entity e : level.getEntities(null, box)) {
                                        if (e instanceof Mob) {
                                            GlitchMobTracker.add(e.getUUID());
                                        }
                                    }
                                    return 1;
                                }))
        );
    }
}
