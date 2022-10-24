package com.bdj.dash;

import com.bdj.dash.controller.Game;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

// This allows the user to play the game.
  public static void main(String[] args) {
    Game newGame = new Game();

    newGame.playGame();
  }

}
