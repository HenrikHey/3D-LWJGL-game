package company.levels;

import company.AI.BasicAI;
import company.CollisionDetection.CheckCollision;
import company.GUIs.GUIRenderer;
import company.GUIs.GUITexture;
import company.Terrians.Terrian;
import company.drops.DropType;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.entities.PlayerWorks.Player;
import company.entities.PlayerWorks.PlayerMechanics;
import company.fontMeshCreator.FontType;
import company.fontMeshCreator.GUIText;
import company.fontRendering.TextMaster;
import company.game.GameEngineLoop;
import company.game.PauseScreen.PauseScreen;
import company.models.RawModel;
import company.models.TexturedModel;
import company.multiModelObjects.MultiModelObject;
import company.objLoading.ModelData;
import company.objLoading.OBJFileLoader;
import company.objLoading.OBJLoader;
import company.particles.ParticleSystem;
import company.particles.ParticleTexture;
import company.particles.particleMaster;
import company.physics.Physics;
import company.projectiles.Projectile;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import company.renderer.MasterRenderer;
import company.shaders.StaticShader;
import company.textures.ModelTexture;
import company.textures.TerrainTexture;
import company.textures.TerrainTexturePack;
import company.water.WaterRenderer;
import company.water.WaterShader;
import company.water.WaterTile;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level1 {

    public static int bridgeheight = 30;

    //are public and static to allow the menu class to access them
    public static List<Entity> trees = new ArrayList<Entity>();
    public static List<Entity> bridgeParts = new ArrayList<Entity>();
    public static List<Entity> grass = new ArrayList<Entity>();

    public static List<Entity> pushableObjects = new ArrayList<Entity>();
    public static List<Boolean> objectIsPushed =  new ArrayList<Boolean>();
    public static List<Float> playerKineticForce_Objects = new ArrayList<Float>();

    public static List<Entity> flowers = new ArrayList<Entity>();
    public static List<GUITexture> GUIs = new ArrayList<GUITexture>();
    public static List<BasicAI> AIs = new ArrayList<BasicAI>();
    public static List<Integer> explosionLife = new ArrayList<Integer>();

    public static void loadLevel1(Loader loader, GUIRenderer guiRenderer) {

        /**FONT GENERATION**/
        TextMaster.init(loader);

        FontType Font_Tahoma = new FontType(loader.loadTexture("Orator"), new File("res/Orator.fnt"));
        GUIText text_1;
        GUIText text_2;
        GUIText text_3;

        /**END OF FONT GENERATION**/

        /**MODEL CREATION STUFF**/

        ModelData model = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(model.getVertices(), model.getTextureCoords(),
                model.getNormals(), model.getIndices());

        ModelData model1 = OBJFileLoader.loadOBJ("boulder");
        RawModel grassModel = loader.loadToVAO(model1.getVertices(), model1.getTextureCoords(),
                model1.getNormals(), model1.getIndices());

        ModelData player1 = OBJFileLoader.loadOBJ("Box");
        RawModel playerModel = loader.loadToVAO(player1.getVertices(), player1.getTextureCoords(),
                player1.getNormals(), player1.getIndices());

        TexturedModel retroTree1 = new TexturedModel(OBJLoader.loadOBJModel("tree", loader),
                new ModelTexture(loader.loadTexture("tree")));
        TexturedModel retroTree2 = new TexturedModel(OBJLoader.loadOBJModel("tree", loader),
                new ModelTexture(loader.loadTexture("retroTree2")));
        TexturedModel staticModel1 = new TexturedModel(OBJLoader.loadOBJModel("boulder", loader),
                new ModelTexture(loader.loadTexture("boulder")));
        TexturedModel staticModel2 = new TexturedModel(OBJLoader.loadOBJModel("person", loader),
                new ModelTexture(loader.loadTexture("acid")));
        TexturedModel staticModel3 = new TexturedModel(OBJLoader.loadOBJModel("Box", loader),
                new ModelTexture(loader.loadTexture("projectileTexture")));
        TexturedModel Box = new TexturedModel(OBJLoader.loadOBJModel("Box", loader),
                new ModelTexture(loader.loadTexture("crateTexture_1")));
        TexturedModel BridgePart1 = new TexturedModel(OBJLoader.loadOBJModel("tester1", loader),
                new ModelTexture(loader.loadTexture("ground1")));

        ModelTexture texture = retroTree1.getTexture();
        retroTree1.getTexture().setHasTransparency(true);
        ModelTexture texturetree2 = retroTree2.getTexture();
        retroTree2.getTexture().setHasTransparency(true);
        ModelTexture texture1 = staticModel1.getTexture();
        staticModel1.getTexture().setHasTransparency(true);
        ModelTexture texture2 = staticModel1.getTexture();
        staticModel2.getTexture().setHasTransparency(true);
        ModelTexture texture3 = staticModel3.getTexture();
        staticModel3.getTexture().setHasTransparency(true);
        ModelTexture BoxTexture = Box.getTexture();
        Box.getTexture().setHasTransparency(true);
        ModelTexture BridgeTexture = BridgePart1.getTexture();
        BridgePart1.getTexture().setHasTransparency(true);

        //sets the shine dampers abd reflectivity for all models
        texture.setShineDamper(5);
        texture.setReflectivity(0.1f);
        texture1.setShineDamper(10);
        texture1.setReflectivity(0.1f);
        texture2.setShineDamper(10);
        texture2.setReflectivity(0.15f);
        texture3.setShineDamper(10);
        texture3.setReflectivity(0.5f);
        texturetree2.setShineDamper(0);
        texturetree2.setReflectivity(1f);
        BoxTexture.setShineDamper(10);
        BoxTexture.setReflectivity(0.1f);
        BridgeTexture.setShineDamper(10);
        BridgeTexture.setReflectivity(0.1f);

        /**END OF MODEL CREATION STUFF**/


        /**TERRAIN LOADER STUFF**/

        //loads the terrain height and blend maps
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("ground1"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("crateTexture_1"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("concrete"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("retro"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("BlendMapTestIMG"));
        Terrian terrian = new Terrian( 0, 0, loader, texturePack, blendMap, "heightmaptest");

        /**END OF TERRAIN LOADER STUFF**/


        /**ENTITY LISTS AND CREATORS**/
        //generates all entities and such
        Random random = new Random();

        for(int i = 0; i < 10; i ++ ){
            float x = random.nextFloat() * terrian.SIZE;
            float z = random.nextFloat() * terrian.SIZE;
            float y = terrian.getHeightOfTerrain(x, z);
            trees.add(new Entity(retroTree1, new Vector3f(x,y, z + random.nextFloat()),0,0,0,10f));
        }

        /**AI**/
        for(int i = 0; i < 20; i ++ ){
            float x = random.nextFloat() * terrian.SIZE;
            float z = random.nextFloat() * terrian.SIZE;
            AIs.add(new BasicAI(staticModel2, new Vector3f(x, 0, z), 0,0,0,1.5f));
            BasicAI.isAlive.add(true);
            explosionLife.add(30);
        }

        /**DROPS**/

        for(int i = 0; i < 10; i++) {
            float x = random.nextFloat() * terrian.SIZE-100;
            float z = random.nextFloat() * terrian.SIZE-100;
            DropType ammo = new DropType(staticModel3, new Vector3f(x, terrian.getHeightOfTerrain(x,z) + 10, z), 0,0,0,3f,1);
            DropType.dropTypes.add(ammo);
        }

        /**END OF DROPS**/


        /**PUSHABLE OBJECTS**/

        for(int i = 0; i < 10; i++) {
            float x = (random.nextFloat() * 20) + 505;
            float z = (random.nextFloat() * 20) + 505;
            pushableObjects.add(new Entity(Box, new Vector3f(x, terrian.getHeightOfTerrain(x, z) + 3, z),0,0,0,4f));
            pushableObjects.add(new Entity(Box, new Vector3f(x, terrian.getHeightOfTerrain(x, z) + 12, z),0,0,0,4f));
            objectIsPushed.add(false);
            playerKineticForce_Objects.add(0f);
            objectIsPushed.add(false);
            playerKineticForce_Objects.add(0f);
        }

        /**END OF PUSHABLE OBJECTS**/

        /** BRIDGE PARTS**/

        float x = 175;
        float y = 526;
        Entity bridgepart1 = new Entity(BridgePart1, new Vector3f(x, terrian.getHeightOfTerrain(x,y) + bridgeheight, y), 0, 0, 0, 5f);
        bridgeParts.add(bridgepart1);
        Entity bridgepart2 = new Entity(BridgePart1, new Vector3f(x + 60, terrian.getHeightOfTerrain(x,y) + bridgeheight, y), 0, 0, 0, 5f);
        bridgeParts.add(bridgepart2);
        /** BRIDGE PARTS**/

        /**MULTI MODEL OBJECTS**/

        MultiModelObject.loadObjects(staticModel3, 0, terrian.getHeightOfTerrain(0f,0f), 0);

        /**END OF MULTI MODEL OBJECTS**/

        /**END OF ENTITY LISTS AND CREATORS**/
        Player player = new Player(staticModel3, new Vector3f(500, terrian.getHeightOfTerrain(500,500) -90f, 500), 0,0,0,1f);
        Light sun = new Light(new Vector3f(-1000000, 1500000, -1000000), new Vector3f(0.99f, 0.82f, 0.4f));
        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer(loader, camera);
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
        List<WaterTile> waters = new ArrayList<WaterTile>();
        waters.add(new WaterTile(520, 520, terrian.getHeightOfTerrain(500,500) + 2));

        //Particles repurposed for sign lol
        particleMaster.init(loader, renderer.getProjectionMatrix());
        ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("sign1"), 1);
        ParticleSystem system = new ParticleSystem(particleTexture, 0.085f, 0.0000000f, -0.00f, 100f, 30f);
        ParticleTexture particleTexture2 = new ParticleTexture(loader.loadTexture("sign2"), 1);
        ParticleSystem system2 = new ParticleSystem(particleTexture2, 0.085f, 0.0000000f, -0.00f, 100f, 30f);

        /**GUI CREATION**/

        GUITexture GUI1 = new GUITexture(loader.loadTexture("AmmoContianer"), new Vector2f(0.74f, -0.75f), new Vector2f(0.35f, 0.25f));
        GUIs.add(GUI1);
        GUITexture GUI2 = new GUITexture(loader.loadTexture("crosshairs"), new Vector2f(0.0f, 0.0f), new Vector2f(0.055f, 0.075f));
        GUIs.add(GUI2);
        GUITexture BGColor = new GUITexture(loader.loadTexture("MenuBG"), new Vector2f(0.6f, -0.90f), new Vector2f(1.6f, 2f));
       // GUIs.add(BGColor);
        GUITexture raineffects1 = new GUITexture(loader.loadTexture("rainddrops1"), new Vector2f(-0f, -0f), new Vector2f(1f, 1.25f));
        //GUIs.add(raineffects1);
        GUITexture GUI3 = new GUITexture(loader.loadTexture("crosshairsFired"), new Vector2f(0.0f, 0.0f), new Vector2f(0.055f, 0.075f));

        /**END OF GUI CREATION**/

        int fireRate = 0;
        int countertime = 0;
        //makes the cursor invisible
        Cursor emptyCursor = null;
        try {
            emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
            Mouse.setNativeCursor(emptyCursor);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Mouse.setCursorPosition(DisplayManager.WIDTH/2, DisplayManager.HEIGHT/2);
        
        //only runs while the window is open
        while(!Display.isCloseRequested()) {

            //renders everything
            for(Entity tree: trees){
                renderer.processEntity(tree);
                //checks if the player is colliding with any of these entities
                CheckCollision.CheckCollision(player, tree, tree.getScale(), tree.getScale(), 1.85f);
            }
            for(Entity grasses: grass){
                renderer.processEntity(grasses);
            }
            for(Entity flower: flowers){
                renderer.processEntity(flower);
            }

            //pushable Objects
            for (int i = 0; i < pushableObjects.size(); i++) {
                renderer.processEntity(pushableObjects.get(i));
                for (Entity Object: pushableObjects) {
                    CheckCollision.CheckCollisionAI(Object, pushableObjects.get(i), pushableObjects.get(i).getScale() + 3f,
                            pushableObjects.get(i).getScale() + 3f, 1.45f);
                }
                CheckCollision.CheckCollision(player, pushableObjects.get(i), pushableObjects.get(i).getScale() + 1,
                        pushableObjects.get(i).getScale() + 1, 2.25f);
                Physics.updatePlayerKineticInteraction_Object(player, pushableObjects.get(i), 3f,
                        pushableObjects.get(i).getScale() + 3f, pushableObjects.get(i).getScale() + 3f, i, terrian);
            }

            //AI
            for(int i = 0; i < AIs.size(); i++){
                renderer.processEntity(AIs.get(i));
                CheckCollision.CheckCollisionWithAI(player, AIs.get(i), 10, 10, 1.45f);
                for (BasicAI ai: AIs){
                    CheckCollision.CheckCollisionAI(ai, AIs.get(i), 10, 10, 1.45f);
                }
                for(Entity tree: trees){
                    CheckCollision.CheckCollisionAI(AIs.get(i), tree, tree.getScale(), tree.getScale(), 1.45f);
                }
                for(int b = 0; b < pushableObjects.size(); b++) {
                    Physics.updateEntityKineticInteraction_Object(AIs.get(i), pushableObjects.get(b), 2f,
                            pushableObjects.get(b).getScale() + 3f, pushableObjects.get(b).getScale() + 3f, b, terrian);
                    CheckCollision.CheckCollisionAI(AIs.get(i), pushableObjects.get(b), pushableObjects.get(b).getScale() + 3f,
                            pushableObjects.get(b).getScale() + 3f, 1.45f);
                }
                AIs.get(i).moveAI(terrian, player, AIs.get(i), i);
            }

            for (int i = 0; i < DropType.dropTypes.size(); i++) {
                renderer.processEntity(DropType.dropTypes.get(i));
                DropType.dropTypes.get(i).increaseRotation(0,5,0);
                DropType.update(player, i);
            }

            //Projectiles
            if(Mouse.isButtonDown(0) && fireRate > 5){
                Player.WeaponAmmo-=1;
                Projectile projectile = new Projectile(staticModel3, new Vector3f(camera.getPosition().x,
                        camera.getPosition().y, camera.getPosition().z),0,0,0,0.15f);
                Projectile.projectiles.add(projectile);
                Projectile.life.add(Projectile.lifeTime);
                Projectile.fireProjectile(player, camera);
                GUIs.set(1, GUI3);
                fireRate=0;
            }else{
                fireRate++;
                GUIs.set(1, GUI2);
            }

            for(int i = 0; i < Projectile.projectiles.size(); i++){
                renderer.processEntity(Projectile.projectiles.get(i));
                for(int a = 0; a < AIs.size(); a++){
                    if (CheckCollision.CheckProjectileCollision(Projectile.projectiles.get(i), AIs.get(a), AIs.get(a).getScale() + 5, AIs.get(a).getScale() + 5)) {
                        Projectile.life.set(i, 1);
                        BasicAI.isAlive.set(a, false);
                    }
                }
                Projectile.update(Projectile.projectiles.get(i), i, terrian);
            }

            for(Entity e: bridgeParts) {
                renderer.processEntity(e);
                CheckCollision.CheckCollisionWithBridge(player, e, 16+e.getScale(), 16+e.getScale(), 10f, camera);
            }

            //core renderer
            renderer.processTerrians(terrian);
            renderer.processEntity(player);
            renderer.render(camera, sun);
            waterRenderer.render(waters, camera);

            if(countertime <= 2000) {
                //particles
                particleMaster.update();
                system.generateParticles(new Vector3f(270 - 100, terrian.getHeightOfTerrain(270 - 50, 300) + 20, 300));
                system.generateParticles(new Vector3f(320 - 100, terrian.getHeightOfTerrain(290 - 50, 300) + 20, 300));
                system.generateParticles(new Vector3f(270 - 100, terrian.getHeightOfTerrain(270 - 50, 300) + 20, 300 - 100));
                system.generateParticles(new Vector3f(320 - 100, terrian.getHeightOfTerrain(290 - 50, 300) + 20, 300 - 100));
                system2.generateParticles(new Vector3f(270 - 100, terrian.getHeightOfTerrain(270 - 50, 300) + 20, 300 + 50));
                system2.generateParticles(new Vector3f(320 - 100, terrian.getHeightOfTerrain(290 - 50, 300) + 20, 300 + 50));
                system2.generateParticles(new Vector3f(270 - 100, terrian.getHeightOfTerrain(270 - 50, 300) + 20, 300 + 150));
                system2.generateParticles(new Vector3f(320 - 100, terrian.getHeightOfTerrain(290 - 50, 300) + 20, 300 + 150));
                particleMaster.renderParticles(camera);
                countertime++;
            }

            guiRenderer.Render(GUIs);

            //player mechanics and such
            PlayerMechanics.headBob(camera);
            player.move(terrian);
            camera.move();

            //renders GUI Text!
            text_1 = new GUIText("Kills: " + Player.PlayerScore, 1.2f, Font_Tahoma, new Vector2f(0.001f, 0.001f), 1f, false);
            text_2 = new GUIText("Player Health: " + Player.PlayerHealth + "%", 1.2f, Font_Tahoma, new Vector2f(0.74f, 0.85f), 1f, false);
            text_3 = new GUIText("Ammo: " + Player.WeaponAmmo + " | 200", 1.2f, Font_Tahoma, new Vector2f(0.74f, 0.90f), 1f, false);
            text_1.setColour(1f,0.6f,0.2f);
            text_2.setColour(1f,0.6f,0.2f);
            text_3.setColour(1f,0.6f,0.2f);
            TextMaster.loadText(text_1);
            TextMaster.loadText(text_2);
            TextMaster.loadText(text_3);
            TextMaster.render();
            TextMaster.removeText(text_1);
            TextMaster.removeText(text_2);
            TextMaster.removeText(text_3);

            if(Keyboard.isKeyDown(Keyboard.KEY_P)){
                GameEngineLoop.GameIsPaused = true;
                PauseScreen.CreateMenu(camera, renderer, loader, guiRenderer, terrian, player, sun);
            }

            //updates the display
            DisplayManager.updateDisplay();
        }
        particleMaster.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
    }

}
