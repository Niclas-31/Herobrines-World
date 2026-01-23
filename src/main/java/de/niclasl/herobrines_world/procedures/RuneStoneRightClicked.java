package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

public class RuneStoneRightClicked {
	public static void execute(Entity _ent) {
		if (_ent == null)
			return;
		if (_ent.isShiftKeyDown()) {
			if (!(_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("hasSavedPosition", false)) {
				{
					final String _tagName = "savedX";
					final double _tagValue = (_ent.getX());
					CustomData.update(DataComponents.CUSTOM_DATA, (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "savedY";
					final double _tagValue = (_ent.getY());
					CustomData.update(DataComponents.CUSTOM_DATA, (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "savedZ";
					final double _tagValue = (_ent.getZ());
					CustomData.update(DataComponents.CUSTOM_DATA, (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "savedDimension";
					final String _tagValue = ("" + _ent.level().dimension());
					CustomData.update(DataComponents.CUSTOM_DATA, (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putString(_tagName, _tagValue));
				}
				if (_ent instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("§2Position was successfully saved in the item!"), true);
				{
					final String _tagName = "hasSavedPosition";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
				}
			} else {
				if (_ent instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("§4A position has already been saved on this item!"), true);
			}
		} else {
			if (("" + _ent.level().dimension()).equals((_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("savedDimension", ""))
					&& (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("hasSavedPosition", false)) {
				{
                    _ent.teleportTo(((_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedX", 0)),
							((_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedY", 0)),
							((_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedZ", 0)));
					if (_ent instanceof ServerPlayer _serverPlayer) {
                        _serverPlayer.connection.teleport(_serverPlayer.getMainHandItem().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedX", 0),
                                _serverPlayer.getMainHandItem().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedY", 0),
                                _serverPlayer.getMainHandItem().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("savedZ", 0), _ent.getYRot(), _ent.getXRot());
                    }
				}
			} else if (!(("" + _ent.level().dimension())
					.equals((_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getStringOr("savedDimension", "")))
					&& (_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("hasSavedPosition", false)) {
				if (_ent instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("§4You cannot teleport to another dimension!"), true);
			} else if (!(_ent instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("hasSavedPosition", false)) {
				if (_ent instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("§4No position has been saved yet."), true);
			}
		}
	}
}