package de.niclasl.herobrines_world.block.custom;

import net.minecraft.BlockUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import de.niclasl.herobrines_world.world.teleporter.UnderworldTeleporter;
import de.niclasl.herobrines_world.world.teleporter.UnderworldPortalShape;

import javax.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;

import com.mojang.logging.LogUtils;

public class UnderworldPortal extends NetherPortalBlock {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static void portalSpawn(Level world, BlockPos pos) {
		Optional<UnderworldPortalShape> optional = UnderworldPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
        optional.ifPresent(underworldPortalShape -> underworldPortalShape.createPortalBlocks(world));
	}

	public UnderworldPortal(BlockBehaviour.Properties properties) {
		super(properties.noCollision().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 0).noLootTable());
	}

	private UnderworldTeleporter getTeleporter(ServerLevel level) {
		return new UnderworldTeleporter(level);
	}

	@Override
	protected @NotNull BlockState updateShape(BlockState state, @NotNull LevelReader reader,
											  @NotNull ScheduledTickAccess tickAccess, @NotNull BlockPos pos,
											  Direction direction, @NotNull BlockPos newPos,
											  @NotNull BlockState newState, @NotNull RandomSource random) {
		Direction.Axis axis = direction.getAxis();
		Direction.Axis axis1 = state.getValue(AXIS);
		boolean flag = axis1 != axis && axis.isHorizontal();
		return !flag && !newState.is(this) && !UnderworldPortalShape.findAnyShape(reader, pos, axis1).isComplete()
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, reader, tickAccess, pos, direction, newPos, newState, random);
	}

	@Override
	@Nullable
	public TeleportTransition getPortalDestination(ServerLevel level, @NotNull Entity entity, @NotNull BlockPos pos) {
		ResourceKey<Level> resourcekey = level.dimension() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("herobrines_world:underworld"))
				? Level.OVERWORLD
				: ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("herobrines_world:underworld"));
		ServerLevel serverlevel = level.getServer().getLevel(resourcekey);
		if (serverlevel == null) {
			return null;
		} else {
			boolean flag = serverlevel.dimension() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("herobrines_world:underworld"));
			WorldBorder worldborder = serverlevel.getWorldBorder();
			double d0 = DimensionType.getTeleportationScale(level.dimensionType(), serverlevel.dimensionType());
			BlockPos blockpos = worldborder.clampToBounds(pos.getX() * d0, pos.getY(), pos.getZ() * d0);
			return this.getExitPortal(serverlevel, entity, pos, blockpos, flag, worldborder);
		}
	}

	@Nullable
	private TeleportTransition getExitPortal(ServerLevel level, Entity entity, BlockPos pos, BlockPos newPos, boolean flag, WorldBorder border) {
		Optional<BlockPos> optional = getTeleporter(level).findClosestPortalPosition(newPos, flag, border);
		BlockUtil.FoundRectangle foundRectangle;
		TeleportTransition.PostTeleportTransition teleportTransition;
		if (optional.isPresent()) {
			BlockPos blockpos = optional.get();
			BlockState blockstate = level.getBlockState(blockpos);
			foundRectangle = BlockUtil.getLargestRectangleAround(blockpos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, p_351970_ -> level.getBlockState(p_351970_) == blockstate);
			teleportTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(p_351967_ -> p_351967_.placePortalTicket(blockpos));
		} else {
			Direction.Axis axis = entity.level().getBlockState(pos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
			Optional<BlockUtil.FoundRectangle> optional1 = getTeleporter(level).createPortal(newPos, axis);
			if (optional1.isEmpty()) {
				LOGGER.error("Unable to create a portal, likely target out of worldborder");
				return null;
			}
			foundRectangle = optional1.get();
			teleportTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
		}
		return getDimensionTransitionFromExit(entity, pos, foundRectangle, level, teleportTransition);
	}

	private static TeleportTransition getDimensionTransitionFromExit(Entity entity, BlockPos pos, BlockUtil.FoundRectangle rectangle, ServerLevel level, TeleportTransition.PostTeleportTransition teleport) {
		BlockState blockstate = entity.level().getBlockState(pos);
		Direction.Axis axis;
		Vec3 vec3;
		if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
			axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
			BlockUtil.FoundRectangle foundRectangle = BlockUtil.getLargestRectangleAround(pos, axis, 21, Direction.Axis.Y, 21, p_351016_ -> entity.level().getBlockState(p_351016_) == blockstate);
			vec3 = entity.getRelativePortalPosition(axis, foundRectangle);
		} else {
			axis = Direction.Axis.X;
			vec3 = new Vec3(0.5, 0.0, 0.0);
		}
		return createDimensionTransition(level, rectangle, axis, vec3, entity, teleport);
	}

	private static TeleportTransition createDimensionTransition(ServerLevel level,
																BlockUtil.FoundRectangle foundRectangle,
																Direction.Axis axis, Vec3 pos,
																Entity entity,
																TeleportTransition.PostTeleportTransition teleportTransition) 
	{
		BlockPos blockpos = foundRectangle.minCorner;
		BlockState blockstate = level.getBlockState(blockpos);
		Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
		double d0 = foundRectangle.axis1Size;
		double d1 = foundRectangle.axis2Size;
		EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
		int i = axis == direction$axis ? 0 : 90;
		double d2 = entitydimensions.width() / 2.0 + (d0 - entitydimensions.width()) * pos.x();
		double d3 = (d1 - entitydimensions.height()) * pos.y();
		double d4 = 0.5 + pos.z();
		boolean flag = direction$axis == Direction.Axis.X;
		Vec3 vec3 = new Vec3(blockpos.getX() + (flag ? d2 : d4), blockpos.getY() + d3, blockpos.getZ() + (flag ? d4 : d2));
		Vec3 vec31 = UnderworldPortalShape.findCollisionFreePosition(vec3, level, entity, entitydimensions);
		return new TeleportTransition(level, vec31, Vec3.ZERO, i, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), teleportTransition);
	}

	@Override
	public int getPortalTransitionTime(@NotNull ServerLevel world, @NotNull Entity entity) {
		return 0;
	}

	@Override
	public Portal.@NotNull Transition getLocalTransition() {
		return Transition.CONFUSION;
	}

	@Override
	public void randomTick(@NotNull BlockState blockstate, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
		for (int i = 0; i < 4; i++) {
			double px = pos.getX() + random.nextFloat();
			double py = pos.getY() + random.nextFloat();
			double pz = pos.getZ() + random.nextFloat();
			double vx = (random.nextFloat() - 0.5) / 2.;
			double vy = (random.nextFloat() - 0.5) / 2.;
			double vz = (random.nextFloat() - 0.5) / 2.;
			int j = random.nextInt(4) - 1;
			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
				px = pos.getX() + 0.5 + 0.25 * j;
				vx = random.nextFloat() * 2 * j;
			} else {
				pz = pos.getZ() + 0.5 + 0.25 * j;
				vz = random.nextFloat() * 2 * j;
			}
			world.addParticle(ParticleTypes.PORTAL, px, py, pz, vx, vy, vz);
		}
		if (random.nextInt(110) == 0)
			world.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("block.portal.ambient"))), SoundSource.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
	}
}
