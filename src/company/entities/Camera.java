package company.entities;

import company.entities.PlayerWorks.Player;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
    private Vector3f position = new Vector3f(0, 0, 0);
    public float pitch = 0;
    private float yaw;
    public float roll;

    private float distanceFromPlayer = 0;
    private float angleAroundPlayer = 90;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }
    
    public void move()
    {
        //calculateZoom();
        calculatePitch();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 90 - (player.getRotY() + angleAroundPlayer);

        /**to exit the game window**/
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            System.exit(0);
        }
    }

    public Vector3f getPosition() 
    {
        return position;
    }

    public float getPitch() 
    {
        return pitch;
    }

    public float getYaw() 
    {
        return yaw;
    }

    public float getRoll() 
    {
        return roll;
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance) {
        //float theta = player.getRotY() + angleAroundPlayer;
        //float offsetX = (float) (horizDistance + Math.cos(Math.toRadians(theta)));
        //float offsetZ = (float) (verticDistance + Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x; //- offsetX;
        position.z = player.getPosition().z; //- offsetZ;
        position.y = player.getPosition().y + verticDistance;

    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch))) + 12;
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        //if(Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 1f;
            pitch -= pitchChange;
        //}
    }
}
