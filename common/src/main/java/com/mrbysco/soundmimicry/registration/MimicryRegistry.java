package com.mrbysco.soundmimicry.registration;

import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.blocks.SoundEmitterBlock;
import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

public class MimicryRegistry {

	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(BuiltInRegistries.BLOCK, Constants.MOD_ID);
	public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Constants.MOD_ID);
	public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);


	public static final RegistryObject<SoundEmitterBlock> SOUND_EMITTER = BLOCKS.register("sound_emitter", () -> new SoundEmitterBlock(Block.Properties.of().setId(getBlockKey("sound_emitter")).mapColor(MapColor.TERRACOTTA_BLUE).strength(0.8F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<BlockItem> SOUND_EMITTER_ITEM = ITEMS.register("sound_emitter", () -> new BlockItem(SOUND_EMITTER.get(), new Item.Properties().useBlockDescriptionPrefix().setId(getItemKey("sound_emitter"))));

	public static final RegistryObject<BlockEntityType<SoundEmitterBlockEntity>> SOUND_EMITTER_ENTITY = BLOCK_ENTITIES.register("sound_emitter", Services.PLATFORM::createBlockEntityType);

	private static ResourceKey<Item> getItemKey(String path) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, path));
	}

	private static ResourceKey<Block> getBlockKey(String path) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, path));
	}

	// Called in the mod initializer / constructor in order to make sure that items are registered
	public static void loadClass() {
	}
}
