package de.niclasl.herobrines_world.world.teleporter;

import org.apache.commons.lang3.mutable.MutableInt;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.block.ModBlocks;

import javax.annotation.Nullable;

import java.util.function.Predicate;
import java.util.Optional;

public class HerobrinesRealmPortalShape {
	private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.getBlock() == ModBlocks.CURSED_STONE.get();
	private final Direction.Axis axis;
	private final Direction rightDir;
	private final int numPortalBlocks;
	private final BlockPos bottomLeft;
	private final int height;
	private final int width;

	public HerobrinesRealmPortalShape(Direction.Axis axis, int numPortalBlocks, Direction rightDir, BlockPos bottomLeft,
									  int width, int height) {
		this.axis = axis;
		this.numPortalBlocks = numPortalBlocks;
		this.rightDir = rightDir;
		this.bottomLeft = bottomLeft;
		this.width = width;
		this.height = height;
	}

	public static Optional<HerobrinesRealmPortalShape> findEmptyPortalShape(LevelAccessor levelAccessor, BlockPos pos, Direction.Axis axis) {
		return findPortalShape(levelAccessor, pos, shape -> shape.isValid() && shape.numPortalBlocks == 0, axis);
	}

	public static Optional<HerobrinesRealmPortalShape> findPortalShape(LevelAccessor levelAccessor, BlockPos pos, Predicate<HerobrinesRealmPortalShape> shape, Direction.Axis axis) {
		Optional<HerobrinesRealmPortalShape> optional = Optional.of(findAnyShape(levelAccessor, pos, axis)).filter(shape);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(findAnyShape(levelAccessor, pos, axis1)).filter(shape);
		}
	}

	public static HerobrinesRealmPortalShape findAnyShape(BlockGetter getter, BlockPos pos, Direction.Axis axis) {
		Direction direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos blockpos = calculateBottomLeft(getter, direction, pos);
		if (blockpos == null) {
			return new HerobrinesRealmPortalShape(axis, 0, direction, pos, 0, 0);
		} else {
			int i = calculateWidth(getter, blockpos, direction);
			if (i == 0) {
				return new HerobrinesRealmPortalShape(axis, 0, direction, blockpos, 0, 0);
			} else {
				MutableInt mutableint = new MutableInt();
				int j = calculateHeight(getter, blockpos, direction, i, mutableint);
				return new HerobrinesRealmPortalShape(axis, mutableint.getValue(), direction, blockpos, i, j);
			}
		}
	}

	@Nullable
	private static BlockPos calculateBottomLeft(BlockGetter getter, Direction direction, BlockPos pos) {
		int i = Math.max(getter.getMinY(), pos.getY() - 21);
		while (pos.getY() > i && isEmpty(getter.getBlockState(pos.below()))) {
			pos = pos.below();
		}
		int j = getDistanceUntilEdgeAboveFrame(getter, pos, direction.getOpposite()) - 1;
		return j < 0 ? null : pos.relative(direction, j);
	}

	private static int calculateWidth(BlockGetter getter, BlockPos pos, Direction direction) {
		int i = getDistanceUntilEdgeAboveFrame(getter, pos, direction);
		return i >= 2 && i <= 21 ? i : 0;
	}

	private static int getDistanceUntilEdgeAboveFrame(BlockGetter getter, BlockPos pos, Direction direction) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (int i = 0; i <= 21; i++) {
			mutableBlockPos.set(pos).move(direction, i);
			BlockState blockstate = getter.getBlockState(mutableBlockPos);
			if (!isEmpty(blockstate)) {
				if (FRAME.test(blockstate, getter, mutableBlockPos)) {
					return i;
				}
				break;
			}
			BlockState blockstate1 = getter.getBlockState(mutableBlockPos.move(Direction.DOWN));
			if (!FRAME.test(blockstate1, getter, mutableBlockPos)) {
				break;
			}
		}
		return 0;
	}

	private static int calculateHeight(BlockGetter getter, BlockPos pos, Direction direction, int n, MutableInt mutableInt) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int i = getDistanceUntilTop(getter, pos, direction, mutableBlockPos, n, mutableInt);
		return i >= 3 && i <= 21 && hasTopFrame(getter, pos, direction, mutableBlockPos, n, i) ? i : 0;
	}

	private static boolean hasTopFrame(BlockGetter getter, BlockPos pos, Direction direction,
									   BlockPos.MutableBlockPos mutableBlockPos, int a, int n) {
		for (int i = 0; i < a; i++) {
			BlockPos.MutableBlockPos blockpos = mutableBlockPos.set(pos).move(Direction.UP, n).move(direction, i);
			if (!FRAME.test(getter.getBlockState(blockpos), getter, blockpos)) {
				return false;
			}
		}
		return true;
	}

	private static int getDistanceUntilTop(BlockGetter getter, BlockPos pos, Direction direction,
										   BlockPos.MutableBlockPos mutableBlockPos, int n, MutableInt mutableInt) {
		for (int i = 0; i < 21; i++) {
			mutableBlockPos.set(pos).move(Direction.UP, i).move(direction, -1);
			if (!FRAME.test(getter.getBlockState(mutableBlockPos), getter, mutableBlockPos)) {
				return i;
			}
			mutableBlockPos.set(pos).move(Direction.UP, i).move(direction, n);
			if (!FRAME.test(getter.getBlockState(mutableBlockPos), getter, mutableBlockPos)) {
				return i;
			}
			for (int j = 0; j < n; j++) {
				mutableBlockPos.set(pos).move(Direction.UP, i).move(direction, j);
				BlockState blockstate = getter.getBlockState(mutableBlockPos);
				if (!isEmpty(blockstate)) {
					return i;
				}
				if (blockstate.getBlock() == ModBlocks.HEROBRINES_REALM_PORTAL.get()) {
					mutableInt.increment();
				}
			}
		}
		return 21;
	}

	private static boolean isEmpty(BlockState state) {
		return state.isAir() || state.getBlock() == ModBlocks.HEROBRINES_REALM_PORTAL.get();
	}

	public boolean isValid() {
		return this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
	}

	public void createPortalBlocks(LevelAccessor levelAccessor) {
		BlockState blockstate = ModBlocks.HEROBRINES_REALM_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach(pos -> levelAccessor.setBlock(pos, blockstate, 18));
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity,
												 EntityDimensions entityDimensions) {
		if (!(entityDimensions.width() > 4.0F) && !(entityDimensions.height() > 4.0F)) {
			double d0 = entityDimensions.height() / 2.0;
			Vec3 vec3 = pos.add(0.0, d0, 0.0);
			VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, entityDimensions.width(), 0.0, entityDimensions.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6));
			Optional<Vec3> optional = level.findFreePosition(entity, voxelshape, vec3, entityDimensions.width(), entityDimensions.height(), entityDimensions.width());
			Optional<Vec3> optional1 = optional.map(p_259019_ -> p_259019_.subtract(0.0, d0, 0.0));
			return optional1.orElse(pos);
		} else {
			return pos;
		}
	}
}
