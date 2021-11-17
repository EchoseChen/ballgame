package entity;

import config.Change;
import config.Figure;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton{
    private Change change;
    private Figure figure;
    private Image img;

    public GameButton(Icon icon, Figure figure){
        super(icon);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        this.figure = figure;
    }

    public GameButton(Icon icon, Change change){
        super(icon);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        this.change = change;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
