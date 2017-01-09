package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.ru.tgra.game.Spaceship;
import com.ru.tgra.graphics.*;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by johan on 2016-10-20.
 */
public class ParticleEmitter {
    Texture texture1;
    Texture texture2;
    Point3D position;
    Vector3D direction;

    private float timeBetweenParticles;
    private float initialTimeToLive;
    private float particleSize;
    private float timeSinceParticle;
    private int maxParticleCount;
    private Random rand;
    private float scale;

    ArrayList<Particle> particles;

    public ParticleEmitter(Point3D position, Texture texture1, Texture texture2, float particleSize,
                           float initialTimeToLive, float timeBetweenParticles, float scale) {

        this.texture1 = texture1;
        this.texture2 = texture2;
        this.position = position;

        this.particleSize = particleSize;
        this.initialTimeToLive = initialTimeToLive;
        this.timeBetweenParticles = timeBetweenParticles;
        this.maxParticleCount = (int)(initialTimeToLive * timeBetweenParticles);
        this.scale = scale;
        rand = new Random();
        direction = new Vector3D(0, 0, 0);

        particles = new ArrayList<Particle>();
    }

    public void update(float deltaTime, Camera cam, Point3D pos) {
        timeSinceParticle += deltaTime;
        position.x = pos.x;
        position.y = pos.y;
        position.z = pos.z;
        direction = new Vector3D(cam.n.x, cam.n.y, cam.n.z);
        //System.out.println("PE: " + pos.x + ", " + pos.y + ", " + pos.z);
        while(timeSinceParticle > timeBetweenParticles) {
            Vector3D particleSpeed = new Vector3D((rand.nextFloat() - 0.5f),
                    (rand.nextFloat() - 0.5f), (rand.nextFloat() - 0.5f));
            particleSpeed.scale(scale);
            timeSinceParticle -= timeBetweenParticles;

            particles.add(new Particle(particleSize,
                    new Point3D(position.x, position.y, position.z),
                    particleSpeed,
                    initialTimeToLive, rand.nextFloat() * 180.0f, texture1, texture2, texture1, this));
        }


        //System.out.println(particles.size());

        while (!particles.isEmpty() && !particles.get(0).isAlive()) {
            particles.remove(0);
        }

        for(int i = 0; i < particles.size(); i++) {
                particles.get(i).update(deltaTime);
        }
    }

    public Point3D getPosition() {
        return position;
    }

    public void draw(Shader shader, float boost, int type) {
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        shader.setMaterialDiffuse(0, 0, 0, 0.3f);
        for(Particle particle : particles) {
            particle.draw(shader, boost, type);
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

    }


}
