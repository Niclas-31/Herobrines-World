package de.niclasl.herobrines_world.registries.item.custom;

import de.niclasl.herobrines_world.registries.block.entity.custom.StorageControllerBlockEntity;
import de.niclasl.herobrines_world.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.registries.components.SmartChipData;
import de.niclasl.herobrines_world.registries.screen.custom.SmartChipMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.NonNull;

public class SmartChip extends Item {
    public SmartChip(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (hand == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        player.awardStat(Stats.ITEM_USED.get(this));

        player.openMenu(new SimpleMenuProvider(
                (id, inv, p) -> new SmartChipMenu(id, inv.player.getMainHandItem()),
                Component.translatable("item.herobrines_world.smart_chip")
        ));

        return InteractionResult.CONSUME;
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (player == null) return InteractionResult.FAIL;

        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        ResourceKey<Level> dim = level.dimension();

        BlockEntity be = player.level().getBlockEntity(pos);

        if (!(be instanceof StorageControllerBlockEntity)) return InteractionResult.FAIL;

        SmartChipData.Transfer old = stack.getOrDefault(ModDataComponents.TRANSFER.get(), SmartChipData.Transfer.DEFAULT);

        SmartChipData.Transfer updated = new SmartChipData.Transfer(
                old.range(),
                pos,
                dim,
                old.speed(),
                old.mode()
        );

        stack.set(ModDataComponents.TRANSFER.get(), updated);

        return InteractionResult.SUCCESS;
    }
}