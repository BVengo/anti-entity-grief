# Anti Entity Grief
![GitHub license](https://img.shields.io/github/license/BVengo/antientitygrief.svg)
![GitHub release](https://img.shields.io/github/release/BVengo/antientitygrief.svg)
![GitHub issues](https://img.shields.io/github/issues/BVengo/antientitygrief.svg)

A minecraft mod for fabric, allowing you to control the griefing capabilities of entities!

### The Issue
Entities can grief the world in all sorts of ways, leading from minor nuisances to major issues. The team at Mojang recognised this and so added the gamerule `mobGriefing` which allows you to disable all mobs from griefing. Unfortunately this is too broad a command to be useful, since in your attempt to disable endermen from stealing blocks you have now stopped your villagers from farming crops!

VanillaTweaks has some fantastic datapacks to handle this, namely 'Anti Creeper Grief', 'Anti Enerman Grief', and 'Anti Ghast Grief'. However these require downloading multiple datapacks to the server, and have limited control over entities due to the capabilities of datapacks.

### The Solution
This mod tackles the issues above by adding a new command (not gamerule) `entityGriefing` with which you can enable or disable the griefing capabilities of individual entity types.


Currently, controls have been implemented for the following list of entities:

| **Entity**    | **How do they grief?**                        |
|---------------|-----------------------------------------------|
| :x: Blaze        | Set fires                                     |
| :x: Creeper      | Destroy blocks                                |
| :white_check_mark: Enderman      | Pick up / place blocks                        |
| :x: Ender Dragon | Destroy blocks                                |
| :x: End Crystal  | Destroy blocks                                |
| :x: Frog         | Place eggs                                    |
| :x: Ghast        | Destroy blocks, set fires                     |
| :x: Player       | All the things                                |
| :x: Silverfish   | Destroy blocks                                |
| :x: Snow golem   | Leave snow trails                             |
| :x: Turtle       | Place eggs                                    |
| :x: TNT          | Destroy blocks - also impacts TNT minecarts   |
| :x: Villager     | Farm crops                                    |
| :x: Wither       | Destroy blocks                                |


### Contributing
To further discuss or get notified about new updates, check out my [Discord](https://discord.com/invite/kUhf3WSSfv). If you like what I do, consider supporting me on Ko-Fi!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/C0C7DZ3FB)
