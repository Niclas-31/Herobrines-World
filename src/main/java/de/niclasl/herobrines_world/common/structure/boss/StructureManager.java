package de.niclasl.herobrines_world.common.structure.boss;

import de.niclasl.herobrines_world_api.event.StructureLoadEvent;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import de.niclasl.herobrines_world_api.structure.StructureAPI;
import de.niclasl.herobrines_world_api.structure.StructureDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;

public class StructureManager implements StructureAPI {

    @Override
    public void spawnStructure(ServerLevel level, Identifier id, BlockPos pos) {
        StructureDefinition definition = HWRegistries.STRUCTURES.get(id);

        if(definition == null) {
            return;
        }

        if(!definition.enabled()) {
            return;
        }

        NeoForge.EVENT_BUS.post(new StructureLoadEvent.Pre(id, pos));

        StructureLoader.load(level, id, pos);

        NeoForge.EVENT_BUS.post(new StructureLoadEvent.Post(id, pos));
    }
}