package company.entities.PlayerWorks;

import company.entities.Camera;
import company.renderer.DisplayManager;

public class PlayerMechanics {

    private static double headbobCounter = 0;
    private static boolean headBobbedUp = false;

    public static void headBob(Camera camera) {
        if(Player.isMoving) {
            if(headbobCounter <= 2 && !headBobbedUp) {
                headbobCounter+=0.5;
                camera.pitch += (0.000 * -1) * DisplayManager.getFrameTimeSeconds();
            }else{
                headBobbedUp = true;
                headbobCounter-=0.5;
                camera.pitch += (0.000 * 1)* DisplayManager.getFrameTimeSeconds();
                if(headbobCounter<=0) {
                    headBobbedUp = false;
                }
            }
        } else {
            if(headbobCounter <= 1 && !headBobbedUp) {
                headbobCounter+=0.15;
                camera.pitch += (0.0000 * -1)* DisplayManager.getFrameTimeSeconds();
            }else{
                headBobbedUp = true;
                headbobCounter-=0.15;
                camera.pitch += (0.0000 * 1)* DisplayManager.getFrameTimeSeconds();
                if(headbobCounter<=0) {
                    headBobbedUp = false;
                }
            }
        }
    }

}
