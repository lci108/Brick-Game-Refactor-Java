package brickGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class Soundeffects {
    private final MediaPlayer blockHitPlayer;
    private final MediaPlayer itemCatchPlayer;
    private final MediaPlayer heartUpPlayer;
    private final MediaPlayer heartDownPlayer;
    private final MediaPlayer goldTimePlayer;
    private final MediaPlayer freezeTimePlayer;

    private final MediaPlayer spookyPlayer;
    private final MediaPlayer winPlayer;
    private final MediaPlayer gameOverPlayer;

    /**
     * Constructs a Soundeffects object, initializing MediaPlayer instances for each sound effect.
     */
    public Soundeffects() {
        blockHitPlayer = createPlayer("block_hit.mp3");
        itemCatchPlayer = createPlayer("item_catch.mp3");
        heartUpPlayer = createPlayer("heart_up.mp3");
        heartDownPlayer = createPlayer("heart_down.mp3");
        goldTimePlayer = createPlayer("goldblock_hit.mp3");
        freezeTimePlayer = createPlayer("ice_time.mp3");
        spookyPlayer = createPlayer("spooky.mp3");
        winPlayer = createPlayer("win.mp3");
        gameOverPlayer = createPlayer("gameOver.mp3");
    }
    /**
     * Creates a MediaPlayer for a given sound file.
     *
     * @param soundFileName The name of the sound file.
     * @return A MediaPlayer instance for the specified sound file.
     */
    private MediaPlayer createPlayer(String soundFileName) {
        URL resource = getClass().getResource("/" + soundFileName);
        if (resource != null) {
            Media sound = new Media(resource.toString());
            return new MediaPlayer(sound);
        } else {
            System.out.println("Resource not found: " + soundFileName);
            return null;
        }
    }
    /**
     * Plays the sound associated with a block hit.
     */
    private void playSound(MediaPlayer player) {
        if (player != null) {
            try {
                player.seek(javafx.util.Duration.ZERO); // Reset to start
                player.play();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        } else {
            System.err.println("MediaPlayer is null, cannot play sound.");
        }
    }

    public void playBlockHit() {
        playSound(blockHitPlayer);
    }

    public void playItemCatch() {
        playSound(itemCatchPlayer);
    }

    public void playHeartUp() {
        playSound(heartUpPlayer);
    }

    public void playHeartDown() {
        playSound(heartDownPlayer);
    }

    public void playGoldBlockHit() {
        playSound(goldTimePlayer);
    }

    public void playFreezeTime() {
        playSound(freezeTimePlayer);
    }

    public void playSpooky(){
        playSound(spookyPlayer);
    }

    public void playWin(){playSound(winPlayer);}

    public void playGameOver(){playSound(gameOverPlayer);}


}
