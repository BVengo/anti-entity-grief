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
This mod tackles the issues above by adding a new command `entityGriefing` with which you can enable or disable the griefing capabilities of individual entity types.

The command takes the following form:
```
/entityGriefing <entity> [<capability> [true|false]]
```
where `<entity>` is the entity type you want to control, `<capability>` is the type of griefing you want to control, and `[true|false]` is whether you want to enable or disable the capability.

_Not all entities will appear in tab-completion, either because I haven't identified any griefing capabilities or because they haven't been implemented yet. Please open an issue on [GitHub](https://github.com/BVengo/anti-entity-grief/) or visit my [Discord](https://discord.com/invite/kUhf3WSSfv) to let me know if there is a missing feature that you feel should be included._

---

#### Examples
To stop endermen from picking up blocks, you would run:
```
/entityGriefing minecraft:enderman DESTROY_BLOCKS false
```

To see if villagers can farm crops, you would run:
```
/entityGriefing minecraft:villager FARM_CROPS
```

To see all permissions for a pig, you would run:
```
/entityGriefing minecraft:pig
```

### Contributing
To further discuss or get notified about new updates, check out my [Discord](https://discord.com/invite/kUhf3WSSfv). If you like what I do, consider supporting me on Ko-Fi!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/C0C7DZ3FB)
