package de.niclasl.herobrines_world.client;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayers {

    public static final ModelLayerLocation HEROBRINE_BOSS =
            new ModelLayerLocation(
                    Identifier.fromNamespaceAndPath(
                            HerobrinesWorld.MOD_ID,
                            "herobrine_boss"
                    ),
                    "main"
            );
}