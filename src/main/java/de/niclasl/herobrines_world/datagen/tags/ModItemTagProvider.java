package de.niclasl.herobrines_world.datagen.tags;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.common.registries.items.ModItems;
import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HerobrinesWorld.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.REPAIRS_NATURE_ARMOR)
                .add(ModItems.getRK(ModItems.GREEN_GEMSTONE.get()));
        tag(ModTags.Items.REPAIRS_FIRE_ARMOR)
                .add(BlockItemIds.MAGMA_BLOCK.item());
        tag(ModTags.Items.REPAIRS_HEROBRINE_ARMOR)
                .add(ModItems.getRK(ModItems.HEROBRINE_DIAMOND.get()));
        tag(ModTags.Items.REPAIRS_PLATIN_ARMOR)
                .add(ModItems.getRK(ModItems.PLATIN_INGOT.get()));

        tag(ModTags.Items.NATURE_TOOL_MATERIALS)
                .add(ModItems.getRK(ModItems.GREEN_GEMSTONE.get()));
        tag(ModTags.Items.FIRE_TOOL_MATERIALS)
                .add(BlockItemIds.MAGMA_BLOCK.item());
        tag(ModTags.Items.HEROBRINE_TOOL_MATERIALS)
                .add(ModItems.getRK(ModItems.HEROBRINE_DIAMOND.get()));
        tag(ModTags.Items.PLATIN_TOOL_MATERIALS)
                .add(ModItems.getRK(ModItems.PLATIN_INGOT.get()));
        tag(ModTags.Items.ASH_TOOL_MATERIALS)
                .add(ModItems.getRK(ModItems.ASH_INGOT.get()));

        tag(ItemTags.SWORDS)
                .add(ModItems.getRK(ModItems.NATURE_SWORD.get()))
                .add(ModItems.getRK(ModItems.FIRE_SWORD.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_SWORD.get()))
                .add(ModItems.getRK(ModItems.PLATIN_SWORD.get()));
        tag(ItemTags.PICKAXES)
                .add(ModItems.getRK(ModItems.NATURE_PICKAXE.get()))
                .add(ModItems.getRK(ModItems.FIRE_PICKAXE.get()))
                .add(ModItems.getRK(ModItems.ASH_PICKAXE.get()))
                .add(ModItems.getRK(ModItems.ORE_DETECTOR.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_PICKAXE.get()))
                .add(ModItems.getRK(ModItems.PLATIN_PICKAXE.get()));
        tag(ItemTags.SHOVELS)
                .add(ModItems.getRK(ModItems.NATURE_SHOVEL.get()))
                .add(ModItems.getRK(ModItems.FIRE_SHOVEL.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_SHOVEL.get()))
                .add(ModItems.getRK(ModItems.PLATIN_SHOVEL.get()));
        tag(ItemTags.AXES)
                .add(ModItems.getRK(ModItems.NATURE_AXE.get()))
                .add(ModItems.getRK(ModItems.FIRE_AXE.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_AXE.get()))
                .add(ModItems.getRK(ModItems.PLATIN_AXE.get()));
        tag(ItemTags.HOES)
                .add(ModItems.getRK(ModItems.NATURE_HOE.get()))
                .add(ModItems.getRK(ModItems.FIRE_HOE.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_HOE.get()))
                .add(ModItems.getRK(ModItems.PLATIN_HOE.get()));
        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.getRK(ModItems.NATURE_HELMET.get()))
                .add(ModItems.getRK(ModItems.FIRE_HELMET.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_HELMET.get()))
                .add(ModItems.getRK(ModItems.PLATIN_HELMET.get()));
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.getRK(ModItems.NATURE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.FIRE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.PLATIN_CHESTPLATE.get()));
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.getRK(ModItems.NATURE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.FIRE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.PLATIN_LEGGINGS.get()));
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.getRK(ModItems.NATURE_BOOTS.get()))
                .add(ModItems.getRK(ModItems.FIRE_BOOTS.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_BOOTS.get()))
                .add(ModItems.getRK(ModItems.PLATIN_BOOTS.get()));

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.getRK(ModItems.NATURE_HELMET.get()))
                .add(ModItems.getRK(ModItems.NATURE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.NATURE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.NATURE_BOOTS.get()))

                .add(ModItems.getRK(ModItems.FIRE_HELMET.get()))
                .add(ModItems.getRK(ModItems.FIRE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.FIRE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.FIRE_BOOTS.get()))

                .add(ModItems.getRK(ModItems.HEROBRINE_HELMET.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.HEROBRINE_BOOTS.get()))

                .add(ModItems.getRK(ModItems.PLATIN_HELMET.get()))
                .add(ModItems.getRK(ModItems.PLATIN_CHESTPLATE.get()))
                .add(ModItems.getRK(ModItems.PLATIN_LEGGINGS.get()))
                .add(ModItems.getRK(ModItems.PLATIN_BOOTS.get()));
    }
}