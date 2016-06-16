package company.entities.PlayerWorks;

import company.CollisionDetection.CheckCollision;
import company.Terrians.Terrian;
import company.entities.Entity;
import company.game.GameEngineLoop;
import company.levels.Level1;
import company.models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.security.Key;

public class Player extends Entity {

    public static float RUN_SPEED = 3.0f, GRAVITY = -0.5f, JUMP_POWER = 4.8f;

    public static boolean isMoving = false, isColliding = false;

    public static float currentSpeed = 0, currentTurnSpeed = 0, upwardsSpeed = 0;
    public static boolean isInAir = false;

    public static int PlayerScore = 0, PlayerHealth = 100, WeaponAmmo = 200, jumpCoolDown = 30;
    public static float dx = 0;
    public static float dz = 0;
    public static float distance = 0;
    static boolean isStrafing = false, isIsStrafingDiagonally = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrian terrian) {
        float px = terrian.getHeightOfTerrain(super.getPosition().x -1, super.getPosition().z);
        float pz = terrian.getHeightOfTerrain(super.getPosition().x + 1, super.getPosition().z);
        if(px-pz >= 3 && px > super.getPosition().y) {
            currentSpeed = 0;
            jumpCoolDown = 30;
            isMoving = false;
            isColliding = true;
            super.increasePosition(3.5f,-3.5f,0);
        }
        px = terrian.getHeightOfTerrain(super.getPosition().x +1, super.getPosition().z);
        pz = terrian.getHeightOfTerrain(super.getPosition().x - 1, super.getPosition().z);
        if(px-pz >= 3 && px > super.getPosition().y) {
            currentSpeed = 0;
            jumpCoolDown = 30;
            isMoving = false;
            isColliding = true;
            super.increasePosition(-3.5f,-3.5f,0);
        }
        px = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z-1);
        pz = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z+1);
        if(px-pz >= 3 && px > super.getPosition().y) {
            currentSpeed = 0;
            jumpCoolDown = 30;
            isMoving = false;
            isColliding = true;
            super.increasePosition(0,-3.5f,3.5f);
        }
        px = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z+1);
        pz = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z-1);
        if(px-pz >= 3 && px > super.getPosition().y) {
            currentSpeed = 0;
            jumpCoolDown = 30;
            isMoving = false;
            isColliding = true;
            super.increasePosition(0,-3.5f,-3.5f);
        }
        checkInputs();

        super.increaseRotation(0, currentTurnSpeed, 0);
        distance = currentSpeed;
        if(currentSpeed > 2){
            currentSpeed = 2;
        }
        if (isStrafing) {
            dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY() - 90f)));
            dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY() - 90f)));
        } else{
            dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
            dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        }
        super.increasePosition(dx, 0, dz);
        super.increasePosition(0, upwardsSpeed, 0);
        float terrainHeight = terrian.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
        if(CheckCollision.isOnBridge){
            super.getPosition().y = terrainHeight + Level1.bridgeheight;
            GRAVITY = 0;
            JUMP_POWER = 0;
            isInAir = false;
        } else {
            GRAVITY = -0.5f;
            JUMP_POWER = 4.8f;
            jumpCoolDown--;
            upwardsSpeed += GRAVITY;
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                jump();
            }
        }
    }

    private void jump() {
        if (!isInAir && jumpCoolDown <= 0) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
            jumpCoolDown = 30;
        }
    }

    private void checkInputs() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && !isColliding){
            this.currentSpeed = -RUN_SPEED;
            isStrafing = false;
            isMoving = true;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S) && !isColliding){
            this.currentSpeed = RUN_SPEED;
            isStrafing = false;
            isMoving = true;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_D) && !isColliding){
            this.currentSpeed = -RUN_SPEED;
            isStrafing = true;
            isMoving = true;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A) && !isColliding){
            this.currentSpeed = RUN_SPEED;
            isStrafing = true;
            isMoving = true;
        }
        if (!Keyboard.isKeyDown(Keyboard.KEY_S)&&!Keyboard.isKeyDown(Keyboard.KEY_W)
                &&!Keyboard.isKeyDown(Keyboard.KEY_D)&&!Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.currentSpeed = 0;
            isMoving = false;
        }
        this.currentTurnSpeed = (float) (Mouse.getDX() * -1);
    }

}
