# Anti Entity Grief
![GitHub license](https://img.shields.io/github/license/BVengo/antientitygrief.svg)
![GitHub release](https://img.shields.io/github/release/BVengo/antientitygrief.svg)
![GitHub issues](https://img.shields.io/github/issues/BVengo/antientitygrief.svg)

A minecraft mod for fabric, allowing you to control the griefing capabilities of entities!

### Summary
Entities can grief the world in all sorts of ways, leading from minor nuisances to major issues. The team at Mojang recognised this and so added the gamerule `mobGriefing` which allows you to disable all mobs from griefing. Unfortunately this is too broad a command to be useful, since in your attempt to disable endermen from stealing blocks you have now stopped your villagers from farming crops!

VanillaTweaks has some fantastic datapacks to handle this, namely 'Anti Creeper Grief', 'Anti Enerman Grief', and 'Anti Ghast Grief'. However these require downloading multiple datapacks to the server, and have limited control over entities due to the capabilities of datapacks.

This mod introduces a new method of applying settings per-entity type. It includes settings unique to each type (e.g. separate pick up / place block options for endermen) as well as generic settings (e.g. trampling crops).

</details>

<details>
<summary>
<h3>Usage</h3>

This section contains examples of how to use the commands provided by this mod to control the behaviours of entities.
</summary>


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

</details>

<details>
<summary>
<h3>Configs</h3>

This section contains a list of all capabilities available for modification, and which entities they apply to.

</summary>

<table>
  <tr>
    <th>Capability</th>
    <th>Description</th>
    <th>Applicable Entities</th>
  </tr>
  <tr>
    <td><code>BREAK_DOORS</code></td>
    <td>The ability to break down doors</td>
    <td>
      <ul>
        <li>Zombies (and all variants)</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>DESTROY_BLOCKS</code></td>
    <td>
      <p>The ability to destroy blocks through movement or other actions. See <code>EXPLODE_BLOCKS</code> for destruction through explosions.</p>
      <p>This cannot currently be applied to players.</p>
    </td>
    <td>
      <ul>
        <li>Endermen <i>(picking up blocks)</i></li>
        <li>Ender dragons <i>(through movement)</i></li>
        <li>Ravagers <i>(through movement)</i></li>
        <li>Silverfish <i>(merging with stone, breaking out of infested stone)</i></li>
        <li>Withers <i>(through movement)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>EAT_BLOCKS</code></td>
    <td>The ability to modify blocks through some form of eating.</td>
    <td>
      <ul>
        <li>Foxes <i>(eating sweet berries)</i></li>
        <li>Rabbits <i>(eating carrots)</i></li>
        <li>Sheep <i>(eating grass)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>EXPLODE_BLOCKS</code></td>
    <td>Breaking blocks through means of explosions.</td>
    <td>
      <ul>
        <li>Creepers (including charged)</li>
        <li>End Crystal</li>
        <li>Ghasts <i>(via large fireball)</i></li>
        <li>TNT <i>(and TNT minecart)</i></li>
        <li>Withers <i>(via skulls)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>FARM_CROPS</code></td>
    <td>The ability to farm crops.</td>
    <td>
      <ul>
        <li>Villagers (Farmer variant)</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>MELT_SNOW</code></td>
    <td>Living entities on fire will melt powdered snow blocks when standing inside them.</td>
    <td>
      <ul>
        <li>All living entities</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>PICKUP_ITEMS</code></td>
    <td>The ability to pick up items. Not a primary focus of this mod, so has not been fully implemented yet.</td>
    <td>
      <ul>
        <li>Allay</li>
        <li><i>(Future)</i> Piglins, zombies, skeletons, etc</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>PLACE_BLOCKS</code></td>
    <td>The ability to place blocks.</td>
    <td>
      <ul>
        <li>Endermen <i>(placing blocks they are holding)</i></li>
        <li>Snow golem <i>(placing snow below them)</i></li>
        <li>Wither <i>(placing wither roses where entities die)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>PLACE_EGGS</code></td>
    <td>The ability to place eggs in the world. Does not disrupt the pregnancy cycle.</td>
    <td>
      <ul>
        <li>Turtles</li>
        <li><i>(Future)</i> Frogs</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>SET_FIRE</code></td>
    <td>The ability to set fire to blocks.</td>
    <td>
      <ul>
        <li>Blaze <i>(via small fireball)</i></li>
        <li>Ghast <i>(via large fireball)</i></li>
        <li>Lightning <i>(natural or channelled)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>TRAMPLE_CROPS</code></td>
    <td>
      <p>Living entities over a certain size (<code>width<sup>2</sup> * height > 0.512</code>) will trample crops and farmland when walking on them. This option handles the destruction of crops only (see <code>TRAMPLE_FARMLAND</code> for turning farmland into dirt).</p>
      <p>If this is disabled, farmland trampling is only possible if no crops are above them.</p>
    </td>
    <td>
      <ul>
        <li>All applicable entities <i>(this is a calculated field)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>TRAMPLE_FARMLAND</code></td>
    <td>
      <p>Living entities over a certain size (<code>width<sup>2</sup> * height > 0.512</code>) will trample crops and farmland when walking on them. This option only handles turning farmland to dirt (see <code>TRAMPLE_CROPS</code> for destruction of crops).</p>
      <p>If this is disabled, crops on farmland can still be trampled without turning the farmland to dirt </p>
    </td>
    <td>
      <ul>
        <li>All applicable entities <i>(this is a calculated field)</i></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>TRAMPLE_EGGS</code></td>
    <td>All living entities except for turtles and bats will trample eggs when walking on them.</td>
    <td>
      <ul>
        <li>All applicable entities <i>(this is a calculated field)</i></li>
        <li>Zombie (and all variants) <i>(intentional egg trampling)</i></li>
      </ul>
    </td>
  </tr>
</table>

</details>

### Contributing
To further discuss or get notified about new updates, check out my [Discord](https://discord.gg/gyTa5v7kKk). If you like what I do, consider supporting me on Ko-Fi!

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/C0C7DZ3FB)
