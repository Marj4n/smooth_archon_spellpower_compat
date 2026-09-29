package org.marj4n.smooth_archon_spellpower_compat.compat;

import net.minecraft.entity.attribute.EntityAttribute;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

/**
 * Presents the legacy Spell Power view expected by Archon Renewed 1.1.5.
 *
 * Archon was compiled against a SpellSchool that exposed a public
 * `EntityAttribute attribute` field. Spell Power 1.6.x on Minecraft 1.20.1
 * stores that value through `attributeEntry`, or as an internally-owned
 * attribute before/alongside registry linking.
 */
public final class ModernSpellPowerAccess {
    private ModernSpellPowerAccess() {
    }

    @Nullable
    public static EntityAttribute attribute(SpellSchool school) {
        if (school == null) {
            return null;
        }

        if (school.attributeEntry != null) {
            return school.attributeEntry.value();
        }

        return school.ownedAttribute();
    }
}
