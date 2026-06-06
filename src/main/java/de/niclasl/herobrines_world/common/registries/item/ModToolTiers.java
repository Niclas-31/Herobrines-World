package de.niclasl.herobrines_world.common.registries.item;

import de.niclasl.herobrines_world.common.util.ModTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolTiers {
    public static final ToolMaterial NATURE = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_NATURE_TOOL,
            150,
            4.5f,
            1.2f,
            8,
            ModTags.Items.NATURE_TOOL_MATERIALS
    );

    public static final ToolMaterial FIRE = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_FIRE_TOOL,
            300,
            6.5f,
            2.2f,
            14,
            ModTags.Items.FIRE_TOOL_MATERIALS
    );

    public static final ToolMaterial HEROBRINE = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_HEROBRINE_TOOL,
            1700,
            8.5f,
            3.5f,
            12,
            ModTags.Items.HEROBRINE_TOOL_MATERIALS
    );

    public static final ToolMaterial PLATIN = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_PLATIN_TOOL,
            1400,
            7.5f,
            2.8f,
            18,
            ModTags.Items.PLATIN_TOOL_MATERIALS
    );

    public static final ToolMaterial ASH = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_ASH_TOOL,
            200,
            6.0f,
            2.0F,
            15,
            ModTags.Items.ASH_TOOL_MATERIALS
    );
}