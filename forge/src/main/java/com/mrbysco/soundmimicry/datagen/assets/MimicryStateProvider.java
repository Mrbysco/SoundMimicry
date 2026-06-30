package com.mrbysco.soundmimicry.datagen.assets;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.blocks.SoundEmitterBlock;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MimicryStateProvider extends BlockStateProvider {
	public MimicryStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Constants.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		soundEmitter(MimicryRegistry.SOUND_EMITTER.get());
	}

	public void soundEmitter(Block block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(SoundEmitterBlock.FACING);
			boolean powered = state.getValue(SoundEmitterBlock.POWERED);
			ResourceLocation texture = powered ? modLoc("block/sound_emitter_front_powered") : modLoc("block/sound_emitter_front_unpowered");
			ResourceLocation plainTexture = modLoc("block/sound_emitter");

			return ConfiguredModel.builder()
					.modelFile(models().getBuilder("sound_emitter" + (powered ? "_on" : ""))
							.texture("front", texture)
							.texture("side", plainTexture)
							.texture("top", plainTexture)
							.texture("bottom", plainTexture)
							.texture("particle", plainTexture)
							.parent(models().getExistingFile(mcLoc("block/orientable"))))
					.rotationY((int) facing.getOpposite().toYRot())
					.build();
		});
	}
}
