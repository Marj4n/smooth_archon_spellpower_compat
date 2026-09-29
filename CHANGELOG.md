# Changelog

## 1.0.0

- Added compatibility for Archon Renewed 1.1.5 with Spell Power 1.6.x on Minecraft 1.20.1.
- Fixed startup crash caused by Archon's legacy `SpellSchool.attribute` field access.
- Redirected Archon's `SpellAttributable#addPower` attribute lookup to the modern `SpellSchool.attributeEntry` / `ownedAttribute()` API.
- Kept Archon's existing crit chance and crit damage attribute logic unchanged because those `SpellPowerMechanics.Entry.attribute` fields still exist in the target Spell Power API.
