package application.pileofballs.Controller;

import application.pileofballs.Model.Ball;
import javafx.scene.paint.Color;

public class BallGroup {
    private Ball[] balls = new Ball[3];
    private boolean centerBallDown;
    private double centerX, centerY;
    private double spacing = 20;

    public BallGroup(double startX, double startY) {
        centerX = startX;
        centerY = startY;
        balls[0] = new Ball(centerX, centerY, Color.RED);
        balls[1] = new Ball(centerX, centerY, Color.BLUE);
        balls[2] = new Ball(centerX, centerY, Color.GREEN);
        centerBallDown = true;
        applyLayout();
    }

    public void rotate(boolean orario) {
        if (orario && !centerBallDown || !orario && centerBallDown) {
            Ball temp = balls[1];
            balls[1] = balls[2];
            balls[2] = temp;
        } else {
            Ball temp = balls[0];
            balls[0] = balls[1];
            balls[1] = temp;
        }
        centerBallDown = !centerBallDown;
        applyLayout();
    }

    private void updatePositions() {
        applyLayout();
    }

    private void applyLayout() {
        double offsetY = 34.64101615 * (centerBallDown ? -1 : 1);
        if (balls[1] != null) balls[1].setCenter(centerX, centerY + offsetY);
        double sideY = centerY - offsetY;
        if (balls[0] != null) balls[0].setCenter(centerX - spacing, sideY);
        if (balls[2] != null) balls[2].setCenter(centerX + spacing, sideY);
    }

    public Ball[] getBalls() {
        return balls != null ? balls : new Ball[0];
    }

    public boolean moveDown(double speed, java.util.List<Ball> staticBalls) {
        centerY += speed;
        updatePositions();
        for (Ball b : balls) {
            if (b == null) continue;
            if (b.getY() >= 600 - spacing) {
                return true;
            }
            for (Ball staticBall : staticBalls) {
                double dx = staticBall.getX() - b.getX();
                double dy = staticBall.getY() - (b.getY() + speed);
                if (dx * dx + dy * dy < 1600) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveLeft() {
        if (centerX - spacing * 2 >= spacing) {
            centerX -= spacing;
            updatePositions();
        }
    }

    public void moveRight() {
        if (centerX + spacing * 2 <= 400 - spacing) {
            centerX += spacing;
            updatePositions();
        }
    }
}