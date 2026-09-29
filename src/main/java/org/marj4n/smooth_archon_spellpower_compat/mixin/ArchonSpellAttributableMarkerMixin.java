package org.marj4n.smooth_archon_spellpower_compat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/**
 * Empty marker used only to make Mixin run our post-transform pass on Archon's
 * SpellAttributable interface.
 */
@Pseudo
@Mixin(targets = "safro.archon.api.spell.SpellAttributable", priority = 100, remap = false)
public interface ArchonSpellAttributableMarkerMixin {
}
