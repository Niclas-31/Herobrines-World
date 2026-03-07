package de.niclasl.herobrines_world.datagen;

import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world.util.ModTags;
import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HerobrinesWorld.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.NATURE_REPAIRABLE)
                .add(ModItems.GREEN_GEMSTONE.get());
        tag(ModTags.Items.FIRE_REPAIRABLE)
                .add(Items.MAGMA_BLOCK);
        tag(ModTags.Items.HEROBRINE_REPAIRABLE)
                .add(ModItems.HEROBRINE_DIAMOND.get());
        tag(ModTags.Items.TOXENIUM_REPAIRABLE)
                .add(ModItems.TOXENIUM_INGOT.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.NATURE_SWORD.get())
                .add(ModItems.FIRE_SWORD.get())
                .add(ModItems.HEROBRINE_SWORD.get())
                .add(ModItems.TOXENIUM_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.NATURE_PICKAXE.get())
                .add(ModItems.FIRE_PICKAXE.get())
                .add(ModItems.ASH_PICKAXE.get())
                .add(ModItems.ORE_DETECTOR.get())
                .add(ModItems.HEROBRINE_PICKAXE.get())
                .add(ModItems.TOXENIUM_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.NATURE_SHOVEL.get())
                .add(ModItems.FIRE_SHOVEL.get())
                .add(ModItems.HEROBRINE_SHOVEL.get())
                .add(ModItems.TOXENIUM_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.NATURE_AXE.get())
                .add(ModItems.FIRE_AXE.get())
                .add(ModItems.HEROBRINE_AXE.get())
                .add(ModItems.TOXENIUM_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.NATURE_HOE.get())
                .add(ModItems.FIRE_HOE.get())
                .add(ModItems.HEROBRINE_HOE.get())
                .add(ModItems.TOXENIUM_HOE.get());
        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.NATURE_HELMET.get())
                .add(ModItems.FIRE_HELMET.get())
                .add(ModItems.HEROBRINE_HELMET.get())
                .add(ModItems.TOXENIUM_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.NATURE_CHESTPLATE.get())
                .add(ModItems.FIRE_CHESTPLATE.get())
                .add(ModItems.HEROBRINE_CHESTPLATE.get())
                .add(ModItems.TOXENIUM_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.NATURE_LEGGINGS.get())
                .add(ModItems.FIRE_LEGGINGS.get())
                .add(ModItems.HEROBRINE_LEGGINGS.get())
                .add(ModItems.TOXENIUM_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.NATURE_BOOTS.get())
                .add(ModItems.FIRE_BOOTS.get())
                .add(ModItems.HEROBRINE_BOOTS.get())
                .add(ModItems.TOXENIUM_BOOTS.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.NATURE_HELMET.get())
                .add(ModItems.NATURE_CHESTPLATE.get())
                .add(ModItems.NATURE_LEGGINGS.get())
                .add(ModItems.NATURE_BOOTS.get())

                .add(ModItems.FIRE_HELMET.get())
                .add(ModItems.FIRE_CHESTPLATE.get())
                .add(ModItems.FIRE_LEGGINGS.get())
                .add(ModItems.FIRE_BOOTS.get())

                .add(ModItems.HEROBRINE_HELMET.get())
                .add(ModItems.HEROBRINE_CHESTPLATE.get())
                .add(ModItems.HEROBRINE_LEGGINGS.get())
                .add(ModItems.HEROBRINE_BOOTS.get())

                .add(ModItems.TOXENIUM_HELMET.get())
                .add(ModItems.TOXENIUM_CHESTPLATE.get())
                .add(ModItems.TOXENIUM_LEGGINGS.get())
                .add(ModItems.TOXENIUM_BOOTS.get());
    }
}