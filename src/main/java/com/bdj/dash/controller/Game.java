package com.bdj.dash.controller;

import com.bdj.dash.view.AsciiArt;

public class Game {
  AsciiArt titleArt = new AsciiArt();

  public void beginGame(){
    titleArt.title();
    titleArt.instructions();
  }


}
