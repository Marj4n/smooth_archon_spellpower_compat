package org.marj4n.smooth_archon_spellpower_compat;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SmoothArchonSpellPowerCompat implements ModInitializer {
    public static final String MOD_ID = "smooth_archon_spellpower_compat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Smooth Archon SpellPower Compat loaded: Archon legacy SpellSchool attribute bridge enabled.");
    }
}
