package entity;

import config.Figure;

import java.awt.*;
import java.io.Serializable;

public class Gizmo implements Serializable {
    protected int x;
    protected int y;
    protected int sizeRate = 1;
    protected Figure figure;
    protected Image img;
    protected double angle = 0 * Math.PI / 180;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSizeRate() {
        return sizeRate;
    }

    public void setSizeRate(int sizeRate) {
        this.sizeRate = sizeRate;
    }

    public Figure getFigure() {return figure; }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
