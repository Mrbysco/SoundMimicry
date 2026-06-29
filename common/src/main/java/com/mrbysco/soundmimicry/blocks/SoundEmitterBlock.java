package com.mrbysco.soundmimicry.blocks;

import com.mojang.serialization.MapCodec;
import com.mrbysco.soundmimicry.blocks.entity.SoundEmitterBlockEntity;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SoundEmitterBlock extends BaseEntityBlock {
	public static final MapCodec<SoundEmitterBlock> CODEC = simpleCodec(SoundEmitterBlock::new);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty MIMICING = BooleanProperty.create("mimicing");

	public SoundEmitterBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE).setValue(MIMICING, Boolean.FALSE));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof SoundEmitterBlockEntity blockEntity) {
			if (level.isClientSide) {
				com.mrbysco.soundmimicry.client.screen.SoundEmitterEditScreen.openScreen(pos, level.dimension().location(),
						blockEntity.soundLocation, blockEntity.soundSource, blockEntity.offset, blockEntity.volume, blockEntity.pitch,
						blockEntity.minVolume);
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return super.useWithoutItem(state, level, pos, player, hitResult);
	}


	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SoundEmitterBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (!level.isClientSide && state.getValue(POWERED)) {
			return createTickerHelper(blockEntityType, MimicryRegistry.SOUND_EMITTER_ENTITY.get(), SoundEmitterBlockEntity::serverTick);
		}
		return null;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		if (state.getValue(MIMICING)) {
			return RenderShape.INVISIBLE;
		}
		return RenderShape.MODEL;
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		boolean flag = level.hasNeighborSignal(pos);
		if (flag != (Boolean) state.getValue(POWERED)) {
			if (flag) {
				this.playSound(level, pos);
			}

			level.setBlock(pos, (BlockState) state.setValue(POWERED, flag), 3);
		}
	}

	private void playSound(Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof SoundEmitterBlockEntity blockEntity) {
			blockEntity.executeCommand();
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
		if (state.getValue(POWERED) && !serverLevel.hasNeighborSignal(pos)) {
			serverLevel.setBlock(pos, state.cycle(POWERED), 2);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(POWERED, MIMICING);
	}
}
