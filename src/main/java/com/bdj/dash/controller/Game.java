package com.bdj.dash.controller;

import com.bdj.dash.model.Commands;
import com.bdj.dash.view.AsciiArt;

public class Game {
  AsciiArt titleArt = new AsciiArt();
  Commands commands = new Commands();

  public void beginGame(){
    titleArt.title();
    titleArt.instructions();
    commands.playGame();
  }

}
