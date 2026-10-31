package de.niclasl.herobrines_world.common.registries.gamerules;

import de.niclasl.herobrines_world.HerobrinesWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = HerobrinesWorld.MOD_ID)
public class ModGameRules {

    public static GameRule<Boolean> THREE_HEARTS;

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.GAME_RULE)) {
            THREE_HEARTS = GameRules.registerBoolean(
                    "three_hearts",
                    GameRuleCategory.MISC,
                    true
            );
        }
    }
}