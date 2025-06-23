package application.pileofballs.Controller;

import application.pileofballs.Model.Ball;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class GameController {

    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private BallGroup currentGroup;
    private AnimationTimer timer;
    private Vector<Ball> staticBalls = new Vector<>();
    private List<Ball> fallingBalls = new ArrayList<>();

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        currentGroup = new BallGroup(200, 95.02577388);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(event -> {
            if (currentGroup == null || !fallingBalls.isEmpty()) return;

            switch (event.getCode()) {
                case UP -> currentGroup.rotate(true);
                case LEFT -> currentGroup.moveLeft();
                case RIGHT -> currentGroup.moveRight();
                case DOWN -> currentGroup.rotate(false);
            }
        });
        timer = new AnimationTimer() {
            public void handle(long now) {
                gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

                if (currentGroup != null) {
                    for (Ball b : currentGroup.getBalls()) {
                        gc.setFill(b.getColor());
                        gc.fillOval(b.getX() - 20, b.getY() - 20, 40, 40);
                    }
                    if (currentGroup.moveDown(1.73205081, staticBalls)) {
                        Collections.addAll(fallingBalls, currentGroup.getBalls());
                        currentGroup = null;
                    }
                } else if (fallingBalls.isEmpty()) {
                    currentGroup = new BallGroup(200, 95.02577388);
                } else {
                    for (Ball b : fallingBalls) {
                        if (BallPhysics.applyGravity(b, 1.73205081, staticBalls, fallingBalls)) {
                            fallingBalls.remove(b);
                            staticBalls.add(b);
                        }
                    }
                    // Disegna le palline singole che cadono
                    for (Ball b : fallingBalls) {
                        gc.setFill(b.getColor());
                        gc.fillOval(b.getX() - 20, b.getY() - 20, 40, 40);
                    }
                }
                for (Ball b : staticBalls) {
                    gc.setFill(b.getColor());
                    gc.fillOval(b.getX() - 20, b.getY() - 20, 40, 40);
                }


            }
        };
        timer.start();
    }


}












