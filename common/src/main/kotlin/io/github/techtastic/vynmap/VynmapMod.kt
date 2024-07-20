package io.github.techtastic.vynmap

import io.github.techtastic.vynmap.dynmap.VynmapAPIListener
import org.dynmap.DynmapCommonAPIListener

object VynmapMod {
    const val MOD_ID = "vynmap"

    @JvmStatic
    fun init() {
        VynmapAPIListener.register()
    }

    @JvmStatic
    fun initClient() {
    }
}
