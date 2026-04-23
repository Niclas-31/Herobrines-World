package de.niclasl.herobrines_world.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.niclasl.herobrines_world.components.ModDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class SmartChipRecipe implements CraftingRecipe {

    public final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;
    final boolean showNotification;
    final int input_level;
    final  int output_level;
    private @Nullable PlacementInfo placementInfo;

    public SmartChipRecipe(String group, ShapedRecipePattern pattern, ItemStack result, int input_level, int output_level, boolean showNotification) {
        super();
        this.group = group;
        this.pattern = pattern;
        this.result = result;
        this.input_level = input_level;
        this.output_level = output_level;
        this.showNotification = showNotification;
    }

    @Override
    public @NonNull RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ModRecipes.SMART_CHIP_SERIALIZER.get();
    }

    @Override
    public @NonNull RecipeType<CraftingRecipe> getType() {
        return ModRecipes.SMART_CHIP_TYPE.get();
    }

    @Override
    public @NonNull String group() {
        return group;
    }

    @Override
    public @NonNull CraftingBookCategory category() {
        return CraftingBookCategory.REDSTONE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.createFromOptionals(this.pattern.ingredients());
        }

        return this.placementInfo;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public boolean matches(@NonNull CraftingInput input, @NonNull Level level) {
        if (!this.pattern.matches(input)) return false;

        int foundLevel = getSmartChipLevel(input);

        return foundLevel == this.input_level;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull CraftingInput input, HolderLookup.@NonNull Provider provider) {
        ItemStack output = result.copy();

        int inputLevel = getSmartChipLevel(input);
        int nextLevel = clampLevel(inputLevel + 1);

        output.set(
                ModDataComponents.MACHINE_UPGRADE_LEVEL.get(),
                nextLevel
        );

        return output;
    }

    private int getSmartChipLevel(CraftingInput input) {
        int maxLevel = 1;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);

            int level = stack.getOrDefault(
                    ModDataComponents.MACHINE_UPGRADE_LEVEL.get(),
                    1
            );

            level = clampLevel(level);

            maxLevel = Math.max(maxLevel, level);
        }

        return maxLevel;
    }

    private int clampLevel(int level) {
        return Math.clamp(level, 1, 3);
    }

    @Override
    public @NonNull List<RecipeDisplay> display() {
        return List.of(new ShapedCraftingRecipeDisplay(this.pattern.width(), this.pattern.height(), this.pattern.ingredients().stream().map(opt -> opt.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(), new SlotDisplay.ItemStackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }

    public static class Serializer implements RecipeSerializer<SmartChipRecipe> {
        public static final MapCodec<SmartChipRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                        ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                        Codec.INT.optionalFieldOf("input_level", 1).forGetter(r -> r.input_level),
                        Codec.INT.optionalFieldOf("output_level", 1).forGetter(r -> r.output_level),
                        Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(r -> r.showNotification)
                ).apply(inst, SmartChipRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmartChipRecipe> STREAM_CODEC = StreamCodec.of(
                SmartChipRecipe.Serializer::toNetwork, SmartChipRecipe.Serializer::fromNetwork
        );

        public Serializer() {
            super();
        }

        public @NonNull MapCodec<SmartChipRecipe> codec() {
            return CODEC;
        }

        public @NonNull StreamCodec<RegistryFriendlyByteBuf, SmartChipRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmartChipRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            int input_level = buffer.readInt();
            int output_Level = buffer.readInt();
            boolean showNotification = buffer.readBoolean();

            return new SmartChipRecipe(group, pattern, result, input_level, output_Level, showNotification);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmartChipRecipe recipe) {
            buffer.writeUtf(recipe.group);
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeInt(recipe.input_level);
            buffer.writeInt(recipe.output_level);
            buffer.writeBoolean(recipe.showNotification);
        }
    }
}