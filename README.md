# Smooth Archon SpellPower Compat

Small Fabric 1.20.1 compatibility bridge for **Archon Renewed 1.1.5** and
**Spell Power 1.6.x**.

## What it fixes

Archon Renewed 1.1.5 still reads the legacy public field:

```text
net.spell_power.api.SpellSchool.attribute
```

Spell Power 1.6.x stores a school's power attribute via `attributeEntry` /
`ownedAttribute()` instead. The mismatch causes a startup `NoSuchFieldError`
from `safro.archon.api.spell.SpellAttributable#addPower` while Archon creates
its wand items.

This mod rewrites that one legacy `GETFIELD` instruction at class-transform
time and redirects it to a small modern Spell Power bridge.

## Target stack

- Minecraft 1.20.1
- Fabric Loader 0.19.5+
- Fabric API 0.92.12+1.20.1
- Archon Renewed 1.1.5 (`mod id: archon`)
- Spell Power 1.6.x

## Build

```powershell
.\gradlew clean build
```

Output:

```text
build/libs/smooth_archon_spellpower_compat-1.0.0.jar
```
