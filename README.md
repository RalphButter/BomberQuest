# Bomber Quest

Bomber Quest is an exciting and engaging game where players navigate through various levels, strategically placing bombs to defeat enemies and overcome obstacles. The game is developed using Java and the LibGDX framework, and it features a rich set of functionalities and an intuitive user interface.

## Table of Contents

- [Installation](#installation)
- [Gameplay](#gameplay)
- [Features](#features)
- [Controls](#controls)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle
- Git

### Steps

1. Clone the repository:
    ```sh
    git clone https://github.com/RalphButter/BomberQuest.git
    cd BomberQuest
    ```

2. Build the project using Gradle:
    ```sh
    ./gradlew build
    ```

3. Run the game:
    ```sh
    ./gradlew run
    ```

## Gameplay

In Bomber Quest, players control a character who must navigate through a maze-like environment, placing bombs to destroy obstacles and defeat enemies. The goal is to clear all enemies and reach the exit to advance to the next level.

### Objectives

- Defeat all enemies in the level.
- Avoid getting caught in bomb explosions.
- Collect power-ups to enhance abilities.
- Reach the exit to progress to the next level.

## Features

- **Multiple Levels**: Progress through increasingly challenging levels.
- **Power-Ups**: Collect various power-ups to gain advantages.
- **Enemies**: Face different types of enemies with unique behaviors.
- **HUD**: A heads-up display showing important game information such as time, blast radius, concurrent bombs, and remaining enemies.
- **Graphics**: Engaging 2D graphics with smooth animations.
- **Sound**: Immersive sound effects and background music.

## Controls

- **Arrow Keys**: Move the character.
- **Space Bar**: Place a bomb.
- **P**: Pause the game.
- **R**: Restart the level.

## Development

### Project Structure

- `core/src/de/tum/cit/ase/bomberquest/Characters/`: Contains character classes such as Player and Enemy.
- `core/src/de/tum/cit/ase/bomberquest/map/`: Contains map-related classes.
- `core/src/de/tum/cit/ase/bomberquest/texture/`: Contains texture and drawable classes.
- `core/src/de/tum/cit/ase/bomberquest/screen/`: Contains screen classes for different game states.

### Key Classes

- **Player**: Represents the player character.
- **Enemy**: Represents enemy characters.
- **Hud**: Manages the heads-up display.
- **GameScreen**: Manages the main game screen and game loop.

### Building and Running

To build and run the project, use the following Gradle commands:

- **Build**: `./gradlew build`
- **Run**: `./gradlew run`
- **Test**: `./gradlew test`

## Contributing

We welcome contributions from the community! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Make your changes and commit them with descriptive messages.
4. Push your changes to your fork.
5. Create a pull request to the main repository.

Please ensure your code follows the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Thank you for playing Bomber Quest! We hope you enjoy the game as much as we enjoyed developing it.