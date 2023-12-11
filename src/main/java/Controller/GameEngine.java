package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
/**
 * The GameEngine class orchestrates the game loop and provides a framework for game-related actions.
 * It manages the update, initialization, physics calculations, and time tracking of a game.
 */
public class GameEngine {

    private OnAction onAction;
    private volatile int fps = 15;
    private Timeline updateTimeline;
    private Timeline physicsTimeline;
    private volatile boolean isStopped = true;

    private long time = 0;
    private Timeline timeTimeline;
    private final Object timeLock = new Object();

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        if (fps <= 0) {
            throw new IllegalArgumentException("FPS must be greater than 0");
        }
        this.fps = 1000 / fps;
    }

    private void update() {
        updateTimeline = new Timeline(new KeyFrame(Duration.millis(fps), e -> onAction.onUpdate()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void initialize() {
        onAction.onInit();
    }

    private void physicsCalculation() {
        physicsTimeline = new Timeline(new KeyFrame(Duration.millis(fps), e -> onAction.onPhysicsUpdate()));
        physicsTimeline.setCycleCount(Timeline.INDEFINITE);
        physicsTimeline.play();
    }

    public void start() {
        synchronized (timeLock) {
            time = 0;
        }
        isStopped = false;
        initialize();
        update();
        physicsCalculation();
        timeStart();
    }

    public void stop() {
        isStopped = true;
        if (updateTimeline != null) {
            updateTimeline.stop();
        }
        if (physicsTimeline != null) {
            physicsTimeline.stop();
        }
        if (timeTimeline != null) {
            timeTimeline.stop();
        }
    }

    private void timeStart() {
        timeTimeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            synchronized (timeLock) {
                time++;
            }
            onAction.onTime(time);
        }));
        timeTimeline.setCycleCount(Timeline.INDEFINITE);
        timeTimeline.play();
    }
    /** Callback interface for game-related actions. */
    public interface OnAction {
        void onUpdate();
        void onInit();
        void onPhysicsUpdate();
        void onTime(long time);
    }
}