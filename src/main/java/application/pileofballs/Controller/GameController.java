package application.pileofballs.Controller;

import application.pileofballs.Model.Ball;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    private Vector<Vector<Ball>> gridGame = new Vector<>();
    private List<Ball> fallingBalls = new ArrayList<>();

    public void initialize() {
        double y = 6;
        int x;
        for (int i = 0; i < 17; i++) {
            x=0;
            Vector<Ball> row = new Vector<>();
            for (int j = 0; j < 19; j++) {
                Ball b = null;
                if (i % 2 == 0 && j % 2 == 0) {
                    b = new Ball(x, y, Color.BLUE);
                } else if (i % 2 != 0 && j % 2 != 0) {
                    b = new Ball(x, y, Color.RED);
                }
                row.add(b);
                x+=20;
            }
            gridGame.add(row);
            x=20;
            y+=34.6;
        }
        gc = gameCanvas.getGraphicsContext2D();
        currentGroup = new BallGroup(200, 95.02577388);
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(event -> {
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
                // Disegna il gruppo attivo
                for (Ball b : currentGroup.getBalls()) {
                    gc.setFill(b.getColor());
                    gc.fillOval(b.getX() - 20, b.getY() - 20, 40, 40);
                }

                // Disegna le palline singole che cadono
                for (Ball b : fallingBalls) {
                    gc.setFill(b.getColor());
                    gc.fillOval(b.getX() - 20, b.getY() - 20, 40, 40);
                }
                List<Ball> stoppedBalls = new ArrayList<>();
                for (Ball b : fallingBalls) {
                    if (BallPhysics.applyGravity(b, 1.73205081, staticBalls, fallingBalls)) {
                        stoppedBalls.add(b);
                    }
                }
                fallingBalls.removeAll(stoppedBalls);
                staticBalls.addAll(stoppedBalls);

                boolean touchedBottom = currentGroup.moveDown(1, staticBalls);
                if (touchedBottom) {
                    Collections.addAll(fallingBalls, currentGroup.getBalls());
                    currentGroup = new BallGroup(200, 95.02577388);
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