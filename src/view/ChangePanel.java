package view;

import config.*;
import controller.BoardController;
import controller.ChangeController;
import entity.GameButton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangePanel extends JPanel{
    private GameButton btnRotate = readImg("icons/rotate.png", Change.Rotation);
    private GameButton btnDelete = readImg("icons/close.png", Change.Delete);
    private GameButton btnPlus = readImg("icons/plus.png", Change.Plus);
    private GameButton btnMinus = readImg("icons/minus.png", Change.Minus);

    private Pattern curPattern;
    List<GameButton> compoments;
    private ChangeController changeController = new ChangeController();

    private ButtonGroup btnGroup = new ButtonGroup();
    public ChangePanel(BoardController board){

        btnGroup.add(btnRotate);
        btnGroup.add(btnDelete);
        btnGroup.add(btnMinus);
        btnGroup.add(btnPlus);


        super.add(btnRotate);
        super.add(btnDelete);
        super.add(btnMinus);
        super.add(btnPlus);

        compoments = new ArrayList<GameButton>();
        compoments.add(btnRotate);
        compoments.add(btnDelete);
        compoments.add(btnMinus);
        compoments.add(btnPlus);

        changeController.addButtonActionListener(compoments, Function.Change);

    }


    private GameButton readImg(String imgPath, Change change){
        ImageIcon img=new ImageIcon(imgPath);
        img.setImage(img.getImage().getScaledInstance(55,55,Image.SCALE_DEFAULT));
        GameButton btn = new GameButton(img, change);
        btn.setEnabled(true);
        return btn;
    }

    @Override
    public void paint(Graphics g) {
        super.setBackground(Color.WHITE);
        super.paint(g);
    }
}
