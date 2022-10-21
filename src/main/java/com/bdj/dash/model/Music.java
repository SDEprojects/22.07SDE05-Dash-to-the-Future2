package com.bdj.dash.model;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// This handles how the music is played within the game.
public class Music {

  public void playMusic(String musicLocation) {

    // Ensure that the music path exists before trying to play to prevent an error.
     try {
      File musicPath = new File(musicLocation);
      if (musicPath.exists()) {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
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

      } else {
        System.out.println("Cannot find the music file");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void stopMusic(String musicLoc) {
  // Ensure that the music path exists before trying to play to prevent an error.
     try {
    File musicPath = new File(musicLoc);
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
      Clip clip = AudioSystem.getClip();
//      clip.open(audioInput);

      // Pause music at exact location

      clip.stop();

      // Start playing music again from location
//      clip.setMicrosecondPosition(clipTimePosition);
//      clip.start();
  }
    catch (Exception ex) {
    ex.printStackTrace();
  }
}


}
