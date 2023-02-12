package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("Tree"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        texture.setShineDamper(10);
        texture.setReflectivity(0);
        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));

//        staticModel.getTexture().setHasTransparency(true);
//        staticModel.getTexture().setUseFakeLighting(true);
        List<Entity> trees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 200; i++) { // Tree generation
            float x = random.nextFloat() * 100;
            float z = random.nextFloat() * 100;
            trees.add(new Entity(staticModel, new Vector3f(x, 0, z), 0, 0, 0, 1));
        }

        Entity entity = new Entity(staticModel, new Vector3f(0, -0.7f, -0.45f), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(100, 100, 0), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        entity.increaseRotation(0, -70, 0);
        while(!Display.isCloseRequested()) {
            //entity.increaseRotation(0, 1, 0);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity tree : trees) {
                renderer.processEntity(tree);
            }

            renderer.processEntity(entity);
            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
