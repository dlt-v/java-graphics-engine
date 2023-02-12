package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0, 1, 0);
    private float pitch; // up, down
    private float yaw = 135; // left, right along Y axis
    private float roll; // tilt along the Z axis

    public Camera() {

    }
    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            position.y += 0.02f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            position.y -= 0.02f;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            yaw += 1f;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            yaw -= 1f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }


}
