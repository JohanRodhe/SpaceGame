package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

/**
 * Created by johan on 2016-10-21.
 */
public class Spaceship {
    private Point3D position;
    private Vector3D velocity;
    private MeshModel model;
    private Camera cam;
    private float boost;
    ModelMatrix orientation;


    public Spaceship(Point3D position, Vector3D velocity, MeshModel model) {
        this.position = position;
        this.velocity = velocity;
        this.model = model;
        orientation = new ModelMatrix();
        orientation.loadIdentityMatrix();
        orientation.addTranslation(position.x, position.y-4, position.z);
        boost = 0;
    }




    public void update(Camera cam, float boost) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            orientation.addRotationZ(90 * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            orientation.addRotationZ(-90f * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            orientation.addRotationX(90f * deltaTime);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            orientation.addRotationX(-90f * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(boost <= 0) {
                //orientation.addTranslation(0, 0, -1.0f * deltaTime);
                cam.slide(0, 0, -1.5f * deltaTime);
            }
            if(boost > 0) {
                //orientation.addTranslation(0, 0, -3.0f * deltaTime);
                cam.slide(0, 0, -3.0f * deltaTime);
            }
        }
        if (boost > 0) {
            cam.slide(0, 0, -3.0f * deltaTime);
        }

        cam.slide(0, 0, -0.5f * deltaTime);

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            //orientation.addTranslation(0, 0, 1.0f * deltaTime);
            cam.slide(0, 0, 0.5f * deltaTime);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            //orientation.addTranslation(-1.0f * deltaTime, 0, 0);
            cam.slide(-1.0f * deltaTime, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            //orientation.addTranslation(1.0f * deltaTime, 0, 0);
            cam.slide(1.0f * deltaTime, 0, 0);
        }

    }


    public void draw(Shader shader, Point3D pos) {
        /*float s2 = Math.abs((float)Math.sin((angle / 1.312) * Math.PI / 180.0));
        float c2 = Math.abs((float)Math.cos((angle / 1.312) * Math.PI / 180.0));*/


        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(pos.x, pos.y, pos.z);
        ModelMatrix.main.addTransformation(orientation.matrix);
        ModelMatrix.main.addScale(0.05f, 0.05f, 0.05f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        model.draw(shader);
        ModelMatrix.main.popMatrix();

    }

    public Point3D getPosition() {
        return position;
    }

}
