package com.bdj.dash.model;
import java.io.File;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// This handles how the music is played within the game.
public class Music {

  public void playMusic(String musicLocation) {

    // Ensure that the music path exists before trying to play to prevent an error.
    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream music = classLoader.getResourceAsStream("BGM.wav");
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInput);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      // Pause music at exact location
      long clipTimePosition = clip.getMicrosecondPosition();
      clip.stop();
      // Start playing music again from location
      clip.setMicrosecondPosition(clipTimePosition);
      clip.start();
    } catch (Exception ex) {
      System.out.println("Music file not found!");
    }
  }
}





