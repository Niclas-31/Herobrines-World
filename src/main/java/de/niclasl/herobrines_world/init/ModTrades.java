package de.niclasl.herobrines_world.init;

import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

@EventBusSubscriber
public class ModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == ResourceKey.create(Registries.VILLAGER_PROFESSION, ResourceLocation.parse("herobrines_world:lumberjack"))) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.OAK_LOG), new ItemStack(Items.EMERALD), 12, 7, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.BIRCH_LOG), new ItemStack(Items.EMERALD), 12, 5, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.SPRUCE_LOG), new ItemStack(Items.EMERALD), 12, 5, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.JUNGLE_LOG), new ItemStack(Items.EMERALD), 12, 5, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.ACACIA_LOG), new ItemStack(Items.EMERALD), 12, 5, 0.05f));
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(Blocks.DARK_OAK_LOG), new ItemStack(Items.EMERALD), 12, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Blocks.MANGROVE_LOG), new ItemStack(Items.EMERALD, 2), 15, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Blocks.BAMBOO_BLOCK), new ItemStack(Items.EMERALD, 2), 15, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(Blocks.CHERRY_LOG), new ItemStack(Items.EMERALD, 2), 15, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Blocks.PALE_OAK_LOG), new ItemStack(Items.EMERALD, 3), 20, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Blocks.WARPED_STEM), new ItemStack(Items.EMERALD, 3), 20, 5, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(Blocks.CRIMSON_STEM), new ItemStack(Items.EMERALD, 3), 20, 5, 0.05f));
		}
		if (event.getType() == ResourceKey.create(Registries.VILLAGER_PROFESSION, ResourceLocation.parse("herobrines_world:lumberjack"))) {
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.IRON_AXE), 1, 5, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12), new ItemStack(Items.DIAMOND_AXE), 1, 5, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 25), new ItemStack(Items.NETHERITE_AXE), 1, 5, 0.05f));
		}
	}
}