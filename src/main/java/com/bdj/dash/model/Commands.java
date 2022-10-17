package com.bdj.dash.model;

import com.bdj.dash.view.AsciiArt;
import java.util.Scanner;

public class Commands {

  public boolean playing;

  // this class contains the information for when a user starts the game or when they type help
  // but it does not contain all commands. It may get moved and deleted later to compile.
  // This is not currently in use

  public void startGame() {
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to begin the game? y/n");
    String command = input.nextLine().toLowerCase().trim();
    processCommand(command);
  }

  public void inputCommand(){
    Scanner input = new Scanner(System.in);
    System.out.println("enterCommand: ");
    String command = input.nextLine().toLowerCase().trim();
    processCommand(command);
  }

  public void processCommand(String command){
      if (command.equals("y")) {
        playing = true;
        System.out.println("The game has begun!");
      } else if (command.equals("n")) {
        playing = false;
        System.out.println("Maybe next time!");
      } else if (command.equals("help")) {
        System.out.println("This has been moved");

        playing = true;
      } else if (command.equals("quit")) {
        playing =false;
        System.out.println("See you next time");
      } else {
        System.out.println("Invalid command. Please enter y/n.");
      }

    }


  public boolean isPlaying() {
    return playing;
  }

  public void setPlaying(boolean playing) {
    this.playing = playing;
  }

  }