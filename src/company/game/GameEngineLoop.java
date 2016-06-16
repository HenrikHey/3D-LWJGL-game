//Henrik Hey
//Last revised Feb 25 2016
//3D Java Game Engine
package company.game;

import company.GUIs.GUIRenderer;
import company.fontRendering.TextMaster;
import company.levels.Level1;
import company.renderer.DisplayManager;
import company.renderer.Loader;
import org.lwjgl.opengl.Display;

public class GameEngineLoop 
{
    //is used to check if the menu is open
    public static boolean menuIsOpen = true, GameIsPaused = false;

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        GUIRenderer guiRenderer = new GUIRenderer(loader);
        TextMaster.init(loader);

        //main game loop
        while(!Display.isCloseRequested()) {
            //creates menu
            //MainMenu.CreateMenu(loader, guiRenderer);
            //loads the according levels
            Level1.loadLevel1(loader, guiRenderer);

            //TODO: rest of level coding.

        }
        //cleans up all the remaining data
        loader.cleanUP();
        guiRenderer.cleanUp();
        DisplayManager.destroyDisplay();
    }
}
