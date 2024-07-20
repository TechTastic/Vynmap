package io.github.techtastic.vynmap.fabric.config;

import io.github.techtastic.vynmap.config.IVynmapConfig;
import io.github.techtastic.vynmap.config.MarkersDisplayed;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public class VynmapFabricConfig implements IVynmapConfig {
    public static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> UNLOADED_ICON;
    public static ForgeConfigSpec.ConfigValue<MarkersDisplayed> MARKERS_DISPLAYED;

    public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_SHIP_ID;
    public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_SHIP_MASS;
    public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_SHIP_VELOCITY;

    static {
        BUILDER.push("Vynmap Config");

        BUILDER.comment("Marker Display Cnnfig");
        UNLOADED_ICON = BUILDER.comment("Should Unloaded Ships use the greyscaled Icon?")
                .translation("config.vynmap.unloaded_icon").define("unloadedIcon", true);
        MARKERS_DISPLAYED = BUILDER.comment("What Ship Markers should be displayed on Dynmap?",
                        "Values: ALL, POLYLINE, ICON, NONE").translation("config.vynmap.markers_displayed")
                .define("markersDisplayed", MarkersDisplayed.ALL);

        BUILDER.comment("Marker Label Config");
        SHOW_SHIP_ID = BUILDER.comment("Should ShipIDs be added to Marker Labels??")
                .translation("config.vynmap.show_ship_id").define("showShipId", true);
        SHOW_SHIP_MASS = BUILDER.comment("Should Ship Mass be added to Marker Labels?")
                .translation("config.vynmap.show_mass").define("showMass", true);
        SHOW_SHIP_VELOCITY = BUILDER.comment("Should Velocity be added to Marker Labels?")
                .translation("config.vynmap.show_velocity").define("showVelocity", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    @Override
    public boolean useUnloadedIcon() {
        return UNLOADED_ICON.get();
    }

    @NotNull
    @Override
    public MarkersDisplayed getMarkersDisplayed() {
        return MARKERS_DISPLAYED.get();
    }

    @Override
    public boolean showShipID() {
        return SHOW_SHIP_ID.get();
    }

    @Override
    public boolean showShipMass() {
        return SHOW_SHIP_MASS.get();
    }

    @Override
    public boolean showShipVelocity() {
        return SHOW_SHIP_VELOCITY.get();
    }
}
