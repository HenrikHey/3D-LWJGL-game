package company.drops;

import company.entities.Entity;
import company.entities.PlayerWorks.Player;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DropType extends Entity{

    private static int type;
    public static List<DropType>dropTypes = new ArrayList<DropType>();

    public DropType(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int type) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.type = type;
    }

    public static void update(Player player, int index) {
        if(player.getPosition().z > (dropTypes.get(index).getPosition().z - 5) && player.getPosition().z < (dropTypes.get(index).getPosition().z + 5)
                && player.getPosition().x > (dropTypes.get(index).getPosition().x - 5) && player.getPosition().x < (dropTypes.get(index).getPosition().x + 5)){
            if(type == 1) {
                Player.WeaponAmmo+= 10;
            } else if(type == 2) {
                Player.PlayerHealth+=20;
            }
            dropTypes.remove(index);
        }
    }
}
