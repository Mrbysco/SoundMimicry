package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;

public class MimicryModelProvider extends FabricModelProvider {

	public MimicryModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.family(MimicryRegistry.SOUND_EMITTER.get());
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {

	}

	private ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
	}
}
