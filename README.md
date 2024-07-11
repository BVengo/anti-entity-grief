# Anti Entity Grief
![GitHub license](https://img.shields.io/github/license/BVengo/antientitygrief.svg)
![GitHub release](https://img.shields.io/github/release/BVengo/antientitygrief.svg)
![GitHub issues](https://img.shields.io/github/issues/BVengo/antientitygrief.svg)

A minecraft mod for fabric, allowing you to control the griefing capabilities of entities!

### The Issue
Entities can grief the world in all sorts of ways, leading from minor nuisances to major issues. The team at Mojang recognised this and so added the gamerule `mobGriefing` which allows you to disable all mobs from griefing. Unfortunately this is too broad a command to be useful, since in your attempt to disable endermen from stealing blocks you have now stopped your villagers from farming crops!

VanillaTweaks has some fantastic datapacks to handle this, namely 'Anti Creeper Grief', 'Anti Enerman Grief', and 'Anti Ghast Grief'. However these require downloading multiple datapacks to the server, and have limited control over entities due to the capabilities of datapacks.

---

### Usage
This mod tackles the issues above by adding a couple of new commands with which you can enable or disable the griefing capabilities of individual entity types.

The commands take on the following form:
```
/entityGriefing <entity> [<capability> [true|false]]
/entityGriefingAll <capability> <true|false>
/entityGriefingReset
```
where `<entity>` is the entity type you want to control, `<capability>` is the type of griefing you want to control, and `[true|false]` is whether you want to enable or disable the capability.

_Not all entities will appear in tab-completion, either because I haven't identified any griefing capabilities or because they haven't been implemented yet. Please open an issue on [GitHub](https://github.com/BVengo/anti-entity-grief/) or visit my [Discord](https://discord.com/invite/kUhf3WSSfv) to let me know if there is a missing feature that you feel should be included._

For example, to stop endermen from picking up blocks, you would run:
```
/entityGriefing minecraft:enderman DESTROY_BLOCKS false
```

To set the BREAK_DOORS permission for all applicable entities, you would run:
```
/entityGriefingAll BREAK_DOORS false
```

To see if villagers can farm crops, you would run:
```
/entityGriefing minecraft:villager FARM_CROPS
```

And finally to see all the things that a pig can do, you would run:
```
/entityGriefing minecraft:pig ALL
```

### Configs
The following capabilities are available for modification, and are available for the applicable entities:
- `BREAK_DOORS` - The ability to break down doors
  - Zombies (and all variants)
- `DESTROY_BLOCKS` - A general ability to destroy blocks through movement or other actions.
  - Endermen (picking up blocks)
  - Ender dragons (through movement)
  - Ravagers(through movement)
  - Silverfish (merging with stone and breaking out of infested stone)
  - Withers (through movement)
- `EAT_BLOCKS` - The ability to modify blocks through some form of eating.
  - Foxes (eating sweet berries)
  - Rabbits (eating carrots)
  - Sheep (eating grass)
- `EXPLODE_BLOCKS` - Breaking blocks through means of explosions.
  - Creepers (and charged creepers)
  - End Crystal
  - Ghasts (via large fireball)
  - TNT (and TNT minecart)
  - Withers (via skulls)
- `FARM_CROPS` - The ability to farm crops.
  - Villagers (Farmer variant)
- `MELT_SNOW` - Living entities on fire will melt powdered snow blocks when standing inside them. This is automatically
    included for all applicable entities.
- `PICKUP_ITEMS` - The ability to pick up items. Not a primary focus of this mod, so not fully implemented yet.
  - Allay
  - _(Future)_ Piglins, zombies, skeletons, etc
- `PLACE_BLOCKS` - The ability to place blocks.
  - Endermen (placing blocks they pick up)
  - Snow golem (placing snow below them)
  - Wither (placing wither roses where entities die)
- `PLACE_EGGS` - The ability to place eggs in the world. Does not disrupt the pregnancy cycle.
  - Turtles
  - _(Future)_ Frogs
- `SET_FIRE` - The ability to set fire to blocks.
  - Blaze (via small fireball)
  - Ghast (via large fireball)
  - Lightning (natural or channelled)
- `TRAMPLE_CROPS` - Living entities of a certain size will trample crops and farmland when walking on them. This is
  automatically included for all applicable entities.
- `TRAMPLE_EGGS` - All living entities except for turtles and bats will trample eggs when walking on them. This is
  automatically included for all applicable entities. This additionally impacts:
  - Zombie (and all variants) - the intentional attempt to trample turtle eggs

### Contributing
To further discuss or get notified about new updates, check out my [Discord](https://discord.gg/gyTa5v7kKk). If you like what I do, consider supporting me on Ko-Fi!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/C0C7DZ3FB)
