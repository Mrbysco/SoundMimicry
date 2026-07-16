package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class MimicryBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

	public MimicryBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addOptional(MimicryRegistry.SOUND_EMITTER.get());
	}
}