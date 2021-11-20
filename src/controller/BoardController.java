package controller;

import config.Figure;
import config.Function;
import config.GameInterface;
import config.Pattern;
import entity.Gizmo;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import view.BoardPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class BoardController{
//    private MyThread thread;

    private static World world;//游戏环境
    private static final Vec2 gravity = new Vec2(0,-80);//游戏中的球重力

    private float timeStep = 0.03f;//游戏的更新频率
    private static final int velocityIterations = 8;//速度精度参数，最快速度刷新速度
    private static final int positionIterations = 2;//位置精度参数
    private static Body ground; // 地面
    private static int rowNum = GameInterface.LINES - 1; // 格子的行数

    private boolean canFocus = true; //光标是否在board中

    private int rowHeight = 10;
    private static boolean designFunction = true;
    private static Function function;

    //gizmo的一些配置
    private final static int size = 5;
    private static GizmoController curGizmoController;
    private List<GizmoController> components = new ArrayList<>();//组件列表
    private int sizeRate = 1;

    public void newWorld(){
        world = new World(gravity);
        for (int i = 0; i <= 1; i++){
            for (int j = 0; j <= 1; j++)
                addSingleBoarder(i, j);
        }
        GizmoController.setWorld(world);
    }

    //加单边
    private static void addSingleBoarder(int x,int y){
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.position.set(x * size * rowNum, y * size * rowNum);
        Body body = world.createBody(def);
        PolygonShape box = new PolygonShape();
        if (y == x) {
            box.setAsBox(size * rowNum, 0);
        } else {
            box.setAsBox(0, size * rowNum);
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.restitution = 0.9f;
        body.createFixture(fixtureDef);
        if (x == 0 && y == 0)
            ground = body;
    }

    //实现组件的可视化
    public void paintComponents(Graphics2D g2D, int grid, int length){
        double px, py;
        int x, y;
        int sizeRate;
        Image curImg;
        this.rowHeight = grid;
        for (int i = 0; i < components.size(); i++){
            AffineTransform transform = (AffineTransform) g2D.getTransform().clone();
            GizmoController gizmoController = components.get(i);
            if (gizmoController.getBody().getUserData() == null) {
                world.destroyBody(gizmoController.getBody());
                continue;
            }
            x = gizmoController.getX();
            y = gizmoController.getY();
            curImg = gizmoController.getImg();


            sizeRate = gizmoController.getSizeRate();

            if (designFunction){
                px = Coordinate(x) + GameInterface.X0;
                py = Coordinate(y) + GameInterface.Y0;
                if (gizmoController.getFigure() == Figure.Triangle || gizmoController.getFigure() == Figure.Curve) {
                    g2D.setTransform(getTransform(px + 0.5 * sizeRate * rowHeight, py + 0.5 * sizeRate * rowHeight, -gizmoController.getBody().getAngle(), g2D.getTransform()));
                }
            }else {
                Vec2 position = gizmoController.getBody().getPosition();
                px = position.x / getLength() * rowNum * grid + GameInterface.X0;
                py = position.y / getLength()  * rowNum * grid;
                if (gizmoController.getFigure() != Figure.Ball) {
                    if (gizmoController.getFigure() == Figure.LeftPaddle||gizmoController.getFigure() == Figure.RightPaddle){
                        /*px -= rowHeight / 2.0f;
                        py += 0.875f * rowHeight;*/
                        px -= gizmoController.getSizeRate() * rowHeight / 2.0f;
                        py += 0.875f * rowHeight;
                    }
                    //else if (gizmo.getShape() == Shape.Track){
                    //    px -= 0.875 * rowHeight;
                    //    py += rowHeight;
                    //}
                    else {
                        px -= gizmoController.getSizeRate() * rowHeight / 2.0f;
                        py += gizmoController.getSizeRate() * rowHeight / 2.0f;
                    }

                } else {
                    px -= rowHeight / 4.0f;
                    py += rowHeight / 4.0f;

                }
                py = length - py;
                if (gizmoController.getFigure() != Figure.Ball)
                    g2D.setTransform(getTransform(px + 0.5 * sizeRate * rowHeight, py + 0.5 * sizeRate * rowHeight, -gizmoController.getBody().getAngle(), g2D.getTransform()));
            }

            sizeRate = gizmoController.getSizeRate();

            if (gizmoController.getFigure() == Figure.Ball){
                g2D.drawImage(curImg, (int)px, (int)py, rowHeight , rowHeight , null);
            }
            // else if (gizmo.getShape() == Shape.Track){
            //g2D.setColor(new Color(138,204,241));
            //g2D.fill(paintPaddle(px, py, sizeRate));
            // }
            else if (gizmoController.getFigure() == Figure.LeftPaddle||gizmoController.getFigure() == Figure.RightPaddle){
                g2D.drawImage(curImg, (int)px, (int)py, sizeRate * rowHeight, sizeRate * rowHeight, null);
            }
            else {
                g2D.drawImage(curImg, (int)px, (int)py, sizeRate * rowHeight, sizeRate * rowHeight, null);
            }
            g2D.setTransform(transform);
        }
    }
    //获取真实坐标
    private double Coordinate(double i){
        return i * rowHeight;
    }
    private AffineTransform getTransform(double x, double y, double angle, AffineTransform transform) {
        transform.rotate(angle, x, y);
        return transform;
    }
    public static int getLength() {
        return size * rowNum;
    }
    public void updateCompoments(){
        for (GizmoController gizmo : components) {
            gizmo.updateBody();
        }
    }

    public void leftPaddleMove(int dx, int dy){
        for (GizmoController gizmo : components) {
            if (gizmo.getFigure() == Figure.LeftPaddle) {
                gizmo.move(dx, dy);
            }
        }
    }

    public void rightPaddleMove(int dx, int dy){
        for (GizmoController gizmo : components) {
            if (gizmo.getFigure() == Figure.RightPaddle) {
                gizmo.move(dx, dy);
            }
        }
    }
    public void dragGizmo(GizmoController gizmo,int dx,int dy){
        if(gizmo!=null){
            gizmo.move(dx,dy);
        }
    }

    public void setStep(){
        world.step(timeStep, velocityIterations, positionIterations);
    }

    public void addComponents(GizmoController tmp){
        components.add(tmp);
    }

    public GizmoController getGizmo(int x, int y) {
        for (int i = 0; i < components.size(); i++) {
            GizmoController temp = components.get(i);
            int tempX = temp.getX();
            int tempY = temp.getY();
            int sizeRate = temp.getSizeRate();
            if (x >= tempX && x < tempX + sizeRate && y >= tempY && y < tempY + sizeRate)
                return temp;
        }
        return null;
    }

    public GizmoController getGizmoDrag(int x, int y) {
        for (int i = 0; i < components.size(); i++) {
            GizmoController temp = components.get(i);
            int tempX = temp.getX();
            int tempY = temp.getY();
            int sizeRate = temp.getSizeRate();
            if (x >= tempX-1 && x <= tempX + sizeRate && y >= tempY-1 && y <= tempY + sizeRate)
                return temp;
        }
        return null;
    }

    public boolean getCanFocus(){
        return canFocus;
    }

    public void setCanFocus(boolean canFocus) {
        this.canFocus = canFocus;
    }

    public float getTimeStep() {
        return timeStep;
    }

    public static boolean isDesignFunction(){ return designFunction; }
    public static void setDesignFunction( boolean designPattern){ BoardController.designFunction = designPattern; }

    public static Function getFunction() {
        return function;
    }

    public static void setFunction(Function function) {
        BoardController.function = function;
    }

    public void addGizmo(GizmoController gizmo){
        if (gizmo != null){
            gizmo.large();
        }
    }

    public void minusGizmo(GizmoController gizmo){
        if (gizmo != null){
            gizmo.small();
        }
    }

    public void rotateGizmo(GizmoController gizmo){
        if (gizmo != null) {
            gizmo.rotate();
        }
    }

    public void deleteGizmo(GizmoController gizmo){
        if (gizmo != null){
            gizmo.delete();
            components.remove(gizmo);
        }
    }

    public void setXY(GizmoController gizmo){
        if (gizmo != null){
            gizmo.drag();
        }
    }





    public void setRowHeight(int height){
        this.rowHeight = height;
    }

    public int getSizeRate() {
        return sizeRate;
    }

    public void setSizeRate(int sizeRate) {
        this.sizeRate = sizeRate;
    }

    public boolean canRotate(int x, int y, int size, GizmoController curGizmo){
        int dx, dy;
        dx = curGizmo.getX();
        dy = curGizmo.getY();
        if (dx >= x && dy <= y && dx <= x + size && dy >= y - size && curGizmo.getFigure() != curGizmo.getFigure()){
            return false;
        }
        return true;
    }


    public boolean canAdd(int x, int y, int size, Figure figure){
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (getGizmo(i, j) != null) {
                    return false;
                }
            }
        }
        return true;
    }
    public void newScene(BoardPanel boardPanel) {
        newWorld();
        components.clear();
    }

    public void saveScene(String fileName) {
        GameController.save(components, fileName);
    }

    public void loadScene(String fileName) {
        World bworld = world;
        newWorld();
        java.util.List<GizmoController> list = GameController.load(fileName);
        if (components == null) {
            world = bworld;
        } else {
            components = list;
        }
    }

}
