package org.marj4n.smooth_archon_spellpower_compat.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Bytecode bridge for Archon Renewed 1.1.5 -> Spell Power 1.6.x.
 *
 * Archon's SpellAttributable#addPower was compiled as:
 *
 *   element.getSchool().attribute
 *
 * Modern Spell Power no longer has that public SpellSchool.attribute field.
 * Replacing GETFIELD with a static bridge keeps the original Archon logic and
 * stack shape intact without bundling or modifying Archon itself.
 */
public final class SmoothArchonSpellPowerCompatMixinPlugin implements IMixinConfigPlugin {
    private static final String ARCHON_TARGET = "safro.archon.api.spell.SpellAttributable";
    private static final String SPELL_SCHOOL = "net/spell_power/api/SpellSchool";
    private static final String ACCESS = "org/marj4n/smooth_archon_spellpower_compat/compat/ModernSpellPowerAccess";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return FabricLoader.getInstance().isModLoaded("archon")
                && FabricLoader.getInstance().isModLoaded("spell_power");
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
        if (!ARCHON_TARGET.equals(targetClassName)) {
            return;
        }

        int patched = 0;
        for (MethodNode method : targetClass.methods) {
            patched += patchMethod(method);
        }

        if (patched == 0) {
            throw new IllegalStateException(
                    "Smooth Archon SpellPower Compat could not find Archon's legacy SpellSchool.attribute access. "
                            + "Archon may have changed; refusing to silently leave the known incompatibility unpatched."
            );
        }
    }

    private static int patchMethod(MethodNode method) {
        int patched = 0;

        for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; ) {
            AbstractInsnNode next = insn.getNext();

            if (insn instanceof FieldInsnNode field
                    && field.getOpcode() == Opcodes.GETFIELD
                    && SPELL_SCHOOL.equals(field.owner)
                    && "attribute".equals(field.name)
                    && isEntityAttribute(field.desc)) {

                method.instructions.set(field, new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        ACCESS,
                        "attribute",
                        Type.getMethodDescriptor(Type.getType(field.desc), Type.getObjectType(SPELL_SCHOOL)),
                        false
                ));
                patched++;
            }

            insn = next;
        }

        return patched;
    }

    private static boolean isEntityAttribute(String descriptor) {
        return "Lnet/minecraft/entity/attribute/EntityAttribute;".equals(descriptor)
                || "Lnet/minecraft/class_1320;".equals(descriptor);
    }
}
