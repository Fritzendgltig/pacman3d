package game;

import engine.Entity;
import engine.Utils;
import engine.Window;
import engine.graph.*;
import org.joml.Matrix4f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Renderer2D {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(65.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation2D transformation;

    private ShaderProgram shaderProgram;

    private float specularPower;

    private Camera camera;

    public Renderer2D() {
        transformation = new Transformation2D();
        specularPower = 10f;
        camera = new Camera();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex2d.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment2d.fs"));
        shaderProgram.link();

        // Create uniforms for modelView and projection matrices and texture
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("texture_sampler");
        // Create uniform for material
        shaderProgram.createMaterialUniform("material");
    }

    public void render(Window window, ArrayList<Entity> entities) {
        Camera camera = new Camera();
        camera.setPosition(0, 0, 1);

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getOrthoMatrix(-(float)window.getWidth()/window.getHeight(),
                (float)window.getWidth()/window.getHeight(), -1, 1, Z_NEAR, Z_FAR);
//        Matrix4f projectionMatrix = transformation.getOrthoMatrix(-window.getWidth()/2, window.getWidth()/2,
//                -window.getHeight()/2, window.getHeight()/2, Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);


        shaderProgram.setUniform("texture_sampler", 0);
        // Render each entity
        for (Entity entity : entities) {
            Model model = entity.getModel();

            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(entity, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);

            shaderProgram.setUniform("material", model.getMaterial());
            model.render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
