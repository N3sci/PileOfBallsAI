package application.pileofballs.Model;

import javafx.scene.paint.Color;

public class Ball {
    private double x;
    private double y;
    private Color color;

    public Ball(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setCenter(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}