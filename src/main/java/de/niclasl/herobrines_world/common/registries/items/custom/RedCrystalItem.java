package de.niclasl.herobrines_world.common.registries.items.custom;

import de.niclasl.herobrines_world.common.registries.entities.custom.RedCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RedCrystalItem extends Item {
    public RedCrystalItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        if (!blockState.is(Blocks.OBSIDIAN) && !blockState.is(Blocks.BEDROCK)) {
            return InteractionResult.FAIL;
        } else {
            BlockPos above = pos.above();
            if (!level.isEmptyBlock(above)) {
                return InteractionResult.FAIL;
            } else {
                double x = above.getX();
                double y = above.getY();
                double z = above.getZ();
                List<Entity> entities = level.getEntities(null, new AABB(x, y, z, x + 1.0, y + 2.0, z + 1.0));
                if (!entities.isEmpty()) {
                    return InteractionResult.FAIL;
                } else {
                    if (level instanceof ServerLevel) {
                        RedCrystal crystal = new RedCrystal(level, x + 0.5, y, z + 0.5);
                        crystal.setShowBottom(false);
                        level.addFreshEntity(crystal);
                        level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, above);
                    }

                    context.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
    }
}