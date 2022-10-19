package com.bdj.dash;

import com.bdj.dash.controller.Game;
import java.io.IOException;

public class Main {

// This allows the user to play the game.
  public static void main(String[] args) throws IOException {
    Game newGame = new Game();

    newGame.playGame();
  }


}
