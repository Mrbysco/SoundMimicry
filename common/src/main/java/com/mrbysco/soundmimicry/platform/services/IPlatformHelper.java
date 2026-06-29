package com.mrbysco.soundmimicry.platform.services;

import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IPlatformHelper {

	/**
	 * Creates a BlockEntityType for the SoundEmitterBlockEntity.
	 * @return the block entity type
	 */
	BlockEntityType<SoundEmitterBlockEntity> createBlockEntityType();

    /**
     * Sends a custom packet payload to the server.
     *
     * @param payload The payload to send to the server.
     */
    void sendPayloadToServer(CustomPacketPayload payload);

}
