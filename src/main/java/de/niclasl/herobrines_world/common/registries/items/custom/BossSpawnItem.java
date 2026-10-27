package de.niclasl.herobrines_world.common.registries.items.custom;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class BossSpawnItem extends Item {

    private final Identifier bossId;

    public BossSpawnItem(
            Properties properties,
            Identifier bossId
    ) {
        super(properties);
        this.bossId = bossId;
    }

    public Identifier getBossId() {
        return bossId;
    }
}