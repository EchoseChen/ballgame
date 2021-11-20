package view;

import controller.FileController;
import controller.GizmoController;
import entity.Gizmo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JMenuBar{
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem newGame = new JMenuItem("newGame");
    private JMenuItem saveGame = new JMenuItem("loadGame");
    private JMenuItem openGame = new JMenuItem("readGame");
    //private JMenu introMenu = new JMenu("Introduction");
    private JMenuItem introduction = new JMenuItem("introduction");

    public GamePanel(BoardPanel boardPanel){
        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(openGame);

        super.add(fileMenu);
        super.add(introduction);

        newGame.addActionListener(e -> {
            boardPanel.newScene(boardPanel);
        });
        saveGame.addActionListener(e -> {
            boardPanel.getBoard().setCanFocus(false);
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(this);
            File file = chooser.getSelectedFile();
            String fileName = file.getAbsolutePath();
            if (!fileName.endsWith(".psc")) {
                fileName += ".psc";
            }
            boardPanel.getBoard().saveScene(fileName);
            boardPanel.getBoard().setCanFocus(true);
        });
        openGame.addActionListener(e -> {
            boardPanel.getBoard().setCanFocus(false);
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileController());
            chooser.showOpenDialog(this);
            File file = chooser.getSelectedFile();
            boardPanel.loadScene(file.getAbsolutePath());
            boardPanel.getBoard().setCanFocus(true);
        });
        introduction.addActionListener(e -> {

            JFrame ind = new JFrame("游戏介绍");
            ind.setSize(1000,600);
            ind.setLayout(new FlowLayout());
            JLabel jl=new JLabel(new ImageIcon("icons/introduction.png"));
            ind.add(jl);
            jl.setBounds(0,0,1000,1000);


            ind.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            ind.pack();
            ind.setVisible(true);
        });
    }
}

