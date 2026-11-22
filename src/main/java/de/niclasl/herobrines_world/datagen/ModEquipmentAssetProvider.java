package de.niclasl.herobrines_world.datagen;

import com.mojang.serialization.Codec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.items.ModArmorMaterials;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEquipmentAssetProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetProvider(PackOutput output) {
        super();
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        bootstrap(output);
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        consumer.accept(ModArmorMaterials.NATURE, onlyHumanoid("nature"));
        consumer.accept(ModArmorMaterials.FIRE, onlyHumanoid("fire"));
        consumer.accept(ModArmorMaterials.HEROBRINE, onlyHumanoid("herobrine"));
        consumer.accept(ModArmorMaterials.PLATIN, onlyHumanoid("platin"));
    }

    public static EquipmentClientInfo onlyHumanoid(String name) {
        return EquipmentClientInfo.builder().addHumanoidLayers(Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, name)).build();
    }

    @Override
    public @NonNull CompletableFuture<?> run(@NonNull CachedOutput cache) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
        this.registerModels((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to register mod equipment asset twice for id: " + id);
            }
        });
        Codec<EquipmentClientInfo> codec = EquipmentClientInfo.CODEC;
        PackOutput.PathProvider pathProvider = this.pathProvider;
        Objects.requireNonNull(pathProvider);
        return DataProvider.saveAll(cache, codec, pathProvider::json, equipmentAssets);
    }

    @Override
    public @NonNull String getName() {
        return "Mod Equipment Asset Definitions";
    }
}