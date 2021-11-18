package controller;

import config.Figure;
import entity.Gizmo;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import view.BoardPanel;

import java.awt.*;

public class GizmoController extends Gizmo {
    private static World world;//随着不一样的world而改变

    private final static int size = 5;//组件固定大小
    private Body body;

    private static float angularResistForce = 1f;
    private static float linearResistForce = 1f;


    public GizmoController(int x, int y, int sizeRate, Figure figure, Image img){
        this.x = x;
        this.y = y;
        this.sizeRate = sizeRate;
        this.figure = figure;
        this.img = img;
        addBody();
    }

    private void addBall(int x, int y) {
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.DYNAMIC;
        float r = size / 2.0f;
        def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
        do {
            body = world.createBody(def);
        } while (body == null);
        CircleShape circle = new CircleShape();
        circle.setRadius(r / 2.0f);
        try {
            body.createFixture(circle, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //圆形组件
    private void addCircle(int x, int y, int sizeRate) {
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.angularDamping = angularResistForce;
        def.linearDamping = linearResistForce;
        int r = sizeRate * size;
        def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
        body = world.createBody(def);
        CircleShape circle = new CircleShape();
        circle.setRadius(r / 2.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 50;
        fixtureDef.restitution = 0.8f;//默认的摩擦系数是0.2,这个设置为碰撞系数，为能量损失参数
        body.createFixture(fixtureDef);
    }
    //方形组件
    public void addSquare(int x, int y, int sizeRate) {
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.angularDamping = angularResistForce;
        def.linearDamping = linearResistForce;
        int a = sizeRate * size;
        def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
        def.userData = img;
        body = world.createBody(def);
        PolygonShape box = new PolygonShape();
        box.setAsBox(a / 2.0f, a / 2.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 50;
        fixtureDef.restitution = 0.8f;//
        body.createFixture(fixtureDef);
    }
    //三角形组件
    public void addTriangle(int x, int y, int sizeRate) {
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.angularDamping = angularResistForce;
        def.linearDamping = linearResistForce;
        int a = sizeRate * size;
        def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
        def.angle = (float) - angle;
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        float r = a / 2.0f;
        shape.set(new Vec2[]{new Vec2(-r, -r), new Vec2(-r, r), new Vec2(r, -r)}, 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50;
        fixtureDef.restitution = 0.8f;
        body.createFixture(fixtureDef);
    }
    //track组件，track根据实际情况没法旋转
    public void addTrack(int x, int y){
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        int a = sizeRate * size;
        def.position.set(x * size + a/ 2.0f , y * size- a / 2.0f);
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;//这里没有摩擦
        body.createFixture(shape, 1);
    }
    //curve组件
    public void addCurve(int x, int y, int sizeRate){
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.angularDamping = angularResistForce;
        def.linearDamping = linearResistForce;
        int a = sizeRate * size;
        def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
        def.angle = (float) - angle;
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        float r = a / 2.0f;
        shape.set(new Vec2[]{new Vec2(-r, -r), new Vec2(-r, r), new Vec2(r, -r)}, 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.8f;//这里同样设置为更高的摩擦系数
        body.createFixture(fixtureDef);
    }

    //paddle组件，增加paddle的时候不分左右
    public void addLeftPaddle(int x, int y, int sizeRate){
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        int a = sizeRate * size;
        def.position.set(x * size + a/ 2.0f, y * size - size * 0.875f);
        body = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f * a, 0.125f * a);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1.5f;//反冲力更大，给球提供更大的动力
        body.createFixture(fixtureDef);
    }
    public void addRightPaddle(int x,int y, int sizeRate){
        addLeftPaddle(x,y,sizeRate);
    }

    //absorber组件,和圆形障碍物几乎一样，但是ristitution为0，表示就没速度了
    public void addAbsorber(int x, int y, int sizeRate){
        y = 20 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.angularDamping = angularResistForce;
        def.linearDamping = linearResistForce;
        int r = sizeRate * size;
        def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
        body = world.createBody(def);
        CircleShape circle = new CircleShape();
        circle.setRadius(r / 2.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 50;
        fixtureDef.restitution = 0;//the difference
        body.createFixture(fixtureDef);
    }

    public void addBody(){
        switch(figure){
            case Ball:
                addBall(x,y);
                break;
            case Square:
                addSquare(x,y,sizeRate);
                break;
            case Triangle:
                addTriangle(x,y, sizeRate);
                break;
            case Circle:
                addCircle(x,y,sizeRate);
                break;
            case Track:
                addTrack(x,y);
                break;
            case Curve:
                addCurve(x,y,sizeRate);
                break;
            case LeftPaddle:
                addLeftPaddle(x,y,sizeRate);
                break;
            case RightPaddle:
                addRightPaddle(x,y,sizeRate);
                break;
            case Absorber:
                addAbsorber(x,y,sizeRate);
        }
        body.setUserData(figure);
    }

    //关于Gizmo中的world环境和碰撞
    public static void setWorld(World world) {
        GizmoController.world = world;
        GizmoController.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body body1 = contact.getFixtureA().getBody();
                Body body2 = contact.getFixtureB().getBody();
                if (body2.getUserData() == Figure.Ball) {
                    Body b = body1;
                    body1 = body2;
                    body2 = b;
                }
                if (body1.getUserData() == Figure.Ball && body2.getUserData() == Figure.Absorber) {
                    body1.setUserData(null);
                    GizmoController.world.destroyBody(body1);
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    public Body getBody() {
        return body;
    }
    public void updateBody() {
        world.destroyBody(body);
        addBody();
    }
    //移动paddle位置
    public void move(int dx, int dy) {
        Vec2 position = body.getPosition();
        body.setTransform(new Vec2(position.x + dx, position.y + dy), 0);
    }

    public void large(){
        sizeRate += 1;
        updateBody();
    }

    public void small(){
        sizeRate -= 1;
        updateBody();
    }
    public void rotate(){
        angle += Math.PI/2;
        updateBody();
    }

    public void delete(){
        world.destroyBody(body);
    }







}
