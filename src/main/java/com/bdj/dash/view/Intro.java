package com.bdj.dash.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Intro {

  public static final String HELP_COMMANDS =
      "\n These are all the options available to the player. Not all options"
          + " are available to use at all times \n Try typing some of these commands below \n\n"
          + " go east \n   -This moves the player in the specified direction you may also"
          + " try north, south, or west \n\n get\n   -This allows the user to pick up any "
          + "item that may be in the zone \n\n quit \n -this allows the user to quit playing the "
          + "game \n\n new game \n   -This allows the user to start the game over. \n";

//        talk npc \n   -This allows you to talk to npc in the"
//            + " area if one is in this zone. NPC may give assistance
//        use weapon \n   -This allows the user to use "
//            + "weapons with the selected name. \n If no items available the player can use cry "
//            + "instead. Try using items from inventory such as crossbow if they are in your "
//            + "inventory


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
      System.out.println(builder);
    }
  }

  public void instructions(){
    System.out.println(
        "\n This is a text-based RPG game that features a Door Dasher that wanders around \n"
            + " in an apocalyptic future. The character can talk to NPC, pick up items, \n"
            + " and attack zombies. The objective of the game is to either find a way back \n"
            + " to the past or at least find a safe haven. Type help at any time to see a list "
            + "of usable commands\n");

    System.out.println(HELP_COMMANDS);
  }

}
