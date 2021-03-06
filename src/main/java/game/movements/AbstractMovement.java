package game.movements;

import engine.Entity;
import game.entities.Node;
import game.entities.Vector3f;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by kilian on 08.02.17.
 */
public abstract class AbstractMovement {

    public static final Vector3f UP = new Vector3f(0, 0, -.01f);
    public static final Vector3f DOWN = new Vector3f (0, 0, .01f);
    public static final Vector3f RIGHT = new Vector3f (.01f, 0, 0);
    public static final Vector3f LEFT = new Vector3f (-.01f, 0, 0);
    public static final Vector3f STOP = new Vector3f (0, 0, 0);
    public static final Vector3f FALL = new Vector3f(0, -.1f, 0);
    private final Map<String, Vector3f> directions = new HashMap<>();

    private Node currentNode;
    private Entity movingObject;
    private Node nextNode;
    private Vector3f direction;

    public AbstractMovement(Node node, Entity movingObject) {
        this.currentNode = node;
        this.nextNode = node;
        this.movingObject = movingObject;
        this.direction = this.STOP;

        directions.put("UP", this.UP);
        directions.put("DOWN", this.DOWN);
        directions.put("RIGHT", this.RIGHT);
        directions.put("LEFT", this.LEFT);
    }

    public void move() {
        this.movingObject.movePosition(this.direction);
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public Entity getMovingObject() {
        return movingObject;
    }

    public Node getNextNode() {
        return nextNode;
    }

    protected void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    protected void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    protected void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Vector3f getNextNodePosition() {
        return nextNode.getPosition();

    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getCurrentPosition() {
        return this.currentNode.getPosition();
    }

    public void die() {}

    public boolean backToLife() {
        return false;
    }
}
