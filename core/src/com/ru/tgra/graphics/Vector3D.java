package com.ru.tgra.graphics;

public class Vector3D {

	public float x;
	public float y;
	public float z;

	public Vector3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void scale(float S)
	{
		x *= S;
		y *= S;
		z *= S;
	}

	public void add(Vector3D v2)
	{
		x += v2.x;
		y += v2.y;
		z += v2.z;
	}

	public static Vector3D scale(Vector3D v, float s) {
		return new Vector3D(v.x *= s, v.y *= s, v.z *= s);

	}

	public float dot(Vector3D v2)
	{
		return x*v2.x + y*v2.y + z*v2.z;
	}

	public float dotSelf()
	{
		return x*x + y*y + z*z;
	}

	public float length()
	{
		return (float)Math.sqrt(dotSelf());
	}

	public void normalize()
	{
		float len = length();
		x = x / len;
		y = y / len;
		z = z / len;
	}

	public Vector3D cross(Vector3D v2)
	{
		return new Vector3D(y*v2.z - z*v2.y, z*v2.x - x*v2.z, x*v2.y - y*v2.x);
	}

	public static Vector3D difference(Point3D P2, Point3D P1)
	{
		return new Vector3D(P2.x-P1.x, P2.y-P1.y, P2.z-P1.z);
	}
}
