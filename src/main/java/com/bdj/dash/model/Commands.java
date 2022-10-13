package com.bdj.dash.model;

import java.util.Scanner;

public class Commands {

  public boolean playing = false;

  public void playGame() {
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to begin the game? y/n");
    String command = input.nextLine().toLowerCase();

    if (command.equals("y")) {
        playing = true;
      System.out.println("The game has begun!");
    } else if (command.equals("n")) {
      playing = false;
      System.out.println("Maybe next time!");
    } else {
      System.out.println("Invalid command. Please enter y/n.");
    }

  }

}
