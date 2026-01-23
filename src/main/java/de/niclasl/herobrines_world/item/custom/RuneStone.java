package de.niclasl.herobrines_world.item.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import de.niclasl.herobrines_world.procedures.RuneStoneRightClicked;
import org.jetbrains.annotations.NotNull;

public class RuneStone extends Item {
	public RuneStone(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE).stacksTo(1));
	}

	@Override
	public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		RuneStoneRightClicked.execute(entity);
		return ar;
	}
}