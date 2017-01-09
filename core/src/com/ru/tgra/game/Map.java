package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.motion.LinearMotion;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.SphereGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

import java.util.ArrayList;

/**
 * Created by johan on 2016-10-21.
 */
public class Map {
    private ArrayList<MeshModel> models;
    private Shader shader;
    private ArrayList<Texture> textures;
    private Camera cam;

    private Point3D ringPos1;
    private Point3D ringPos2;
    private Point3D ringPos3;
    private Point3D ringPos4;
    private Point3D ringPos5;
    private Point3D ringPos6;
    private Point3D ringPos7;
    private Point3D ringPos8;

    LinearMotion astronautMotion;
    Point3D modelPos;



    float currentTime;
    boolean firstFrame = true;


    public Map(ArrayList<MeshModel> models, Shader shader, ArrayList<Texture> textures, Camera cam, int level) {
        this.models = models;
        this.shader = shader;
        this.textures = textures;
        this.cam = cam;

        if (level == 1) {
            ringPos1 = new Point3D(-2.0f, 1.0f, -10.0f);
            ringPos2 = new Point3D(-1.0f, 4.0f, -16.0f);
            ringPos3 = new Point3D(2.0f, 3.0f, -20.0f);
            ringPos4 = new Point3D(-2.0f, 2.0f, -25.0f);
            ringPos5 = new Point3D(-7.0f, 2.0f, -23.0f);
            ringPos6 = new Point3D(-16.0f, 2.0f, -23.0f);
            ringPos7 = new Point3D(-20.0f, 8.0f, -26.0f);
            ringPos8 = new Point3D(-1.0f, 4.0f, -23.0f);

            astronautMotion = new LinearMotion(new Point3D(-20, 20, -16), new Point3D(50, -50, -25), 2.0f, 14.0f);
            modelPos = new Point3D();
        }
        if (level == 2 || level == 3 || level == 4 || level == 5) {
            ringPos1 = new Point3D(0.0f, 4.0f, -3.0f);
            ringPos2 = new Point3D(0.0f, 6.0f, -16.0f);
            ringPos3 = new Point3D(2.0f, 10.0f, -22.0f);
            ringPos4 = new Point3D(8.0f, 15.0f, -32.0f);
            ringPos5 = new Point3D(16.0f, 16.0f, -30.0f);
            ringPos6 = new Point3D(21.0f, 16.0f, -26.0f);
            ringPos7 = new Point3D(26.0f, 12.0f, -10.0f);
            ringPos8 = new Point3D(-12.0f, -4.0f, -23.0f);
        }



    }

    public void draw(float angle, int level) {
        //float deltaTime = Gdx.graphics.getDeltaTime();
        //float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
        //float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

        if (firstFrame) {
            currentTime = 0.0f;
            firstFrame = false;
        }
        else {
            currentTime += Gdx.graphics.getRawDeltaTime();
        }


        if (level == 1) {
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addScale(50.0f, 50.0f, 50.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(0), null, null);
            ModelMatrix.main.popMatrix();


            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

            shader.setSpotDirection(0, 0, 0, 0.0f);
            //shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
            shader.setSpotExponent(0.0f);
            shader.setConstantAttenuation(1.0f);
            shader.setLinearAttenuation(0.00f);
            shader.setQuadraticAttenuation(0.00f);

            //shader.setLightColor(s2, 0.4f, c2, 1.0f);
            //shader.setLightPosition(1, 1, 1, 0);
            shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
            shader.setLightPosition(5.0f, .0f, -5.0f, 0);
            shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);

            //shader.setMaterialDiffuse(s, 0.4f, c, 1.0f);
            shader.setMaterialDiffuse(0.0f, 0.0f, 0.0f, 1.0f);
            shader.setMaterialSpecular(0.2f, 0.2f, 0.2f, 1.0f);
            //shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
            shader.setMaterialEmission(0, 0, 0, 1);
            shader.setShininess(50.0f);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);

            //Moon
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(-5.0f, 10.0f, -6.0f);
            ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
            ModelMatrix.main.addRotation(angle / 6, new Vector3D(0, 1, 0));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(1), null, null);
            ModelMatrix.main.popMatrix();

            //Earth
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(-14.0f, 1.0f, -15.0f);
            ModelMatrix.main.addScale(6f, 6f, 6f);
            ModelMatrix.main.addRotation(angle / 6, new Vector3D(0, 1, 0));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(2), null, null);
            ModelMatrix.main.popMatrix();


            //Mars
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(10.0f, 1.0f, -10.0f);
            ModelMatrix.main.addScale(2f, 2f, 2f);
            ModelMatrix.main.addRotation(angle / 6, new Vector3D(0, 1, 0));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(8), null, null);
            ModelMatrix.main.popMatrix();

            //Jupiter
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(25.0f, 20.0f, -35.0f);
            ModelMatrix.main.addScale(10f, 10f, 10f);
            ModelMatrix.main.addRotation(angle / 6, new Vector3D(0, 1, 0));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(11), null, null);
            ModelMatrix.main.popMatrix();

            //Sun
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(-10.0f, 12.0f, -25.0f);
            ModelMatrix.main.addScale(3f, 3f, 3f);
            ModelMatrix.main.addRotation(angle / 6, new Vector3D(0, 1, 0));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(9), null, null);
            ModelMatrix.main.popMatrix();

            //Astronaut
            Gdx.gl.glDisable(GL20.GL_BLEND);
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(s*10, c * 10, -10);
            astronautMotion.getCurrentPos(currentTime, modelPos);
            ModelMatrix.main.addTranslation(modelPos.x, modelPos.y, modelPos.z);
            ModelMatrix.main.addRotation(angle, new Vector3D(1, 0, 0));
            ModelMatrix.main.addScale(0.2f, 0.2f, 0.2f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(4).draw(shader);
            ModelMatrix.main.popMatrix();
            Gdx.gl.glEnable(GL20.GL_BLEND);

            //Ring 1
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos1.x, ringPos1.y, ringPos1.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 2
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos2.x, ringPos2.y, ringPos2.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 3
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos3.x, ringPos3.y, ringPos3.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 4
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos4.x, ringPos4.y, ringPos4.z);
            ModelMatrix.main.addRotationY(50);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 5
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos5.x, ringPos5.y, ringPos5.z);
            ModelMatrix.main.addRotationY(80);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 6
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(-16.0f, 2.0f, -23.0f);
            ModelMatrix.main.addRotationY(80);
            ModelMatrix.main.addRotationX(20);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 7
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(-20.0f, 8.0f, -26.0f);
            ModelMatrix.main.addRotationX(80);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
            shader.setMaterialDiffuse(1.0f, 0, 0, 1.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, null, null, null);
            ModelMatrix.main.popMatrix();

        }

        if (level == 2) {
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addScale(50.0f, 50.0f, 50.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, textures.get(10), null, null);
            ModelMatrix.main.popMatrix();

            //Draw level 2 stuff...
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

            shader.setSpotDirection(0, 0, 0, 0.0f);
            //shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
            shader.setSpotExponent(0.0f);
            shader.setConstantAttenuation(1.0f);
            shader.setLinearAttenuation(0.00f);
            shader.setQuadraticAttenuation(0.00f);

            //shader.setLightColor(s2, 0.4f, c2, 1.0f);
            //shader.setLightPosition(1, 1, 1, 0);
            shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
            shader.setLightPosition(5.0f, .0f, -5.0f, 0);
            shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);

            //shader.setMaterialDiffuse(s, 0.4f, c, 1.0f);
            shader.setMaterialDiffuse(0.0f, 0.0f, 0.0f, 1.0f);
            shader.setMaterialSpecular(0.2f, 0.2f, 0.2f, 1.0f);
            //shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
            shader.setMaterialEmission(0, 0, 0, 1);
            shader.setShininess(50.0f);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);

            //Figure 1
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(3.0f, 15.0f, -15.0f);
            ModelMatrix.main.addScale(0.8f, 0.8f, 0.8f);
            ModelMatrix.main.addRotation(angle, new Vector3D(1, 1, -1));
            shader.setEmissionTexture(null);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(0).draw(shader);
            ModelMatrix.main.popMatrix();

            //test model
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(13.0f, 9.0f, -20.0f);
            ModelMatrix.main.addScale(1.0f, 1.0f, 1.0f);
            ModelMatrix.main.addRotation(angle, new Vector3D(1, 1, -1));
            shader.setEmissionTexture(null);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(1).draw(shader);
            ModelMatrix.main.popMatrix();

            //Dice
            ModelMatrix.main.pushMatrix();
            //ModelMatrix.main.addTranslation(0.0f + c * 10.0f, 1.0f, 1.5f + s * 10.0f);
            ModelMatrix.main.addTranslation(-4.0f, 1.0f, -20.0f);
            ModelMatrix.main.addScale(0.8f, 0.8f, 0.8f);
            ModelMatrix.main.addRotation(angle, new Vector3D(1, 1, -1));
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            BoxGraphic.drawSolidCube(shader, textures.get(3), null, null);
            ModelMatrix.main.popMatrix();

            //Ring 1
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos1.x, ringPos1.y, ringPos1.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 2
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos2.x, ringPos2.y, ringPos2.z);
            ModelMatrix.main.addRotationX(10);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();


            //Ring 3
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos3.x, ringPos3.y, ringPos3.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            ModelMatrix.main.addRotationX(20);
            ModelMatrix.main.addRotationY(-20);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 4
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos4.x, ringPos4.y, ringPos4.z);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            ModelMatrix.main.addRotationX(20);
            ModelMatrix.main.addRotationY(-40);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 5
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos5.x, ringPos5.y, ringPos5.z);
            ModelMatrix.main.addRotationY(45);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 6
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos6.x, ringPos6.y, ringPos6.z);
            ModelMatrix.main.addRotationX(10);
            ModelMatrix.main.addRotationY(45);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.popMatrix();

            //Ring 7
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(ringPos7.x, ringPos7.y, ringPos7.z);
            ModelMatrix.main.addRotationX(40);
            ModelMatrix.main.addScale(0.4f, 0.4f, 0.4f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            models.get(2).draw(shader);
            ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
            shader.setMaterialDiffuse(1.0f, 0, 0, 1.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            SphereGraphic.drawSolidSphere(shader, null, null, null);
            ModelMatrix.main.popMatrix();
        }



    }

    public Point3D getRingPos(int n) {
        if (n == 1) {
            return ringPos1;
        }
        else if (n == 2) {
            return ringPos2;
        }
        else if (n == 3) {
            return ringPos3;
        }
        else if (n == 4) {
            return ringPos4;
        }
        else if (n == 5) {
            return ringPos5;
        }
        else if (n == 6) {
            return ringPos6;
        }
        else {
            return ringPos7;
        }
    }
}
