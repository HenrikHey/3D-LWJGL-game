package company.projectiles;

import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.PlayerWorks.Player;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Projectile extends Entity{

    private static float speed = 8f;
    public static int lifeTime = 100;

    public static List<Projectile> projectiles = new ArrayList<Projectile>();
    public static List<Integer> life = new ArrayList<Integer>();
    public static List<Float> dx = new ArrayList<Float>();
    public static List<Float> dz = new ArrayList<Float>();
    public static List<Float> dy = new ArrayList<Float>();

    public Projectile(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    //gets the angle at which the projectile is fired & stores it in the appropriate lists
    public static void fireProjectile(Player player, Camera camera) {
        dx.add((float) (speed * Math.sin(Math.toRadians(player.getRotY()))));
        dz.add((float) (speed * Math.cos(Math.toRadians(player.getRotY()))));
        dy.add((float) (speed * Math.sin(Math.toRadians(camera.getPitch()))));
    }

    //updates the projectile and checks if it has to be removed
    public static void update(Projectile projectile, int index, Terrian terrian) {
        int currentlife = life.get(index);
        currentlife--;
        life.set(index, currentlife);
        if (life.get(index) <= 0 ||
                projectile.getPosition().getY() <= terrian.getHeightOfTerrain(projectile.getPosition().x, projectile.getPosition().z)) {
            projectiles.remove(index);
            life.remove(index);
            dx.remove(index);
            dz.remove(index);
            dy.remove(index);
        } else {
            projectile.increasePosition(-dx.get(index), -dy.get(index), -dz.get(index));
        }
    }
}
