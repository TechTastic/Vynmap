package io.github.techtastic.vynmap.forge.mixin;

import io.github.techtastic.vynmap.forge.ducks.IForgeServerGetter;
import org.dynmap.common.DynmapServerInterface;
import org.dynmap.forge_1_18_2.DynmapPlugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DynmapPlugin.class)
public class MixinDynmapPlugin implements IForgeServerGetter {
    @Shadow private DynmapPlugin.ForgeServer fserver;

    @Override
    public DynmapServerInterface vynmap$getServer() {
        return fserver;
    }
}
