package edu.du.cs.geometry;
import java.awt.geom.Point2D;

/**
 * Triangle class that holds 3 points, and the triangle's orientation
 * @author Andy Brunner
 *
 */
public class Triangle {
	
	enum Orientation {CCW, CW};
	private Point2D[] m_Points = new Point2D[3];
	private Orientation m_orient = Orientation.CW;
	
	//each triangle will be made of 3 points, each of which will be in an array list
	//of all points in a Delaunay Triangulation
	//orderPoints holds the index of each point in that arraylist
	private int[] orderPoints;
	
	public Triangle(Point2D[] somePoints)
	{
		for(int i = 0; i < 3; i++)
		{
			m_Points[i] = somePoints[i];
		}
	}
	
	public Triangle(Point2D[] somePoints, Orientation someOrient)
	{
		for(int i = 0; i < 3; i++)
		{
			m_Points[i] = somePoints[i];
		}
		m_orient = someOrient;
	}
	
	public Triangle(Point2D point2d, Point2D point2d2, Point2D point2d3)
	{
		m_Points[0] = point2d;
		m_Points[1] = point2d2;
		m_Points[2] = point2d3;
	}

	public Orientation getOrient() {
		return m_orient;
	}

	public void setOrient(Orientation m_orient) {
		this.m_orient = m_orient;
	}

	public Point2D[] getPoints() {
		return m_Points;
	}

	public void setPoints(Point2D[] m_Points) {
		this.m_Points = m_Points;
	}
	
	public int[] getXPoints()
	{
		int[] anArray = {(int) m_Points[0].getX(), (int) m_Points[1].getX(), (int) m_Points[2].getX()};
		return anArray;
	}
	
	public int[] getYPoints()
	{
		int[] anArray = {(int) m_Points[0].getY(), (int) m_Points[1].getY(), (int) m_Points[2].getY()};
		return anArray;
	}
	
	public String toString()
	{
		String result = "( " + m_Points[0].getX() + " , " + m_Points[0].getY() + " )";
		result += " ( " + m_Points[1].getX() + " , " + m_Points[1].getY() + " )";
		result += " ( " + m_Points[2].getX() + " , " + m_Points[2].getY() + " )";
		
		return result;

	}
	
	
	/**
	 * Takes point p and converts it from x,y coordinates to barycentric coordinates in this triangle
	 * @param p a Point3 with at least x and y coordinates (the z coordinate will be ignored in this method)
	 * @return float array of size 3 with the alpha, beta, and gamma barycentric coordinates of p
	 */
	public float[] getBary(Point3 p)
	{
		float alpha = (float) (((m_Points[1].getY() - m_Points[2].getY())*(p.getX() - m_Points[2].getX()) + (m_Points[2].getX() - m_Points[1].getX())*(p.getY() - m_Points[2].getY())) /
		        ((m_Points[1].getY() - m_Points[2].getY())*(m_Points[0].getX() - m_Points[2].getX()) + (m_Points[2].getX() - m_Points[1].getX())*(m_Points[0].getY() - m_Points[2].getY())));
		float beta = (float) (((m_Points[2].getY() - m_Points[0].getY())*(p.getX() - m_Points[2].getX()) + (m_Points[0].getX() - m_Points[2].getX())*(p.getY() - m_Points[2].getY())) /
		       ((m_Points[1].getY() - m_Points[2].getY())*(m_Points[0].getX() - m_Points[2].getX()) + (m_Points[2].getX() - m_Points[1].getX())*(m_Points[0].getY() - m_Points[2].getY())));
		float gamma = 1.0f - alpha - beta;
		
		return new float[] {alpha, beta, gamma};
	}
	
	/**
	 * Takes a barycentric coordinate and returns it's x and y values
	 * @param baryCoords float array containing the alpha, beta, and gamma values of this barycentric coordinate
	 * @return a Point3 with the x and y value of baryCoords
	 */
	public Point3 reverseBary(float[] baryCoords)
	{
		int x = (int) (baryCoords[0]*m_Points[0].getX() + baryCoords[1]*m_Points[1].getX() +  baryCoords[2]*m_Points[2].getX());
		int y = (int) (baryCoords[0]*m_Points[0].getY() + baryCoords[1]*m_Points[1].getY() +  baryCoords[2]*m_Points[2].getY());  

		return(new Point3(x,y));
	}

	public int[] getOrderPoints() {
		return orderPoints;
	}

	public void setOrderPoints(int[] orderPoints) {
		this.orderPoints = orderPoints;
	}
	
	

}
