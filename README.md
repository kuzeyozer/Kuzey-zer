# Eclipse Realms

A Fabric mod for **Minecraft 1.21.1** that adds a whole new dimension with five biomes, four structures, three mobs and seven legendary weapons with special abilities.

## The Eclipse (new dimension)

An eternally dark realm with a frozen midnight sky, purple fog and glowing flora. You get there with a crafted **Eclipse Portal**:

```
C A C      C = Crying Obsidian
A E A      A = Amethyst Shard
C A C      E = Eye of Ender
```

Place it and walk into it. On the other side the mod finds the nearest linked portal. If there isn't one, it builds a small brick shrine with a return portal, so you can always get home. Monsters spawn freely in the dark, so bring torches.

## Biomes

| Biome | What you'll find |
|---|---|
| **Duskwood Forest** | Purple duskmoss, glowing Starlight trees, luminite crystals |
| **Crystal Spires** | Calcite plains with towering void-crystal spires, amethyst geodes, many endermen |
| **Glowshroom Marsh** | Giant mushrooms, teal grass, spore particles, mooshrooms |
| **Ashen Wastes** | Ash dunes with fires that never go out, basalt spikes, lava lakes, blazes and wither skeletons |
| **Abyssal Sea** | Rare, deep oceans floored with abyssal sand, glowing coral pillars and anemones |

## Structures

| Structure | Where | Inside |
|---|---|---|
| **Void Shrine** | Duskwood Forest, Crystal Spires, Glowshroom Marsh | Crystal altar with a loot chest, guarded by Umbral Stalker spawners |
| **Eclipse Tower** | Any land biome | 20-block spire with a ladder, Void Wisp and Umbral Stalker spawners, and loot chests at the top that can hold legendary weapons |
| **Ashen Forge** | Ashen Wastes | Blackstone smithy with a lava trough, an Ember Fiend spawner and chests full of ingots |
| **Sunken Ruin** | Abyssal Sea | Broken temple on the sea floor with an underwater chest |

## Mobs

| Mob | Where | Notes |
|---|---|---|
| **Umbral Stalker** | Most biomes | Tough shadow zombie. Hits inflict Darkness and Weakness. Drops void crystal shards |
| **Ember Fiend** | Ashen Wastes | Armored fire spirit that shoots fireballs. Can drop Eclipse Ingots |
| **Void Wisp** | Most land biomes | Flies through walls. Hits inflict Wither. Drops luminite |

Each mob has a spawn egg in the creative tab.

## Finding things with /locate

`/locate` searches the dimension you're standing in. Inside the Eclipse:

```
/locate biome eclipserealms:crystal_spires
/locate structure eclipserealms:eclipse_tower
/locate structure #eclipserealms:eclipse_structures   (nearest structure of any kind)
```

From the Overworld, put `/execute in eclipserealms:eclipse run` in front, for example:

```
/execute in eclipserealms:eclipse run locate structure eclipserealms:void_shrine
```

The biomes are `duskwood_forest`, `crystal_spires`, `glowshroom_marsh`, `ashen_wastes` and `abyssal_sea`. The structures are `void_shrine`, `eclipse_tower`, `ashen_forge` and `sunken_ruin`.

## Weapons

| Weapon | Damage | Passive | Right-click ability |
|---|---|---|---|
| **Voidblade** | 10 | Heals 1.5 hearts per hit, 40% chance to wither | *Void Blink*: teleport 12 blocks forward |
| **Frostfang Dagger** | 9 (very fast) | Slowness III + freezing | *Frost Dash*: powerful lunge |
| **Eclipse Greatsword** | 18 | +8 damage to targets below half health | *Eclipse Shockwave*: 18 damage to all foes within 8 blocks, plus knockback and weakness |
| **Stormbreaker** | 17 | Launches foes, 50% chance of +6 shock damage | Calls a storm of three lightning bolts |
| **Ember Scythe** | 16 | Sets targets on fire for 8s | *Inferno Ring*: 12 damage to every foe within 7 blocks, plus burning |
| **Void Staff** | - | - | Fires withering void skulls quickly |
| **Ember Staff** | - | - | Fires a fan of five fireballs |

Abilities have cooldowns and use durability. All weapons can be enchanted.

## Progression

1. Build an Eclipse Portal in the Overworld.
2. Mine **Void Crystal Ore** in the Eclipse (iron pickaxe or better) for **Void Crystal Shards**.
3. Collect **Luminite Clusters**. Combine 4 shards, 4 gold ingots and 1 luminite cluster into an **Eclipse Ingot**.
4. Forge the weapons (check JEI/REI or the recipe book). The Ember Staff can be crafted in the Overworld from blaze materials.

Other blocks: Abyssal Sand, Bricks, Coral and Anemones (ocean blocks), Umbral Stone and Bricks, Duskmoss, Ash, Starlight Logs, Planks and Leaves, and Void Crystal Blocks. English and Turkish translations are included.

## Building

Requires Java 21.

```
./gradlew build      # jar ends up in build/libs/
./gradlew runClient  # launch a dev client
```

Every push is also built by GitHub Actions. You can download the jar from the run's artifacts. Install it with Fabric Loader and Fabric API.
