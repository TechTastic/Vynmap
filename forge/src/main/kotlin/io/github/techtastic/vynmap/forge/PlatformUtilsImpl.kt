package io.github.techtastic.vynmap.forge

import io.github.techtastic.vynmap.config.IVynmapConfig
import io.github.techtastic.vynmap.forge.config.VynmapForgeConfig
import io.github.techtastic.vynmap.forge.ducks.IForgeServerGetter
import net.minecraft.server.level.ServerLevel
import org.dynmap.common.DynmapServerInterface
import org.dynmap.forge_1_18_2.DynmapMod.plugin
import org.dynmap.forge_1_18_2.ForgeWorld

object PlatformUtilsImpl {
    @JvmStatic
    fun getDynmapServer(): DynmapServerInterface? = (plugin as IForgeServerGetter).`vynmap$getServer`()

    @JvmStatic
    fun getWorldName(level: ServerLevel): String =
            ForgeWorld.getWorldName(level)

    @JvmStatic
    fun getIconPath(): String = "icon.png"

    @JvmStatic
    fun getConfig(): IVynmapConfig = VynmapForgeConfig
}