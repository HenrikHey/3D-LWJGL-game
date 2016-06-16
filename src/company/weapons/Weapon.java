package company.weapons;

import company.entities.Entity;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Weapon extends Entity{

    int fireRate = 0;
    int damage = 0;

    public Weapon(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int fireRate, int damage) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.fireRate = fireRate;
        this.damage = damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
