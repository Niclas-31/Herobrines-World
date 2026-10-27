package de.niclasl.herobrines_world.client.renderer.entity;

import de.niclasl.herobrines_world.common.registries.entities.ModEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GOOD_HEROBRINE.get(),
                GoodHerobrineRenderer::new);

        event.registerEntityRenderer(ModEntities.BAD_HEROBRINE.get(),
                BadHerobrineRenderer::new);

        event.registerEntityRenderer(ModEntities.CHRISTMAS_NICLASL.get(),
                ChristmasNiclaslRenderer::new);

        event.registerEntityRenderer(ModEntities.ENTITY_303.get(),
                Entity303Renderer::new);

        event.registerEntityRenderer(ModEntities.HEROBRINE_BOSS.get(),
                HerobrineBossRenderer::new);

        event.registerEntityRenderer(ModEntities.NICLASL.get(),
                NiclaslRenderer::new);

        event.registerEntityRenderer(ModEntities.STATUE_ENTITY_303.get(),
                context -> new StatueEntity303Renderer(context, false));
    }
}