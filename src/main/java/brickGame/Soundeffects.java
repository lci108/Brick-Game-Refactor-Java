package brickGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class Soundeffects {
    private MediaPlayer blockHitPlayer;
    private MediaPlayer itemCatchPlayer;
    private MediaPlayer heartUpPlayer;
    private MediaPlayer heartDownPlayer;
    private MediaPlayer goldTimePlayer;
    private MediaPlayer freezeTimePlayer;


    public Soundeffects() {
        blockHitPlayer = createPlayer("block_hit.mp3");
        itemCatchPlayer = createPlayer("item_catch.mp3");
        heartUpPlayer = createPlayer("heart_up.mp3");
        heartDownPlayer = createPlayer("heart_down.mp3");
        goldTimePlayer = createPlayer("goldblock_hit.mp3");
        freezeTimePlayer = createPlayer("ice_time.mp3");
    }

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



    private void disposePlayer(MediaPlayer player) {
        if (player != null) {
            player.stop();
            player.dispose();
        }
    }
}
