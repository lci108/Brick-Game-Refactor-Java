package brickGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ExhaustTail {
    private List<ImageView> particles;
    private Ball ball;
    private final int maxParticles = 4;
    private Random random;
    private Image particleImage;

    public ExhaustTail(Ball ball) {
        this.ball = ball;
        this.particleImage = new Image("tail.png");
        particles = new ArrayList<>();
        random = new Random();
    }

    public void update() {
        createParticle();
        fadeParticles();
    }

    private void createParticle() {
        if (particles.size() < maxParticles) {
            ImageView particle = new ImageView(particleImage);
            double offsetX = random.nextDouble() * 40 - 20; // Increased spread X
            double offsetY = random.nextDouble() * 40 - 20; // Increased spread Y
            particle.setX(ball.getBallX() + offsetX);
            particle.setY(ball.getBallY() + offsetY);
            particle.setFitWidth(10 + random.nextDouble() * 40); // Adjusted size
            particle.setFitHeight(10 + random.nextDouble() * 40);
            particle.setOpacity(0.5 + random.nextDouble() * 0.5); // Random opacity
            particles.add(particle);
        }
    }

    private void fadeParticles() {
        Iterator<ImageView> iterator = particles.iterator();
        while (iterator.hasNext()) {
            ImageView particle = iterator.next();
            double fadeRate = 0.01 + random.nextDouble() * 0.008; // Adjusted fade rate
            double newOpacity = particle.getOpacity() - fadeRate;
            particle.setOpacity(Math.max(newOpacity, 0));
            if (newOpacity <= 0) {
                iterator.remove();
            }
        }
    }

    public List<ImageView> getParticles() {
        return particles;
    }
}
