package brickGame;

import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {

    private OnAction onAction;
    private int fps = 15;
    private Timer updateTimer, physicsTimer, timeTimer;
    private boolean isStopped = true;
    private long time = 0;
    private final Object timeLock = new Object();

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    private void initialize() {
        onAction.onInit();
    }

    private void startUpdateTimer() {
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onAction.onUpdate();
            }
        }, 0, fps);
    }

    private void startPhysicsTimer() {
        physicsTimer = new Timer();
        physicsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onAction.onPhysicsUpdate();
            }
        }, 0, fps);
    }

    private void startTimeTimer() {
        timeTimer = new Timer();
        timeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (timeLock) {
                    time++;
                }
                onAction.onTime(time);
            }
        }, 0, 1);
    }

    public void start() {
        if (isStopped) {
            isStopped = false;
            initialize();
            startUpdateTimer();
            startPhysicsTimer();
            startTimeTimer();
        }
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            if (updateTimer != null) {
                updateTimer.cancel();
            }
            if (physicsTimer != null) {
                physicsTimer.cancel();
            }
            if (timeTimer != null) {
                timeTimer.cancel();
            }
        }
    }

    public interface OnAction {
        void onUpdate();
        void onInit();
        void onPhysicsUpdate();
        void onTime(long time);
    }
}

