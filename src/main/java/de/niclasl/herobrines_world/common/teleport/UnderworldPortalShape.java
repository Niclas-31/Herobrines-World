package de.niclasl.herobrines_world.common.teleport;

import de.niclasl.herobrines_world.common.registries.blocks.ModBlocks;
import de.niclasl.herobrines_world.common.registries.blocks.custom.UnderworldPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class UnderworldPortalShape {
	private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.getBlock() == ModBlocks.ABYSSAL_BLOCK.get();
	private final Direction.Axis axis;
	private final Direction rightDir;
	private final int numPortalBlocks;
	private final BlockPos bottomLeft;
	private final int height;
	private final int width;

	public UnderworldPortalShape(Direction.Axis axis, int numPortalBlocks, Direction rightDir, BlockPos bottomLeft,
	                                  int width, int height) {
		this.axis = axis;
		this.numPortalBlocks = numPortalBlocks;
		this.rightDir = rightDir;
		this.bottomLeft = bottomLeft;
		this.width = width;
		this.height = height;
	}

	public static Optional<UnderworldPortalShape> findEmptyPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
		return findPortalShape(level, bottomLeft, shape -> shape.isValid() && shape.numPortalBlocks == 0, axis);
	}

	public static Optional<UnderworldPortalShape> findPortalShape(LevelAccessor level, BlockPos bottomLeft, Predicate<UnderworldPortalShape> predicate, Direction.Axis axis) {
		Optional<UnderworldPortalShape> optional = Optional.of(findAnyShape(level, bottomLeft, axis)).filter(predicate);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(findAnyShape(level, bottomLeft, axis1)).filter(predicate);
		}
	}

	public static UnderworldPortalShape findAnyShape(BlockGetter level, BlockPos bottomLeft, Direction.Axis axis) {
		Direction direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos blockpos = calculateBottomLeft(level, direction, bottomLeft);
		if (blockpos == null) {
			return new UnderworldPortalShape(axis, 0, direction, bottomLeft, 0, 0);
		} else {
			int i = calculateWidth(level, blockpos, direction);
			if (i == 0) {
				return new UnderworldPortalShape(axis, 0, direction, blockpos, 0, 0);
			} else {
				MutableInt mutableint = new MutableInt();
				int j = calculateHeight(level, blockpos, direction, i, mutableint);
				return new UnderworldPortalShape(axis, mutableint.intValue(), direction, blockpos, i, j);
			}
		}
	}

	private static @Nullable BlockPos calculateBottomLeft(BlockGetter level, Direction p_direction, BlockPos pos) {
		int i = Math.max(level.getMinY(), pos.getY() - 21);

		while (pos.getY() > i && isEmpty(level.getBlockState(pos.below()))) {
			pos = pos.below();
		}

		Direction direction = p_direction.getOpposite();
		int j = getDistanceUntilEdgeAboveFrame(level, pos, direction) - 1;
		return j < 0 ? null : pos.relative(direction, j);
	}

	private static int calculateWidth(BlockGetter level, BlockPos bottomLeft, Direction direction) {
		int i = getDistanceUntilEdgeAboveFrame(level, bottomLeft, direction);
		return i >= 2 && i <= 21 ? i : 0;
	}

	private static int getDistanceUntilEdgeAboveFrame(BlockGetter level, BlockPos pos, Direction direction) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i <= 21; i++) {
			mutableBlockPos.set(pos).move(direction, i);
			BlockState blockstate = level.getBlockState(mutableBlockPos);
			if (!isEmpty(blockstate)) {
				if (FRAME.test(blockstate, level, mutableBlockPos)) {
					return i;
				}
				break;
			}

			BlockState blockstate1 = level.getBlockState(mutableBlockPos.move(Direction.DOWN));
			if (!FRAME.test(blockstate1, level, mutableBlockPos)) {
				break;
			}
		}

		return 0;
	}

	private static int calculateHeight(BlockGetter level, BlockPos pos, Direction direction, int width, MutableInt portalBlocks) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		int i = getDistanceUntilTop(level, pos, direction, mutableBlockPos, width, portalBlocks);
		return i >= 3 && i <= 21 && hasTopFrame(level, pos, direction, mutableBlockPos, width, i) ? i : 0;
	}

	private static boolean hasTopFrame(BlockGetter level, BlockPos pos, Direction direction,
	                                   BlockPos.MutableBlockPos checkPos, int width, int distanceUntilTop) {
		for (int i = 0; i < width; i++) {
			BlockPos.MutableBlockPos blockpos = checkPos.set(pos).move(Direction.UP, distanceUntilTop).move(direction, i);
			if (!FRAME.test(level.getBlockState(blockpos), level, blockpos)) {
				return false;
			}
		}

		return true;
	}

	private static int getDistanceUntilTop(BlockGetter level, BlockPos pos, Direction direction,
	                                       BlockPos.MutableBlockPos checkPos, int width, MutableInt portalBlocks) {
		for (int i = 0; i < 21; i++) {
			checkPos.set(pos).move(Direction.UP, i).move(direction, -1);
			if (!FRAME.test(level.getBlockState(checkPos), level, checkPos)) {
				return i;
			}

			checkPos.set(pos).move(Direction.UP, i).move(direction, width);
			if (!FRAME.test(level.getBlockState(checkPos), level, checkPos)) {
				return i;
			}

			for (int j = 0; j < width; j++) {
				checkPos.set(pos).move(Direction.UP, i).move(direction, j);
				BlockState blockstate = level.getBlockState(checkPos);
				if (!isEmpty(blockstate)) {
					return i;
				}

				if (blockstate.getBlock() == ModBlocks.UNDERWORLD_PORTAL.get()) {
					portalBlocks.increment();
				}
			}
		}
		return 21;
	}

	private static boolean isEmpty(BlockState state) {
		return state.isAir() || state.is(ModBlocks.UNDERWORLD_PORTAL.get());
	}

	public boolean isValid() {
		return this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
	}

	public void createPortalBlocks(LevelAccessor level) {
		BlockState blockstate = ModBlocks.UNDERWORLD_PORTAL.get().defaultBlockState().setValue(UnderworldPortalBlock.AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
				.forEach(pos -> level.setBlock(pos, blockstate, 18));
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity,
	                                             EntityDimensions dimensions) {
		if (!(dimensions.width() > 4.0F) && !(dimensions.height() > 4.0F)) {
			double d0 = dimensions.height() / 2.0;
			Vec3 vec3 = pos.add(0.0, d0, 0.0);
			VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, dimensions.width(), 0.0, dimensions.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6));
			Optional<Vec3> optional = level.findFreePosition(entity, voxelshape, vec3, dimensions.width(), dimensions.height(), dimensions.width());
			Optional<Vec3> optional1 = optional.map(p_259019_ -> p_259019_.subtract(0.0, d0, 0.0));
			return optional1.orElse(pos);
		} else {
			return pos;
		}
	}
}