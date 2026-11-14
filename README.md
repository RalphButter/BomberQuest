# Bomber Quest

Bomber Quest is a 2D action game developed in Java with the [LibGDX](https://libgdx.com/) framework. Players navigate a tile-based maze, place bombs to destroy obstacles and defeat enemies, collect power-ups, and reach the exit before time runs out.

Developed as a university project at the Technical University of Munich (TUM), 2024–2025.

The game's source code was written manually by the developers, without AI-generated code.

The README documentation was revised on November 14, 2026, by Ralph Butter with assistance from Claude (Anthropic) to improve its structure, clarity, and presentation.

## Features

The following features are verified from the source code:

- **120-second countdown timer** – race to clear the level before time expires
- **3-heart health system** – lose a heart on enemy contact or by your own blast; game over at zero hearts
- **Bomb mechanics** – place bombs with configurable blast radius and concurrent bomb cap; explosions destroy destructible walls and kill enemies
- **Two power-ups** – blast radius upgrade (`MaxRadiusPU`) and max concurrent bombs upgrade (`MaxBombPU`); both award points when collected
- **A\* pathfinding enemies** – enemies navigate the maze autonomously using A* search to reach patrol targets
- **Destructible and indestructible walls** – bombs carve paths through destructible blocks; indestructible walls stop blasts
- **Locked exit** – the exit tile is locked until all enemies are defeated
- **HUD** – live display of hearts, timer, score, blast radius, max bombs, enemy count, and exit status
- **Pause screen** (ESC) – pause gameplay, load a different map via native file chooser, or return to the main menu
- **Win screen and Game Over screen** – shown on level completion or when health/time runs out, displaying final score and time
- **Credits screen** – accessible from the main menu
- **Background music and sound effects** – separate tracks for menu and gameplay, plus sounds for bombs, blasts, item pickups, and death
- **Two bundled maps** – `map-1.properties` and `map-2.properties` in `assets/maps/`

## Technology Stack

| Component | Version                  |
|-----------|--------------------------|
| Java      | 17                       |
| LibGDX    | 1.13.0                   |
| Backend   | LWJGL3                   |
| Build     | Gradle (wrapper bundled) |

## Requirements

- **JDK 17** – the Gradle toolchain enforces `JavaLanguageVersion.of(17)`
- Windows, macOS, or Linux desktop

## Installation and Launch

### Windows

1. Clone the repository:
   ```bat
   git clone https://github.com/RalphButter/BomberQuest.git
   cd BomberQuest
   ```

2. Run with the bundled Gradle wrapper (no separate Gradle or LibGDX installation needed):
   ```bat
   .\gradlew.bat :desktop:run
   ```

Dependencies are downloaded automatically on first run.

### macOS / Linux

```sh
./gradlew :desktop:run
```

> macOS: the `-XstartOnFirstThread` JVM argument is added automatically by the Gradle task.

## Controls

| Key           | Action                        |
|---------------|-------------------------------|
| W / A / S / D | Move up / left / down / right |
| Space         | Place bomb                    |
| ESC           | Pause / unpause               |

## Project Structure

```
BomberQuest/
├── assets/                       # Runtime resources (working directory when the game runs)
│   ├── maps/                     # Bundled map files (.properties)
│   ├── skin/craftacular/         # Craftacular UI skin
│   ├── texture/                  # Sprite sheets and textures
│   ├── audio/                    # Background music
│   └── sound/                    # Sound effects
├── core/src/de/tum/cit/ase/bomberquest/
│   ├── BomberQuestGame.java      # Main application class and screen manager
│   ├── Characters/               # Player and Enemy entities
│   ├── MapManager/               # Map loading and grid management
│   ├── gameobject/               # Bombs, Blast, power-ups, Cell, CellType, CountdownTimer
│   ├── map/                      # GameMap, Chest, Flowers
│   ├── pathFinding/              # A* implementation (Node, PathFinding)
│   ├── screen/                   # All game screens (Menu, Game, GameOver, Win, Credits)
│   ├── texture/                  # SpriteSheet and animation helpers
│   └── util/                     # FollowCamera, Manager, SoundsManager, Rectangle
├── desktop/src/                  # Desktop launcher (LWJGL3)
├── build.gradle                  # Multi-project Gradle configuration
└── settings.gradle               # Gradle project settings
```

## Credits

Developed by **Ralph Butter** and **Jonas Wahl**  
Technical University of Munich, WS 2024/25

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

Third-party components included in this repository:
- **LibGDX** (Apache License 2.0) – https://libgdx.com/
- **Craftacular UI skin** by Raymond "Raeleus" Buckley – see `assets/skin/craftacular/`

---

*Thank you for playing Bomber Quest!*
