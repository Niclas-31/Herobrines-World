package de.niclasl.herobrines_world.common.boss;

import com.mojang.serialization.Codec;
import de.niclasl.herobrines_world.HerobrinesWorld;
import de.niclasl.herobrines_world_api.structure.ArenaBox;
import de.niclasl.herobrines_world_api.structure.BossArena;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossArenaStorage extends SavedData {

    private final Map<Identifier, BossArena> arenas = new HashMap<>();

    public static final Codec<BossArenaStorage> CODEC = CompoundTag.CODEC.xmap(
            tag -> {
                BossArenaStorage instance = new BossArenaStorage();
                instance.read(tag);
                return instance;
            }, instance -> instance.save(new CompoundTag())
    );
    public static final SavedDataType<BossArenaStorage> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(HerobrinesWorld.MOD_ID, "boss_arenas"),
                    BossArenaStorage::new, CODEC);

    public void read(CompoundTag tag) {

        arenas.clear();

        CompoundTag arenasTag = tag.getCompoundOrEmpty("arenas");

        for (String structureIdString : arenasTag.keySet()) {

            Identifier structureId;

            try {
                structureId = Identifier.parse(structureIdString);
            } catch (Exception ignored) {
                continue;
            }

            CompoundTag structureTag =
                    arenasTag.getCompoundOrEmpty(structureIdString);

            CompoundTag originTag =
                    structureTag.getCompoundOrEmpty("origin");

            BlockPos origin = new BlockPos(
                    originTag.getIntOr("x", 0),
                    originTag.getIntOr("y", 0),
                    originTag.getIntOr("z", 0)
            );

            ListTag boxesTag =
                    structureTag.getListOrEmpty("boxes");

            List<ArenaBox> boxes = new ArrayList<>();

            for (int i = 0; i < boxesTag.size(); i++) {

                CompoundTag boxTag =
                        boxesTag.getCompoundOrEmpty(i);

                boxes.add(new ArenaBox(
                        boxTag.getDoubleOr("minX", 0.0),
                        boxTag.getDoubleOr("minY", 0.0),
                        boxTag.getDoubleOr("minZ", 0.0),

                        boxTag.getDoubleOr("maxX", 0.0),
                        boxTag.getDoubleOr("maxY", 0.0),
                        boxTag.getDoubleOr("maxZ", 0.0)
                ));
            }

            arenas.put(structureId, new BossArena(origin, boxes));
        }
    }

    public CompoundTag save(CompoundTag tag) {

        CompoundTag arenasTag = new CompoundTag();

        for (var entry : arenas.entrySet()) {

            Identifier structureId = entry.getKey();
            BossArena arena = entry.getValue();

            CompoundTag structureTag = new CompoundTag();

            BlockPos origin = arena.origin();

            CompoundTag originTag = new CompoundTag();

            originTag.putInt("x", origin.getX());
            originTag.putInt("y", origin.getY());
            originTag.putInt("z", origin.getZ());

            structureTag.put("origin", originTag);

            ListTag boxesTag = new ListTag();

            for (ArenaBox box : arena.boxes()) {

                CompoundTag boxTag = new CompoundTag();

                boxTag.putDouble("minX", box.minX());
                boxTag.putDouble("minY", box.minY());
                boxTag.putDouble("minZ", box.minZ());

                boxTag.putDouble("maxX", box.maxX());
                boxTag.putDouble("maxY", box.maxY());
                boxTag.putDouble("maxZ", box.maxZ());

                boxesTag.add(boxTag);
            }

            structureTag.put("boxes", boxesTag);

            arenasTag.put(structureId.toString(), structureTag);
        }

        tag.put("arenas", arenasTag);
        return tag;
    }

    public void setBoxes(
            Identifier structureId,
            BossArena arena
    ) {
        arenas.put(
                structureId,
                arena
        );

        setDirty();
    }

    public BossArena getArena(Identifier structureId) {
        return arenas.get(structureId);
    }
}