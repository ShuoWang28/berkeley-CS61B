package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int menuWIDTH = 40;
    public static final int menuHEIGHT = 40;
    public static final Font headerFont = new Font("Monaco", Font.BOLD, 30);
    public static final Font optionFont = new Font("Monaco", Font.BOLD, 16);
    private boolean gameOver;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        //Initialize Menu page
        StdDraw.setCanvasSize(menuWIDTH * 16, menuHEIGHT * 16);
        StdDraw.setFont(headerFont);
        StdDraw.setXscale(0, menuWIDTH);
        StdDraw.setYscale(0, menuHEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        //Display Game name
        StdDraw.setFont(headerFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(menuWIDTH/2, 3 * menuWIDTH / 4 , "CS61B: THE GAME");

        //Display Options
        StdDraw.setFont(optionFont);
        StdDraw.text(menuWIDTH/2, menuWIDTH / 2 , "NEW GAME (N)");
        StdDraw.text(menuWIDTH/2, menuWIDTH / 2 - 1 , "LOAD GAME (L)");
        StdDraw.text(menuWIDTH/2, menuWIDTH / 2 - 2 , "QUIT (Q)");
        StdDraw.show();

        //Take user input
        String action = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char choice = StdDraw.nextKeyTyped();
                action = String.valueOf(choice);
                menuOption(action);
            }
        }
    }

    private void menuOption(String action) {
        switch (action) {
            case "N":
                long seed = enterSEED();
                WorldGenerator worldMap = new WorldGenerator(seed);
                worldMap.playGame();
                break;
            case "L":
                WorldGenerator saved = WorldGenerator.loadWorld();
                saved.playGame();
                break;
            case "Q":
                System.exit(0);
            default:
        }
    }

    private long enterSEED() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(headerFont);
        StdDraw.text(menuWIDTH / 2, menuWIDTH / 2, "PLEASE ENTER A SEED:");
        StdDraw.setFont(optionFont);
        StdDraw.text(menuWIDTH / 2, 2, "PRESS 'R' TO RETURN");
        StdDraw.show();
        String result = "";
        while (!StdDraw.isKeyPressed(10)) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setFont(headerFont);
                StdDraw.text(menuWIDTH / 2, menuWIDTH / 2, "PLEASE ENTER A SEED:");
                StdDraw.setFont(optionFont);
                StdDraw.text(menuWIDTH / 2, 2, "PRESS 'R' TO RETURN");
                StdDraw.show();
                result = result + StdDraw.nextKeyTyped();
                StdDraw.text(menuWIDTH / 2, menuWIDTH / 2 - 2, "NUMBER ENTERED: " + result);
                StdDraw.show();
            }
        }
        try {
            return Long.parseLong(result);
        } catch (NumberFormatException e) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setFont(headerFont);
            StdDraw.text(menuWIDTH / 2, menuWIDTH / 2, "INVALID NUMBER ENTERED!");
            StdDraw.show();
            StdDraw.pause(1000);
            return 1;
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        WorldGenerator world = null;
        while (!input.equals("")) {
            if (input.charAt(0) == 'N') {
                input = input.substring(1);
                long seed = 0;
                while (Character.isDigit(input.charAt(0))) {
                    int i = input.charAt(0) - 48;
                    seed = seed * 10 + Integer.valueOf(i);
                    input = input.substring(1);
                }
                world = new WorldGenerator(seed);
            } else if (input.charAt(0) == 'L') {
                world = WorldGenerator.loadWorld();
                input = input.substring(1);
            }
            while (!input.equals("")) {
                String action = String.valueOf(input.charAt(0));
                if (action.equals("Q")) {
                    return world.returnMap();
                } else {
                    world.player.move(action);
                    input = input.substring(1);
                }
            }
        }
        return world.returnMap();
    }
}
