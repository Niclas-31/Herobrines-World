package de.niclasl.herobrines_world.common.structure.boss;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.structure.StructureDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class StructureLoader {

    public static boolean load(ServerLevel level, StructureDefinition definition, BlockPos position) {
        if (!level.dimension().equals(definition.dimension())) {
            return false;
        }

        StructureTemplateManager manager = level.getStructureManager();

        Optional<StructureTemplate> optional = manager.get(definition.id());

        if (optional.isEmpty()) {
            HerobrinesWorld.LOGGER.warn("Structure not found: {}", definition.id());
            return false;
        }

        StructureTemplate template = optional.get();

        template.placeInWorld(
                level,
                position,
                position,
                new StructurePlaceSettings(),
                level.getRandom(),
                2
        );

        return true;
    }
}