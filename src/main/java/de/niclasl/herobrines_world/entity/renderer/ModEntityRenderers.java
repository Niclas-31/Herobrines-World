package de.niclasl.herobrines_world.entity.renderer;

import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.entity.renderer.custom.*;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GOOD_HEROBRINE.get(),
                context -> new GoodHerobrineRenderer(context, false));

        event.registerEntityRenderer(ModEntities.BAD_HEROBRINE.get(),
                context -> new BadHerobrineRenderer(context, false));

        event.registerEntityRenderer(ModEntities.CHRISTMAS_NICLASL.get(),
                context -> new ChristmasNiclaslRenderer(context, false));

        event.registerEntityRenderer(ModEntities.ENTITY_303.get(),
                context -> new Entity303Renderer(context, false));

        event.registerEntityRenderer(ModEntities.HEROBRINE_BOSS.get(),
                context -> new HerobrineBossRenderer(context, false));

        event.registerEntityRenderer(ModEntities.NICLASL.get(),
                context -> new NiclaslRenderer(context, false));

        event.registerEntityRenderer(ModEntities.STATUE_ENTITY_303.get(),
                context -> new StatueEntity303Renderer(context, false));
    }
}