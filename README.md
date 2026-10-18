# Eclipse Realms

A Fabric mod for **Minecraft 1.21.1** that adds a whole new dimension, five biomes and seven legendary weapons with special abilities.

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
| **Abyssal Sea** | Deep dark oceans with kelp, sea pickles and glow squid |

## Weapons

| Weapon | Passive | Right-click ability |
|---|---|---|
| **Voidblade** | Lifesteal, may wither | *Void Blink*: teleport 8 blocks forward |
| **Frostfang Dagger** | Slows and freezes | *Frost Dash*: lunge forward |
| **Eclipse Greatsword** | Bonus damage below half health | *Eclipse Shockwave*: AoE blast + weakness |
| **Stormbreaker** | Launches foes, may shock | Call lightning where you look |
| **Ember Scythe** | Sets targets on fire | *Inferno Ring*: burn every nearby enemy |
| **Void Staff** | - | Fires withering void skulls |
| **Ember Staff** | - | Fires a spread of three fireballs |

Abilities have cooldowns and use durability. All weapons can be enchanted.

## Progression

1. Build an Eclipse Portal in the Overworld.
2. Mine **Void Crystal Ore** in the Eclipse (iron pickaxe or better) for **Void Crystal Shards**.
3. Collect **Luminite Clusters**. Combine 4 shards, 4 gold ingots and 1 luminite cluster into an **Eclipse Ingot**.
4. Forge the weapons (check JEI/REI or the recipe book). The Ember Staff can be crafted in the Overworld from blaze materials.

Other blocks: Umbral Stone and Bricks, Duskmoss, Ash, Starlight Logs, Planks and Leaves, and Void Crystal Blocks. English and Turkish translations are included.

## Building

Requires Java 21.

```
./gradlew build      # jar ends up in build/libs/
./gradlew runClient  # launch a dev client
```

Every push is also built by GitHub Actions. You can download the jar from the run's artifacts. Install it with Fabric Loader and Fabric API.
