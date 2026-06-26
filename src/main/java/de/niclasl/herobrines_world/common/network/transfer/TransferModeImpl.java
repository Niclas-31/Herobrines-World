package de.niclasl.herobrines_world.common.network.transfer;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.network.transfer.mode.*;
import de.niclasl.herobrines_world_api.api.transfer.TransferMode;
import de.niclasl.herobrines_world_api.registry.HWRegistries;
import net.minecraft.resources.Identifier;

public class TransferModeImpl {

    public static final TransferMode INSERT = new InsertMode();
    public static final TransferMode EXTRACT = new ExtractMode();

    public static void register() {
        registerTransferMode(id("insert"), INSERT);
        registerTransferMode(id("extract"), EXTRACT);
    }

    public static void registerTransferMode(Identifier id, TransferMode mode) {
        for (TransferMode existing : HWRegistries.TRANSFER_MODES.values()) {

            if (existing.priority() == mode.priority()) {
                throw new IllegalStateException(
                        "Duplicate TransferMode priority "
                                + mode.priority()
                                + " between "
                                + existing.id()
                                + " and "
                                + mode.id()
                );
            }
        }

        HWRegistries.TRANSFER_MODES.put(id, mode);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, path);
    }
}