package company.renderer;

import company.Skybox.SkyboxRenderer;
import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.models.*;
import company.shaders.StaticShader;
import company.shaders.TerrianShader;
import company.toolBox.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private Matrix4f projectionMatrix;
    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    private SkyboxRenderer skyboxRenderer;
    private TerrianRenderer terrianRenderer;
    private TerrianShader terrianShader = new TerrianShader();

    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;

    private static final float RED = 0.5f;
    private static final float GREEN = 0.5f;
    private static final float BLUE = 0.5f;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrian> terrians = new ArrayList<Terrian>();

    public MasterRenderer(Loader loader, Camera cam) {
        enableCulling();
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrianRenderer = new TerrianRenderer(terrianShader, projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Camera camera, Light light) {
        prepare();
        shader.start();
        shader.loadSkyColor(RED, GREEN, BLUE);
        shader.loadLights(light);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        terrianShader.start();
        terrianShader.loadSkyColor(RED, GREEN, BLUE);
        terrianShader.loadLight(light);
        terrianShader.loadViewMatrix(camera);
        terrianRenderer.render(terrians);
        terrianShader.stop();
        skyboxRenderer.render(camera, RED, GREEN, BLUE);
        terrians.clear();
        entities.clear();
    }

    public void processTerrians(Terrian terrian) {
        terrians.add(terrian);
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
        terrianShader.cleanUp();
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    private void createProjectionMatrix(){
        projectionMatrix = new Matrix4f();
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
