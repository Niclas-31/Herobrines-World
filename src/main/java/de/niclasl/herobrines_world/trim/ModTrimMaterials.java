package de.niclasl.herobrines_world.trim;

import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

public class ModTrimMaterials {
    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                                 Style style) {
        TrimMaterial trimmaterial = new TrimMaterial(MaterialAssetGroup.create("bismuth"),
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style));
        context.register(trimKey, trimmaterial);
    }
}
