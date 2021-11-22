package controller;

import view.ChangePanel;
import config.Function;
import entity.GameButton;
import config.Change;
import config.Figure;
import config.Pattern;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ChangeController {
    private static Figure figure;
    private static Function curFunction;
    private static Change change;
    private static Image img;

    public void addButtonActionListener(List<GameButton> compoments, Function function){
        for (GameButton button : compoments){
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (BoardController.isDesignFunction()){
                        curFunction = function;
                        BoardController.setFunction(curFunction);
                        figure = button.getFigure();
                        change = button.getChange();
                        img = button.getImg();
                    }
                }
            });
        }
    }

    public static Image getImg() {
        return img;
    }

    public static Change getChange() {
        return change;
    }

    public static void clear(){
        curFunction = null;
        figure = null;
        change = null;
        img = null;
    }

    public static Figure getFigure() {
        return figure;
    }

}
