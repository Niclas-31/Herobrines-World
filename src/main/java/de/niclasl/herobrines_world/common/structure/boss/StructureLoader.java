package de.niclasl.herobrines_world.common.structure.boss;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.world.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class StructureLoader {

    public static void load(ServerLevel level, Identifier structureId, BlockPos position) {

        StructureTemplateManager manager = level.getStructureManager();

        Optional<StructureTemplate> optional = manager.get(structureId);

        HerobrinesWorld.LOGGER.info("Structure found: {}", manager.get(structureId));

        if (optional.isEmpty()) {
            HerobrinesWorld.LOGGER.warn("Structure not found: {}", structureId);
            return;
        }

        StructureTemplate template = optional.get();

        if (level.dimension() != ModDimensions.HEROBRINE_REALM) {
            return;
        }

        template.placeInWorld(
                level,
                position,
                position,
                new StructurePlaceSettings(),
                level.getRandom(),
                2
        );
    }
}