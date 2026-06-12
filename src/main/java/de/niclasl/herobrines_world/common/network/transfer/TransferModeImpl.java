package de.niclasl.herobrines_world.common.network.transfer;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.transfer.mode.ExtractMode;
import de.niclasl.herobrines_world.common.network.transfer.mode.InsertMode;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class TransferModeImpl {

    public static final TransferMode INSERT = new InsertMode();
    public static final TransferMode EXTRACT = new ExtractMode();

    public static void register() {
        HWRegistries.TRANSFER_MODES.put(id("insert"), INSERT);
        HWRegistries.TRANSFER_MODES.put(id("extract"), EXTRACT);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}