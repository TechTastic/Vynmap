package io.github.techtastic.vynmap.forge

import io.github.techtastic.vynmap.VynmapMod.MOD_ID
import io.github.techtastic.vynmap.VynmapMod.init
import io.github.techtastic.vynmap.VynmapMod.initClient
import io.github.techtastic.vynmap.forge.config.VynmapForgeConfig
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(MOD_ID)
class VynmapModForge {
    init {
        MOD_BUS.addListener { event: FMLClientSetupEvent? ->
            clientSetup(
                event
            )
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, VynmapForgeConfig.SPEC, "$MOD_ID-config.toml")

        init()
    }

    private fun clientSetup(event: FMLClientSetupEvent?) {
        initClient()
    }

    companion object {
        fun getModBus(): IEventBus = MOD_BUS
    }
}
