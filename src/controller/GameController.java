package controller;

import entity.Gizmo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    public static void save(List<GizmoController> components, String path){
        List<Gizmo> list = new ArrayList();
        for (GizmoController g : components){
            Gizmo fg = new Gizmo();
            fg.setAngle(g.getAngle());
            fg.setFigure(g.getFigure());
            fg.setSizeRate(g.getSizeRate());
            fg.setX(g.getX());
            fg.setY(g.getY());
            //fg.setImg(g.getImg());
//            System.out.println(fg.getX());
            list.add(fg);
        }
        writeObjectToFile(list, path);
    }

    public static List<GizmoController> load(String path) {
        List<Gizmo> list = (List<Gizmo>) readObjectFromFile(path);
        List<GizmoController> components = new ArrayList<>();
        ImageIcon pinball=new ImageIcon("icons/playball.png");
        ImageIcon circle=new ImageIcon("icons/ball.png");
        ImageIcon absorber=new ImageIcon("icons/blackhole.png");
        ImageIcon square =new ImageIcon("icons/square.png");
        ImageIcon triangle=new ImageIcon("icons/triangle.png");
        ImageIcon track=new ImageIcon("icons/track.png");
        ImageIcon leftPeddle=new ImageIcon("icons/peddle.png");
        ImageIcon rightPaddle=new ImageIcon("icons/peddle.png");
        ImageIcon curve=new ImageIcon("icons/curve.png");
        for (Gizmo g : list) {
            switch (g.getFigure()){
                case LeftPaddle:
                    g.setImg(leftPeddle.getImage());
                    break;
                case RightPaddle:
                    g.setImg(rightPaddle.getImage());
                    break;
                case Square:
                    g.setImg(square.getImage());
                    break;
                case Circle:
                    g.setImg(circle.getImage());
                    break;
                case Track:
                    g.setImg(track.getImage());
                    break;
                case Triangle:
                    g.setImg(triangle.getImage());
                    break;
                case Absorber:
                    g.setImg(absorber.getImage());
                    break;
                case Ball:
                    g.setImg(pinball.getImage());
                    break;
                case Curve:
                    g.setImg(curve.getImage());
                    break;
            }
            GizmoController gizmo = new GizmoController(g.getX(), g.getY(), g.getSizeRate(), g.getFigure(), g.getImg());
            if (g.getAngle() != 0) {
                gizmo.setAngle(g.getAngle());
                gizmo.updateBody();
            }
            components.add(gizmo);
        }
        return components;
    }

    public static Object readObjectFromFile(String path) {
        Object temp = null;
        File file = new File(path);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
//            System.out.println("read object success!");
        } catch (IOException e) {
//            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void writeObjectToFile(Object obj, String path) {
        File file = new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
//            System.out.println("write object success!");
        } catch (IOException e) {
//            System.out.println("write object failed");
            e.printStackTrace();
        }
    }
}
