package byog.Core;
import byog.TileEngine.*;

import java.util.Random;


public class WorldGeneratorTest {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    public static void main(String[] args) {

        Game game = new Game();
        Random RANDOM = new Random(511643);
        System.out.println(RandomUtils.uniform(new Random(31643), 15, 20));
        System.out.println(RandomUtils.uniform(new Random(31644), 15, 20));
        System.out.println(RandomUtils.uniform(new Random(31645), 15, 20));

        TETile[][] worldState = game.playWithInputString("n3164322222222s");
        TETile[][] worldStat3 = game.playWithInputString("n3164322222222s");
        TETile[][] worldSta33 = game.playWithInputString("n3164322222222s");


    }

}
