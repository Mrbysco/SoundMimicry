package com.mrbysco.soundmimicry;

import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.networking.SetSoundDataPayload;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class CommonClass {

	public static void init() {
		MimicryRegistry.loadClass();
	}

	public static void handleSetSound(ServerLevel level, ServerPlayer player, SetSoundDataPayload payload) {
		if (level.getBlockEntity(payload.pos()) instanceof SoundEmitterBlockEntity blockEntity) {
			blockEntity.setData(player, payload.soundLocation(), payload.soundSource(), payload.offset(),
					payload.volume(), payload.pitch(), payload.minVolume());
			blockEntity.refreshClient();
		}
	}
}