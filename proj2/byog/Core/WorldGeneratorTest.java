package byog.Core;
import byog.TileEngine.*;

import java.util.Random;


public class WorldGeneratorTest {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */

    public static void main(String[] args) {

        WorldGenerator worldMap = new WorldGenerator(1111);

        worldMap.playGame();

        worldMap.player.getLoc();

        System.out.println(TETile.toString(worldMap.world));
        worldMap.player.getLoc();

    }

}
