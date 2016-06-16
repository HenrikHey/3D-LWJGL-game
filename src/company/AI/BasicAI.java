package company.AI;

import company.Terrians.Terrian;
import company.entities.Entity;
import company.entities.PlayerWorks.Player;
import company.game.GameEngineLoop;
import company.levels.Level1;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

public class BasicAI extends Entity{

    public static List<Boolean> isAlive = new ArrayList<Boolean>();

    private static float AISpeed = 0.75f;

    public BasicAI(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public  void moveAI(Terrian terrian, Player player, BasicAI AI, int index) {
        //player positions
        float playerPosX = player.getPosition().x;
        float playerPosZ = player.getPosition().z;

        if (isAlive.get(index) == true) {
            if (AI.getPosition().x < playerPosX) AI.getPosition().x += AISpeed;
            if (AI.getPosition().x > playerPosX) AI.getPosition().x -= AISpeed;
            if (AI.getPosition().z < playerPosZ) AI.getPosition().z += AISpeed;
            if (AI.getPosition().z > playerPosZ) AI.getPosition().z -= AISpeed;
        } else {
            //Level1.AIs.remove(index);
            //isAlive.remove(index);
        }
        super.getPosition().y = terrian.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
    }
}
