package com.mrbysco.soundmimicry.platform;

import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.platform.services.IPlatformHelper;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {


	@Override
	public BlockEntityType<SoundEmitterBlockEntity> createBlockEntityType() {
		return BlockEntityType.Builder.of(SoundEmitterBlockEntity::new, MimicryRegistry.SOUND_EMITTER.get()).build(null);
	}

	@Override
	public void sendPayloadToServer(CustomPacketPayload payload) {
		PacketDistributor.sendToServer(payload);
	}
}
