package edu.du.cs.geometry;


import java.awt.Point;

/**
 * Basic Point class with 3 dimensions
 * @author Andy Brunner
 *
 */
public class Point3 {
	
	private int x, y, z;
	
	public Point3(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Point to2D()
	{
		return new Point(x,y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public void setLocation(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void print()
	{
		System.out.println("( " + this.getX() + " , " + this.getY() + " , " + this.getZ() + " )");
	}
	

}
