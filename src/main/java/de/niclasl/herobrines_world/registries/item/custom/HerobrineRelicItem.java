package de.niclasl.herobrines_world.registries.item.custom;

import de.niclasl.herobrines_world.registries.components.ModDataComponents;
import de.niclasl.herobrines_world.registries.components.RelicData;
import de.niclasl.herobrines_world.registries.entity.ModEntities;
import de.niclasl.herobrines_world.registries.entity.custom.HerobrineBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class HerobrineRelicItem extends Item {

    private int ticks;

    public HerobrineRelicItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NonNull ServerLevel level, @NonNull Entity entity, @Nullable EquipmentSlot slot) {

        RelicData data = stack.get(ModDataComponents.RELIC_DATA);

        if (data != null) {
            if (this.ticks > 0) {
                this.ticks--;
            }
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx) {
        if (!(ctx.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }

        if (!(ctx.getPlayer() instanceof ServerPlayer player)) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = ctx.getItemInHand();

        RelicData data = stack.get(ModDataComponents.RELIC_DATA);

        if (data == null || data.owner() == null) {
            player.sendSystemMessage(Component.translatable("item.herobrines_world.herobrine_relic.is_defect"));
            return InteractionResult.FAIL;
        }

        if (!data.owner().equals(player.getUUID())) {
            player.sendSystemMessage(Component.translatable("item.herobrines_world.herobrine_relic.is_not_yours"));
            return InteractionResult.FAIL;
        }

        if (data.bossUUID() != null && !data.bossUUID().isBlank()) {

            Entity e = level.getEntity(UUID.fromString(data.bossUUID()));

            RelicData value = new RelicData(data.owner(), data.ownerName(),1200, "");
            if (e instanceof HerobrineBoss boss && boss.isAlive()) {

                if (player.isShiftKeyDown()) {
                    boolean newMode = !boss.isMinionMode();
                    boss.setMinionMode(newMode);

                    player.sendSystemMessage(Component.translatable(
                            newMode
                                    ? "item.herobrines_world.herobrine_relic.minion_mode.enabled"
                                    : "item.herobrines_world.herobrine_relic.minion_mode.disabled"
                    ));
                    return InteractionResult.SUCCESS;
                }

                boss.remove(Entity.RemovalReason.DISCARDED);

                stack.set(ModDataComponents.RELIC_DATA,
                        value
                );

                player.sendSystemMessage(Component.translatable("item.herobrines_world.herobrine_relic.boss.removed"));
                return InteractionResult.SUCCESS;
            }

            stack.set(ModDataComponents.RELIC_DATA,
                    value
            );
        }

        if (ticks != 0) {
            player.sendSystemMessage(Component.translatable("item.herobrines_world.herobrine_relic.cooldown_active", ticks));
            return InteractionResult.FAIL;
        }

        BlockPos pos = ctx.getClickedPos().above();

        HerobrineBoss boss = ModEntities.HEROBRINE_BOSS.get()
                .create(level, EntitySpawnReason.MOB_SUMMONED);

        if (boss == null) {
            return InteractionResult.FAIL;
        }

        boss.setPos(
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5
        );

        boss.setTamedOwner(player);
        boss.setMinionMode(true);
        boss.setHideBossBar(true);

        level.addFreshEntity(boss);

        stack.set(ModDataComponents.RELIC_DATA,
                new RelicData(
                        data.owner(),
                        data.ownerName(),
                        data.cooldownTicks(),
                        boss.getStringUUID()
                )
        );

        this.ticks = data.cooldownTicks();

        player.sendSystemMessage(Component.translatable("item.herobrines_world.herobrine_relic.boss.spawned"));

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onDroppedByPlayer(@NotNull ItemStack stack, @NotNull Player player) {
        player.getInventory().placeItemBackInInventory(stack);
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull TooltipContext context,
                                @NotNull TooltipDisplay tooltipDisplay,
                                @NotNull Consumer<Component> tooltipAdder,
                                @NotNull TooltipFlag flag) {

        RelicData data = stack.get(ModDataComponents.RELIC_DATA);

        if (data == null || data.ownerName() == null || data.ownerName().isBlank()) {
            tooltipAdder.accept(Component.translatable(
                    "item.herobrines_world.herobrine_relic.unbound"
            ));
        } else {
            tooltipAdder.accept(Component.translatable(
                    "item.herobrines_world.herobrine_relic.bound_on",
                    data.ownerName()
            ));
        }
    }
}