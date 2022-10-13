package com.bdj.dash.model;

import com.bdj.dash.view.AsciiArt;
import java.util.Scanner;

public class Commands {

  public boolean playing;


  public void playGame() {
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to begin the game? y/n");
    String command = input.nextLine().toLowerCase().trim();

      if (command.equals("y")) {
        playing = true;
        System.out.println("The game has begun!");
      } else if (command.equals("n")) {
        playing = false;
        System.out.println("Maybe next time!");
      } else if (command.equals("help")) {
        System.out.println("\n These are all the options available to the player. Not all options"
            + " are available to use at all times \n Try typing some of these commands below \n\n"
            + " go east \n   -This moves the player in the specified direction you may also"
            + " try north, south, or west \n\n talk npc \n   -This allows you to talk to npc in the"
            + " area if one is in this zone. NPC may give assistance \n\n pickup item \n   -This"
            + " allows the user to pick up any item that may be in the zone \n\n use weapon \n   "
            + "-This allows the user to use weapons with the selected name. If no items available the"
            + " player can use cry instead. Try using items from inventory such as crossbow"
            + " if they are in your inventory \n\n look \n   -This gives the player the information"
            + " about the space around them. Such as directions and items \n\n quit \n   -this allows"
            + " the user to quit playing the game");
        playing = true;
      } else if (command.equals("quit")) {
        playing =false;
        System.out.println("See you next time");
      } else {
        System.out.println("Invalid command. Please enter y/n.");
      }

    }
  }