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

        if (nextY >= CANVAS_HEIGHT - RADIUS) return true;

        boolean blockCenter = false;
        boolean blockLeft = false;
        boolean blockRight = false;

        for (Ball other : concat(staticBalls, fallingBalls)) {
            if (other == b) continue;

            double dx = other.getX() - b.getX();
            double dy = other.getY() - nextY;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 2 * RADIUS) {
                if (Math.abs(dx) ==0) {
                    if (!blockRight && b.getX() + X_OFFSET <= CANVAS_WIDTH - RADIUS) {
                        b.setCenter(b.getX() + X_OFFSET, b.getY() + HEIGHT_STEP);
                        return false;
                    }
                } else if (dx < 0) {
                    blockLeft = true;
                } else {
                    blockRight = true;
                }
            }
        }

        // Se tocca il centro
        if (blockCenter) {
            if (!blockRight && b.getX() + X_OFFSET <= CANVAS_WIDTH - RADIUS) {
                b.setCenter(b.getX() + X_OFFSET, b.getY() + HEIGHT_STEP);
                return false;
            } else if (!blockLeft && b.getX() - X_OFFSET >= RADIUS) {
                b.setCenter(b.getX() - X_OFFSET, b.getY() + HEIGHT_STEP);
                return false;
            } else {
                return true;
            }
        }

        // Se bloccata lateralmente (uno solo)
        if (blockLeft && !blockRight && b.getX() + X_OFFSET <= CANVAS_WIDTH - RADIUS) {
            b.setCenter(b.getX() + X_OFFSET, b.getY() + HEIGHT_STEP);
            return false;
        }
        if (blockRight && !blockLeft && b.getX() - X_OFFSET >= RADIUS) {
            b.setCenter(b.getX() - X_OFFSET, b.getY() + HEIGHT_STEP);
            return false;
        }

        // Altrimenti scende dritta
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