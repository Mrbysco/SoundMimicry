package com.mrbysco.soundmimicry.client.screen;

import com.mrbysco.soundmimicry.client.screen.components.DeltaSuggestions;
import com.mrbysco.soundmimicry.client.screen.components.SoundSourceSuggestions;
import com.mrbysco.soundmimicry.client.screen.components.SoundSuggestions;
import com.mrbysco.soundmimicry.client.screen.widget.NumberEditBox;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSoundEmitterEditScreen extends Screen {
	private static final Component SET_SOUND_LABEL = Component.translatable("soundmimicry.setSound");

	private static final Component SOUND_LABEL = Component.translatable("soundmimicry.sound");
	private static final Component SOUND_SOURCE_LABEL = Component.translatable("soundmimicry.soundSource");
	private static final Component OFFSET_LABEL = Component.translatable("soundmimicry.offset");
	private static final Component VOLUME_LABEL = Component.translatable("soundmimicry.volume");
	private static final Component PITCH_LABEL = Component.translatable("soundmimicry.pitch");
	private static final Component MIN_VOLUME_LABEL = Component.translatable("soundmimicry.minVolume");

	protected EditBox soundLocationEdit;
	protected EditBox soundSourceEdit;
	protected EditBox offsetEdit;
	protected NumberEditBox volumeEdit;
	protected NumberEditBox pitchEdit;
	protected NumberEditBox minVolumeEdit;

	protected Button doneButton;
	protected Button cancelButton;
	SoundSuggestions soundSuggestions;
	SoundSourceSuggestions soundSourceSuggestions;
	DeltaSuggestions offsetSuggestions;

	private static final String typeSuggestion = "Sound Identifier";
	private static final String soundSourceSuggestion = "Sound Source";
	private static final String offsetSuggestion = "Offset";
	private static final String volumeSuggestion = "Volume";
	private static final String pitchSuggestion = "Pitch";
	private static final String minVolumeSuggestion = "Min Volume";

	public AbstractSoundEmitterEditScreen() {
		super(GameNarrator.NO_TITLE);
	}

	@Override
	protected void init() {
		this.addRenderableWidget(this.doneButton = Button.builder(CommonComponents.GUI_DONE, (button) -> {
			this.onDone();
		}).bounds(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20).build());

		this.addRenderableWidget(this.cancelButton = Button.builder(CommonComponents.GUI_CANCEL, (button) -> {
			this.onClose();
		}).bounds(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20).build());

		// Sound Identifier
		this.soundLocationEdit = new EditBox(this.font, this.width / 2 - 151, 50, 150, 20,
				Component.literal(typeSuggestion)) {
			@Override
			@NotNull
			protected MutableComponent createNarrationMessage() {
				return super.createNarrationMessage().append(AbstractSoundEmitterEditScreen.this.soundSuggestions.getNarrationMessage());
			}
		};
		this.soundLocationEdit.setMaxLength(100);
		this.soundLocationEdit.setResponder(this::onEdited);
		this.soundLocationEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.sound.tooltip")));
		this.addRenderableWidget(this.soundLocationEdit);
		this.setInitialFocus(this.soundLocationEdit);
		this.soundLocationEdit.setFocused(true);
		this.soundSuggestions = new SoundSuggestions(this.minecraft, this, this.soundLocationEdit, this.font);
		this.soundSuggestions.setAllowSuggestions(true);
		this.soundSuggestions.updateCommandInfo();

		// Sound Source
		this.soundSourceEdit = new EditBox(this.font, this.width / 2 + 1, 50, 150, 20,
				Component.literal(soundSourceSuggestion)) {

			@Override
			@NotNull
			protected MutableComponent createNarrationMessage() {
				return super.createNarrationMessage().append(AbstractSoundEmitterEditScreen.this.soundSourceSuggestions.getNarrationMessage());
			}
		};
		this.soundSourceEdit.setMaxLength(200);
		this.soundSourceEdit.setResponder(this::onSoundSourceEdited);
		this.soundSourceEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.soundSource.tooltip")));
		this.addRenderableWidget(this.soundSourceEdit);
		this.soundSourceSuggestions = new SoundSourceSuggestions(this.minecraft, this, this.soundSourceEdit, this.font);
		this.soundSourceSuggestions.setAllowSuggestions(true);
		this.soundSourceSuggestions.setAllowSuggestions(true);
		this.soundSourceSuggestions.updateCommandInfo();


		// Offset
		this.offsetEdit = new EditBox(this.font, this.width / 2 - 151, 84, 74, 20,
				Component.literal(offsetSuggestion)) {
			@Override
			@NotNull
			protected MutableComponent createNarrationMessage() {
				return super.createNarrationMessage().append(AbstractSoundEmitterEditScreen.this.offsetSuggestions.getNarrationMessage());
			}
		};
		this.offsetEdit.setValue("~ ~ ~");
		this.offsetEdit.setMaxLength(30);
		this.offsetEdit.setResponder(this::onOffsetEdited);
		this.offsetEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.offset.tooltip")));
		this.addRenderableWidget(this.offsetEdit);
		this.offsetSuggestions = new DeltaSuggestions(this.minecraft, this, this.offsetEdit, this.font, true);
		this.offsetSuggestions.setAllowSuggestions(true);
		this.offsetSuggestions.updateCommandInfo();

		// Volume
		this.volumeEdit = new NumberEditBox(this.font, this.width / 2 + 1, 84, 48, 20,
				Component.translatable("soundmimicry.volume"), 4) {
		};
		this.volumeEdit.setMaxLength(3);
		this.volumeEdit.setValue("0");
		this.volumeEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.volume.tooltip")));
		this.addRenderableWidget(this.volumeEdit);

		// Pitch
		this.pitchEdit = new NumberEditBox(this.font, this.width / 2 + 52, 84, 48, 20,
				Component.translatable("soundmimicry.pitch"), 0) {
		};
		this.pitchEdit.setMaxLength(3);
		this.pitchEdit.setMaxValue(2.0F);
		this.pitchEdit.setValue("0");
		this.pitchEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.pitch.tooltip")));
		this.addRenderableWidget(this.pitchEdit);

		// Min Volume
		this.minVolumeEdit = new NumberEditBox(this.font, this.width / 2 + 103, 84, 48, 20,
				Component.translatable("soundmimicry.minVolume"), 0) {
		};
		this.minVolumeEdit.setMaxLength(3);
		this.minVolumeEdit.setMaxValue(1.0F);
		this.minVolumeEdit.setValue("0");
		this.minVolumeEdit.setTooltip(Tooltip.create(Component.translatable("soundmimicry.minVolume.tooltip")));
		this.addRenderableWidget(this.minVolumeEdit);
	}

	@Override
	public void tick() {
		if (this.soundLocationEdit.isFocused())
			this.soundSuggestions.tick();
		if (this.soundSourceEdit.isFocused())
			this.soundSourceSuggestions.tick();
		if (this.offsetEdit.isFocused())
			this.offsetSuggestions.tick();

		updateSuggestion(soundLocationEdit, typeSuggestion);
		updateSuggestion(offsetEdit, offsetSuggestion);
		updateSuggestion(soundSourceEdit, soundSourceSuggestion);
		updateSuggestion(volumeEdit, volumeSuggestion);
		updateSuggestion(pitchEdit, pitchSuggestion);
		updateSuggestion(minVolumeEdit, minVolumeSuggestion);

		if (!soundLocationEdit.isFocused() && soundLocationEdit.suggestion != null)
			soundSuggestions.hide();

		if (!soundSourceEdit.isFocused() && soundSourceEdit.suggestion != null)
			soundSourceSuggestions.hide();

		if (!offsetEdit.isFocused() && offsetEdit.suggestion != null)
			offsetSuggestions.hide();
	}

	private void updateSuggestion(EditBox box, String suggestion) {
		if (!box.getValue().isEmpty()) {
			box.setSuggestion(null);
		} else {
			box.setSuggestion(suggestion);
		}
	}

	@Override
	public void resize(Minecraft mc, int width, int height) {
		this.init(mc, width, height);
		this.soundLocationEdit.setValue(this.soundLocationEdit.getValue());
		this.soundSuggestions.updateCommandInfo();
		this.soundSourceEdit.setValue(this.soundSourceEdit.getValue());
		this.soundSourceSuggestions.updateCommandInfo();
		this.offsetEdit.setValue(this.offsetEdit.getValue());
		this.offsetSuggestions.updateCommandInfo();

		this.soundSourceEdit.setValue(this.soundSourceEdit.getValue());
		this.volumeEdit.setValue(this.volumeEdit.getValue());
		this.pitchEdit.setValue(this.pitchEdit.getValue());
		this.minVolumeEdit.setValue(this.minVolumeEdit.getValue());
	}

	protected void onDone() {
		this.populateAndSendPacket();

		this.minecraft.setScreen((Screen) null);
	}

	@Override
	public void removed() {

	}

	protected abstract void populateAndSendPacket();

	private void onEdited(String soundLocation) {
		this.soundSuggestions.updateCommandInfo();
	}

	private void onSoundSourceEdited(String soundSource) {
		this.soundSourceSuggestions.updateCommandInfo();
	}

	private void onOffsetEdited(String offset) {
		this.offsetSuggestions.updateCommandInfo();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (soundLocationEdit.isFocused() && this.soundSuggestions.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else if (offsetEdit.isFocused() && this.offsetSuggestions.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else if (soundSourceEdit.isFocused() && this.soundSourceSuggestions.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else if (super.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else if (keyCode != 257 && keyCode != 335) {
			return false;
		} else {
			this.onDone();
			return true;
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (soundLocationEdit.isFocused()) {
			return this.soundSuggestions.mouseScrolled(scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
		} else if (offsetEdit.isFocused()) {
			return this.offsetSuggestions.mouseScrolled(scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
		} else if (soundSourceEdit.isFocused()) {
			return this.soundSourceSuggestions.mouseScrolled(scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
		}
		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int delta) {
		// The ugly setFocused calls are to stop multiple edit boxes from being focused at once
		if (soundLocationEdit.isFocused() && this.soundSuggestions.mouseClicked(mouseX, mouseY, delta)) {
			offsetEdit.setFocused(false);
			soundSourceEdit.setFocused(false);
			volumeEdit.setFocused(false);
			pitchEdit.setFocused(false);
			minVolumeEdit.setFocused(false);
			return true;
		} else if (offsetEdit.isFocused() && this.offsetSuggestions.mouseClicked(mouseX, mouseY, delta)) {
			soundLocationEdit.setFocused(false);
			soundSourceEdit.setFocused(false);
			volumeEdit.setFocused(false);
			pitchEdit.setFocused(false);
			minVolumeEdit.setFocused(false);
			return true;
		} else if (soundSourceEdit.isFocused() && this.soundSourceSuggestions.mouseClicked(mouseX, mouseY, delta)) {
			soundLocationEdit.setFocused(false);
			offsetEdit.setFocused(false);
			volumeEdit.setFocused(false);
			pitchEdit.setFocused(false);
			minVolumeEdit.setFocused(false);
			return true;
		} else {
			soundLocationEdit.setFocused(false);
			offsetEdit.setFocused(false);
			soundSourceEdit.setFocused(false);
			volumeEdit.setFocused(false);
			pitchEdit.setFocused(false);
			minVolumeEdit.setFocused(false);
			return super.mouseClicked(mouseX, mouseY, delta);
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.drawCenteredString(this.font, SET_SOUND_LABEL, this.width / 2, 20, 16777215);
		guiGraphics.drawString(this.font, SOUND_LABEL, this.width / 2 - 151, 40, 10526880, false);
		guiGraphics.drawString(this.font, SOUND_SOURCE_LABEL, this.width / 2 + 1, 40, 10526880, false);

		guiGraphics.drawString(this.font, OFFSET_LABEL, this.width / 2 - 150, 74, 10526880, false);

		guiGraphics.drawString(this.font, VOLUME_LABEL, this.width / 2 + 1, 74, 10526880, false);
		guiGraphics.drawString(this.font, PITCH_LABEL, this.width / 2 + 52, 74, 10526880, false);
		guiGraphics.drawString(this.font, MIN_VOLUME_LABEL, this.width / 2 + 103, 74, 10526880, false);


		if (soundLocationEdit.isFocused())
			this.soundSuggestions.render(guiGraphics, mouseX, mouseY);
		if (offsetEdit.isFocused())
			this.offsetSuggestions.render(guiGraphics, mouseX, mouseY);
		if (soundSourceEdit.isFocused())
			this.soundSourceSuggestions.render(guiGraphics, mouseX, mouseY);
	}
}