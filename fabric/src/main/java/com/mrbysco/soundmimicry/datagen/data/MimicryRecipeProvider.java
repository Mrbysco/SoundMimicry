package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class MimicryRecipeProvider extends FabricRecipeProvider {
	public MimicryRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MimicryRegistry.SOUND_EMITTER.get())
				.pattern("SSS")
				.pattern("RNR")
				.pattern("GSG")
				.define('S', Items.AMETHYST_SHARD)
				.define('R', ConventionalItemTags.REDSTONE_DUSTS)
				.define('N', Items.NOTE_BLOCK)
				.define('G', ConventionalItemTags.GLOWSTONE_DUSTS)
				.unlockedBy("has_noteblock", has(Items.NOTE_BLOCK))
				.save(recipeOutput);
	}
}
