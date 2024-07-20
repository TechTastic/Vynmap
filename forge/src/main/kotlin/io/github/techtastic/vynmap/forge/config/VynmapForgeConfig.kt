package io.github.techtastic.vynmap.forge.config

import io.github.techtastic.vynmap.config.IVynmapConfig
import io.github.techtastic.vynmap.config.MarkersDisplayed
import net.minecraftforge.common.ForgeConfigSpec

object VynmapForgeConfig: IVynmapConfig {
    val BUILDER = ForgeConfigSpec.Builder()
    val SPEC: ForgeConfigSpec

    val UNLOADED_ICON: ForgeConfigSpec.ConfigValue<Boolean>
    val MARKERS_DISPLAYED: ForgeConfigSpec.ConfigValue<MarkersDisplayed>

    val SHOW_SHIP_ID: ForgeConfigSpec.ConfigValue<Boolean>
    val SHOW_SHIP_MASS: ForgeConfigSpec.ConfigValue<Boolean>
    val SHOW_SHIP_VELOCITY: ForgeConfigSpec.ConfigValue<Boolean>

    init {
        BUILDER.push("Vynmap Config")

        BUILDER.comment("Marker Display Cnnfig")
        UNLOADED_ICON = BUILDER.comment("Should Unloaded Ships use the greyscaled Icon?")
                .translation("config.vynmap.unloaded_icon").define("unloadedIcon", true)
        MARKERS_DISPLAYED = BUILDER.comment("What Ship Markers should be displayed on Dynmap?",
                "Values: ALL, POLYLINE, ICON, NONE").translation("config.vynmap.markers_displayed")
                .define("markersDisplayed", MarkersDisplayed.ALL)

        BUILDER.comment("Marker Label Config")
        SHOW_SHIP_ID = BUILDER.comment("Should ShipIDs be added to Marker Labels??")
                .translation("config.vynmap.show_ship_id").define("showShipId", true)
        SHOW_SHIP_MASS = BUILDER.comment("Should Ship Mass be added to Marker Labels?")
                .translation("config.vynmap.show_mass").define("showMass", true)
        SHOW_SHIP_VELOCITY = BUILDER.comment("Should Velocity be added to Marker Labels?")
                .translation("config.vynmap.show_velocity").define("showVelocity", true)

        BUILDER.pop()
        SPEC = BUILDER.build()
    }

    override fun useUnloadedIcon(): Boolean = UNLOADED_ICON.get()

    override fun getMarkersDisplayed(): MarkersDisplayed = MARKERS_DISPLAYED.get()

    override fun showShipID(): Boolean = SHOW_SHIP_ID.get()

    override fun showShipMass(): Boolean = SHOW_SHIP_MASS.get()

    override fun showShipVelocity(): Boolean = SHOW_SHIP_VELOCITY.get()
}