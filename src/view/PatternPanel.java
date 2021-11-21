package view;

import controller.BoardController;
import controller.ChangeController;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PatternPanel extends JPanel {
    private JButton btnDesign = new JButton("DesignPattern");
    private JButton btnPlay = new JButton("   PlayPattern  ");

    public PatternPanel(BoardPanel boardPanel){
//        super.setBorder(new TitledBorder(new EtchedBorder(), "模式栏", TitledBorder.CENTER, TitledBorder.TOP));

        btnDesign.setBounds(30,30,140,30);
//        btnDesign.setFont(new Font("等线light", Font.BOLD, 20));
        btnDesign.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                BoardController.setDesignFunction(true);
                boardPanel.design();
            }
        });


        btnPlay.setBounds(30,70,140,30);
//        btnPlay.setFont(new Font("等线light", Font.BOLD, 20));
        btnPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                BoardController.setDesignFunction(false);
                boardPanel.design();
                ChangeController.clear();
            }
        });

        super.add(btnDesign);
        super.add(btnPlay);
    }


}
