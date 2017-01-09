package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.ru.tgra.game.Spaceship;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.Vector3D;

/**
 * Created by johan on 2016-10-20.
 */
public class Particle {
    float timeToLive;
    Point3D position;
    Vector3D velocity;
    Texture emissionTexture;
    Texture emissionTexture2;
    Texture alphaTexture;
    ParticleEmitter emitter;
    Vector3D direction;
    Vector3D v;
    float size;
    float rotationAngle;

    public Particle(float size, Point3D position, Vector3D velocity,
                    float timeToLive, float rotationAngle, Texture emissionTexture, Texture emissionTexture2,
                    Texture alphaTexture, ParticleEmitter emitter) {
        this.size = size;
        this.position = position;
        this.velocity = velocity;
        this.emitter = emitter;
        this.timeToLive = timeToLive;
        this.emissionTexture = emissionTexture;
        this.emissionTexture2 = emissionTexture2;
        this.alphaTexture = alphaTexture;
        this.rotationAngle = rotationAngle;

    }

    public boolean isAlive() {
        return timeToLive > 0;
    }


    public void update(float deltaTime) {
        if(isAlive()) {
            v = Vector3D.difference(position, emitter.position);
            //v.normalize();
            //emitter.direction.normalize();
            direction = new Vector3D(emitter.direction.x - v.x, emitter.direction.y - v.y, emitter.direction.z - v.z);
            //direction = new Vector3D(v.x - emitter.direction.x, v.y - emitter.direction.y, v.z - emitter.direction.z);
            position.x += velocity.x * deltaTime * direction.x * 0.998;
            position.y += velocity.y * deltaTime * direction.y * 0.998;
            position.z +=  velocity.z * deltaTime * direction.z * 0.998;
            timeToLive -= deltaTime;

            //System.out.println(position.x);
        }
    }

    public void draw(Shader shader, float boost, int type) {

        if(isAlive()) {
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(position.x, position.y, position.z);
            ModelMatrix.main.addScale(size, size, size);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            if(boost <= 0) {
                SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
            }
            if(boost > 0 && type == 1) {
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);

            }
            ModelMatrix.main.addRotationY(rotationAngle);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            if(boost <= 0) {
                SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
            }
            if(boost > 0 && type == 1) {
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
            }
            ModelMatrix.main.addRotationX(rotationAngle);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            if(boost <= 0) {
                SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
            }
            if(boost > 0 && type == 1) {
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);

            }
            ModelMatrix.main.addRotationZ(rotationAngle);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            if(boost <= 0) {
                SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
            }
            if(boost > 0) {
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);
                SpriteGraphic.drawSprite(shader, emissionTexture2, alphaTexture);

            }
            ModelMatrix.main.popMatrix();

            //System.out.println("PARTICLE: " + position.x + ", " + position.y + ", " + position.z);

        }
    }
}
