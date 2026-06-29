package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class MimicryBlockTagProvider extends BlockTagsProvider {
	public MimicryBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @javax.annotation.Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MimicryRegistry.SOUND_EMITTER.get());
	}
}