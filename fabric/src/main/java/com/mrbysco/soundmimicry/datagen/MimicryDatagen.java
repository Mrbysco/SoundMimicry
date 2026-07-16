package com.mrbysco.soundmimicry.datagen;

import com.mrbysco.soundmimicry.datagen.assets.MimicryLanguageProvider;
import com.mrbysco.soundmimicry.datagen.assets.MimicryModelProvider;
import com.mrbysco.soundmimicry.datagen.data.MimicryBlockTagProvider;
import com.mrbysco.soundmimicry.datagen.data.MimicryLootProvider;
import com.mrbysco.soundmimicry.datagen.data.MimicryRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MimicryDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(MimicryModelProvider::new);
		pack.addProvider(MimicryLanguageProvider::new);

		pack.addProvider(MimicryBlockTagProvider::new);
		pack.addProvider(MimicryLootProvider::new);
		pack.addProvider(MimicryRecipeProvider::new);
	}
}