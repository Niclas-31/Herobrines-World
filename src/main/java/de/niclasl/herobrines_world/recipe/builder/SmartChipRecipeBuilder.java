package de.niclasl.herobrines_world.recipe.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.niclasl.herobrines_world.recipe.SmartChipRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SmartChipRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final Item result;
    private final ItemStack resultStack;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private @Nullable String group;
    private boolean showNotification = true;

    private int inputLevel = 1;
    private int outputLevel = 1;

    private SmartChipRecipeBuilder(HolderGetter<Item> items, ItemLike result, int count) {
        this(items, new ItemStack(result, count));
    }

    private SmartChipRecipeBuilder(HolderGetter<Item> items, ItemStack result) {
        this.items = items;
        this.result = result.getItem();
        this.resultStack = result;
    }

    public SmartChipRecipeBuilder inputLevel(int level) {
        this.inputLevel = level;
        return this;
    }

    public SmartChipRecipeBuilder outputLevel(int level) {
        this.outputLevel = level;
        return this;
    }

    public static SmartChipRecipeBuilder smartChip(HolderGetter<Item> items, ItemLike result) {
        return smartChip(items, result, 1);
    }

    public static SmartChipRecipeBuilder smartChip(HolderGetter<Item> items, ItemLike result, int count) {
        return new SmartChipRecipeBuilder(items, result, count);
    }

    public SmartChipRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(this.items.getOrThrow(tag)));
    }

    public SmartChipRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }

    public SmartChipRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public SmartChipRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.getFirst().length()) {
            throw new IllegalArgumentException("Pattern must be same width!");
        }
        this.rows.add(pattern);
        return this;
    }

    public @NonNull SmartChipRecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public @NonNull SmartChipRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public SmartChipRecipeBuilder showNotification(boolean show) {
        this.showNotification = show;
        return this;
    }

    @Override
    public @NonNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput output, @NonNull ResourceKey<Recipe<?>> key) {
        ShapedRecipePattern shapedrecipepattern = this.ensureValid(key);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        SmartChipRecipe recipe = new SmartChipRecipe(
                Objects.requireNonNullElse(this.group, ""),
                shapedrecipepattern,
                this.resultStack,
                this.inputLevel,
                this.outputLevel,
                this.showNotification
        );
        output.accept(
                key,
                recipe,
                advancement.build(key.identifier().withPrefix("recipes/redstone/"))
        );
    }

    private ShapedRecipePattern ensureValid(ResourceKey<Recipe<?>> key) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + key.identifier());
        }
        return ShapedRecipePattern.of(this.key, this.rows);
    }
}