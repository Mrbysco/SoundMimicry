package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MimicryLanguageProvider extends LanguageProvider {
	public MimicryLanguageProvider(PackOutput output) {
		super(output, Constants.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addBlock(MimicryRegistry.SOUND_EMITTER, "Sound Emitter");

		add("soundmimicry.sound", "Sound Identifier");
		add("soundmimicry.sound.tooltip", "The ID of the sound");
		add("soundmimicry.soundSource", "Sound Source");
		add("soundmimicry.soundSource.tooltip", "The source type of the sound");
		add("soundmimicry.setSound", "Set Sound");
		add("soundmimicry.offset", "Offset");
		add("soundmimicry.offset.tooltip", "The offset of the sound from the emitter's position");
		add("soundmimicry.volume", "Volume");
		add("soundmimicry.volume.tooltip", "The volume of the sound");
		add("soundmimicry.pitch", "Pitch");
		add("soundmimicry.pitch.tooltip", "The pitch of the sound");
		add("soundmimicry.minVolume", "Min Volume");
		add("soundmimicry.minVolume.tooltip", "The minimum volume of the sound");

		add("soundmimicry.networking.set_sound_data.failed", "Failed to set Sound Emitter data %s");
	}
}
