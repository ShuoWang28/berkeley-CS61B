package byog.Core;
import byog.TileEngine.*;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;


public class WorldGenerator implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    public long SEED;
    private Random RANDOM;
    protected int roomCount;
    protected Gamer player;
    private Key key;
    private Position lockedDoor;
    private boolean gameOver;
    public TETile[][] world;
    private TERenderer ter;



    public WorldGenerator(long i) {
        this.SEED = i;
        this.RANDOM = new Random(SEED);
        this.roomCount = RandomUtils.uniform(RANDOM, 12, 20);

        // initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addRooms();
        smoothing();

        // add player and key, locked door.
        addLockedDoor();
        player = new Gamer();
        key = new Key();

    }

    public TETile[][] returnMap() {
        return world;
    }

    public static WorldGenerator loadWorld() {
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                WorldGenerator savedWorld = (WorldGenerator) os.readObject();
                os.close();
                return savedWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new WorldGenerator(1);
    }

    public void saveWorld() {
        File f = new File("./world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this);
            os.close();
            //System.exit(0);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public void displayMouseLocation() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        StdDraw.setPenColor(Color.white);
        if (x < WIDTH && y < HEIGHT) {
            StdDraw.text(3, HEIGHT - 2, world[x][y].description());
        }
        StdDraw.text(WIDTH/2, HEIGHT - 2, "Current Level: " + SEED);
        StdDraw.show();
    }

    /** move the player to get key and unlock the door */
    public void playGame() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);

        while (!gameOver) {
            while (StdDraw.hasNextKeyTyped()) {
                char action = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (action != ':') {
                    player.move(action);
                } else {
                    while (StdDraw.hasNextKeyTyped()) {
                        char action2 = StdDraw.nextKeyTyped();
                        player.moveToSave(action2);
                    }
                }
            }
            displayMouseLocation();
            ter.renderFrame(world);
        }
        WorldGenerator newGame = new WorldGenerator(SEED + 1);
        newGame.playGame();
    }

    /**
     * Position class.
     */
    public class Position implements Serializable {
        private int px;
        private int py;

        public Position(int x, int y) {
            this.px = x;
            this.py = y;
        }

        public int getPx() {
            return this.px;
        }

        public int getPy() {
            return this.py;
        }

        public void printPosition() {
            System.out.println("Px: " + px + ", Py: " + py);
        }
    }

    /**
     * Room Class. Use to generate a triangle room.
     * Use left upper corner Position and width and lenght(height in our map).
     */
    public class Room {
        private final int width;
        private final int length;
        private final Position RoomLoc;
        private boolean isolated = true;

        /** Create a room */
        public Room() {
            int w = RandomUtils.uniform(RANDOM, 3, 10);
            int l = RandomUtils.uniform(RANDOM, 3, 10);
            int x = RandomUtils.uniform(RANDOM, 2, WIDTH - w - 2);
            int y = RandomUtils.uniform(RANDOM, l + 1, HEIGHT - 2);
            Position p = new Position(x, y);
            this.RoomLoc = p;
            this.width = w;
            this.length = l;
        }

        /** Check if this room intersect with Room r */
        public boolean intersect(Room r) {
            int x1 = this.RoomLoc.getPx();
            int x2 = r.RoomLoc.getPx();
            int y1 = this.RoomLoc.getPy();
            int y2 = r.RoomLoc.getPy();
            if (x2 > x1 - r.width && x2 < x1 + this.width) {
                if (y2 > y1 - this.length && y2 < y1 + r.length) {
                    return true;
                }
            }
            return false;
        }

        /** draw rooms */
        public void drawRoom() {
            int x = RoomLoc.getPx();
            int y = RoomLoc.getPy();
            for (int i = x; i < x + width; i++) {
                for (int j = y; j > y - length; j--) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }

        /** Add tunnels between rooms */
        public void drawTunnel(Room r) {
            int x1 = this.RoomLoc.getPx();
            int x2 = r.RoomLoc.getPx();
            int y1 = this.RoomLoc.getPy();
            int y2 = r.RoomLoc.getPy();
            int xx1 = RandomUtils.uniform(RANDOM, x1, x1 + width - 1);
            int yy1 = RandomUtils.uniform(RANDOM, y1 - length + 1, y1);
            int xx2 = RandomUtils.uniform(RANDOM, x2, x2 + r.width - 1);
            int yy2 = RandomUtils.uniform(RANDOM, y2 - r.length + 1, y2);
            int rand = RandomUtils.uniform(RANDOM, 0, 1);
            if (rand == 0) {
                drawHorLine(xx1, xx2, yy2);
                drawVerLine(xx1, yy1, yy2);
            } else {
                drawHorLine(xx1, xx2, yy1);
                drawVerLine(xx2, yy1, yy2);
            }
        }
    }

    /** gamer class.
     *  Generate one gamer who can walk around in the maze
     *  and find key to unlock to door. */
    public class Gamer implements Serializable {
        public Position loc;
        private boolean hasKey;

        public Gamer() {
            hasKey = false;
            while (true) {
                int xx = RandomUtils.uniform(RANDOM, 1, WIDTH - 1);
                int yy = RandomUtils.uniform(RANDOM, 1, HEIGHT - 1);
                if (world[xx][yy] == Tileset.FLOOR) {
                    loc = new Position(xx, yy);
                    world[xx][yy] = Tileset.PLAYER;
                    break;
                }
            }
        }

        public void getLoc() {
            this.loc.printPosition();
        }

        public void updateLoc(Position p) {
            world[loc.getPx()][loc.getPy()] = Tileset.FLOOR;
            world[p.getPx()][p.getPy()] = Tileset.PLAYER;
            loc = p;
        }

        /** use keyboard to make a move */
        public void move(char Action) {
            char action = Character.toLowerCase(Action);
            switch (action) {
                case 'w' :
                    moveHelper(new Position(loc.getPx(), loc.getPy() + 1));
                    break;
                case 'a' :
                    moveHelper(new Position(loc.getPx() - 1, loc.getPy()));
                    break;
                case 's' :
                    moveHelper(new Position(loc.getPx(), loc.getPy() - 1));
                    break;
                case 'd' :
                    moveHelper(new Position(loc.getPx() + 1, loc.getPy()));
                    break;
                default:
            }
        }

        public void moveToSave(char Action) {
            char action = Character.toLowerCase(Action);
            if (action == 'q') {
                saveWorld();
            }
        }

        /**return if it's feasible to make a move */
        public void moveHelper(Position p) {
            switch (world[p.getPx()][p.getPy()].description() ) {
                case "wall" :
                    break;
                case "floor" :
                    updateLoc(p);
                    break;
                case "key" :
                    updateLoc(p);
                    player.hasKey = true;
                    break;
                case "locked door" :
                    if (player.hasKey) {
                        updateLoc(p);
                        gameOver = true;
                    }
                    break;
                    default:
            }
        }
    }

    private class Key implements Serializable {
        private Position loc;

        public Key() {
            while (true) {
                int xx = RandomUtils.uniform(RANDOM, 1, WIDTH - 1);
                int yy = RandomUtils.uniform(RANDOM, 1, HEIGHT - 1);
                loc = new Position(xx, yy);
                if (world[xx][yy] == Tileset.FLOOR) {
                    world[xx][yy] = Tileset.KEY;
                    break;
                }
            }
        }
    }


    /** Helper method to draw verizon line */
    private void drawVerLine(int x, int y1, int y2) {
        if (y1 == y2) {
            return;
        } else if (y1 < y2) {
            for (int i = y1; i <= y2; i++) {
                world[x][i] = Tileset.FLOOR;
            }
        } else {
            for (int i = y2; i <= y1; i++) {
                world[x][i] = Tileset.FLOOR;
            }
        }
    }

    /** Helper method to draw horizontal line */
    private void drawHorLine(int x1, int x2, int y) {
        if (x1 == x2) {
            return;
        } else if (x1 < x2) {
            for (int i = x1; i <= x2; i++) {
                world[i][y] = Tileset.FLOOR;
            }
        } else {
            for (int i = x2; i <= x1; i++) {
                world[i][y] = Tileset.FLOOR;
            }
        }
    }
    /** Add walls the the map after draw all the floors. */
    public void smoothing() {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                int emptyCount = smoothingHelper(i, j);
                if (world[i][j] == Tileset.NOTHING && emptyCount != 0) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * Return the number of Nothing tiles of a tile.
     */
    private int smoothingHelper( int x, int y) {
        int result = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            if (world[i][y - 1] == Tileset.FLOOR) {
                result += 1;
            }
        }
        for (int i = x - 1; i <= x + 1; i++) {
            if (world[i][y + 1] == Tileset.FLOOR) {
                result += 1;
            }
        }
        if (world[x - 1][y] == Tileset.FLOOR) {
            result += 1;
        }
        if (world[x + 1][y] == Tileset.FLOOR) {
            result += 1;
        }
        return result;
    }

    /** Add rooms to the map.
     * starting with main room.
     */
    public void addRooms() {
        // Add main room
        Room r1 = new Room();
        r1.isolated = true;
        r1.drawRoom();
        //world[r1.RoomLoc.getPx()][r1.RoomLoc.getPy()] = Tileset.FLOWER;

        Room[] rooms = new Room[roomCount];
        rooms[0] = r1;
        for (int i = 1; i < roomCount; i++) {
            rooms[i] = new Room();
            if (rooms[i].intersect(r1)) {
                rooms[i].isolated = false;
            }
            rooms[i].drawRoom();
        }

        // Connect all rooms.
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].isolated) {
                int connectRoom = RandomUtils.uniform(RANDOM, 0, roomCount);
                while (connectRoom == i) {
                    connectRoom = RandomUtils.uniform(RANDOM, 0, roomCount);
                }
                rooms[i].drawTunnel(rooms[connectRoom]);
                if (!rooms[connectRoom].isolated) {
                    rooms[i].isolated = false;
                }
            }
        }
    }


    /** Add a locked door on the map with all the connected rooms */
    public void addLockedDoor() {
        while (true) {
            int lockedX = RandomUtils.uniform(RANDOM, 1, WIDTH - 1);
            int lockedY = RandomUtils.uniform(RANDOM, 1, HEIGHT - 1);
            if (world[lockedX][lockedY] == Tileset.WALL &&
                       smoothingHelper(lockedX, lockedY) == 3) {
                world[lockedX][lockedY] = Tileset.LOCKED_DOOR;
                lockedDoor = new Position(lockedX, lockedY);
                break;
            }
        }
    }

}
