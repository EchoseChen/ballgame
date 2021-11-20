package view;

import config.Change;
import config.Figure;
import config.Function;
import controller.ChangeController;
import entity.GameButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FigurePanel extends JPanel {
    private GameButton btnFinger = readImg("icons/finger.png", "0.", Figure.Finger);
    private GameButton btnBall = readImg("icons/playball.png","1.", Figure.Ball);
    private GameButton btnAbsorber = readImg("icons/blackhole.png","2.", Figure.Absorber);
    private GameButton btnTriangle = readImg("icons/triangle.png","3.", Figure.Triangle);
    private GameButton btnCircle = readImg("icons/circle.png","4.", Figure.Circle);
    private GameButton btnSquare = readImg("icons/square.png","5.", Figure.Square);
    private GameButton btnTrack = readImg("icons/track.png","6.", Figure.Track);
    private GameButton btnCurve = readImg("icons/curve.png","7.", Figure.Curve);
    private GameButton btnPaddleLeft = readImg("icons/peddle.png","8.", Figure.LeftPaddle);
    private GameButton btnPaddleRight = readImg("icons/peddle.png","9.", Figure.RightPaddle);

    private ButtonGroup btnGroup = new ButtonGroup();
    List<GameButton> compoments;
    private ChangeController changeController = new ChangeController();

    private GameButton readImg(String imgPath, String num, Figure figure){
        ImageIcon img=new ImageIcon(imgPath);
        img.setImage(img.getImage().getScaledInstance(45,45,Image.SCALE_DEFAULT));
        GameButton btn = new GameButton(img, figure);
        btn.setImg(img.getImage());
        btn.setEnabled(true);
        return btn;
    }


    public FigurePanel(){
        btnGroup.add(btnFinger);
        btnGroup.add(btnBall);
        btnGroup.add(btnCircle);
        btnGroup.add(btnAbsorber);
        btnGroup.add(btnSquare);
        btnGroup.add(btnTriangle);
        btnGroup.add(btnTrack);
        btnGroup.add(btnPaddleLeft);
        btnGroup.add(btnPaddleRight);
        btnGroup.add(btnCurve);

        btnFinger.setSelected(true);

        super.add(btnFinger);
        super.add(btnBall);
        super.add(btnCircle);
        super.add(btnAbsorber);
        super.add(btnSquare);
        super.add(btnTriangle);
        super.add(btnTrack);
        super.add(btnCurve);
        super.add(btnPaddleLeft);
        super.add(btnPaddleRight);

        //将button组成一个队列，方便重载button类的属性
        compoments = new ArrayList();
        compoments.add(btnFinger);
        compoments.add(btnBall);
        compoments.add(btnCircle);
        compoments.add(btnAbsorber);
        compoments.add(btnSquare);
        compoments.add(btnTriangle);
        compoments.add(btnTrack);
        compoments.add(btnCurve);
        compoments.add(btnPaddleLeft);
        compoments.add(btnPaddleRight);


        changeController.addButtonActionListener(compoments, Function.Figure);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
