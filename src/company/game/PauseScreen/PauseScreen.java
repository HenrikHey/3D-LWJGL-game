package company.game.PauseScreen;

import company.GUIs.GUIRenderer;
import company.GUIs.GUITexture;
import company.Terrians.Terrian;
import company.entities.Camera;
import company.entities.Entity;
import company.entities.Light;
import company.entities.PlayerWorks.Player;
import company.fontMeshCreator.FontType;
import company.fontMeshCreator.GUIText;
import company.fontRendering.TextMaster;
import company.game.GameEngineLoop;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import company.renderer.MasterRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PauseScreen {

    private static List<GUITexture> GUIs = new ArrayList<GUITexture>();
    public static List<Entity> trees = new ArrayList<Entity>();

    public static void CreateMenu(Camera camera, MasterRenderer renderer, Loader loader, GUIRenderer guiRenderer, Terrian terrian
            , Player player, Light sun) {
        //is a counter for the map edge
        //font
        FontType Font_Tahoma = new FontType(loader.loadTexture("tahoma"), new File("res/tahoma.fnt"));
        GUIText text_1;
        GUIText text_play;
        GUIText text_help;
        GUIText text_returnToReality;

        GUITexture BGColorPaused = new GUITexture(loader.loadTexture("loadingScreen"), new Vector2f(0.6f, -0.5f), new Vector2f(1.6f, 1.5f));
        GUIs.add(BGColorPaused);

        while(GameEngineLoop.GameIsPaused) {

            for(Entity tree: trees){
                renderer.processEntity(tree);
            }

            renderer.processTerrians(terrian);
            renderer.processEntity(player);
            renderer.render(camera, sun);

            if (Mouse.getX() < 305 && Mouse.getX() > 70 && Mouse.getY() > 440 && Mouse.getY() < 515){

                text_play = new GUIText("<--\"RESUME\"-->", 6, Font_Tahoma, new Vector2f(0.05f, 0.20f), 1f, false);
                text_play.setColour(0.22f,1f,0.078f);

                if (Mouse.isButtonDown(0)){
                    GUIs.remove(BGColorPaused);
                    GameEngineLoop.GameIsPaused = false;
                }
            }else{
                text_play = new GUIText("RESUME", 6, Font_Tahoma, new Vector2f(0.05f, 0.25f), 1f, false);
                text_play.setColour(0f,1f,1f);
            }
            if (Mouse.getX() < 300 && Mouse.getX() > 70 && Mouse.getY() > 340 && Mouse.getY() < 415){

                text_help = new GUIText("<--\"HELP\"-->", 6, Font_Tahoma, new Vector2f(0.05f, 0.35f), 1f, false);
                text_help.setColour(0.22f,1f,0.078f);
                text_play = new GUIText("RESUME", 6, Font_Tahoma, new Vector2f(0.05f, 0.23f), 1f, false);
                text_play.setColour(0f,1f,1f);

            }else{
                text_help = new GUIText("HELP", 5, Font_Tahoma, new Vector2f(0.05f, 0.40f), 1f, false);
                text_help.setColour(0f,1f,1f);
            }
            if (Mouse.getX() < 500 && Mouse.getX() > 70 && Mouse.getY() > 240 && Mouse.getY() < 310) {

                text_returnToReality = new GUIText("<--\"QUIT\"?-->", 3, Font_Tahoma, new Vector2f(0.05f, 0.55f), 1f, false);
                text_returnToReality.setColour(0.22f,1f,0.078f);

                if (Mouse.isButtonDown(0)){
                    System.exit(0);
                }
            }else{
                text_returnToReality = new GUIText("QUIT?", 3, Font_Tahoma, new Vector2f(0.05f, 0.55f), 1f, false);
                text_returnToReality.setColour(0f,1f,1f);
            }

            text_1 = new GUIText("PRE-ALPHA (Henrik's Build)", 2, Font_Tahoma, new Vector2f(0.001f, 0.001f), 1f, false);
            text_1.setColour(0.1f,1f,0.1f);

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
        }

    }
}
