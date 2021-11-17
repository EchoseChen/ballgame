package view;

import config.Figure;
import entity.GameButton;

import javax.swing.*;
import java.awt.*;

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

    private GameButton readImg(String imgPath, String num, Figure figure){
        ImageIcon img=new ImageIcon(imgPath);
        img.setImage(img.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
        GameButton btn = new GameButton(img, figure);
        btn.setImg(img.getImage());
        btn.setEnabled(true);
        return btn;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
