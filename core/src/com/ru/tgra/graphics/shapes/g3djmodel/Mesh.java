package com.ru.tgra.graphics.shapes.g3djmodel;

import java.nio.FloatBuffer;
import java.util.Vector;

public class Mesh {
	//public Vector<String> attributes;
	public FloatBuffer vertices;
	public FloatBuffer normals;

	public Mesh()
	{
		vertices = null;
		normals = null;
	}
}
