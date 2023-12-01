# COMP2042_CW_efycl17
# Brick Breaker Game - Enhanced Edition

## Compilation Instructions

Follow these clear, step-by-step instructions to compile the code and produce the enhanced Brick Breaker game application.

### 1. Download Zip File

Download the zip file containing the source code from the [repository](#your-repository-link). You can find the "Download ZIP" option on the main repository page.

### 2. Extract to Your Desired Location

Extract the downloaded zip file to your preferred location on your local machine.

### 3. Download IntelliJ

Ensure you have IntelliJ IDEA installed on your system. You can download the latest version from the official [IntelliJ IDEA website](https://www.jetbrains.com/idea/download/).

### 4. Open the Project in IntelliJ

Open IntelliJ IDEA and navigate to "File" -> "Open" to locate and open the downloaded Brick Breaker game project.

### 5. Configure Project Libraries

1. Navigate to "File" -> "Project Structure."
2. In the Project Structure window, select "Libraries" under "Project Settings."
3. Click the "+" icon to add a new library.
4. Choose "Java" to add a Java library.
5. Paste the path of the "lib" directory in the downloaded Brick Breaker game where the required JAR files are located.
6. Select the "lib" directory and press "OK" to add it as a library.

### 6. Enjoy the Game

Your project is now configured! You can now run the game by executing the main class. Simply click "Run" in IntelliJ, and enjoy the enhanced Brick Breaker gaming experience.

Feel free to explore the customizable elements and features added in this enhanced edition. Have fun gaming!

*Note: Make sure to configure the project libraries as mentioned in step 5 to avoid any dependency-related issues.*

GameControls
---

| Keys | Description | 
| -------- | -------- | 
| RIGHT ARROW KEY | Move right |
| LEFT ARROW KEY| Move left|
| SPACEBAR | Start/pause the game |

## Implemented and Working Properly

The following features have been successfully implemented and are functioning as expected:

**1)New Blocks**: Spooky Block and Mystery Block are seamlessly integrated into the gameplay, providing a dynamic and entertaining experience.
I've added two new blocks to spice up the gameplay:

- **Spooky Block**: Initially adorned with pumpkins hanging below, the Spooky Block transforms into a regular Spooked Block after being hit once. Spooked Blocks spawn randomly on the screen and are destroyed upon impact.

- **Mystery Block**: Striking this block triggers the drop of a Mystery Bonus. Upon collision with the paddle, players have a 50% chance to gain +5 score or freeze the paddle for 3 seconds. A countdown penalty label warns players during the freeze.

To enhance maintainability, developers can easily configure the chances for each block, adding a layer of customization to increase the fun factor.

**2)New Blocks Layout**: The revamped logic for 8 custom layout levels ensures a challenging and strategic progression through the game.
- I've revamped the game's logic, introducing 8 levels with custom layouts. Each level presents a unique challenge, offering a more engaging and strategic gaming experience. The speed of the ball dynamically changes across levels, ensuring a progressively challenging adventure.

**3)Exhaust Tail**: The aesthetic improvement of the ball's trail with varying sizes and fading times adds a visually appealing element to the gaming experience.
- Aesthetic improvements abound with the introduction of the Exhaust Tail. Star images now trail behind the ball with varying sizes and fading times, enhancing the visual appeal of the game. , providing a delightful touch to the overall gaming experience.

**4)Sound Effects**: The newly added sound effects enhance key events in the game, creating a more immersive and engaging auditory atmosphere.

Immerse yourself in the game with a range of new sound effects:

- Block destruction
- Victory
- Game over
- Lose a heart
- Gain a heart
- Freeze effect
- Bonus collision with the paddle
- Spooky Block impact
  
**5) UI Overhaul and Interactive Backgrounds**: The modified UI and interactive backgrounds provide an improved and visually engaging interface for players.
- I've modified the entire original UI to provide a better gaming experience. The addition of WinRoot and GameOver Root enhances user interaction. Moreover, interactive backgrounds dynamically change during goldTime and freeze events, adding a layer of visual engagement to the game.

## Implemented but Not Working Properly

The following features have been implemented but are not working correctly. Steps have been taken to address the issues:

**1) Thread to Timeline Conversion**
- **Issue**: `nextLevel()` is running twice when all the blocks are destroyed.
- **Solution**: Flags have been added to ensure that `nextLevel()` runs only once in one update.
- **Explanation**: In the original implementation, the game used traditional `Thread` for handling animation and updates. However, this approach led to synchronization issues.The game engine's animation and update mechanism was refactored to use `Timeline` instead of `Thread`. The transition to `Timeline` provides a more synchronized and event-driven approach to managing game updates.However the `nextLevel()` method is being executed twice in a single update causing it to skip levels level1 -> level3 -> level5... after changing to `Timeline` , and I cant seem find the reason causing it.So I introduced flags ensuring that the 'nextLevel()` method is executed only once per update cycle.

Although shift to `Timeline` resolved the specific issue and also offers better control over the game's animation and update cycle, enhancing the overall stability and responsiveness of the game.
But I hard coded to solve the issue, by introducing flags to make sure the method only run once in an update without solving the real issue of why its running twice and might cause a harder maintainbility of the game in the future.

**2) Ball Penetrating Multiple Blocks**
- **Issue**: The ball penetrates multiple blocks when hitting from certain angles without deflecting.
- **Solution**: Revamped `checkHitToBlock()` logic and introduced a new variable `ballRadius`. Modified ball physics in `setPhysicsToBall()` to deflect the ball only within a certain angle, reducing the likelihood of penetrating multiple blocks due to collisions with a large angle.
- **Explanation**: The initial issue stemmed from the ball penetrating multiple blocks under specific collision conditions. The traditional collision detection logic (`checkHitToBlock()`) and ball physics (`setPhysicsToBall()`) were not robust enough to handle certain angles of impact. The `checkHitToBlock()` logic was revamped to provide more accurate and reliable collision checks. Additionally, a new variable, `ballRadius`, was introduced to better define the ball's size and improve collision accuracy. Furthermore, modifications were made to the ball physics when colliding with break in the `setPhysicsToBall()` method. These changes ensured that the ball deflects only within a specific angle range, minimizing the chances of it penetrating multiple blocks when colliding at a large angle.

Although these modifications did reduce the likelihood of the ball to penetrate multiple blocks , but sometimes it will still occur. Besides that , I changed the physics of collision between ball and break to make sure the ball doesnt deflect to an angle which is too steep that will cause penetration of multiple blocks, this changed the original game physics just to reduce the bugs.


## Features Not Implemented

The following feature was contemplated but not implemented, accompanied by an explanation:

**1) Fortified Block and Broken Block Mechanism**
- **Description**: I intended to introduce a special block called `FORTIFIED_BLOCK`. The concept was that once this block was hit by the ball, it would change its appearance to a `BROKEN_BLOCK`. The `FORTIFIED_BLOCK` would require one hit to transform into a `BROKEN_BLOCK`, adding an element of challenge and strategy to the gameplay.

- **Issue Encountered**: During implementation attempts in the `onUpdate()` method, the desired behavior did not materialize. The ball, upon hitting the `FORTIFIED_BLOCK`, did not sufficiently deflect away quickly enough. As a result, when the `BROKEN_BLOCK` was spawned and destroyed in a split second because the ball immediately collided with it, behaving as if it were a regular block.

- **Attempts to Resolve**: Several approaches, including cooldown periods, hit flags, and tweaking ball physics, were experimented with to address the issue. However, none of these attempts proved successful in achieving the intended behavior.

- **Decision**: Due to the challenges in ensuring the proper interaction between the ball, `FORTIFIED_BLOCK`, and `BROKEN_BLOCK`, and considering the complexity of implementing a cooldown mechanism that didn't compromise the game's fluidity, the decision was made to defer the implementation of this feature. While the idea was intriguing, the technical challenges outweighed the potential gameplay enhancement in this specific context.



## Enjoy the Enhanced Experience!
We hope you thoroughly enjoy these new features and enhancements in the Brick Breaker Game - Enhanced Edition. Feel free to explore, adapt, and make the most out of the customizable elements to tailor the gameplay to your liking. Happy gaming!
