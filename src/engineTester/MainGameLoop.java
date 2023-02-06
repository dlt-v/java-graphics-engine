package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.IOException;

public class MainGameLoop {
    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);




        RawModel model = OBJLoader.loadObjModel("avali", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("white"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));

        Entity entity = new Entity(staticModel, new Vector3f(0, -0.7f, -0.6f), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(-1, 0, 0), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            // game logic
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            //render
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
