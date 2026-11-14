# BackSlot
BackSlot adds extra slots which are easily accessible for storing your sword on your back.

### Installation
BackSlot is a mod built for both the [Fabric Loader](https://fabricmc.net/) and [NeoForge](https://neoforged.net/).

- **Fabric:** requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately. [Amecs](https://www.curseforge.com/minecraft/mc-mods/amecs) is optional and allows rebinding the switch keys with modifiers like Shift.
- **NeoForge:** requires [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately.

### License
BackSlot is licensed under GPLv3.

### Datapacks
Add items which can be worn on the back or belt via a datapack. There are two backslot item tags given called `backslot_items` and `beltslot_items`. Items in the `beltslot_excluded` tag can never be put on the belt. To adjust the position and size of the rendered items, a resource pack is needed. BackSlot uses the head renderer which can be set or configured in the models folder and looks like this:

```json
"head": {
			"rotation": [ 0, 0, 270 ],
			"translation": [ 0, 3.5, 0.05],
			"scale":[ 1.05, 1.05, 1.0 ]
		}
```

A full example can be found below:
```json
{
    "parent": "item/generated",
    "textures": {
        "layer0": "minecraft:item/iron_sword"
    },
    "display": {
        "head": {
			"rotation": [ 0, 0, 270 ],
			"translation": [ 0, 3.5, 0.05],
			"scale":[ 1.05, 1.05, 1.0 ]
		}
    }
}
```
