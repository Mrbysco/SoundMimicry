package com.mrbysco.soundmimicry.networking.handler;

import com.mrbysco.soundmimicry.CommonClass;
import com.mrbysco.soundmimicry.networking.SetSoundDataPayload;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleSoundData(final SetSoundDataPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer player) {
						MinecraftServer server = player.getServer();
						var dimensionKey = ResourceKey.create(Registries.DIMENSION, payload.dimension());
						ServerLevel level = server.getLevel(dimensionKey);
						CommonClass.handleSetSound(level, player, payload);
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("soundmimicry.networking.set_sound_data.failed", e.getMessage()));
					return null;
				});
	}
}
