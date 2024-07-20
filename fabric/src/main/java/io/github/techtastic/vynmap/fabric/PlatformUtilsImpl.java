package io.github.techtastic.vynmap.fabric;

import io.github.techtastic.vynmap.config.IVynmapConfig;
import io.github.techtastic.vynmap.fabric.config.VynmapFabricConfig;
import net.minecraft.server.level.ServerLevel;
import org.dynmap.common.DynmapServerInterface;
import org.dynmap.fabric_1_18_2.FabricWorld;

import static org.dynmap.fabric_1_18_2.DynmapMod.plugin;

public class PlatformUtilsImpl {
    public static DynmapServerInterface getDynmapServer() {
        if (plugin == null)
            return null;
        return plugin.getFabricServer();
    }

    public static String getWorldName(ServerLevel level) {
        return FabricWorld.getWorldName(plugin, level);
    }

    public static String getIconPath() {
        return "assets/vynmap/icon.png";
    }

    public static IVynmapConfig getConfig() {
        return new VynmapFabricConfig();
    }
}
