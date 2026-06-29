package com.mrbysco.soundmimicry.networking;

import com.mrbysco.soundmimicry.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetSoundDataPayload(BlockPos pos, ResourceLocation dimension, String soundLocation, String soundSource,
                                  String offset,
                                  String volume, String pitch, String minVolume) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SetSoundDataPayload> CODEC = CustomPacketPayload.codec(SetSoundDataPayload::write, SetSoundDataPayload::new);
	public static final Type<SetSoundDataPayload> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "set_sound_data"));


	public SetSoundDataPayload(final FriendlyByteBuf packetBuffer) {
		this(packetBuffer.readBlockPos(), packetBuffer.readResourceLocation(), packetBuffer.readUtf(),
				packetBuffer.readUtf(), packetBuffer.readUtf(), packetBuffer.readUtf(),
				packetBuffer.readUtf(), packetBuffer.readUtf());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeResourceLocation(dimension);
		buf.writeUtf(soundLocation);
		buf.writeUtf(soundSource);
		buf.writeUtf(offset);
		buf.writeUtf(volume);
		buf.writeUtf(pitch);
		buf.writeUtf(minVolume);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
