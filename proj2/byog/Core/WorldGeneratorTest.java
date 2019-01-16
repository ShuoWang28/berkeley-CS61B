package byog.Core;
import byog.TileEngine.*;


public class WorldGeneratorTest {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        WorldGenerator.Room r1 = new WorldGenerator.Room(new WorldGenerator.Position(20,20), 5, 5);
        WorldGenerator.Room r2 = new WorldGenerator.Room(new WorldGenerator.Position(25,10), 5, 5);
        r1.drawRoom(world);
        r2.drawRoom(world);
        r1.drawTunnel(r2,world);
        System.out.println(r1.intersect(r2));

        ter.renderFrame(world);

    }

}
