package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MimicryRecipeProvider extends RecipeProvider {
	public MimicryRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		super(provider, recipeOutput);
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.REDSTONE, MimicryRegistry.SOUND_EMITTER.get())
				.pattern("SSS")
				.pattern("RNR")
				.pattern("GSG")
				.define('S', Items.AMETHYST_SHARD)
				.define('R', Tags.Items.DUSTS_REDSTONE)
				.define('N', Items.NOTE_BLOCK)
				.define('G', Tags.Items.DUSTS_GLOWSTONE)
				.unlockedBy("has_noteblock", has(Items.NOTE_BLOCK))
				.save(output);
	}

	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			return new MimicryRecipeProvider(provider, recipeOutput);
		}

		@NotNull
		@Override
		public String getName() {
			return "Sound Mimicry recipes";
		}
	}
}
