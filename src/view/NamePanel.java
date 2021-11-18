package view;

import javax.swing.*;
import java.awt.*;

public class NamePanel extends JPanel {
    public NamePanel(String text){
        JLabel jLabel = new JLabel(text,JLabel.CENTER);
        jLabel.setForeground(Color.white);
        super.setBackground(new Color(190, 189, 189));
        super.add(jLabel);
    }
}
