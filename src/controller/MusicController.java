package controller;


import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class MusicController {
    private static Clip bgm;//背景乐
    private static Clip hit;//音效
    private static AudioInputStream ais;

    MusicController() {
    }

    public static void playBackground() {
        try {
            bgm = AudioSystem.getClip();
            InputStream is = MusicController.class.getClassLoader().getResourceAsStream("background.wav");
            //getclassLoader得到当前类的加载器.getResourceAsStream加载资源，只能加载wav的音乐格式
            if (is != null) {
                ais = AudioSystem.getAudioInputStream(is);//获取输入流
            }
            bgm.open(ais);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        bgm.start();//开始播放
        bgm.loop(Clip.LOOP_CONTINUOUSLY);//循环播放
    }
    public static void playHit() {
        try {
            hit = AudioSystem.getClip();
            InputStream is = MusicController.class.getClassLoader().getResourceAsStream("contact.wav");
            //getclassLoader得到当前类的加载器.getResourceAsStream加载资源，只能加载wav的音乐格式
            if (is != null) {
                ais = AudioSystem.getAudioInputStream(is);//获取输入流
            }
            hit.open(ais);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        hit.start();//开始播放
//        bgm.loop(Clip.LOOP_CONTINUOUSLY);//循环播放
    }


    public static void stop() {
        if (ais != null)
            bgm.close();
    }
}
