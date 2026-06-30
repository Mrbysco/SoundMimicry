package com.mrbysco.soundmimicry.blocks.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mrbysco.soundmimicry.Constants;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SoundEmitterBlockEntity extends BlockEntity {
	public String soundLocation, soundSource, offset, volume, pitch, minVolume;
	public String soundCommand;

	public SoundEmitterBlockEntity(BlockPos pos, BlockState state) {
		super(MimicryRegistry.SOUND_EMITTER_ENTITY.get(), pos, state);
		setData(null, "", "", "", "", "", "");
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, SoundEmitterBlockEntity blockEntity) {
//		if (blockEntity.interval <= 0) {
//			blockEntity.interval = 20;
//		}
//		if (level.getGameTime() % blockEntity.interval == 0) {
//			MinecraftServer server = level.getServer();
//			blockEntity.constructCommand();
//		}
	}

	public void executeCommand() {
		if (!this.level.isClientSide()) {
			MinecraftServer server = level.getServer();
			constructCommand();
			if (!StringUtil.isNullOrEmpty(soundCommand)) {
				try {
					CommandSourceStack commandsourcestack = server.createCommandSourceStack()
							.withPosition(Vec3.atCenterOf(getBlockPos()))
							.withSuppressedOutput()
							.withLevel((ServerLevel) level);
					server.getCommands().performPrefixedCommand(commandsourcestack, soundCommand);
				} catch (Throwable throwable) {
					CrashReport crashreport = CrashReport.forThrowable(throwable, "Executing sound emitter block");
					CrashReportCategory crashreportcategory = crashreport.addCategory("Sound command to be executed");
					crashreportcategory.setDetail("Command", soundCommand);
					crashreportcategory.setDetail("Name", () -> getBlockState().getBlock().getName().getString());
					throw new ReportedException(crashreport);
				}
			}
		}
	}

	public void setData(@Nullable Player player, String soundLocation, String soundSource, String offset, String volume, String pitch, String minVolume) {
		this.soundLocation = soundLocation;
		this.soundSource = soundSource;
		this.offset = checkOffset(player, offset);
		this.volume = volume;
		this.pitch = pitch;
		this.minVolume = minVolume;
	}

	private String checkOffset(@Nullable Player player, String offset) {
		String offsetString = offset;
		if (!offsetString.isEmpty()) {
			//Check the offset against the position of the Sound Emitter to see if it's within 5 blocks of the block
			try {
				MinecraftServer server = level.getServer();
				WorldCoordinates worldcoordinates = WorldCoordinates.parseInt(new StringReader(offsetString));
				Vec3 centerPos = Vec3.atCenterOf(this.getBlockPos());
				CommandSourceStack sourceStack = server.createCommandSourceStack().withPosition(centerPos).withSuppressedOutput().withLevel((ServerLevel) level);
				Vec3 position = worldcoordinates.getPosition(sourceStack);
				if (position != null) {
					if (!position.closerThan(centerPos, 5)) {
						if (player != null) {
							player.sendSystemMessage(Component.literal("Offset is too far away from the Sound Emitter. Resetting to ~ ~1 ~").withStyle(ChatFormatting.RED));
						} else {
							Constants.LOGGER.debug("Offset is too far away from the Sound Emitter. Resetting to ~ ~1 ~");
						}
						return "~ ~1 ~";
					}
				}
			} catch (CommandSyntaxException e) {
				//Nope
			}
			return offsetString;
		}

		return offsetString;
	}

	public void constructCommand() {
		// If any of the required fields are empty, don't construct the command
		if (StringUtil.isNullOrEmpty(soundLocation) || StringUtil.isNullOrEmpty(soundSource) || StringUtil.isNullOrEmpty(offset) || StringUtil.isNullOrEmpty(volume) || StringUtil.isNullOrEmpty(pitch) || StringUtil.isNullOrEmpty(minVolume)) {
			this.soundCommand = "";
			return;
		}

		//Construct command string
		StringBuilder commandBuilder = new StringBuilder("playsound");
		//Add the sound location to the command
		commandBuilder.append(" ").append(soundLocation);
		//Add the source
		commandBuilder.append(" ").append(soundSource);
		//Add target
		commandBuilder.append(" @p");
		//Add the offset
		commandBuilder.append(" ").append(offset);
		//Add volume
		commandBuilder.append(" ").append(volume);
		//Add pitch
		commandBuilder.append(" ").append(pitch);
		//Add min volume
		commandBuilder.append(" ").append(minVolume);

		this.soundCommand = commandBuilder.toString();
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.soundLocation = tag.getString("soundLocation");
		this.soundSource = tag.getString("soundSource");
		this.offset = tag.getString("offset");
		this.volume = tag.getString("volume");
		this.pitch = tag.getString("pitch");
		this.minVolume = tag.getString("minVolume");
		this.constructCommand();
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putString("soundLocation", this.soundLocation);
		tag.putString("soundSource", this.soundSource);
		tag.putString("offset", this.offset);
		tag.putString("volume", this.volume);
		tag.putString("pitch", this.pitch);
		tag.putString("minVolume", this.minVolume);

	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt, lookupProvider);
		return nbt;
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Update the client whenever the Sound Emitter is modified
	 */
	public void refreshClient() {
		setChanged();
		BlockState state = level.getBlockState(worldPosition);
		level.sendBlockUpdated(worldPosition, state, state, 2);
	}
}