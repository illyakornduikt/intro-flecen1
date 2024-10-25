package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {

    private Clip clip;

    public SoundPlayer(String filePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
        File soundFile = new File(filePath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        
    }

    public void play() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

}