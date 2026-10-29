package de.niclasl.herobrines_world.common.registries.blocks.properties;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum BossFragmentType implements StringRepresentable {
    HEROBRINE("herobrine", Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "herobrine_boss")),
    ENTITY_303("entity_303", Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "entity_303"));

    private final String name;
    private final Identifier bossId;

    BossFragmentType(String name, Identifier bossId) {
        this.name = name;
        this.bossId = bossId;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }

    public Identifier getBossId() {
        return bossId;
    }
}