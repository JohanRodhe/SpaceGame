package com.ru.tgra.graphics.shapes;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.Shader;

public class BoxGraphic {

	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	public static void create() {

		//VERTEX ARRAY IS FILLED HERE
		float[] vertexArray = {-0.5f, -0.5f, -0.5f,
						-0.5f, 0.5f, -0.5f,
						0.5f, 0.5f, -0.5f,
						0.5f, -0.5f, -0.5f,
						
						-0.5f, -0.5f, 0.5f,
						-0.5f, 0.5f, 0.5f,
						0.5f, 0.5f, 0.5f,
						0.5f, -0.5f, 0.5f,
						
						-0.5f, -0.5f, -0.5f,
						0.5f, -0.5f, -0.5f,
						0.5f, -0.5f, 0.5f,
						-0.5f, -0.5f, 0.5f,
						
						-0.5f, 0.5f, -0.5f,
						0.5f, 0.5f, -0.5f,
						0.5f, 0.5f, 0.5f,
						-0.5f, 0.5f, 0.5f,
						
						-0.5f, -0.5f, -0.5f,
						-0.5f, -0.5f, 0.5f,
						-0.5f, 0.5f, 0.5f,
						-0.5f, 0.5f, -0.5f,
						
						0.5f, -0.5f, -0.5f,
						0.5f, -0.5f, 0.5f,
						0.5f, 0.5f, 0.5f,
						0.5f, 0.5f, -0.5f};

		vertexBuffer = BufferUtils.newFloatBuffer(72);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, 72);
		vertexBuffer.rewind();


		//NORMAL ARRAY IS FILLED HERE
		float[] normalArray = {0.0f, 0.0f, -1.0f,
							0.0f, 0.0f, -1.0f,
							0.0f, 0.0f, -1.0f,
							0.0f, 0.0f, -1.0f,
							
							0.0f, 0.0f, 1.0f,
							0.0f, 0.0f, 1.0f,
							0.0f, 0.0f, 1.0f,
							0.0f, 0.0f, 1.0f,
							
							0.0f, -1.0f, 0.0f,
							0.0f, -1.0f, 0.0f,
							0.0f, -1.0f, 0.0f,
							0.0f, -1.0f, 0.0f,
							
							0.0f, 1.0f, 0.0f,
							0.0f, 1.0f, 0.0f,
							0.0f, 1.0f, 0.0f,
							0.0f, 1.0f, 0.0f,
							
							-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f,
							-1.0f, 0.0f, 0.0f,
							
							1.0f, 0.0f, 0.0f,
							1.0f, 0.0f, 0.0f,
							1.0f, 0.0f, 0.0f,
							1.0f, 0.0f, 0.0f};

		normalBuffer = BufferUtils.newFloatBuffer(72);
		BufferUtils.copy(normalArray, 0, normalBuffer, 72);
		normalBuffer.rewind();


		//UV TEXTURE COORD ARRAY IS FILLED HERE
		float[] uvArray = {0.3333f, 0.3333f,
							0.3333f, 0.6666f,
							0.6666f, 0.6666f,
							0.6666f, 0.3333f,

							0.6666f, 0.6666f,
							0.6666f, 1.0f,
							1.0f, 1.0f,
							1.0f, 0.6666f,

							0.3333f, 0.6666f,
							0.3333f, 1.0f,
							0.6666f, 1.0f,
							0.6666f, 0.6666f,

							0.3333f, 0.0f,
							0.3333f, 0.3333f,
							0.6666f, 0.3333f,
							0.6666f, 0.0f,

							0.6666f, 0.3333f,
							0.6666f, 0.6666f,
							1.0f, 0.6666f,
							1.0f, 0.3333f,

							0.0f, 0.3333f,
							0.0f, 0.6666f,
							0.3333f, 0.6666f,
							0.3333f, 0.3333f,};

		uvBuffer = BufferUtils.newFloatBuffer(48);
		BufferUtils.copy(uvArray, 0, uvBuffer, 48);
		uvBuffer.rewind();


		//INDEX ARRAY IS FILLED HERE
		short[] indexArray = {0, 1, 2, 0, 2, 3,
							4, 5, 6, 4, 6, 7,
							8, 9, 10, 8, 10, 11,
							12, 13, 14, 12, 14, 15,
							16, 17, 18, 16, 18, 19,
							20, 21, 22, 20, 22, 23};

		indexBuffer = BufferUtils.newShortBuffer(36);
		BufferUtils.copy(indexArray, 0, indexBuffer, 36);
		indexBuffer.rewind();

	}

public static void drawSolidCube(Shader shader, Texture diffuseTexture, Texture alphaTexture, Texture specTexture) {

	shader.setDiffuseTexture(diffuseTexture);
	shader.setAlphaTexture(alphaTexture);
	shader.setSpecTexture(specTexture);
	shader.setEmissionTexture(null);

	Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
	Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
	Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);

	Gdx.gl.glDrawElements(GL20.GL_TRIANGLES, 36, GL20.GL_UNSIGNED_SHORT, indexBuffer);
}

}
