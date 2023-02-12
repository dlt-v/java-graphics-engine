package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) throws IOException {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        // TERRAIN TEXTURE LOADING

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, 0, loader, texturePack, blendMap);

        // TREE LOADING

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("Tree"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        texture.setShineDamper(10);
        texture.setReflectivity(0);

        List<Entity> trees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 200; i++) { // Tree generation
            float x = random.nextFloat() * 300;
            float z = random.nextFloat() * 300;
            trees.add(new Entity(staticModel, new Vector3f(x, 0, z), 0, 0, 0, 10));
        }

        // PLAYER

        RawModel playerModel = OBJLoader.loadObjModel("avali", loader);
        ModelTexture playerTexture = new ModelTexture(loader.loadTexture("white"));
        TexturedModel playerTexturedModel = new TexturedModel(playerModel, playerTexture);

        Player player = new Player(playerTexturedModel, new Vector3f(1, 1, 0), 0, 0, 0, 2);


        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));

//        staticModel.getTexture().setHasTransparency(true);
//        staticModel.getTexture().setUseFakeLighting(true);


        //Entity entity = new Entity(staticModel, new Vector3f(0, -0.7f, -0.45f), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(100, 100, 0), new Vector3f(1, 1, 1));



        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while(!Display.isCloseRequested()) {
            //entity.increaseRotation(0, 1, 0);
            camera.move();
            player.move();
            renderer.processEntity(player);

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity tree : trees) {
                renderer.processEntity(tree);
            }

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
