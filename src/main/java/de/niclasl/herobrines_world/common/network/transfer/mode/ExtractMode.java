package de.niclasl.herobrines_world.common.network.transfer.mode;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import net.minecraft.resources.Identifier;

public class ExtractMode implements TransferMode {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "extract");
    }
}