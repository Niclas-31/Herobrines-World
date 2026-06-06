package de.niclasl.herobrines_world.common.leaderbaord.season;

import de.niclasl.herobrines_world.common.leaderbaord.RewardEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class SeasonRewardStorage extends SavedData {

    private final Map<UUID, List<RewardEntry>> rewardsByPlayer = new HashMap<>();
    private final Set<UUID> claimed = new HashSet<>();

    public static SeasonRewardStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedDataType<>(
                        "season_reward_storage",
                        ctx -> new SeasonRewardStorage(),
                        ctx -> CompoundTag.CODEC.xmap(
                                tag -> {
                                    SeasonRewardStorage data = new SeasonRewardStorage();
                                    data.read(tag);
                                    return data;
                                },
                                data -> data.save(new CompoundTag())
                        )
                )
        );
    }

    public void read(CompoundTag tag) {
        rewardsByPlayer.clear();
        claimed.clear();

        ListTag players = tag.getListOrEmpty("players");

        for (int i = 0; i < players.size(); i++) {
            CompoundTag p = players.getCompoundOrEmpty(i);

            String uuidStr = p.getStringOr("uuid", "");
            if (uuidStr.isEmpty()) continue;

            UUID uuid = UUID.fromString(uuidStr);

            boolean wasClaimed = p.getBooleanOr("claimed", false);

            List<RewardEntry> rewards = new ArrayList<>();

            ListTag rList = p.getListOrEmpty("rewards");

            for (int j = 0; j < rList.size(); j++) {
                CompoundTag r = rList.getCompoundOrEmpty(j);

                rewards.add(new RewardEntry(
                        r.getStringOr("name", ""),
                        r.getIntOr("amount", 0)
                ));
            }

            rewardsByPlayer.put(uuid, rewards);

            if (wasClaimed) {
                claimed.add(uuid);
            }
        }
    }

    public CompoundTag save(CompoundTag tag) {

        ListTag players = new ListTag();

        for (var entry : rewardsByPlayer.entrySet()) {

            CompoundTag p = new CompoundTag();

            UUID uuid = entry.getKey();

            p.putString("uuid", uuid.toString());
            p.putBoolean("claimed", claimed.contains(uuid));

            ListTag rewardsList = new ListTag();

            for (RewardEntry r : entry.getValue()) {

                CompoundTag rTag = new CompoundTag();
                rTag.putString("name", r.name());
                rTag.putInt("amount", r.amount());

                rewardsList.add(rTag);
            }

            p.put("rewards", rewardsList);
            players.add(p);
        }

        tag.put("players", players);
        return tag;
    }

    public void setRewards(UUID uuid, List<RewardEntry> rewards) {
        rewardsByPlayer.put(uuid, rewards);
        setDirty();
    }

    public List<RewardEntry> getRewards(UUID uuid) {
        return rewardsByPlayer.getOrDefault(uuid, List.of());
    }

    public boolean canClaim(UUID uuid) {
        return rewardsByPlayer.containsKey(uuid) && !claimed.contains(uuid);
    }

    public void markClaimed(UUID uuid) {
        claimed.add(uuid);
        setDirty();
    }

    public void resetSeason() {
        rewardsByPlayer.clear();
        claimed.clear();
        setDirty();
    }
}