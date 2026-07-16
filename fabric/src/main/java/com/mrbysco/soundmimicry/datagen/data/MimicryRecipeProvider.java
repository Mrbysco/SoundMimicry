package com.mrbysco.soundmimicry.datagen.data;

import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MimicryRecipeProvider extends FabricRecipeProvider {
	public MimicryRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@NotNull
	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		return new Provider(provider, recipeOutput);
	}

	@NotNull
	@Override
	public String getName() {
		return "Sound Mimicry recipes";
	}

	public static class Provider extends RecipeProvider {

		protected Provider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
		}

		@Override
		public void buildRecipes() {
			shaped(RecipeCategory.REDSTONE, MimicryRegistry.SOUND_EMITTER.get())
					.pattern("SSS")
					.pattern("RNR")
					.pattern("GSG")
					.define('S', Items.AMETHYST_SHARD)
					.define('R', ConventionalItemTags.REDSTONE_DUSTS)
					.define('N', Items.NOTE_BLOCK)
					.define('G', ConventionalItemTags.GLOWSTONE_DUSTS)
					.unlockedBy("has_noteblock", has(Items.NOTE_BLOCK))
					.save(output);
		}
	}
}
