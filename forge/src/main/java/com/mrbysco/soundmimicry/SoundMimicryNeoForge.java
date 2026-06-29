package com.mrbysco.soundmimicry;


import com.mrbysco.soundmimicry.networking.PacketHandler;
import com.mrbysco.soundmimicry.registration.MimicryRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Constants.MOD_ID)
public class SoundMimicryNeoForge {

    public SoundMimicryNeoForge(IEventBus eventBus) {
        CommonClass.init();

        eventBus.addListener(this::buildContents);
        eventBus.addListener(PacketHandler::setupPackets);
    }

    private void buildContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(MimicryRegistry.SOUND_EMITTER.get());
        }
    }
}