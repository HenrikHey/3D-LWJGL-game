package company.CollisionDetection;

import company.AI.BasicAI;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.PlayerWorks.Player;
import company.game.GameEngineLoop;
import company.projectiles.Projectile;

public class CheckCollision {

    public static void CheckCollision(Player player, Entity entity, float Scalex, float Scalez, float pushbackForce) {
        //checks if the player is inside the hit box for the entity
        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex)
                && player.getPosition().y <= entity.getPosition().y){

            //prevents the player from moving while inside the hit box
            player.isMoving = false;
            player.isColliding = true;

            //checks which side of the hit box the player is colliding with to accurately push the player away
            if(player.getPosition().x >=(entity.getPosition().x - (Scalex - 1)) && player.getPosition().x >= (entity.getPosition().x + 1)) {
                pushBackPlayer(player, pushbackForce, 0);
            }
            if(player.getPosition().x <=(entity.getPosition().x + (Scalex + 1)) && player.getPosition().x <= (entity.getPosition().x + - 1)) {
                pushBackPlayer(player, -pushbackForce, 0);
            }
            if(player.getPosition().z >=(entity.getPosition().z - (Scalez - 1)) && player.getPosition().z >= (entity.getPosition().z + 1)) {
                pushBackPlayer(player, 0, pushbackForce);
            }
            if(player.getPosition().z <=(entity.getPosition().z + (Scalez + 1)) && player.getPosition().z <= (entity.getPosition().z + - 1)) {
                pushBackPlayer(player, 0, -pushbackForce);
            }
            if(player.getPosition().y >= entity.getPosition().y && Player.isInAir) {
                player.getPosition().y = entity.getPosition().y;
                Player.isInAir = false;
            }
        } else {
            player.isColliding = false;
        }
    }

    public static boolean isOnBridge = false;

    public static void CheckCollisionWithBridge(Player player, Entity entity, float Scalex, float Scalez, float pushbackForce, Camera camera) {
        //checks if the player is inside the hit box for the entity
        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex) &&
                player.getPosition().y >= entity.getPosition().y - 4){

            isOnBridge = true;
            player.getPosition().y = entity.getPosition().y + 3;
            camera.getPosition().y+=4;
            Player.isInAir = false;

        } else {
            player.isColliding = false;
            isOnBridge = false;
        }
    }

    public static void CheckCollisionWithAI(Player player, Entity entity, float Scalex, float Scalez, float pushbackForce) {
        //checks if the player is inside the hit box for the entity
        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex)
                &&!(entity.getRotX() == 90) && !(entity.getRotZ() == 90)){

            //prevents the player from moving while inside the hit box
            player.isMoving = false;
            player.isColliding = true;
            Player.PlayerHealth-=10;

            //checks which side of the hit box the player is colliding with to accurately push the player away
            if(player.getPosition().x >=(entity.getPosition().x - (Scalex - 1)) && player.getPosition().x >= (entity.getPosition().x + 1)) {
                pushBackPlayer(player, pushbackForce, 0);
            }
            if(player.getPosition().x <=(entity.getPosition().x + (Scalex + 1)) && player.getPosition().x <= (entity.getPosition().x + - 1)) {
                pushBackPlayer(player, -pushbackForce, 0);
            }
            if(player.getPosition().z >=(entity.getPosition().z - (Scalez - 1)) && player.getPosition().z >= (entity.getPosition().z + 1)) {
                pushBackPlayer(player, 0, pushbackForce);
            }
            if(player.getPosition().z <=(entity.getPosition().z + (Scalez + 1)) && player.getPosition().z <= (entity.getPosition().z + - 1)) {
                pushBackPlayer(player, 0, -pushbackForce);
            }
        } else {
            player.isColliding = false;
        }
    }

    private static void pushBackPlayer(Player player, float xPushBack, float zPushBack) {
        player.increasePosition(xPushBack, 0, zPushBack);
    }

    public static void CheckCollisionAI(Entity player, Entity entity, float Scalex, float Scalez, float pushbackForce) {
        //checks if the player is inside the hit box for the entity
        if(player.getPosition().z > (entity.getPosition().z - Scalez) && player.getPosition().z < (entity.getPosition().z + Scalez)
                && player.getPosition().x > (entity.getPosition().x - Scalex) && player.getPosition().x < (entity.getPosition().x + Scalex)
                &&!(entity.getRotX() == 90) && !(entity.getRotZ() == 90)){

            //checks which side of the hit box the player is colliding with to accurately push the player away
            if(player.getPosition().x >=(entity.getPosition().x - (Scalex - 1)) && player.getPosition().x >= (entity.getPosition().x + 1)) {
                pushBackPlayer(player, pushbackForce, 0);
            }
            if(player.getPosition().x <=(entity.getPosition().x + (Scalex + 1)) && player.getPosition().x <= (entity.getPosition().x + - 1)) {
                pushBackPlayer(player, -pushbackForce, 0);
            }
            if(player.getPosition().z >=(entity.getPosition().z - (Scalez - 1)) && player.getPosition().z >= (entity.getPosition().z + 1)) {
                pushBackPlayer(player, 0, pushbackForce);
            }
            if(player.getPosition().z <=(entity.getPosition().z + (Scalez + 1)) && player.getPosition().z <= (entity.getPosition().z + - 1)) {
                pushBackPlayer(player, 0, -pushbackForce);
            }
        }
    }

    private static void pushBackPlayer(Entity player, float xPushBack, float zPushBack) {
        player.increasePosition(xPushBack, 0, zPushBack);
    }

    public static boolean CheckProjectileCollision(Projectile projectile, BasicAI AI, float Scalex, float Scalez) {
        //checks if the player is inside the hit box for the entity
        if(projectile.getPosition().z > (AI.getPosition().z - Scalez) && projectile.getPosition().z < (AI.getPosition().z + Scalez)
                && projectile.getPosition().x > (AI.getPosition().x - Scalex) && projectile.getPosition().x < (AI.getPosition().x + Scalex)
                && projectile.getPosition().y > AI.getPosition().y && projectile.getPosition().y < (AI.getPosition().y + (Scalex*10))
                &&!(AI.getRotX() == 90) && !(AI.getRotZ() == 90)){
            AI.increaseRotation(90, 0, 90);
            Player.PlayerScore+=1;
            return true;
        }
        return false;
    }

}
