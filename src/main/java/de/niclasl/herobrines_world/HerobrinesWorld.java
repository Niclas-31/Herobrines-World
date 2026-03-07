package de.niclasl.herobrines_world;

import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.renderer.DelayerRenderer;
import de.niclasl.herobrines_world.block.entity.renderer.LogicGateBlockEntityRenderer;
import de.niclasl.herobrines_world.effect.ModEffects;
import de.niclasl.herobrines_world.enchantment.ModEnchantmentEffects;
import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.entity.custom.HerobrineBoss;
import de.niclasl.herobrines_world.item.ModCreativeModeTabs;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.network.ModVariables;
import de.niclasl.herobrines_world.potion.ModPotions;
import de.niclasl.herobrines_world.screen.ModMenuTypes;
import de.niclasl.herobrines_world.screen.custom.*;
import de.niclasl.herobrines_world.villager.ModVillagers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Tuple;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod("herobrines_world")
public class HerobrinesWorld {
	public static final String MODID = "herobrines_world";

    public HerobrinesWorld(IEventBus modEventBus) {

		modEventBus.addListener(this::registerNetworking);

		NeoForge.EVENT_BUS.register(this);

		ModCreativeModeTabs.register(modEventBus);

		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);

		ModEffects.register(modEventBus);
		ModPotions.register(modEventBus);

		ModEnchantmentEffects.register(modEventBus);
		ModEntities.register(modEventBus);

		ModVillagers.register(modEventBus);

		ModBlockEntities.register(modEventBus);

		ModMenuTypes.register(modEventBus);

		ModVariables.ATTACHMENT_TYPES.register(modEventBus);
		HerobrineBoss.register();

	}

	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}

	@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
	public static class Client {
		@SubscribeEvent
		public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(ModBlockEntities.DELAYER.get(), DelayerRenderer::new);
			event.registerBlockEntityRenderer(ModBlockEntities.LOGIC_GATE_BLOCK.get(), LogicGateBlockEntityRenderer::new);
		}

		@SubscribeEvent
		public static void registerScreens(RegisterMenuScreensEvent event) {
			event.register(ModMenuTypes.DELAYER.get(), DelayerScreen::new);
			event.register(ModMenuTypes.TIME.get(), TimeScreen::new);
			event.register(ModMenuTypes.TIMER.get(), TimerScreen::new);
			event.register(ModMenuTypes.SIGNAL_COLOR_CHANGER.get(), SignalColorChangerScreen::new);
			event.register(ModMenuTypes.SIGNAL_AMPLIFIER.get(), SignalAmplifierScreen::new);
		}
	}
}