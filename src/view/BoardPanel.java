package view;

import config.Change;
import config.Figure;
import config.Function;
import config.GameInterface;
import controller.BoardController;
import controller.ChangeController;
import controller.GizmoController;

import javax.swing.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private BoardController boardController;
    private ChangeController changeController;
    private MyThread thread;
    private int length = 220;//面板宽度
    private int grid;//格子
    private static GizmoController gizmoController;


    public BoardPanel(BoardController boardController, ChangeController changeController){
        this.boardController = boardController;
        this.changeController = changeController;
        grid = getGridSize();
        boardController.setRowHeight(grid);
        boardController.newWorld();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                length = getLength();
                int x = (int) ((e.getX() - GameInterface.X0) / grid);
                int y = (int) ((e.getY() - GameInterface.X0)/ grid);
                int sizeRate = boardController.getSizeRate();
                boardController.setFunction(boardController.getFunction());
                gizmoController = boardController.getGizmo(x,y);

                if (boardController.getFunction() == Function.Figure){
                    if (changeController.getFigure() == Figure.Finger){
                        changeController.clear();
                    }
                    if (changeController.getFigure() == Figure.Ball)
                        sizeRate = 1;
                    else if (changeController.getFigure() == Figure.LeftPaddle||changeController.getFigure()==Figure.RightPaddleh我)
                        sizeRate = 2;
                    else if (changeController.getFigure() == Figure.Track)
                        sizeRate = 1;
                    else if (changeController.getFigure() == Figure.Curve)
                        sizeRate = 1;
                    if (boardController.canAdd(x, y, sizeRate, changeController.getFigure())){
                        System.out.println(changeController.getFigure());

                        if (changeController.getFigure() == Figure.Track){
                            GizmoController tmp = new GizmoController(x,y,sizeRate, changeController.getFigure(), changeController.getImg());
                            // Gizmo tmp1 = new Gizmo(x - 1,y, sizeRate, dataSource.getShape(), dataSource.getImg());
                            boardController.addComponents(tmp);
                            // board.addComponents(tmp1);
                        }
                        else {
                            GizmoController tmp = new GizmoController(x,y,sizeRate, changeController.getFigure(), changeController.getImg());
                            boardController.addComponents(tmp);
                        }
                    }
                }else if (boardController.getFunction() == Function.Change){
                    if (changeController.getChange() == Change.Delete){
                        boardController.deleteGizmo(gizmoController);
                    }else if (changeController.getChange() == Change.Rotation){
                        if (boardController.canRotate(x, y, gizmoController.getSizeRate(), gizmoController)){
                            boardController.rotateGizmo(gizmoController);
                        }
                    }else if (changeController.getChange() == Change.Plus){
                        boardController.addGizmo(gizmoController);
                    }else if (changeController.getChange() == Change.Minus){
                        boardController.minusGizmo(gizmoController);
                    }
                }
                repaint();

            }
        });

    }

    public void design(){
        if(boardController.isDesignFunction()){
            thread.interrupt();
            boardController.updateCompoments();
        }else{
            thread = new MyThread();
            thread.start();
            this.requestFocus();
        }
    }

    public int getLength(){return Math.min(getHeight(),getWidth()) - 10;}
    public int getGridSize(){ return getLength()/ (GameInterface.LINES -1);}

    class MyThread extends Thread{
        @Override
        public void run(){
            while(!boardController.isDesignFunction()){
                boardController.setStep();
                repaint();
                try{
                    Thread.sleep((long) (boardController.getTimeStep() * 1000));
                }catch (InterruptedException e){
                    break;
                }
            }
            repaint();
        }
    }
}
