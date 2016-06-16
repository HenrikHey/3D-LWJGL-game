package company.multiModelObjects;

import company.entities.Entity;
import company.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class MultiModelObject {

    public static List<Entity>ComplexAI = new ArrayList<Entity>();

    public static void loadObjects(TexturedModel model, int x, float y, int z) {
        ComplexAI.add(new Entity(model, new Vector3f(x, y + 20, z+25), 0,0,0,1f));
    }
}
