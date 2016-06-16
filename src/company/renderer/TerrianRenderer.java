package company.renderer;

import company.Terrians.Terrian;
import company.models.RawModel;
import company.shaders.TerrianShader;
import company.textures.TerrainTexturePack;
import company.toolBox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrianRenderer {

    private TerrianShader shader;

    public TerrianRenderer(TerrianShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(List<Terrian> terrians) {
        for(Terrian terrian: terrians) {
            prepareTerrian(terrian);
            loadModelMatrix(terrian);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrian.getModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTerrian();
        }
    }

    private void prepareTerrian(Terrian terrian) {
        RawModel rawModel = terrian.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        bindTextures(terrian);
        shader.loadShineVariables(250, 0);
    }

    private void bindTextures(Terrian terrian) {
        TerrainTexturePack texturePack = terrian.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrian.getBlendMap().getTextureID());
    }

    private void unbindTerrian() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrian terrian) {
        Matrix4f translationMatrix = Maths.createTransformationMatrix(new Vector3f(terrian.getX(), 0, terrian.getZ()),
                0,0,0,1);
        shader.loadTransformationMatrix(translationMatrix);
    }
}
