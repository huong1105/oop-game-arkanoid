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

        void update(double deltaTimeSeconds) {
            x += dx * deltaTimeSeconds;
            y += dy  * deltaTimeSeconds;
            alpha -= 1.8 * deltaTimeSeconds;
        }

        boolean isDead() {
            return alpha <= 0;
        }
    }

    private List<Particle> particles = new ArrayList<>();
    private static final int NUM_PARTICLES = 20;
    private Random rand = new Random();

    public FireWorkEffect(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.active = false;
    }

    public void reset(double x, double y, double width, double height) {
        // 1. Cập nhật vị trí và trạng thái
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
        //this.bounds = new javafx.geometry.Rectangle2D(x, y, width, height); // Cập nhật bounds nếu cần

        particles.clear();

        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        for (int i = 0; i < NUM_PARTICLES; i++) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double speed = 120 + rand.nextDouble() * 180;
            double dx = Math.cos(angle) * speed;
            double dy = Math.sin(angle) * speed;
            double sizeParticle = 2 + rand.nextDouble() * 3;
            particles.add(new Particle(centerX, centerY, dx, dy, sizeParticle));
        }
    }

    @Override
    public void update(double deltaTimeSeconds) {
        if (!active) return;

        for (Particle p : particles) {
            p.update(deltaTimeSeconds);
        }

        particles.removeIf(Particle::isDead);

        if (particles.isEmpty()) {
            this.setActive(false);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        for (Particle p : particles) {
            int grayValue = 200 + rand.nextInt(56);
            gc.setFill(Color.rgb(grayValue, grayValue, grayValue, p.alpha));
            gc.fillOval(p.x, p.y, p.size, p.size);
        }
    }
}
