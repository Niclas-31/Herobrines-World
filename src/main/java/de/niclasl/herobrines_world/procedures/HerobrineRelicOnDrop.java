package de.niclasl.herobrines_world.procedures;

import de.niclasl.herobrines_world.item.ModItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;

@EventBusSubscriber
public class HerobrineRelicOnDrop {

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        ItemEntity entity = event.getEntity();
        ItemStack stack = entity.getItem();
        Player player = event.getPlayer();

        if (stack.is(ModItems.HEROBRINE_RELIC.get())) {
            event.setCanceled(true);
            player.addItem(stack.copy());
        }
    }
}