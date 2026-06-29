package com.mrbysco.soundmimicry.networking;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.networking.handler.ServerPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketHandler {
	public static void setupPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);

		registrar.playToServer(SetSoundDataPayload.ID, SetSoundDataPayload.CODEC,
				ServerPayloadHandler.getInstance()::handleSoundData);
	}
}
