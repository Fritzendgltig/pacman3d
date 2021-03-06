package game.level;

import engine.graph.Material;
import engine.graph.Texture;
import game.entities.Breadcrump;
import game.entities.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by fritz on 2/8/17.
 */
public class Level {
    public static final int VOID = 0, NODE = 1, CONNECTOR_ROW = 2, CONNECTOR_COL = 3;

    private int[][] level;
    private ArrayList<Block> blockList;
    private ArrayList<Node> nodeList;
    private ArrayList<Breadcrump> breadcrumpList;
    private int width, height;

    public Level(int width, int height) throws Exception {
        this(new int[width][height]);
    }

    public ArrayList<Breadcrump> getBreadcrumpList() {
        return breadcrumpList;
    }

    public Level(int[][] level) throws Exception {
        this.level = level;
        this.width = level.length;
        this.height = level[0].length;
        this.initializeBlocksNodesBreadcrumps();
        this.setNeighbors();
    }

    public Block getBlock(int row, int col) {
        Block r = null;
        for (Block block: blockList) {
            if (block.getRow() == row && block.getCol() == col)
                r = block;
        }
        return r;
    }

    public Node getNode(int col, int row) {
        Node r = null;
        for (Node node: nodeList) {
            int col0 = node.getCol();
            int row0 = node.getRow();
            if (node.getCol() == col && node.getRow() == row) {
                r = node;
            }
        }
        return r;
    }

    private void initializeBlocksNodesBreadcrumps() throws Exception {
        this.blockList = new ArrayList<>();
        this.breadcrumpList = new ArrayList<>();
        this.nodeList = new ArrayList<>();
        float scale = .2f;
        float levelHeight = -2f;
        Material cross = new Material(new Texture("/textures/cross.png"), 1);
        Material straight = new Material(new Texture("/textures/straight.png"), 1);
        for (int x = 0; x < this.width; x ++) {
            for (int y = 0; y < this.height; y ++) {
                Block block = new Block(x, y);
                block.setScale(scale/2f);
                block.setPosition(x * scale, levelHeight, y * scale);
                switch (this.level[x][y]) {
                    case Level.VOID:
                        block = null;
                        break;
                    case Level.NODE:
                        Node node = new Node(x, y);
                        node.setPosition(x * scale, levelHeight, y * scale);
                        this.nodeList.add(node);
                        Breadcrump breadcrump = new Breadcrump();
                        breadcrump.setScale(scale/10f);
                        breadcrump.setPosition(x * scale, levelHeight + .17f, y*scale);
                        this.breadcrumpList.add(breadcrump);
                        block.setMaterial(cross);
                        break;
                    case Level.CONNECTOR_COL:
                        Breadcrump breadcrump0 = new Breadcrump();
                        breadcrump0.setScale(scale/10f);
                        breadcrump0.setPosition(x * scale, levelHeight + .17f, y*scale);
                        this.breadcrumpList.add(breadcrump0);
                        block.setMaterial(straight);
                        break;
                    case Level.CONNECTOR_ROW:
                        Breadcrump breadcrump1 = new Breadcrump();
                        breadcrump1.setScale(scale/10f);
                        breadcrump1.setPosition(x * scale, levelHeight + .17f, y*scale);
                        this.breadcrumpList.add(breadcrump1);
                        block.setMaterial(straight);
                        block.setRotation(0, 90, 0);
                        break;

                }
                if (block != null) {
//                    block.setColor(new Vector3f(1, 0, 0));
                    this.blockList.add(block);
                }
            }
        }
    }

    private void setNeighbors() throws Exception {
        int[] trail = {Level.NODE, Level.CONNECTOR_COL, Level.CONNECTOR_ROW};

        HashMap<String, Integer[]> directions = new HashMap<>();
        directions.put("left", new Integer[] {-1, 0});
        directions.put("right", new Integer[] {1, 0});
        directions.put("up", new Integer[] {0, -1});
        directions.put("down", new Integer[] {0, 1});

        for (Node node: nodeList) {
            for (Map.Entry<String, Integer[]> direction: directions.entrySet()) {
                int x, y, xOffset, valX, yOffset, valY;
                x = node.getCol();
                y = node.getRow();

                xOffset = valX = direction.getValue()[0];
                yOffset = valY = direction.getValue()[1];

                try {
                    int except = this.level[x+xOffset][y+yOffset];
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }

                int val = this.level[x+xOffset][y+yOffset];
                int val0 = this.level[x+xOffset][y+yOffset];

                if (IntStream.of(trail).anyMatch(i -> i == val)) {
                    while (val0 == Level.CONNECTOR_COL || val0 == Level.CONNECTOR_ROW) {
                        xOffset += valX;
                        yOffset += valY;
                        try {
                            int except = this.level[x+xOffset][y+yOffset];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                        val0 = this.level[x+xOffset][y+yOffset];

                    }
                    node.setNeighbors(this.getNode(x+xOffset, y+yOffset),
                            direction.getKey());
                }

            }
        }
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    public ArrayList<Node> getNodeList(){
        return nodeList;
    }



    public int[][] getLevel() {
        return level;
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
