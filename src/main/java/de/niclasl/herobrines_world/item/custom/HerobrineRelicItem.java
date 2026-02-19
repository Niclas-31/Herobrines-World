package de.niclasl.herobrines_world.item.custom;

import de.niclasl.herobrines_world.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.network.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class HerobrineRelicItem extends Item {

    public HerobrineRelicItem(Properties properties) {
        super(properties.fireResistant().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx) {
        if (!(ctx.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }

        if (!(ctx.getPlayer() instanceof ServerPlayer player)) {
            return InteractionResult.SUCCESS;
        }

        ModVariables.PlayerVariables vars =
                player.getData(ModVariables.PLAYER_VARIABLES);

        UUID owner = vars.getRelicOwner();
        if (owner == null) {
            player.sendSystemMessage(Component.translatable("relic.is_defect"));
            return InteractionResult.FAIL;
        }

        if (!owner.equals(player.getUUID())) {
            player.sendSystemMessage(Component.translatable("relic.is_not_yours"));
            return InteractionResult.FAIL;
        }

        if (vars.ownedBossUUID != null) {
            Entity e = level.getEntity(vars.getOwnedBossUUID());

            if (e instanceof HerobrineBoss boss && boss.isAlive()) {

                if (player.isShiftKeyDown()) {
                    boolean newMode = !boss.isMinionMode();
                    boss.setMinionMode(newMode);

                    player.sendSystemMessage(Component.translatable(
                            newMode
                                    ? "boss.minion_mode.enabled"
                                    : "boss.minion_mode.disabled"
                    ));
                    return InteractionResult.SUCCESS;
                }

                boss.remove(Entity.RemovalReason.DISCARDED);
                vars.ownedBossUUID = null;

                player.sendSystemMessage(
                        Component.translatable("boss.removed")
                );
                return InteractionResult.SUCCESS;
            }

            vars.ownedBossUUID = null;
        }

        BlockPos pos = ctx.getClickedPos().above();

        HerobrineBoss boss =
                ModEntities.HEROBRINE_BOSS.get()
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

        vars.ownedBossUUID = boss.getUUID().toString();

        player.sendSystemMessage(
                Component.translatable("boss.spawned")
        );

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onDroppedByPlayer(@NotNull ItemStack stack, @NotNull Player player) {
        player.getInventory().placeItemBackInInventory(stack);
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay,
                                @NotNull Consumer<Component> tooltipAdder, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        if (Minecraft.getInstance().player != null) {
            ModVariables.PlayerVariables mapVars = Minecraft.getInstance().player.getData(ModVariables.PLAYER_VARIABLES);
            UUID owner = mapVars.getRelicOwner();
            if (owner != null) {
                tooltipAdder.accept(Component.translatable("item.herobrines_world.herobrine_relic.bound_on", owner.toString()));
            } else {
                tooltipAdder.accept(Component.translatable("item.herobrines_world.herobrine_relic.unbound"));
            }
        }
    }
}
