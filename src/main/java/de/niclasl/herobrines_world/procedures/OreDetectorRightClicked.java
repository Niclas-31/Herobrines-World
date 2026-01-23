package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

public class OreDetectorRightClicked {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity.isShiftKeyDown()) {
			if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Coal")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Copper";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §cCopper"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Copper")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Diamond";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §bDiamond"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Diamond")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Emerald";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §2Emerald"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Emerald")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Gold";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §6Gold"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Gold")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Iron";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §8Iron"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Iron")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Lapis";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §1Lapis"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Lapis")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Quartz";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §7Quartz"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Quartz")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Redstone";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §4Redstone"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Redstone")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Ancient_Debris";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §eAncient Debris"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ancient_Debris")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Toxenium";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §eToxenium"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Toxenium")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Ash";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §7Ash"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Ash")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Herobrine";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Herobrine"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Herobrine")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Frozen";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §9Frozen"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Frozen")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Green";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §aGreen"), false);
			} else if ((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("oreMode", "")).equals("Green")) {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Coal";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Coal"), false);
			} else {
				{
					final String _tagName = "oreMode";
					final String _tagValue = "Coal";
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Changed detector mode to §0Coal"), false);
			}
		}
	}
}