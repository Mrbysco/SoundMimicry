package com.mrbysco.soundmimicry.platform;

import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.platform.services.IPlatformHelper;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {


	@Override
	public BlockEntityType<SoundEmitterBlockEntity> createBlockEntityType() {
		return new BlockEntityType<>(SoundEmitterBlockEntity::new, MimicryRegistry.SOUND_EMITTER.get());
	}

	@Override
	public void sendPayloadToServer(CustomPacketPayload payload) {
		ClientPacketDistributor.sendToServer(payload);
	}
}
