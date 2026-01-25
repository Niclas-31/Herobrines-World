package de.niclasl.herobrines_world.network.data;

import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public record WirelessSenderData(
        BlockPos pos,
        String name,
        String password,
        int range
) {
    private static final Set<BlockPos> receivers = new HashSet<>();

    public void addReceiver(BlockPos receiverPos) {
        receivers.add(receiverPos);
    }

    public void removeReceiver(BlockPos receiverPos) {
        receivers.remove(receiverPos);
    }

    public Set<BlockPos> getReceivers() {
        return receivers;
    }

    public boolean hasPassword() {
        return password != null && !password.isEmpty();
    }

    public boolean passwordMatches(String input) {
        return password.equals(input);
    }

    public String getName() {
        return name;
    }
}
