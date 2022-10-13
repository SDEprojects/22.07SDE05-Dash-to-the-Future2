package com.bdj.dash.controller;

import com.bdj.dash.model.Commands;
import com.bdj.dash.model.Locations;
import com.bdj.dash.model.Player;
import com.bdj.dash.view.AsciiArt;
import java.sql.SQLOutput;

public class Game {
  AsciiArt titleArt = new AsciiArt();
  Commands commands = new Commands();
  Locations location = new Locations();
  Player player = new Player();

  public void beginGame(){
    titleArt.title();
    titleArt.instructions();
    commands.playGame();
    while(commands.playing!=false) {
      String userName = player.playerName();
      if (userName.equals("quit")){
        System.out.println("I guess you didn't want to play.");
        break;
      }
      int pHp = player.getHealth();
      System.out.println(userName + " your current health is = " + pHp);
    }
  }

}
