package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MimicryItemModelProvider extends ItemModelProvider {
	public MimicryItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Constants.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		ResourceLocation location = MimicryRegistry.SOUND_EMITTER.getId();
		withExistingParent(location.getPath(), modLoc("block/sound_emitter"));
	}
}
