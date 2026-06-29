package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class MimicryLanguageProvider extends FabricLanguageProvider {
	public MimicryLanguageProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
		translationBuilder.add(MimicryRegistry.SOUND_EMITTER.get(), "Sound Emitter");

		translationBuilder.add("soundmimicry.sound", "Sound Identifier");
		translationBuilder.add("soundmimicry.sound.tooltip", "The ID of the sound");
		translationBuilder.add("soundmimicry.soundSource", "Sound Source");
		translationBuilder.add("soundmimicry.soundSource.tooltip", "The source type of the sound");
		translationBuilder.add("soundmimicry.setSound", "Set Sound");
		translationBuilder.add("soundmimicry.offset", "Offset");
		translationBuilder.add("soundmimicry.offset.tooltip", "The offset of the sound from the emitter's position");
		translationBuilder.add("soundmimicry.volume", "Volume");
		translationBuilder.add("soundmimicry.volume.tooltip", "The volume of the sound");
		translationBuilder.add("soundmimicry.pitch", "Pitch");
		translationBuilder.add("soundmimicry.pitch.tooltip", "The pitch of the sound");
		translationBuilder.add("soundmimicry.minVolume", "Min Volume");
		translationBuilder.add("soundmimicry.minVolume.tooltip", "The minimum volume of the sound");

		translationBuilder.add("soundmimicry.networking.set_sound_data.failed", "Failed to set Sound Emitter data %s");
	}
}
