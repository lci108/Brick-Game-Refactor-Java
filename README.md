# COMP2042_CW_efycl17
GameControls
---

| Keys | Description | 
| -------- | -------- | 
| RIGHT ARROW KEY | Move right |
| LEFT ARROW KEY| Move left|
| SPACEBAR | Start/pause the game |

Changes
# Brick Breaker Game - Enhanced Edition

Welcome to the enhanced version of the classic Brick Breaker game! In this update, we've introduced several exciting features, improvements, and extensions to elevate your gaming experience. Read on to discover the key changes and enhancements.

## Highlighted Changes

### 1. New Blocks - Spooky Block and Mystery Block
We've added two new blocks to spice up the gameplay:

- **Spooky Block**: Initially adorned with pumpkins hanging below, the Spooky Block transforms into a regular Spooked Block after being hit once. Spooked Blocks spawn randomly on the screen and are destroyed upon impact.

- **Mystery Block**: Striking this block triggers the drop of a Mystery Bonus. Upon collision with the paddle, players have a 50% chance to gain +5 score or freeze the paddle for 3 seconds. A countdown penalty label warns players during the freeze.

To enhance maintainability, developers can easily configure the chances for each block, adding a layer of customization to increase the fun factor.

### 2. New Blocks Layout
Say goodbye to random block spawns! We've revamped the game's logic, introducing 8 levels with custom layouts. Each level presents a unique challenge, offering a more engaging and strategic gaming experience. The speed of the ball dynamically changes across levels, ensuring a progressively challenging adventure.

### 3. Exhaust Tail
Aesthetic improvements abound with the introduction of the Exhaust Tail. Star images now trail behind the ball with varying sizes and fading times, enhancing the visual appeal of the game. The new `ExhaustTail.java` class handles this feature, providing a delightful touch to the overall gaming experience.

### 4. Sound Effects
Immerse yourself in the game with a range of new sound effects:

- Block destruction
- Victory
- Game over
- Lose a heart
- Gain a heart
- Freeze effect
- Bonus collision with the paddle
- Spooky Block impact

The `SoundEffects.java` class manages these audio enhancements, creating a more immersive and engaging auditory experience.

## Where and Why
These enhancements are strategically implemented in the following areas:

- **Block and Bonus Classes**: The heart of the gameplay, where new blocks and bonuses bring fresh challenges and excitement. Developers can easily tweak chances for each block, fostering maintainability.

- **Game Logic**: A major overhaul in the game's logic, providing carefully crafted layouts for 8 levels, ensuring a more strategic and enjoyable progression.

- **Exhaust Tail Class**: Aesthetic improvements to the ball's trail, adding a visually appealing element to the gaming experience.

- **Sound Effects Class**: Immersive audio cues complementing key events in the game, enhancing the overall enjoyment and engagement for players.

## Enjoy the Enhanced Experience!
We hope you thoroughly enjoy these new features and enhancements in the Brick Breaker Game - Enhanced Edition. Feel free to explore, adapt, and make the most out of the customizable elements to tailor the gameplay to your liking. Happy gaming!
