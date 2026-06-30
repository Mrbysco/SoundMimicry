package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MimicryModelProvider extends FabricModelProvider {

	public MimicryModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModels) {
		createEmitter(blockModels, MimicryRegistry.SOUND_EMITTER.get(), TexturedModel.ORIENTABLE);
	}

	public final void createEmitter(BlockModelGenerators blockModels, Block block, TexturedModel.Provider provider) {
		ResourceLocation poweredTexture = TextureMapping.getBlockTexture(block, "_front_powered");
		ResourceLocation unpoweredTexture = TextureMapping.getBlockTexture(block, "_front_unpowered");
		ResourceLocation texture = TextureMapping.getBlockTexture(block);

		ResourceLocation model = provider.get(block).updateTextures((textureMapping) ->
				textureMapping
						.put(TextureSlot.FRONT, unpoweredTexture)
						.put(TextureSlot.SIDE, texture)
						.put(TextureSlot.TOP, texture)
						.put(TextureSlot.BOTTOM, texture)
		).create(block, blockModels.modelOutput);

		ResourceLocation poweredModel = provider.get(block).updateTextures((textureMapping) ->
				textureMapping
						.put(TextureSlot.FRONT, poweredTexture)
						.put(TextureSlot.SIDE, texture)
						.put(TextureSlot.TOP, texture)
						.put(TextureSlot.BOTTOM, texture)
		).createWithSuffix(block, "_powered", blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
				.with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.POWERED, poweredModel, model))
				.with(BlockModelGenerators.createHorizontalFacingDispatch()));
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModels) {

	}

	private ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
	}
}
