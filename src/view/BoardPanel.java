package view;

import config.Change;
import config.Figure;
import config.Function;
import config.GameInterface;
import controller.BoardController;
import controller.ChangeController;
import controller.GizmoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

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
                    else if (changeController.getFigure() == Figure.LeftPaddle||changeController.getFigure()==Figure.RightPaddle)
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (boardController.isDesignFunction()) return;
                /*if (e.getKeyChar() == ' '){
                    board.keyApplyForce();
                }*/
                if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN){
                    int dx = 0, dy = 0;
                    switch (code) {
                        case KeyEvent.VK_LEFT:
                            dx = -5;
                            break;
                        case KeyEvent.VK_RIGHT:
                            dx = 5;
                            break;
                        case KeyEvent.VK_UP:
                            dy = 5;
                            break;
                        case KeyEvent.VK_DOWN:
                            dy = -5;
                            break;
                    }
                    boardController.rightPaddleMove(dx, dy);
                }

                else if(code == KeyEvent.VK_A || code == KeyEvent.VK_D || code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                    int dx = 0, dy = 0;
                    switch (code) {
                        case KeyEvent.VK_A:
                            dx = -5;
                            break;
                        case KeyEvent.VK_D:
                            dx = 5;
                            break;
                        case KeyEvent.VK_W:
                            dy = 5;
                            break;
                        case KeyEvent.VK_S:
                            dy = -5;
                            break;
                    }
                    boardController.leftPaddleMove(dx, dy);
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(boardController.getCanFocus())
                    requestFocus();
            }
        });

        repaint();

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

    public void paintBoard(Graphics g){
        Image image = new BufferedImage(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        image.getGraphics().drawRect(0, 0, getWidth(), getHeight());
        length = getLength();
        Graphics2D g2D = (Graphics2D) image.getGraphics();
        super.setBackground(new Color(0, 0, 0));
        g2D.setColor(this.getBackground());
        g2D.fill(new Rectangle(0, 0, getWidth(), getHeight()));
        g2D.setColor(Color.WHITE);
        for (int i = 0; i <= GameInterface.LINES; i++) {
            Line2D row = new Line2D.Double(GameInterface.X0, GameInterface.Y0 + grid * i, length, GameInterface.Y0 + grid * i);
            Line2D col = new Line2D.Double(GameInterface.X0 + grid * i, GameInterface.Y0, GameInterface.X0 + grid * i, length+grid);
            g2D.draw(row);     //绘画横线
            g2D.draw(col);     //绘画纵线
        }
        boardController.paintComponents(g2D, grid, length);
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

    }
}
