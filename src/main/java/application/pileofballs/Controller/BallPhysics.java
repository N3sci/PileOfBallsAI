package application.pileofballs.Controller;

import application.pileofballs.Model.Ball;
import java.util.List;

public class BallPhysics {

    private static final double RADIUS = 20;
    private static final double HEIGHT_STEP = 34.64101615;
    private static final double X_OFFSET = 20;
    private static final double CANVAS_WIDTH = 400;
    private static final double CANVAS_HEIGHT = 600;

    public static boolean applyGravity(Ball b, double speed, List<Ball> staticBalls, List<Ball> fallingBalls) {
        double nextY = b.getY() + speed;

        // Controlla se ha raggiunto il fondo
        if (nextY >= CANVAS_HEIGHT - RADIUS) {
            b.setCenter(b.getX(), CANVAS_HEIGHT - RADIUS);
            return true;
        }

        for (Ball other : concat(staticBalls, fallingBalls)) {
            if (other == b) continue;

            double dx = other.getX() - b.getX();
            double dy = other.getY() - nextY;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= 2 * RADIUS - 1) {
                // collisione diretta sotto (stessa colonna)
                if (Math.abs(dx) < 1) {
                    b.setCenter(b.getX(), other.getY() - 2 * RADIUS);
                    return true;
                }

                // Deviazione laterale
                double newX;
                if (dx > 0 && b.getX() - X_OFFSET >= RADIUS) {
                    newX = b.getX() - X_OFFSET;
                } else if (dx < 0 && b.getX() + X_OFFSET <= CANVAS_WIDTH - RADIUS) {
                    newX = b.getX() + X_OFFSET;
                } else {
                    // Bloccato su entrambi i lati
                    b.setCenter(b.getX(), other.getY() - 2 * RADIUS);
                    return true;
                }

                b.setCenter(newX, b.getY() + HEIGHT_STEP);
                return false;
            }
        }

        // Altrimenti scende normalmente
        b.setCenter(b.getX(), nextY);
        return false;
    }

    private static List<Ball> concat(List<Ball> a, List<Ball> b) {
        return new java.util.ArrayList<>() {{
            addAll(a);
            addAll(b);
        }};
    }
}