package com.mrbysco.soundmimicry;

import com.mrbysco.soundmimicry.networking.SetSoundDataPayload;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;

public class SoundMimicryFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		CommonClass.init();

		PayloadTypeRegistry.serverboundPlay().register(SetSoundDataPayload.ID, SetSoundDataPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SetSoundDataPayload.ID, (payload, context) -> {
			MinecraftServer server = context.player().level().getServer();
			server.execute(() -> {
				if (context.player() instanceof ServerPlayer player) {
					var dimensionKey = ResourceKey.create(Registries.DIMENSION, payload.dimension());
					ServerLevel level = server.getLevel(dimensionKey);
					CommonClass.handleSetSound(level, player, payload);
				}
			});
		});

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
			entries.accept(MimicryRegistry.SOUND_EMITTER.get());
		});
	}
}
