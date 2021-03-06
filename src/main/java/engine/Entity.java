package engine;


import engine.graph.Model;
import game.entities.Vector3f;

public class Entity {
    protected Model model;
    protected Vector3f position;
    protected float scale;
    protected Vector3f rotation;

    public Entity() {
        position = new Vector3f(.0f, .0f, .0f);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Entity(Entity entity) {
        this.model = new Model(entity.getModel());
        this.position = new Vector3f(entity.position);
        this.scale = entity.scale;
        this.rotation = new Vector3f(entity.rotation);
    }

    public Entity(Model model) {
        this.model = model;
        position = new Vector3f(.0f, .0f, .0f);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public void movePosition(Vector3f offset) {
        position.x += offset.x;
        position.y += offset.y;
        position.z += offset.z;
    }

    public int update() {
        return 0;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f position) {
        this.position.x = position.x;
        this.position.y = position.y;
        this.position.z = position.z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        setRotation(rotation.x, rotation.y, rotation.z);
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public Model getModel() {
        return model;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
}