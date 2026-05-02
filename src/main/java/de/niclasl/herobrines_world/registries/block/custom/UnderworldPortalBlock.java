package de.niclasl.herobrines_world.registries.block.custom;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import de.niclasl.herobrines_world.teleporter.HerobrinesRealmPortalShape;
import de.niclasl.herobrines_world.teleporter.HerobrinesRealmTeleporter;
import de.niclasl.herobrines_world.teleporter.UnderworldPortalShape;
import de.niclasl.herobrines_world.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;

public class UnderworldPortalBlock extends Block implements Portal {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final MapCodec<UnderworldPortalBlock> CODEC = simpleCodec(UnderworldPortalBlock::new);
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0));

	@Override
	public @NonNull MapCodec<UnderworldPortalBlock> codec() {
		return CODEC;
	}

	public UnderworldPortalBlock(BlockBehaviour.Properties p_54909_) {
		super(p_54909_);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
	}

	public static boolean portalSpawn(Level level, BlockPos pos) {
		Optional<UnderworldPortalShape> shape =
				UnderworldPortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);

		if (shape.isPresent()) {
			shape.get().createPortalBlocks(level);
			return true;
		}

		return false;
	}

	@Override
	protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level,
	                                       @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPES.get(state.getValue(AXIS));
	}

	@Override
	protected void randomTick(@NonNull BlockState state, ServerLevel level, @NonNull BlockPos pos,
	                          @NonNull RandomSource source) {
		if (level.isSpawningMonsters()
				&& level.environmentAttributes().getValue(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, pos)
				&& source.nextInt(2000) < level.getDifficulty().getId()
				&& level.anyPlayerCloseEnoughForSpawning(pos)) {
			while (level.getBlockState(pos).is(this)) {
				pos = pos.below();
			}

			if (level.getBlockState(pos).isValidSpawn(level, pos, EntityType.ZOMBIFIED_PIGLIN)) {
				Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(level, pos.above(), EntitySpawnReason.STRUCTURE);
				if (entity != null) {
					entity.setPortalCooldown();
					Entity entity1 = entity.getVehicle();
					if (entity1 != null) {
						entity1.setPortalCooldown();
					}
				}
			}
		}
	}

	@Override
	protected @NonNull BlockState updateShape(
			BlockState state,
			@NonNull LevelReader reader,
			@NonNull ScheduledTickAccess access,
			@NonNull BlockPos pos,
			Direction direction,
			@NonNull BlockPos neighborPos,
			@NonNull BlockState neighborState,
			@NonNull RandomSource source
	) {
		Direction.Axis direction$axis = direction.getAxis();
		Direction.Axis direction$axis1 = state.getValue(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && !neighborState.is(this) && !HerobrinesRealmPortalShape.findAnyShape(reader, pos, direction$axis1).isComplete()
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, reader, access, pos, direction, neighborPos, neighborState, source);
	}

	@Override
	protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
	                            Entity entity, @NonNull InsideBlockEffectApplier applier, boolean isInside) {
		if (entity.canUsePortal(false)) {
			entity.setAsInsidePortal(this, pos);
		}
	}

	@Override
	public int getPortalTransitionTime(@NonNull ServerLevel level, @NonNull Entity entity) {
		return entity instanceof Player player
				? Math.max(
				0,
				level.getGameRules()
				.get(player.getAbilities().invulnerable ? GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY)
		)
				: 0;
	}

	@Override
	public @org.jspecify.annotations.Nullable TeleportTransition getPortalDestination(ServerLevel level, @NonNull Entity entity, @NonNull BlockPos pos) {
		ResourceKey<Level> resourcekey = level.dimension() == ModDimensions.UNDERWORLD ? Level.OVERWORLD : ModDimensions.UNDERWORLD;
		ServerLevel serverlevel = level.getServer().getLevel(resourcekey);
		if (serverlevel == null) {
			return null;
		} else {
			boolean flag = serverlevel.dimension() == ModDimensions.UNDERWORLD;
			WorldBorder worldborder = serverlevel.getWorldBorder();
			double d0 = DimensionType.getTeleportationScale(level.dimensionType(), serverlevel.dimensionType());
			BlockPos blockpos = worldborder.clampToBounds(entity.getX() * d0, entity.getY(), entity.getZ() * d0);
			return this.getExitPortal(serverlevel, entity, pos, blockpos, flag, worldborder);
		}
	}

	private @Nullable TeleportTransition getExitPortal(
			ServerLevel level, Entity entity, BlockPos pos, BlockPos exitPos, boolean isNether, WorldBorder worldBorder
	) {
		HerobrinesRealmTeleporter teleporter = new HerobrinesRealmTeleporter(level);

		Optional<BlockPos> optional = teleporter.findClosestPortalPosition(exitPos, isNether, worldBorder);
		BlockUtil.FoundRectangle blockutil$foundrectangle;
		TeleportTransition.PostTeleportTransition teleporttransition$postteleporttransition;
		if (optional.isPresent()) {
			BlockPos blockpos = optional.get();
			BlockState blockstate = level.getBlockState(blockpos);
			blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(
					blockpos,
					blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS),
					21,
					Direction.Axis.Y,
					21,
					p_351970_ -> level.getBlockState(p_351970_) == blockstate
			);
			teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(p_351967_ -> p_351967_.placePortalTicket(blockpos));
		} else {
			Direction.Axis direction$axis = entity.level().getBlockState(pos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
			Optional<BlockUtil.FoundRectangle> optional1 = teleporter.createPortal(exitPos, direction$axis);
			if (optional1.isEmpty()) {
				LOGGER.error("Unable to create a portal, likely target out of worldborder");
				return null;
			}

			blockutil$foundrectangle = optional1.get();
			teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
		}

		return getDimensionTransitionFromExit(entity, pos, blockutil$foundrectangle, level, teleporttransition$postteleporttransition);
	}

	private static TeleportTransition getDimensionTransitionFromExit(
			Entity entity, BlockPos pos, BlockUtil.FoundRectangle rectangle, ServerLevel level, TeleportTransition.PostTeleportTransition postTeleportTransition
	) {
		BlockState blockstate = entity.level().getBlockState(pos);
		Direction.Axis direction$axis;
		Vec3 vec3;
		if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
			direction$axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
			BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(
					pos, direction$axis, 21, Direction.Axis.Y, 21, p_351016_ -> entity.level().getBlockState(p_351016_) == blockstate
			);
			vec3 = entity.getRelativePortalPosition(direction$axis, blockutil$foundrectangle);
		} else {
			direction$axis = Direction.Axis.X;
			vec3 = new Vec3(0.5, 0.0, 0.0);
		}

		return createDimensionTransition(level, rectangle, direction$axis, vec3, entity, postTeleportTransition);
	}

	private static TeleportTransition createDimensionTransition(
			ServerLevel level,
			BlockUtil.FoundRectangle rectangle,
			Direction.Axis axis,
			Vec3 offset,
			Entity entity,
			TeleportTransition.PostTeleportTransition postTeleportTransition
	) {
		BlockPos blockpos = rectangle.minCorner;
		BlockState blockstate = level.getBlockState(blockpos);
		Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
		double d0 = rectangle.axis1Size;
		double d1 = rectangle.axis2Size;
		EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
		int i = axis == direction$axis ? 0 : 90;
		double d2 = entitydimensions.width() / 2.0 + (d0 - entitydimensions.width()) * offset.x();
		double d3 = (d1 - entitydimensions.height()) * offset.y();
		double d4 = 0.5 + offset.z();
		boolean flag = direction$axis == Direction.Axis.X;
		Vec3 vec3 = new Vec3(blockpos.getX() + (flag ? d2 : d4), blockpos.getY() + d3, blockpos.getZ() + (flag ? d4 : d2));
		Vec3 vec31 = HerobrinesRealmPortalShape.findCollisionFreePosition(vec3, level, entity, entitydimensions);
		return new TeleportTransition(level, vec31, Vec3.ZERO, i, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition);
	}

	@Override
	public Portal.@NonNull Transition getLocalTransition() {
		return Portal.Transition.CONFUSION;
	}

	@Override
	public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, RandomSource source) {
		if (source.nextInt(100) == 0) {
			level.playLocalSound(
					pos.getX() + 0.5,
					pos.getY() + 0.5,
					pos.getZ() + 0.5,
					SoundEvents.PORTAL_AMBIENT,
					SoundSource.BLOCKS,
					0.5F,
					source.nextFloat() * 0.4F + 0.8F,
					false
			);
		}

		for (int i = 0; i < 4; i++) {
			double d0 = pos.getX() + source.nextDouble();
			double d1 = pos.getY() + source.nextDouble();
			double d2 = pos.getZ() + source.nextDouble();
			double d3 = (source.nextFloat() - 0.5) * 0.5;
			double d4 = (source.nextFloat() - 0.5) * 0.5;
			double d5 = (source.nextFloat() - 0.5) * 0.5;
			int j = source.nextInt(2) * 2 - 1;
			if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
				d0 = pos.getX() + 0.5 + 0.25 * j;
				d3 = source.nextFloat() * 2.0F * j;
			} else {
				d2 = pos.getZ() + 0.5 + 0.25 * j;
				d5 = source.nextFloat() * 2.0F * j;
			}

			level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader reader, @NonNull BlockPos pos, @NonNull BlockState state, boolean p_386478_) {
		return ItemStack.EMPTY;
	}

	@Override
	protected @NonNull BlockState rotate(@NonNull BlockState state, Rotation rot) {
		return switch (rot) {
			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
				case X -> state.setValue(AXIS, Direction.Axis.Z);
				case Z -> state.setValue(AXIS, Direction.Axis.X);
				default -> state;
			};
			default -> state;
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}