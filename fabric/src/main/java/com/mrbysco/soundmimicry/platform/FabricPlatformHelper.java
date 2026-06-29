package com.mrbysco.soundmimicry.platform;

import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.platform.services.IPlatformHelper;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public BlockEntityType<SoundEmitterBlockEntity> createBlockEntityType() {
		return BlockEntityType.Builder.of(SoundEmitterBlockEntity::new, MimicryRegistry.SOUND_EMITTER.get()).build();
	}

	@Override
	public void sendPayloadToServer(CustomPacketPayload payload) {
		ClientPlayNetworking.send(payload);
	}
}
