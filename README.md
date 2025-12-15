# Multicutter

This is a fork of [rotgruengelb's Stonecutter Mod Template](https://github.com/rotgruengelb/stonecutter-mod-template) altered to fit my specific needs.

Differences from original template:

- Enabled Parchment mappings.
- Added automatic mixin registration.
  - Mixins no longer need to be manually added to the mixin config. By default, they're added as common mixins (both server and client side). To make a mixin client side only, use the following annotation: `@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)`.
- Added versioned access wideners and access transformers.
  - All AWs and ATs are stored in `src/main/resources/aw`. To add an AW/AT for a version, create a new file in that folder, for example `1.21.1.accesswidener` or `1.21.1.cfg`.
- Added versioned resources.
  - Version specific resources can be placed in `src/main/resources/resourcepacks` in the version specific folders, for example `1_21_1/rp` contains client side assets which are only going to be loaded in 1.21.1.
  - There is a small mixin that hides these resource packs from resource pack and datapack selection UIs to prevent clutter (see `PackSelectionModelMixin`).
  - Note that this is implemented for Fabric and NeoForge only.
- Added dependencies:
  - Fzzy Config as the config API
  - Mixson for runtime JSON patching
  - MixinConstraints for conditional mixin loading
- Added a few more useful platform methods.
- Added handling for pre-release versions.
- Added handling for ResourceLocation -> Identifier rename which occured in 1.21.11.
  - It's a bit scuffed, but it works. You may need to add additional string replacements in the buildscripts depending on your project.
- Disabled datagen.
