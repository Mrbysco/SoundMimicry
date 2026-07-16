package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class MimicryLootProvider extends FabricBlockLootSubProvider {
	public MimicryLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	public void generate() {
		dropSelf(MimicryRegistry.SOUND_EMITTER.get());
	}
}
