package net.backslot.mixin.compat;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class BackSlotMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("OnDeathItemDropCompatibility")) {
            if (FabricLoader.getInstance().isModLoaded("universal-graves")) {
                return false;
            } else if (FabricLoader.getInstance().isModLoaded("yigd") || FabricLoader.getInstance().isModLoaded("charm") || FabricLoader.getInstance().isModLoaded("gravestones")) {
                return true;
            }
        }
        if (mixinClassName.contains("EntityMixin") && !mixinClassName.contains("ServerPlayerEntityMixin") && !FabricLoader.getInstance().isModLoaded("lambdynlights"))
            return false;

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

}