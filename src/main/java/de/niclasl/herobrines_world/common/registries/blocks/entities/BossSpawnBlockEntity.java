package de.niclasl.herobrines_world.common.registries.blocks.entities;

import de.niclasl.herobrines_world.common.boss.BossSpawner;
import de.niclasl.herobrines_world.common.registries.items.custom.BossSpawnItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BossSpawnBlockEntity extends BlockEntity implements Container {
    public NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    private boolean locked;
    private float rotation;

    public BossSpawnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOSS_SPAWNER.get(), pos, state);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void tick(Level level) {
        if (level.isClientSide()) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) level;

        ItemStack stack = this.items.getFirst();

        if (stack.getItem() instanceof BossSpawnItem spawnItem && !locked) {
            Identifier bossId = spawnItem.getBossId();

            BlockPos pos = new BlockPos(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

            if (bossId != null) {
                BossSpawner.spawn(serverLevel, pos, bossId);

                this.locked = true;
                this.items.clear();

                this.setChanged();
            }
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void handleUpdateTag(@NonNull ValueInput input) {
        super.handleUpdateTag(input);

        this.locked = input.getBooleanOr("locked", false);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);

        output.putBoolean("locked", this.locked);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);

        this.locked = input.getBooleanOr("locked", false);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public @NonNull ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public @NonNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items, slot, amount);
        if (!stack.isEmpty()) setChanged();
        return stack;
    }

    @Override
    public @NonNull ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, @NonNull ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }
}