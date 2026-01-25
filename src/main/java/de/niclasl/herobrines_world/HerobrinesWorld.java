package de.niclasl.herobrines_world;

import de.niclasl.herobrines_world.block.ModBlocks;
import de.niclasl.herobrines_world.block.entity.ModBlockEntities;
import de.niclasl.herobrines_world.block.entity.renderer.DelayerRenderer;
import de.niclasl.herobrines_world.entity.ModEntities;
import de.niclasl.herobrines_world.init.*;
import de.niclasl.herobrines_world.item.ModItems;
import de.niclasl.herobrines_world.potion.ModMobEffects;
import de.niclasl.herobrines_world.potion.ModPotions;
import de.niclasl.herobrines_world.procedures.OwnerTargetTracker;
import de.niclasl.herobrines_world.world.inventory.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.util.Tuple;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;

import de.niclasl.herobrines_world.network.ModVariables;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

@Mod("herobrines_world")
public class HerobrinesWorld {
	public static final Logger LOGGER = LogManager.getLogger(HerobrinesWorld.class);
	public static final String MODID = "herobrines_world";

	public HerobrinesWorld(IEventBus modEventBus) {
		NeoForge.EVENT_BUS.register(this);

		modEventBus.addListener(this::registerNetworking);

		ModBlocks.register(modEventBus);
		ModItems.register(modEventBus);
		ModEntities.REGISTRY.register(modEventBus);
		ModTabs.register(modEventBus);
		ModVariables.ATTACHMENT_TYPES.register(modEventBus);
		OwnerTargetTracker.register();
		ModPotions.register(modEventBus);
		ModMobEffects.register(modEventBus);
		ModMenus.register(modEventBus);
		ModBlockEntities.register(modEventBus);
		ModVillagerProfessions.PROFESSIONS.register(modEventBus);
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
		}
	}
}


