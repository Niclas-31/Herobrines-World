package de.niclasl.herobrines_world.common.structure.boss;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.structure.StructureDefinition;
import net.minecraft.resources.Identifier;

public class HWStructureTypes {
    public static final StructureDefinition HEROBRINE_TEMPLE =
            new StructureDefinition(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "herobrine_temple"), true);
    public static final StructureDefinition ENTITY_303_TEMPLE =
            new StructureDefinition(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "entity_303_temple"), true);

    public static void register() {
        registerStructure(id("herobrine_temple"), HEROBRINE_TEMPLE);
        registerStructure(id("entity_303_temple"), ENTITY_303_TEMPLE);
    }

    public static void registerStructure(Identifier id, StructureDefinition structure) {
        HWRegistries.STRUCTURES.register(id, structure);
    }

    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name);
    }
}