package view;

import config.Figure;
import controller.BoardController;
import controller.ChangeController;

import javax.swing.*;
import java.awt.*;

public class BallGameWindow {
    private JFrame jFrame = new JFrame("BallGame");
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private BoardController boardController = new BoardController();

    private ChangeController changeController = new ChangeController();

    private BoardPanel boardPanel = new BoardPanel(boardController,changeController);
    private ChangePanel changePanel = new ChangePanel(boardController);
    private FigurePanel figurePanel = new FigurePanel();

    public BallGameWindow(){
        jFrame.setSize(800, 600);
        jFrame.setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx=0.0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;

        jFrame.setJMenuBar(new GamePanel(boardPanel));

        GameBoardInit();
        FunctionBoardInit();

        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public void GameBoardInit(){
        boardPanel.setPreferredSize(new Dimension(600,600));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.weightx = 0.75;
        gridBagConstraints.weighty = 1;
        jFrame.add(boardPanel, gridBagConstraints);
    }

    public void FunctionBoardInit(){
        NamePanel namePanel1 = new NamePanel("Component Bar");
        namePanel1.setPreferredSize(new Dimension(180,23));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        jFrame.add(namePanel1,gridBagConstraints);


        figurePanel.setPreferredSize(new Dimension(180,320));
        figurePanel.setBackground(Color.WHITE);
        gridBagConstraints.gridx = 1;
//        constraints.gridy = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
//        constraints.weightx = 0.3;
//        constraints.weighty = 0.2;
        jFrame.add(figurePanel,gridBagConstraints);

        NamePanel namePanel2 = new NamePanel("Change Bar");
        namePanel2.setPreferredSize(new Dimension(180,23));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        jFrame.add(namePanel2,gridBagConstraints);


        changePanel.setPreferredSize(new Dimension(180,160));
        gridBagConstraints.gridx = 1;
//        constraints.gridy = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 0.3;
        jFrame.add(changePanel,gridBagConstraints);

        NamePanel namePanel3 = new NamePanel("Pattern Bar");
        namePanel3.setPreferredSize(new Dimension(180,23));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        jFrame.add(namePanel3,gridBagConstraints);


        PatternPanel patternPanel = new PatternPanel(boardPanel);
        patternPanel.setPreferredSize(new Dimension(180,80));
        gridBagConstraints.gridx = 1;
//        constraints.gridy = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.2;
        jFrame.add(patternPanel,gridBagConstraints);

    }


    public static void main(String[] args) {
        BallGameWindow gameWindow = new BallGameWindow();
    }
}
