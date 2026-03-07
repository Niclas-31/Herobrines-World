package de.niclasl.herobrines_world.item;

import de.niclasl.herobrines_world.util.ModTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolTiers {
    public static final ToolMaterial NATURE = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_NATURE_TOOL,
            2500, 7.0f, 2.5f, 14, ModTags.Items.NATURE_REPAIRABLE);
    public static final ToolMaterial FIRE = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_FIRE_TOOL,
            5000, 8.5f, 3.5f, 16, ModTags.Items.FIRE_REPAIRABLE);
    public static final ToolMaterial HEROBRINE = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_HEROBRINE_TOOL,
            7500, 10.0f, 4.5f, 18, ModTags.Items.HEROBRINE_REPAIRABLE);
    public static final ToolMaterial TOXENIUM = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_TOXENIUM_TOOL,
            10000, 12.0f, 6.0f, 20, ModTags.Items.TOXENIUM_REPAIRABLE);
}