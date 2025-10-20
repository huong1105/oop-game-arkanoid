package com.arkanoid.entities;
import com.arkanoid.core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireWorkEffect extends GameObject {

    private static class Particle {
        double x, y;
        double dx, dy;
        double size;
        double alpha;

        Particle(double x, double y, double dx, double dy, double size) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.size = size;
            this.alpha = 1.0;
        }

        void update() {
            x += dx;
            y += dy;
            alpha -= 0.03;
        }

        boolean isDead() {
            return alpha <= 0;
        }
    }

    private List<Particle> particles = new ArrayList<>();
    private static final int NUM_PARTICLES = 30;
    private Random rand = new Random();

    public FireWorkEffect(int x, int y, int width, int height) {
        super(x, y, width, height);
        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        for (int i = 0; i < NUM_PARTICLES; i++) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double speed = 2 + rand.nextDouble() * 3;
            double dx = Math.cos(angle) * speed;
            double dy = Math.sin(angle) * speed;
            double sizeParticle = 2 + rand.nextDouble() * 3;
            particles.add(new Particle(centerX, centerY, dx, dy, sizeParticle));
        }
    }

    @Override
    public void update() {
        for (Particle p : particles) {
            p.update();
        }
        particles.removeIf(Particle::isDead);
        if (particles.isEmpty()) setActive(false);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (Particle p : particles) {
            int grayValue = 200 + rand.nextInt(56);
            gc.setFill(Color.rgb(grayValue, grayValue, grayValue, p.alpha));
            gc.fillOval(p.x, p.y, p.size, p.size);
        }
    }
}
