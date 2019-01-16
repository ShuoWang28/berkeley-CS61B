package byog.Core;
import byog.TileEngine.*;

import java.util.Random;
import java.util.concurrent.TimeoutException;

public class WorldGenerator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;

    private static long SEED = 1;
    public static final Random RANDOM = new Random(SEED);
    public static final int roomCount = RandomUtils.uniform(RANDOM, 10, 20);


    /**
     * Position class.
     */

    public void setSeed(long i) {
        this.SEED = i;
    }

    public static class Position {
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

        public void setPx(int x) {
            this.px = x;
        }

        public void setPy(int y) {
            this.py = y;
        }

        public void printPosition() {
            System.out.println("Px: " + px + ", Py: " + py);
        }
    }

    /**
     * Room Class. Use to generate a triangle room.
     * Use left upper corner Position and width and lenght(height in our map).
     */
    public static class Room {
        private Position RoomLoc;
        private int width;
        private int length;
        private boolean isolated = true;

        public Room(Position p, int w, int l) {
            this.RoomLoc = p;
            this.width = w;
            this.length = l;
        }

        public Room(Position p) {
            this.RoomLoc = p;
            this.width = RandomUtils.uniform(RANDOM, 4, 10);
            this.length = RandomUtils.uniform(RANDOM, 4, 10);
        }

        public Room() {
            int w = RandomUtils.uniform(RANDOM, 4, 10);
            int l = RandomUtils.uniform(RANDOM, 4, 10);
            int x = RandomUtils.uniform(RANDOM, 2, WIDTH - w - 2);
            int y = RandomUtils.uniform(RANDOM, l + 1, HEIGHT - 2);
            Position p = new Position(x, y);
            this.RoomLoc = p;
            this.width = w;
            this.length = l;
        }

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

        public boolean intersect(TETile[][] world) {
            int x1 = this.RoomLoc.getPx();
            int y1 = this.RoomLoc.getPy();
            for (int i = x1; i < x1 + width; i++) {
                for (int j = y1; j > y1 - length; j--) {
                    if (world[i][j] != Tileset.NOTHING) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void drawRoom(TETile[][] world) {
            int x = RoomLoc.getPx();
            int y = RoomLoc.getPy();
            for (int i = x; i < x + width; i++) {
                for (int j = y; j > y - length; j--) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }

        public void drawTunnel(Room r, TETile[][] world) {
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
                drawHorLine(world, xx1, xx2, yy2);
                drawVerLine(world, xx1, yy1, yy2);
            } else {
                drawHorLine(world, xx1, xx2, yy1);
                drawVerLine(world, xx2, yy1, yy2);
            }
        }
    }


    private static void drawVerLine(TETile[][] world, int x, int y1, int y2) {
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

    private static void drawHorLine(TETile[][] world, int x1, int x2, int y) {
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

    public static void smoothing(TETile[][] world) {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                int emptyCount = smoothingHelper(world, i, j);
                if (world[i][j] == Tileset.NOTHING && emptyCount != 0) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * return the number of Nothing tiles of a tile
     */
    private static int smoothingHelper(TETile[][] world, int x, int y) {
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

    public static void AddRooms (TETile[][] world) {
        /** Add main room */
        int x = RandomUtils.uniform(RANDOM, 2, WIDTH - 10);
        int y = RandomUtils.uniform(RANDOM, 10, HEIGHT - 2);
        Position entrance = new Position(x, y);
        Room r1 = new Room(entrance);
        r1.isolated = false;
        r1.drawRoom(world);

        Room[] rooms = new Room[roomCount];
        rooms[0] = r1;
        for (int i = 1; i < roomCount; i++) {
            rooms[i] = new Room();
            if (rooms[i].intersect(r1)) {
                rooms[i].isolated = false;
            }
            rooms[i].drawRoom(world);
        }

        /** connect all rooms. */
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].isolated) {
                int connectRoom = RandomUtils.uniform(RANDOM, 0, roomCount);
                while (connectRoom == i) {
                    connectRoom = RandomUtils.uniform(RANDOM, 0, roomCount);
                }
                rooms[i].drawTunnel(rooms[connectRoom], world);
                if (rooms[connectRoom].isolated == false) {
                    rooms[i].isolated = false;
                }
            }
        }
    }

    public static void AddLockedDoor(TETile[][] world) {
        while (true) {
            int LockedX = RandomUtils.uniform(RANDOM, 1, WIDTH - 1);
            int LockedY = RandomUtils.uniform(RANDOM, 1, HEIGHT - 1);
            if(world[LockedX][LockedY] == Tileset.WALL && smoothingHelper(world, LockedX, LockedY) == 3) {
                world[LockedX][LockedY] = Tileset.LOCKED_DOOR;
                break;
            }
        }
    }




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

        AddRooms(world);

        smoothing(world);

        AddLockedDoor(world);

        ter.renderFrame(world);
    }

}
