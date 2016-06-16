package company.game;

import company.GUIs.GUIRenderer;
import company.GUIs.GUITexture;
import company.GUIs.LoadingScreen;
import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.entities.PlayerWorks.Player;
import company.fontMeshCreator.FontType;
import company.fontMeshCreator.GUIText;
import company.fontRendering.TextMaster;
import company.models.RawModel;
import company.models.TexturedModel;
import company.objLoading.ModelData;
import company.objLoading.OBJFileLoader;
import company.objLoading.OBJLoader;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import company.renderer.MasterRenderer;
import company.textures.ModelTexture;
import company.textures.TerrainTexture;
import company.textures.TerrainTexturePack;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private static List<GUITexture> GUIs = new ArrayList<GUITexture>();
    private static List<GUITexture> loadingScreens = new ArrayList<GUITexture>();

    public static void CreateMenu(Loader loader, GUIRenderer guiRenderer) {
        //is a counter for the map edge
        int mapEdge = 0;
        //font
        FontType Font_Tahoma = new FontType(loader.loadTexture("tahoma"), new File("res/tahoma.fnt"));
        GUIText text_1 = null;
        GUIText text_play = null;
        GUIText text_help = null;
        GUIText text_returnToReality = null;

        ModelData player1 = OBJFileLoader.loadOBJ("Box");
        RawModel playerModel = loader.loadToVAO(player1.getVertices(), player1.getTextureCoords(),
                player1.getNormals(), player1.getIndices());

        TexturedModel staticModel3 = new TexturedModel(OBJLoader.loadOBJModel("Box", loader),
                new ModelTexture(loader.loadTexture("projectileTexture")));
        ModelTexture texture3 = staticModel3.getTexture();
        staticModel3.getTexture().setHasTransparency(true);
        texture3.setShineDamper(10);
        texture3.setReflectivity(0.5f);

        TexturedModel Cube = new TexturedModel(OBJLoader.loadOBJModel("Box", loader),
            new ModelTexture(loader.loadTexture("projectileTexture")));
        ModelTexture cubeTexture = Cube.getTexture();
        cubeTexture.setHasTransparency(false);
        cubeTexture.setShineDamper(10);
        cubeTexture.setReflectivity(2f);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("FloorTile3invert"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("retro"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("FloorTile3invert"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("FloorTile3invert"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMaptest"));
        Terrian terrian = new Terrian( 0, 0, loader, texturePack, blendMap, "heightmapflat");

        Entity cube = new Entity(staticModel3, new Vector3f(500, terrian.getHeightOfTerrain(500,500) + 20, 500), 45, 0, 45, 7.5f);

        Player player = new Player(staticModel3, new Vector3f(500, terrian.getHeightOfTerrain(500,500) -90f, 500), 0,0,0,1f);
        Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1f, 1f, 0.85f));
        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer(loader, camera);

        GUITexture BGColor = new GUITexture(loader.loadTexture("MenuBG"), new Vector2f(0.6f, -0.90f), new Vector2f(1.6f, 2f));
        GUIs.add(BGColor);

        GUITexture loadingScreenTexture = new GUITexture(loader.loadTexture("Loading"), new Vector2f(0.6f, -0.90f), new Vector2f(1.6f, 2f));
        loadingScreens.add(loadingScreenTexture);

    while(GameEngineLoop.menuIsOpen) {
        camera.move();
        camera.pitch = 0;//keeps the camera still in terms of pitch
        renderer.processTerrians(terrian);
        renderer.processEntity(player);
        renderer.render(camera, sun);

        renderer.processEntity(cube);

        cube.getPosition().z = player.getPosition().z - 45f;
        cube.increaseRotation(0f,1f,1f);


        if (Mouse.getX() < 780 && Mouse.getX() > 440 && Mouse.getY() > 140 && Mouse.getY() < 315){

            text_play = new GUIText("> PLAY <", 7, Font_Tahoma, new Vector2f(0.0f, 0.60f), 1f, true);
            text_play.setColour(1f,0f,0f);

            if (Mouse.isButtonDown(0)){
                guiRenderer.Render(loadingScreens);
                GUIs.remove(BGColor);
                TextMaster.cleanUp();
                guiRenderer.Render(GUIs);
                startGame(player);
            }
        }else{
            text_play = new GUIText("< PLAY >", 6, Font_Tahoma, new Vector2f(0.0f, 0.6f), 1f, true);
            text_play.setColour(1f,0.6f,0.2f);
        }
        if (Mouse.getX() < 780 && Mouse.getX() > 440 && Mouse.getY() > 40 && Mouse.getY() < 115){

            text_help = new GUIText("> HELP <", 6, Font_Tahoma, new Vector2f(0.0f, 0.75f), 1f, true);
            text_help.setColour(1f,0f,0f);
            text_play = new GUIText("< PLAY >", 6, Font_Tahoma, new Vector2f(0.0f, 0.6f), 1f, true);
            text_play.setColour(1f,0.6f,0.2f);

        }else{
            text_help = new GUIText("< HELP >", 5, Font_Tahoma, new Vector2f(0.0f, 0.75f), 1f, true);
            text_help.setColour(1f,0.6f,0.2f);
        }
        if (Mouse.getX() < 780 && Mouse.getX() > 440 && Mouse.getY() > 0 && Mouse.getY() < 40) {

            text_returnToReality = new GUIText("> RETURN TO \"REALITY\"? <", 3, Font_Tahoma, new Vector2f(0.0f, 0.9f), 1f, true);
            text_returnToReality.setColour(1f,0f,0f);

            if (Mouse.isButtonDown(0)){
                System.exit(0);
            }
        }else{
            text_returnToReality = new GUIText("< RETURN TO REALITY? >", 3, Font_Tahoma, new Vector2f(0.0f, 0.9f), 1f, true);
            text_returnToReality.setColour(1f,0.6f,0.2f);
        }

        text_1 = new GUIText("</D1G1T4L>", 7, Font_Tahoma, new Vector2f(0f, 0.001f), 1f, true);
        text_1.setColour(1f,0.6f,0.2f);

        TextMaster.loadText(text_1);
        TextMaster.loadText(text_play);
        TextMaster.loadText(text_help);
        TextMaster.loadText(text_returnToReality);
        TextMaster.render();
        TextMaster.removeText(text_1);
        TextMaster.removeText(text_play);
        TextMaster.removeText(text_help);
        TextMaster.removeText(text_returnToReality);

        guiRenderer.Render(GUIs);
        DisplayManager.updateDisplay();

        //sets the player(camera, because its a fps) equal to the height of the terrain for the left to right staffing of the menu
        player.getPosition().y = terrian.getHeightOfTerrain(player.getPosition().x, player.getPosition().z);

        //checks if the camera has it the edge of the map and if so reverses its direction
        if (mapEdge < 200) {
            player.getPosition().z++;
            mapEdge++;
        } else {
            player.getPosition().z--;
            if (player.getPosition().z == 100) {
                mapEdge = 0;
            }
        }
    }

}

    //closes Menu thus starting game
    private static void startGame(Player player) {
        GameEngineLoop.menuIsOpen = false;
    }

}
