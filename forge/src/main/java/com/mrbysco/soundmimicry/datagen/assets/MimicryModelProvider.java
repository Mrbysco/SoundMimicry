package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MimicryModelProvider extends ModelProvider {
	public MimicryModelProvider(PackOutput output) {
		super(output, Constants.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		createEmitter(blockModels, MimicryRegistry.SOUND_EMITTER.get(), TexturedModel.ORIENTABLE);
	}

	public final void createEmitter(BlockModelGenerators blockModels, Block block, TexturedModel.Provider provider) {
		Material poweredTexture = TextureMapping.getBlockTexture(block, "_front_powered");
		Material unpoweredTexture = TextureMapping.getBlockTexture(block, "_front_unpowered");
		Material texture = TextureMapping.getBlockTexture(block);

		MultiVariant model = BlockModelGenerators.plainVariant(provider.get(block).updateTextures((textureMapping) ->
				textureMapping
						.put(TextureSlot.FRONT, unpoweredTexture)
						.put(TextureSlot.SIDE, texture)
						.put(TextureSlot.TOP, texture)
						.put(TextureSlot.BOTTOM, texture)
		).create(block, blockModels.modelOutput));

		MultiVariant poweredModel = BlockModelGenerators.plainVariant(provider.get(block).updateTextures((textureMapping) ->
				textureMapping
						.put(TextureSlot.FRONT, poweredTexture)
						.put(TextureSlot.SIDE, texture)
						.put(TextureSlot.TOP, texture)
						.put(TextureSlot.BOTTOM, texture)
		).createWithSuffix(block, "_powered", blockModels.modelOutput));

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.POWERED, poweredModel, model))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
		);
	}
}
