package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;


public class DisplayManager {

    // Set size and FPS of the display window.
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int FPS_CAP = 120;


    public static void createDisplay() {

        // Set the version of the OpenGL version.
        ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("3d graphic engine test");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }
}
