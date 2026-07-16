package com.mrbysco.soundmimicry.client.screen;

import com.mrbysco.soundmimicry.networking.SetSoundDataPayload;
import com.mrbysco.soundmimicry.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class SoundEmitterEditScreen extends AbstractSoundEmitterEditScreen {
	private final String oldSoundLocation;
	private String oldSoundSource;
	private String oldOffset;
	private String oldVolume;
	private String oldPitch;
	private String oldMinVolume;
	private final BlockPos blockPos;
	private final Identifier dimension;

	public SoundEmitterEditScreen(BlockPos blockPos, Identifier dimension, String soundLocation, String soundSource, String offset, String volume, String pitch, String minVolume) {
		this.blockPos = blockPos;
		this.dimension = dimension;
		this.oldSoundLocation = soundLocation;
		this.oldSoundSource = soundSource;
		this.oldOffset = offset;
		this.oldVolume = volume;
		this.oldPitch = pitch;
		this.oldMinVolume = minVolume;
	}

	public static void openScreen(BlockPos pos, Identifier dimension, String soundLocation, String soundSource, String offset, String volume, String pitch, String minVolume) {
		Minecraft.getInstance().setScreen(new SoundEmitterEditScreen(pos, dimension, soundLocation, soundSource, offset, volume, pitch, minVolume));
	}

	@Override
	protected void init() {
		super.init();
		this.soundLocationEdit.setValue(this.oldSoundLocation);
		this.soundSourceEdit.setValue(this.oldSoundSource);
		this.offsetEdit.setValue(this.oldOffset);
		this.volumeEdit.setValue(this.oldVolume);
		this.pitchEdit.setValue(this.oldPitch);
		this.minVolumeEdit.setValue(this.oldMinVolume);
	}

	private void enableControls(boolean value) {
		this.doneButton.active = value;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		this.enableControls(true);
	}

	@Override
	protected void populateAndSendPacket() {
		Services.PLATFORM.sendPayloadToServer(new SetSoundDataPayload(blockPos, dimension, soundLocationEdit.getValue(), soundSourceEdit.getValue(), offsetEdit.getValue(), volumeEdit.getValue(), pitchEdit.getValue(), minVolumeEdit.getValue()));
	}
}