package com.bdj.dash.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import com.bdj.dash.view.ConsoleColors;

public class Intro {

  // These are the help commands available to players at any time when they type "help"
  public static final String HELP_COMMANDS =
      "\n These are all the options available to the player. Not all options"
          + " are available to use at all times \n Try typing some of these commands below \n\n"
          + " go <direction> \n   -This moves the player in the specified direction such as "
          + " north, south, east, or west \n"
          + " get \n   -This allows the user to pick up any"
          + " item that may be in the zone \n"
          + " quit \n   -This allows the user to quit playing the game.\n"
          + " talk \n   -This allows you to talk to npc in the area if one is in this zone. NPC may give assistance\n"
          + " heal \n   -This allows you to restore 50 health if you have the healing item.\n"
          + " use <weapon> \n   -This allows the user to use weapons with the selected name. Eg. crossbow\n"
          + " eat \n   - Must have edible item, adds 25 health.";


  public void title() {

    int width = 150;
    int height = 20;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    g.setFont( new Font("Roboto", Font.BOLD, 12));

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.drawString("DASH TO THE FUTURE", 10,15);

    for (int y = 0; y < height; y++){
      StringBuilder builder = new StringBuilder();
      for (int x = 0; x < width; x++){
        builder.append(image.getRGB(x,y) == -16777216 ? " " : "|");
      }
      System.out.println(ConsoleColors.BRIGHT_RED + ConsoleColors.BOLD + builder + ConsoleColors.RESET);
    }
  }

  public void gameDescription(){
    System.out.println( ConsoleColors.BRIGHT_YELLOW +
        "\n This is a text-based RPG game that features a Door Dasher that wanders around \n"
        + " in an apocalyptic future. The character can talk to NPC, pick up items, \n"
        + " and attack zombies. The objective of the game is to either find a way back \n"
        + " to the past or at least find a safe haven. Type help at any time to see a list "
        + "of usable commands\n " + ConsoleColors.RESET);
  }

  public void instructions(){

    System.out.println(HELP_COMMANDS);
  }

}
