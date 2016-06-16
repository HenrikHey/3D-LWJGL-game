package company.physics;

import company.Terrians.Terrian;
import company.entities.Entity;
import company.entities.PlayerWorks.Player;
import company.game.GameEngineLoop;
import company.levels.Level1;

import java.util.Random;

public class Physics {

    private static float GRAVITY = -0.5f;
    private static float playerDX = 0, playerDZ = 0;
    private static boolean isAbove = false;
    private static Random rand = new Random();
    static float objectStoredEnergy = 0;

    public static void updatePlayerKineticInteraction_Object(Player player, Entity entity, float playerKineticForce, float Scalex, float Scalez,
                                                             int index, Terrian terrian) {


        objectStoredEnergy = Level1.playerKineticForce_Objects.get(index);

        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex)
                && !Level1.objectIsPushed.get(index) && player.getPosition().y <= entity.getPosition().y){

            Level1.playerKineticForce_Objects.set(index, playerKineticForce);
            Level1.objectIsPushed.set(index, true);
            playerDX = (float) (Math.sin(Math.toRadians(player.getRotY()) * (rand.nextFloat() * 1.1f)));
            playerDZ = (float) (Math.cos(Math.toRadians(player.getRotY()) * (rand.nextFloat() * 1.1f)));

        }

        if(Level1.objectIsPushed.get(index) && Level1.playerKineticForce_Objects.get(index) >= 0) {

            pushObject(entity, -(Level1.playerKineticForce_Objects.get(index) * playerDX),
                    -(Level1.playerKineticForce_Objects.get(index) * playerDZ));

            if(playerDX != 0 || playerDZ != 0 ) {
                entity.increaseRotation(0, rand.nextFloat() * 7.5f, 0);
            }

            Level1.playerKineticForce_Objects.set(index, objectStoredEnergy-=0.01f);
        }


        if (Level1.playerKineticForce_Objects.get(index) <= 0) {
            Level1.objectIsPushed.set(index, false);
        }
        for(Entity e: Level1.pushableObjects) {
            if (entity.getPosition().y > e.getPosition().y && e.getPosition().x == entity.getPosition().x && !(entity.getPosition().y >= e.getPosition().y + 10)) {
                isAbove = true;
                break;
            }else {
                isAbove = false;
            }
        }

        if(entity.getPosition().y > terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4 && !isAbove) {
            entity.increasePosition(0,GRAVITY-=0.05f,0);
        }
        if(entity.getPosition().y < terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4) {
            entity.getPosition().y = terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4;
            GRAVITY = -0.5f;
        }
    }

    public static void updateEntityKineticInteraction_Object(Entity player, Entity entity, float playerKineticForce, float Scalex, float Scalez,
                                                             int index, Terrian terrian) {

        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex)
                && !Level1.objectIsPushed.get(index)){

            Level1.playerKineticForce_Objects.set(index, playerKineticForce);
            Level1.objectIsPushed.set(index, true);
            playerDX = (float) (Math.sin(Math.toRadians(player.getRotY()) * (rand.nextFloat() * 1.1f)));
            playerDZ = (float) (Math.cos(Math.toRadians(player.getRotY()) * (rand.nextFloat() * 1.1f)));

        }

        if(Level1.objectIsPushed.get(index) && Level1.playerKineticForce_Objects.get(index) >= 0) {

            pushObject(entity, -(Level1.playerKineticForce_Objects.get(index) * playerDX),
                    -(Level1.playerKineticForce_Objects.get(index) * playerDZ));

            if(playerDX != 0 || playerDZ != 0 ) {
                entity.increaseRotation(0, rand.nextFloat() * 7.5f, 0);
            }

            float objectStoredEnergy = Level1.playerKineticForce_Objects.get(index);
            Level1.playerKineticForce_Objects.set(index, objectStoredEnergy-=0.1f);
        }

        if (Level1.playerKineticForce_Objects.get(index) <= 0) {
            Level1.objectIsPushed.set(index, false);
        }
        for(Entity e: Level1.pushableObjects) {
            if (entity.getPosition().y > e.getPosition().y && e.getPosition().x == entity.getPosition().x && !(entity.getPosition().y >= e.getPosition().y + 10)) {
                isAbove = true;
                break;
            }else {
                isAbove = false;
            }
        }

        if(entity.getPosition().y > terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4 && !isAbove) {
            entity.increasePosition(0,GRAVITY-=0.05f,0);
        }
        if(entity.getPosition().y < terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4) {
            entity.getPosition().y = terrian.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) + 4;
            GRAVITY = -0.5f;
        }
    }

    private static void pushObject(Entity entity, float playerKineticForcex, float playerKineticForcez) {
        entity.increasePosition(playerKineticForcex, 0, playerKineticForcez);
    }

}
