package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MimicryStateProvider extends BlockStateProvider {
	public MimicryStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Constants.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		ResourceLocation location = MimicryRegistry.SOUND_EMITTER.getId();
		ModelFile model = models().cubeAll(location.getPath(), modLoc("block/" + location.getPath()));
		getVariantBuilder(MimicryRegistry.SOUND_EMITTER.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
	}
}
