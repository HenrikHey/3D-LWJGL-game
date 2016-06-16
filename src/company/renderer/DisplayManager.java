package company.renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager 
{
    public static int WIDTH = 1280, HEIGHT = 720, FPS = 30;

    private static long lastFrameTime;
    private static float delta;
    
    public static void createDisplay()
    {
        ContextAttribs attribs = new ContextAttribs(3,3)
                .withForwardCompatible(true)
                .withProfileCore(true);

        System.setProperty( "org.lwjgl.opengl.Window.undecorated" , "true");
        try{
            DisplayMode displayMode = null;
            DisplayMode[] modes = Display.getAvailableDisplayModes();

            for (int i = 0; i < modes.length; i++)
            {
                if (modes[i].getWidth() == WIDTH
                        && modes[i].getHeight() == HEIGHT
                        && modes[i].isFullscreenCapable())
                {
                    displayMode = modes[i];
                    WIDTH = displayMode.getWidth();
                    HEIGHT = displayMode.getHeight();
                }
            }

            Display.setDisplayMode(displayMode);
            Display.setTitle("3D game engine V.0.0.1");
            Display.create(new PixelFormat(), attribs);
            Display.setFullscreen(true);
            Display.setVSyncEnabled(true);
        }catch( LWJGLException e){
            System.err.println("ERROR: Could not create window!");
        }
        
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = currentTime();
    }
    
    public static void updateDisplay()
    {
        delta = 0;
        Display.sync(FPS);
        Display.update();
        long currentTimeFrame = currentTime();
        delta = (currentTimeFrame - lastFrameTime)/2000f;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }
    
    public static void destroyDisplay()
    {
        Display.destroy();
    }

    private static long currentTime() {
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }
}
