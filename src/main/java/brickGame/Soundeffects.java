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




    public Soundeffects() {
        blockHitPlayer = createPlayer("block_hit.mp3");
        itemCatchPlayer = createPlayer("item_catch.mp3");
        heartUpPlayer = createPlayer("heart_up.mp3");
        heartDownPlayer = createPlayer("heart_down.mp3");
        goldTimePlayer = createPlayer("goldblock_hit.mp3");

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



    public  void playBlockHit() {
        playSound(blockHitPlayer);
    }

    public  void playItemCatch() {
        playSound(itemCatchPlayer);
    }

    public void playHeartUp() {
        playSound(heartUpPlayer);
    }

    public void playHeartDown() {
        playSound(heartDownPlayer);
    }

    public void playGoldBlockHit() {playSound(goldTimePlayer);}

    private void playSound(MediaPlayer player) {
        try {
            if (player != null) {
                player.stop();
                player.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

