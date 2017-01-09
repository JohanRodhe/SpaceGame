package com.ru.tgra.game;


import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.motion.BSplineMotion;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

import javax.swing.*;

public class SpaceRaceGame extends ApplicationAdapter implements InputProcessor {

	Shader shader;

	private float angle;
	private float boost;
	private float life;
	private int level;
	private Point3D startPos;

	private Camera cam;
	private Camera orthoCam;

	private float fov = 90.0f;

	private ArrayList<MeshModel> models;
	private ArrayList<Texture> textures;

	private Texture moonTex;
	private Texture diceTex;
	private Texture earthTex;
	private Texture marsTex;
	private Texture sunTex;
	private Texture alphaTex;
	private Texture specTex;
	private Texture skyboxTex;
	private Texture skyboxTex2;
	private Texture particleTex;
	private Texture boostTex;
	private Texture jupiterTex;
	private Texture particleTex2;
	private Texture particleTex3;

	ParticleEmitter particleEmitter;
	ParticleEmitter sun;
	ParticleEmitter particleEmitter2;
	ParticleEmitter particleEmitter3;
	ParticleEmitter particleEmitter4;

	private Point3D sunPos;
	private Point3D sunPos2;
	private Point3D sunPos3;

	BSplineMotion cameraMotion;
	Point3D cameraPos;
	ArrayList<Point3D> controlPoints;

	BSplineMotion cameraMotion2;
	Point3D cameraPos2;
	ArrayList<Point3D> controlPoints2;

	private Spaceship ship;
	private Map map1;
	private Point3D mapCenter;

	float currentTime;
	boolean firstFrame = true;

	private Point3D pos;

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);

		shader = new Shader();

		moonTex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		alphaTex = new Texture(Gdx.files.internal("textures/alpha1.png"));
		diceTex = new Texture(Gdx.files.internal("textures/dice.png"));
		skyboxTex = new Texture(Gdx.files.internal("textures/maxresdefault.jpg"));
		skyboxTex2 = new Texture(Gdx.files.internal("textures/skybox4.png"));
		earthTex = new Texture(Gdx.files.internal("textures/earth_diffuse.jpg"));
		marsTex = new Texture(Gdx.files.internal("textures/mars.jpg"));
		sunTex = new Texture(Gdx.files.internal("textures/sun.jpg"));
		particleTex = new Texture(Gdx.files.internal("textures/emissionTex3.png"));
		boostTex = new Texture(Gdx.files.internal("textures/emissionTex4.png"));
		jupiterTex = new Texture(Gdx.files.internal("textures/jupiter.jpg"));
		particleTex2 = new Texture(Gdx.files.internal("textures/particle2.png"));
		particleTex3 = new Texture(Gdx.files.internal("textures/particle3.png"));


		models = new ArrayList<MeshModel>();
		models.add(G3DJModelLoader.loadG3DJFromFile("figure1.g3dj"));
		models.add(G3DJModelLoader.loadG3DJFromFile("testModel.g3dj"));
		models.add(G3DJModelLoader.loadG3DJFromFile("Ring.g3dj"));
		models.add(G3DJModelLoader.loadG3DJFromFile("spaceship.g3dj"));
		models.add(G3DJModelLoader.loadG3DJFromFile("astronaut.g3dj"));
		textures = new ArrayList<Texture>();
		//Level 1
		textures.add(skyboxTex); //0
		textures.add(moonTex);	//1
		textures.add(earthTex);	//2
		textures.add(diceTex);	//3
		textures.add(specTex);	//4
		textures.add(alphaTex);	//5
		textures.add(particleTex);	//6
		textures.add(boostTex);	//7
		textures.add(marsTex);	//8
		textures.add(sunTex);	//9

		level = 1;

		//Level 2
		textures.add(skyboxTex2);	//10
		textures.add(jupiterTex);	//11


		textures.add(particleTex2);	//12
		textures.add(particleTex3);	//13
		begin();
	}

	private void begin() {

		life = 1024;
		boost = 0;

		sunPos = new Point3D(-10.0f, 12.0f, -25.0f);
		sunPos2 = new Point3D(10.0f, 20.0f, -20.0f);
		sunPos3 = new Point3D(0.0f, -5.0f, -10.0f);


		BoxGraphic.create();
		SphereGraphic.create();
		SpriteGraphic.create();

		ModelMatrix.main = new ModelMatrix();

		ship = new Spaceship(new Point3D(0f, 4f, 0f), new Vector3D(-1, 0, -1), models.get(3));

		particleEmitter = new ParticleEmitter(new Point3D(ship.getPosition().x, ship.getPosition().y,
				ship.getPosition().z), textures.get(6), textures.get(7), 0.08f, 0.5f, 0.008f, 0.3f);

		if (level == 1) {
			sun = new ParticleEmitter(sunPos, textures.get(6), textures.get(6), 10.0f, 3.0f, 0.2f, 0.5f);
			mapCenter = new Point3D(0, 4, -22);

			//Level 1 map animation
			controlPoints = new ArrayList<Point3D>();
			controlPoints.add(new Point3D(0.0f, 4.0f, 1.5f)); //P1
			controlPoints.add(new Point3D(10.0f, 10.0f, -10.0f)); // P2
			controlPoints.add(new Point3D(10.0f, 8.0f, -20.0f)); // P3
			controlPoints.add(new Point3D(4.0f, 4.0f, -25.0f)); // P4 / P1
			controlPoints.add(new Point3D(-10.0f, 4.0f, -22.0f)); // P3
			controlPoints.add(new Point3D(-20.0f, 10.0f, -20.0f)); // P4 / P1
			controlPoints.add(new Point3D(-8.0f, 10.0f, 4.0f)); // P3
			controlPoints.add(new Point3D(0.0f, 4.0f, 1.5f)); // P4 / End
			cameraMotion = new BSplineMotion(controlPoints, 1.0f, 15.0f);
			cameraPos = new Point3D();
		}
		if (level == 2) {
			particleEmitter2 = new ParticleEmitter(new Point3D(-5.0f, 5.0f, -5.0f), textures.get(7), textures.get(7),
					10.0f, 3.0f, 0.2f, 0.5f);
			particleEmitter3 = new ParticleEmitter(new Point3D(0.0f, 5.0f, -10.0f), textures.get(12), textures.get(12),
					8.0f, 4.0f, 0.1f, 0.8f);
			particleEmitter4 = new ParticleEmitter(new Point3D(-5.0f, 5.0f, -5.0f), textures.get(13), textures.get(13),
					10.0f, 3.0f, 0.2f, 0.5f);
			mapCenter = new Point3D(0, 4, -22);

			//Level 2 map animation
			controlPoints2 = new ArrayList<Point3D>();
			controlPoints2.add(new Point3D(0.0f, 4.0f, 1.5f)); //P1
			controlPoints2.add(new Point3D(-15.0f, 12.0f, -10.0f)); // P2
			controlPoints2.add(new Point3D(-15.0f, 12.0f, -20.0f)); // P3
			controlPoints2.add(new Point3D(-6.0f, 16.0f, -28.0f)); // P4 / P1
			controlPoints2.add(new Point3D(15.0f, 16.0f, -28.0f)); // P3
			controlPoints2.add(new Point3D(25.0f, 16.0f, -20.0f)); // P4 / P1
			controlPoints2.add(new Point3D(8.0f, 13.0f, 4.0f)); // P3
			controlPoints2.add(new Point3D(0.0f, 4.0f, 1.5f)); // P4 / End
			cameraMotion2 = new BSplineMotion(controlPoints2, 1001.0f, 1015.0f);
			cameraPos2 = new Point3D();
			mapCenter = new Point3D(0, 4, -25);


		}




		startPos = new Point3D(0f, 4.0f, 1.5f);

		cam = new Camera();
		cam.look(startPos, new Point3D(ship.getPosition().x, ship.getPosition().y, ship.getPosition().z),
				new Vector3D(0,1,0));

		orthoCam = new Camera();
		orthoCam.orthographicProjection(0, Gdx.graphics.getWidth(), 0,
				Gdx.graphics.getHeight(), -5.0f, 5);
		orthoCam.look(new Point3D(0, 0, 0), new Point3D(0, 0, -1), new Vector3D(0, 1, 0));

		map1 = new Map(models, shader, textures, cam, level);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}

	private void input()
	{

	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		angle += 180.0f * deltaTime;
		if (firstFrame) {
			currentTime = 0.0f;
			firstFrame = false;
		}
		else {
			if (Gdx.graphics.getRawDeltaTime() > 0.5) {
				currentTime -= Gdx.graphics.getRawDeltaTime();
			}
			currentTime += Gdx.graphics.getRawDeltaTime();
		}
		if ((currentTime > 15 && currentTime < 999) || (currentTime > 1015 && currentTime < 1999)) {

			life -= 130 * deltaTime;

			if (Gdx.input.isKeyPressed(Input.Keys.R)) {
				cam.slide(0, 1.0f * deltaTime, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.F)) {
				cam.slide(0, -1.0f * deltaTime, 0);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				cam.roll(-90.0f * deltaTime);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				cam.roll(90.0f * deltaTime);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				cam.pitch(90.0f * deltaTime);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				cam.pitch(-90.0f * deltaTime);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.T)) {
				fov -= 30.0f * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.G)) {
				fov += 30.0f * deltaTime;
			}

			ship.update(cam, boost);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		}
		if (currentTime < 15) {
			cameraMotion.getCurrentPos(currentTime, cameraPos);
			cam.eye.x = cameraPos.x;
			cam.eye.y = cameraPos.y;
			cam.eye.z = cameraPos.z;
			cam.look(cam.eye, mapCenter, new Vector3D(0,1,0));
		}
		if (currentTime > 1001 && currentTime < 1015) {
			cameraMotion2.getCurrentPos(currentTime, cameraPos2);
			cam.eye.x = cameraPos2.x;
			cam.eye.y = cameraPos2.y;
			cam.eye.z = cameraPos2.z;
			cam.look(cam.eye, mapCenter, new Vector3D(0,1,0));
		}


		pos = new Point3D(cam.getDir().x * 1.5f + cam.eye.x, cam.getDir().y * 1.5f + cam.eye.y,
					cam.getDir().z * 1.5f + cam.eye.z);
		particleEmitter.update(deltaTime, cam, pos);

		if (level == 1) {
			sun.update(deltaTime, cam, sunPos);

		}
		if (level == 2) {
			particleEmitter2.update(deltaTime, cam, sunPos);
			particleEmitter3.update(deltaTime, cam, sunPos2);
			particleEmitter4.update(deltaTime, cam, sunPos3);
		}

		if (inRing()) {
			boost = 6.0f;
			life += 30;
		}

		if (boost > 0) {
			boost -= 5 * deltaTime;
		}

		if (life <= 0) {
			JOptionPane.showMessageDialog(null, "Time out!");
			begin();
		}


		if (finish()) {
			if (level == 2) {
				JOptionPane.showMessageDialog(null, "Congratulations, You finished the game!");
				Gdx.app.exit();
			}
			level++;
			JOptionPane.showMessageDialog(null, "Level " + level + "!");
			currentTime = 1000.0f;

			begin();
		}
	}
	
	private void display() {
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.perspectiveProjection(fov, (float) Gdx.graphics.getWidth() / (float) (2 * Gdx.graphics.getHeight()), 0.2f, 100.0f);
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
		shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);


		ModelMatrix.main.loadIdentityMatrix();

		map1.draw(angle, level);
		if ((currentTime > 15 && currentTime < 999) || (currentTime > 1015 && currentTime < 1999)) {
			ship.draw(shader, pos);
			particleEmitter.draw(shader, boost, 1);

		}
		if ((currentTime < 15 && currentTime > 0) || (currentTime < 1015 && currentTime > 1000)) {
			ship.draw(shader, new Point3D(0, 4, 0));
		}

		if (level == 1) {
			sun.draw(shader, boost, 2);

		}
		if (level == 2) {
			particleEmitter2.draw(shader, boost, 2);
			particleEmitter3.draw(shader, boost, 2);
			particleEmitter4.draw(shader, boost, 2);
		}



		//Orthographic Projection
		shader.setViewMatrix(orthoCam.getViewMatrix());
		shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
		shader.setEyePosition(0, 0, 0, 1.0f);

		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);

		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.pushMatrix();
		shader.setMaterialDiffuse(0f, 1.0f, 0, 1.0f);
		ModelMatrix.main.addTranslation(25, 0, 0);
		ModelMatrix.main.addScale(50, life, 1);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube(shader, null, null, null);
		ModelMatrix.main.popMatrix();


		Gdx.gl.glDisable(GL20.GL_BLEND);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);


	}


	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}
	private boolean finish() {
		if (Math.abs(map1.getRingPos(7).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(7).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(7).z - pos.z) < 0.5f) {
			return true;
		}
		return false;
	}

	private boolean inRing() {
		if (Math.abs(map1.getRingPos(1).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(1).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(1).z - pos.z) < 0.5f) {
			return true;
		}
		if (Math.abs(map1.getRingPos(2).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(2).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(2).z - pos.z) < 0.5f) {
			return true;
		}
		if (Math.abs(map1.getRingPos(3).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(3).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(3).z - pos.z) < 0.5f) {
			return true;
		}
		if (Math.abs(map1.getRingPos(4).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(4).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(4).z - pos.z) < 0.5f) {
			return true;
		}
		if (Math.abs(map1.getRingPos(5).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(5).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(5).z - pos.z) < 0.5f) {
			return true;
		}
		if (Math.abs(map1.getRingPos(6).x - pos.x) < 0.5f && Math.abs(map1.getRingPos(6).y - pos.y) < 0.5f &&
				Math.abs(map1.getRingPos(6).z - pos.z) < 0.5f) {
			return true;
		}


		return false;
	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}