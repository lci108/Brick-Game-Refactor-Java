# COMP2042_CW_efycl17
# Brick Breaker Game
Brick Game is a JavaFX-based game that combines classic block-breaking gameplay with exciting new features. The game has undergone significant enhancements, including the addition of new blocks, improved levels, visual effects, and sound elements.


## Compilation Instructions

Follow these clear, step-by-step instructions to compile the code and produce the enhanced Brick Breaker game application.

### 1. Download Zip File

Download the zip file containing the source code from the [repository]([#your-repository-link](https://github.com/lci108/COMP2042_CW_efycl17)). You can find the "Download ZIP" option on the main repository page.

### 2. Extract to Your Desired Location

Extract the downloaded zip file to your preferred location on your local machine.

### 3. Download IntelliJ

Ensure you have IntelliJ IDEA installed on your system. You can download the latest version from the official [IntelliJ IDEA website](https://www.jetbrains.com/idea/download/).

### 4. Open the Project in IntelliJ

Open IntelliJ IDEA and navigate to "File" -> "Open" to locate and open the downloaded Brick Breaker game project.

### 5. Download JavaFX Libraries from Gluon

To run the enhanced Brick Breaker game, you need to download the JavaFX libraries from the Gluon website.

1. Open your web browser and go to the [Gluon JavaFX download page](https://gluonhq.com/products/javafx/).
2. Download the JavaFX SDK that matches your operating system. Make sure to download the version compatible with your Java version.
3. Extract the downloaded JavaFX SDK to your preferred location on your local machine.

### 6. Configure Project Libraries in IntelliJ

Now that you have the JavaFX libraries downloaded, you'll need to configure your IntelliJ project to include them.

1. Navigate to "File" -> "Project Structure."
2. In the Project Structure window, select "Libraries" under "Project Settings."
3. Click the "+" icon to add a new library.
4. Choose "Java" to add a Java library.
5. Browse to the location where you extracted the JavaFX SDK and select the `lib` directory inside it.
6. Press "OK" to add the JavaFX library.
 
### 7. Enjoy the Game

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
- To enhance maintainability, developers can easily configure the chances for each block, adding a layer of customization to increase the fun factor.


- **Moving Impenetrable Block**: A new block, the impenetrable block, is introduced. This block only spawns during the last level and exhibits movement behavior.The impenetrable block is implemented with the ability to move horizontally. It enhances the challenge in the final level, requiring players to adapt to the dynamic behavior of this special block.


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

**1) Ball Penetrating Multiple Blocks (rarely)**
- **Issue**: The ball penetrates multiple blocks when hitting from certain angles without deflecting.
- **Solution atemmpted**: Revamped `checkHitToBlock()` logic and introduced a new variable `ballRadius`. Modified ball physics in `setPhysicsToBall()` to deflect the ball only within a certain angle, reducing the likelihood of penetrating multiple blocks due to collisions with a large angle.
- **Explanation**: The initial issue stemmed from the ball penetrating multiple blocks under specific collision conditions. The traditional collision detection logic (`checkHitToBlock()`) and ball physics (`setPhysicsToBall()`) were not robust enough to handle certain angles of impact. The `checkHitToBlock()` logic was revamped to provide more accurate and reliable collision checks. Additionally, a new variable, `ballRadius`, was introduced to better define the ball's size and improve collision accuracy. Furthermore, modifications were made to the ball physics when colliding with break in the `setPhysicsToBall()` method. These changes ensured that the ball deflects only within a specific angle range, minimizing the chances of it penetrating multiple blocks when colliding at a large angle.

Although these modifications did reduce the likelihood of the ball to penetrate multiple blocks , but sometimes it will still occur. Besides that , I changed the physics of collision between ball and break to make sure the ball doesnt deflect to an angle which is too steep that will cause penetration of multiple blocks, this changed the original game physics just to reduce the bugs.

### 2) Moving Impenetrable Block Collision
- **Issue:** The `checkHitToBlock()` method has inconsistent return values, causing the ball to deflect incorrectly when hitting the bottom of a moving impenetrable block.
  - **Explanation:** The collision problem specifically occurs with the moving impenetrable block. It is hypothesized that the issue is related to the block's movement and the returning of previous `x` and `y` coordinates, leading to a lack of synchronization in collision detection.


## Features Not Implemented

The following feature was contemplated but not implemented, accompanied by an explanation:

**1) Fortified Block and Broken Block Mechanism**
- **Description**: I intended to introduce a special block called `FORTIFIED_BLOCK`. The concept was that once this block was hit by the ball, it would change its appearance to a `BROKEN_BLOCK`. The `FORTIFIED_BLOCK` would require one hit to transform into a `BROKEN_BLOCK`, adding an element of challenge and strategy to the gameplay.

- **Issue Encountered**: During implementation attempts in the `onUpdate()` method, the desired behavior did not materialize. The ball, upon hitting the `FORTIFIED_BLOCK`, did not sufficiently deflect away quickly enough. As a result, when the `BROKEN_BLOCK` was spawned and destroyed in a split second because the ball immediately collided with it, behaving as if it were a regular block.

- **Attempts to Resolve**: Several approaches, including cooldown periods, hit flags, and tweaking ball physics, were experimented with to address the issue. However, none of these attempts proved successful in achieving the intended behavior.

- **Decision**: Due to the challenges in ensuring the proper interaction between the ball, `FORTIFIED_BLOCK`, and `BROKEN_BLOCK`, and considering the complexity of implementing a cooldown mechanism that didn't compromise the game's fluidity, the decision was made to defer the implementation of this feature. While the idea was intriguing, the technical challenges outweighed the potential gameplay enhancement in this specific context.

  ## New Java Classes

### 1) ExhaustTail

#### Purpose
The `ExhaustTail` class is responsible for creating and managing the exhaust trail effect behind the ball in the game. It enhances the visual appeal by adding dynamic particles that follow the ball's movement.

#### Location
- Package: `brickGame`
- File: `ExhaustTail.java`

#### Methods
- **Constructor (`ExhaustTail(Ball ball)`):** Initializes the `ExhaustTail` with a reference to the `Ball` object it is associated with, and sets up the particle image, list, and randomizer.
  
- **`update()`:** Invoked to update the state of the exhaust trail. It creates new particles and manages their fading effect.

- **`createParticle()`:** Generates new `ImageView` particles with randomized properties, such as position, size, and opacity.

- **`fadeParticles()`:** Handles the fading effect of existing particles, gradually reducing their opacity until they disappear.

- **`getParticles()`:** Returns the list of `ImageView` particles for rendering.

### 2) Soundeffects

#### Purpose
The `Soundeffects` class manages the game's sound effects. It provides methods to play different sound effects associated with various in-game events, enhancing the auditory experience for the player.

#### Location
- Package: `brickGame`
- File: `Soundeffects.java`

#### Methods
- **Constructor (`Soundeffects()`):** Initializes `MediaPlayer` instances for various sound effects by loading the corresponding audio files.

- **`createPlayer(String soundFileName)`:** Creates a `MediaPlayer` for a given sound file name.

- **`playSound(MediaPlayer player)`:** Plays a sound using the provided `MediaPlayer` instance.

- **Sound-specific methods (`playBlockHit()`, `playItemCatch()`, etc.)**: Trigger playback of specific sound effects for different in-game events.

 ## Modified Java Classes
### 1) Ball 
#### Constructors
- `public Ball(double radius, double initialX, double initialY);`
  - Initializes the ball with a specified radius and initial coordinates.

#### Physics and Movement
- `public void setPhysicsToBall();`
  - Encapsulates physics calculations, collision handling, and movement logic for the ball.

#### Collision Handling
- `public void onUpdateBall(int hitCode);`
  - Handles ball updates based on collision events specified by `hitCode`.
- `private double calculateVelocityX(double relation);`
  - Calculates the new horizontal velocity based on the relation parameter.
- `public void resetColideFlags();`
  - Resets collision flags, promoting cleaner code.

#### Other Methods
- `public void resetHitBottomFlag();`
  - Resets the flag indicating that the ball has hit the bottom of the game scene.
- getters and setters
#### Explaination
- Moved Ball-Specific Methods:
Methods related specifically to the behavior and properties of the ball were separated from the main class.By isolating ball-specific behaviors into the Ball class, we achieve better encapsulation and a cleaner separation of concerns. The setPhysicsToBall method now encapsulates the intricate physics, collision, and movement logic, making the code more readable and maintainable.

### 2) Break

#### Constructors
- `public Break(double x, double y, int width, int height);`
  - Initializes the Break object with specified coordinates, width, and height.

#### Movement
- `public static void move(final int direction);`
  - Initiates the movement of the Break object in the specified direction using a separate thread.

#### Other methods
- getters and setters

#### Explaination
- Moved Break-Specific Methods:
Methods related specifically to the behavior and properties of the break were moved to the Break class.By isolating break-related methods, we enhance code organization and adhere to the principles of encapsulation, making the codebase more modular and comprehensible.

### 3) Model

#### Methods
- **`public CopyOnWriteArrayList<Block> setUpBoard()`:**
  - Sets up the initial arrangement of blocks on the game board based on the player's level.

- **`private int determineBlockType(int randomChance)`:**
  - Determines the type of block to be created based on a random chance.

- **`public boolean allBlocksDestroyed()`:**
  - Checks if all blocks on the game board have been destroyed.
 
- **`public void moveImpenetrableBlock()`:**
  - move the impenetrable blocks that is spawned in the last level  
#### Other Methods 
- getters and setters

#### Explanation
- The `Model` class manages key aspects of the game state, including levels, hearts, scores, and block arrangements on the board. It encapsulates the logic for setting up the game board based on the player's level and determining the type of blocks to be created. The methods provide a way to interact with and update the game state as the player progresses.

### 4) View
#### Constructors
- `public View(boolean loadFromSave, Ball ball, Break rect, int level, ExhaustTail exhaustTail);`
  - Initializes the View with the specified parameters.

#### Methods
- `public void initUI(boolean loadFromSave, Ball ball, Break rect);`
  - Initializes the user interface elements, including buttons, labels, and the game scene.

- `public void updateExhaustTail();`
  - Updates the exhaust tail effect following the ball's movement.

- `public void addBlockToRoot(Block spook);`
  - Adds a block to the root pane.

- `public Scene getScene();`
  - Returns the game scene.

- `public Button getLoadButton();`
  - Returns the load button.

- `public Button getNewGameButton();`
  - Returns the new game button.

- `public void setPenaltyLabelVisibility(boolean visible);`
  - Sets the visibility of the penalty label.

- `public void setPauseLabelLabelVisibility(boolean visible);`
  - Sets the visibility of the pause label.

- `public void setLoadButtonVisibility(boolean visible);`
  - Sets the visibility of the load button.

- `public void setNewGameButtonVisibility(boolean visible);`
  - Sets the visibility of the new game button.

- `public void addToRoot(Node node);`
  - Adds a JavaFX node to the root pane.

- `public void removeStyleClassFromRoot(String styleClass);`
  - Removes a style class from the root pane.

- `public void addStyleClassToRoot(String styleClass);`
  - Adds a style class to the root pane.

- `public void updateScore(int score);`
  - Updates the score label.

- `public void updateHeart(int heart);`
  - Updates the heart label.

- `public void updateLevel(int level);`
  - Updates the level label.

- `public void updatePenaltyTime(int i);`
  - Updates the penalty label with the specified time.

- `public void removeAllElementsFromRoot();`
  - Removes all children nodes and style classes from the root pane.
#### Explaination 
The `View` class plays a crucial role in managing the graphical user interface (GUI) elements for the game. These methods collectively manage the presentation and interaction aspects of the game. The `getScene()` method acts as an interface for external components to access and manipulate the graphical scene, contributing to the modular design of the application.

### 5) Controller

#### Main Methods

- `public void onUpdate()`
  - Responsible for updating the game state and handling various events during each frame.
  - Checks for collisions between the ball and blocks, updating the score and triggering special effects.
  - Manages the appearance of bonus items (chocolates, mysteries) and their interactions with the Break object.
  - Monitors the gold status, adjusting the ball's appearance and handling its duration.
  - Handles the event when the ball hits the bottom of the game scene, decreasing the player's heart count and ending the game if hearts run out.
  - Invokes the `nextLevel()` method when all blocks are destroyed, advancing the game to the next level.
  - Updates the graphical user interface to reflect the current score, heart count, and other relevant information.

- `public void onPhysicsUpdate()`
  - Manages physics-related updates during each frame.
  - Invokes the `setPhysicsToBall()` method for the ball, encapsulating physics calculations, collision handling, and movement logic.
  - Handles specific game mechanics, such as freezing the Break object for a penalty duration if triggered.
  - Controls the movement and gravity of bonus items (chocolates, mysteries) based on elapsed time.

- `public void onTime(long time)`
  - Updates the elapsed time during the game.
  - Enables tracking of time-based events, such as the duration of the gold status.
  - Utilizes the time parameter to synchronize game elements and animations.

- `public void restartGame()`
  - Restarts the game by resetting the level, score, heart count, and other relevant parameters.
  - Initiates the creation of a new game state, allowing the player to start over.

- `public void nextLevel()`
  - Advances the game to the next level, resetting necessary parameters.
  - Clears existing blocks and bonus items, preparing the game state for the next level's setup.
  - Invokes the `start()` method to initiate rendering and updating of the new game state.


#### Explanation
The `Controller` class in the Model-View-Controller (MVC) architecture acts as the central hub for managing game logic and user input. It connects the `Model`, which represents the game state, with the `View`, responsible for rendering the graphical user interface. The `onUpdate()` method handles frame-by-frame updates, checking collisions, updating scores, and interacting with various game elements. The `onPhysicsUpdate()` method manages physics-related aspects, ensuring smooth ball movement and handling penalties. The `onTime()` method tracks the elapsed time, facilitating time-dependent game mechanics.The `restartGame()` and `nextLevel()` methods provide functionality to restart the game and advance to the next level, respectively. Overall, the `Controller` plays a pivotal role in orchestrating the game's behavior and maintaining a separation of concerns within the MVC design pattern.

### 6) GameEngine

#### Changes Made
- **Thread to Timeline Conversion:**
  - Replaced the usage of threads with JavaFX Timelines for various game-related tasks, such as frame updates, physics calculations, and time tracking.
  - Utilized JavaFX Timelines for onUpdate, onPhysicsUpdate, and onTime callbacks, improving synchronization and providing a more streamlined approach to handle periodic tasks in a JavaFX application.

#### Explanation
The `GameEngine` class underwent a modification in its implementation by replacing the traditional thread-based approach with JavaFX Timelines. This change enhances the synchronization of various game-related tasks and aligns with the conventions of JavaFX applications. The onUpdate, onPhysicsUpdate, and onTime callbacks are now executed within the context of JavaFX Timelines, ensuring a more robust and thread-safe execution of game logic. Overall, this adjustment contributes to the coherence and efficiency of the game engine in a JavaFX environment.

### 7) Block

#### Changes Made
- **Refactoring of Collision Detection:**
  - Modified the `checkHitToBlock` method for improved collision detection.
  - Replaced the initial collision detection logic with a more generalized and accurate approach for handling collisions on all sides of the block.
  - The updated logic checks for overlap in both X and Y directions, calculating the depth of overlap in each direction and determining the side of collision based on the minimum overlap.
- **Introduction to different blocks:**
  1. **Mystery Block:**
   - The Mystery Block introduces an element of unpredictability to the game.
   - Hitting this block triggers the release of a Mystery Bonus, offering players a chance to gain extra points or activate special effects.

2. **Spooky Block:**
   - Adorned with spooky decorations, this block adds a Halloween-themed twist.
   - The Spooky Block transforms into a Spooked Block after being hit, creating dynamic challenges for the player.

3. **Impenetrable Block:**
   - Introduced in the last level, the Impenetrable Block poses a significant challenge.
   - This block is impenetrable and requires strategic gameplay to overcome.
 
    

#### Explanation
The `Block` class underwent a modification in the `checkHitToBlock` method to enhance the accuracy and generality of collision detection. The original method, which relied on specific conditions for each side of the block, was replaced with a more systematic approach. The revised logic now considers overlapping in both X and Y directions, determining the depth of overlap in each direction. By comparing these overlaps, the method accurately identifies the side of the block where the collision occurred. This modification improves the robustness and reliability of collision detection in the game, ensuring a more consistent and realistic gameplay experience.Besides that , the introduction to new blocks contribute to a rich gaming experience, requiring players to employ different strategies and skills as they progress through the levels.

### 8) Block Serializable

#### Changes Made
- **Added a new parametre to save 'direction':**

#### Explanation
In order to implement the impenetrable blocks that are moving in different left right directions concurrently , a direction variable is introduced.



## Unexpected Problems Encountered 

### 1. Double Execution of `nextLevel()`

**Issue:** After transitioning from using traditional threads to JavaFX Timelines, a peculiar problem arose where the `nextLevel()` method was being executed twice when all blocks were destroyed, causing the game to skip levels.

**Solution:** To mitigate this issue, flags were introduced to ensure that `nextLevel()` runs only once per update cycle. While this addressed the symptom, the root cause behind the double execution remains elusive, necessitating further investigation.

### 2. Ball Penetrating Multiple Blocks

**Issue:** The ball penetrated multiple blocks when hitting from certain angles, even after modifying the collision detection logic.

**Solution:** The `checkHitToBlock()` logic was revamped to provide more accurate collision checks. Additionally, a new variable, `ballRadius`, was introduced to define the ball's size more precisely. However, occasional penetration may still occur, revealing the complexity of handling collisions at steep angles.

Addressing these challenges involved a combination of code modifications, introducing flags, and enhancing collision detection logic. While these solutions alleviate the immediate issues, they also highlight areas for potential future refinement and optimization. Ongoing efforts will be directed towards a more thorough understanding of the root causes behind these unexpected behaviors.
### 3. Moving Impenetrable Block Collision
- **Issue:** The `checkHitToBlock()` method has inconsistent return values, causing the ball to deflect incorrectly when hitting the bottom of a moving impenetrable block.
  - **No solution yet** The collision problem specifically occurs with the moving impenetrable block. It is hypothesized that the issue is related to the block's movement and the returning of previous `x` and `y` coordinates, leading to a lack of synchronization in collision detection.

