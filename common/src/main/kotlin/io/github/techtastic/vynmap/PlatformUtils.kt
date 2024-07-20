package io.github.techtastic.vynmap

import dev.architectury.injectables.annotations.ExpectPlatform
import io.github.techtastic.vynmap.config.IVynmapConfig
import net.minecraft.server.level.ServerLevel
import org.dynmap.common.DynmapServerInterface

object PlatformUtils {
    @JvmStatic
    @ExpectPlatform
    fun getDynmapServer(): DynmapServerInterface? {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun getWorldName(level: ServerLevel): String {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun getIconPath(): String {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun getConfig(): IVynmapConfig {
        throw AssertionError()
    }
}