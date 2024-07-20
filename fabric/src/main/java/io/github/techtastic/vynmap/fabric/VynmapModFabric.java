package io.github.techtastic.vynmap.fabric;

import io.github.techtastic.vynmap.fabric.config.VynmapFabricConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import io.github.techtastic.vynmap.VynmapMod;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.valkyrienskies.mod.fabric.common.ValkyrienSkiesModFabric;

import static io.github.techtastic.vynmap.VynmapMod.MOD_ID;

public class VynmapModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // force VS2 to load before eureka
        new ValkyrienSkiesModFabric().onInitialize();

        VynmapMod.init();

        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.SERVER, VynmapFabricConfig.SPEC, MOD_ID + "-config.toml");
    }

    @Environment(EnvType.CLIENT)
    public static class Client implements ClientModInitializer {

        @Override
        public void onInitializeClient() {
            VynmapMod.initClient();
        }
    }
}
