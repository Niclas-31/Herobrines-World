package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.registries.enchantment.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HerobrinesWorld.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(EnchantmentTags.NON_TREASURE)
                .add(ModEnchantments.WILDERNESS_ACUMEN)
                .add(ModEnchantments.ROOTS_OF_THE_EARTH)
                .add(ModEnchantments.HEART_OF_THE_FOREST)
                .add(ModEnchantments.MORE_SOULS)
                .add(ModEnchantments.HEROBRINE);

        tag(EnchantmentTags.TRADEABLE)
                .add(ModEnchantments.MORE_SOULS)
                .add(ModEnchantments.HEROBRINE);
    }
}