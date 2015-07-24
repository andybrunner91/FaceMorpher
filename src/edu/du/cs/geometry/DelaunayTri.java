package edu.du.cs.geometry;


import java.awt.Graphics;
import java.util.ArrayList;


/**
 * This class is a Delaunay Triangulation.  It represents the triangulation 
 * by an ArrayList of Triangles, m_triangulation.  It also has a list of all of it's vertices, m_points.
 * @author Andy Brunner
 *
 */
public class DelaunayTri {
	
	private ArrayList<Triangle> m_triangulation;
	private ArrayList<Point3> m_points;
	

	public DelaunayTri()
	{
		 m_triangulation = new ArrayList<Triangle>();
		 m_points = new ArrayList<Point3>();
		
	}
	
	/**
	 * Adds newPoint to m_points, and calculates a new Delaunay triangulation given this new point
	 * @param newPoint
	 */
	public void addPoint(Point3 newPoint)
	{
		m_triangulation.clear();
		m_points.add(newPoint);
		int n = m_points.size();
		int i,j,k,m;
		long xn,yn,zn;
		boolean flag;
		for ( i = 0; i < n ; i++ )
		{
			m_points.get(i).setZ(m_points.get(i).getX()*m_points.get(i).getX() + m_points.get(i).getY()*m_points.get(i).getY());
		}
		for ( i = 0; i < n - 2; i++ )
		{
			for ( j = i + 1; j < n; j++ )
			{
				for ( k = i + 1; k < n; k++ )
				{
					if ( j != k ) 
					{
						xn = (m_points.get(j).getY()-m_points.get(i).getY())*(m_points.get(k).getZ()-m_points.get(i).getZ()) - (m_points.get(k).getY()-m_points.get(i).getY())*(m_points.get(j).getZ()-m_points.get(i).getZ());
						yn = (m_points.get(k).getX()-m_points.get(i).getX())*(m_points.get(j).getZ()-m_points.get(i).getZ()) - (m_points.get(j).getX()-m_points.get(i).getX())*(m_points.get(k).getZ()-m_points.get(i).getZ());
						zn = (m_points.get(j).getX()-m_points.get(i).getX())*(m_points.get(k).getY()-m_points.get(i).getY()) - (m_points.get(k).getX()-m_points.get(i).getX())*(m_points.get(j).getY()-m_points.get(i).getY());
						flag = zn < 0;
						if ( flag )
						{
							for (m = 0; m < n; m++)
							{
								flag = flag && (((m_points.get(m).getX()-m_points.get(i).getX())*xn + (m_points.get(m).getY()-m_points.get(i).getY())*yn + (m_points.get(m).getZ()-m_points.get(i).getZ())*zn) <= 0);
							}
							if (flag) 
							{
								Triangle newTriangle = new Triangle(m_points.get(i).to2D(), m_points.get(j).to2D(), m_points.get(k).to2D());
								int[] theOrderPoints = new int[3];
								theOrderPoints[0] = i;
								theOrderPoints[1] = j;
								theOrderPoints[2] = k;
								newTriangle.setOrderPoints(theOrderPoints);
								m_triangulation.add(newTriangle);
							}
						}
					}
				}
			}
		}
	}
	public ArrayList<Triangle> getTriangulation()
	{
		return m_triangulation;
	}
	
	
	
	public ArrayList<Point3> getPoints()
	{
		return m_points;
	}
	
	/**
	 * Paints the triangulation onto Graphics g
	 * @param g
	 */
	public void paintTri(Graphics g)
	{
		for(int i = 0; i < m_triangulation.size(); i++)
		{
			Triangle currentTri = m_triangulation.get(i);
			int[] xs = currentTri.getXPoints();
			int[] ys = currentTri.getYPoints();

			g.drawLine(xs[0],ys[0],xs[1],ys[1]);
			g.drawLine(xs[1],ys[1],xs[2],ys[2]);
			g.drawLine(xs[2],ys[2],xs[0],ys[0]);
			
		}
	}
	
	/**
	 * Clears the m_points and m_triangulation.
	 */
	public void reset()
	{
		m_triangulation.clear();
		m_points.clear();
	}
	
	
	/**
	 * Adds triangle t to m_triangulation.
	 * @param t
	 */
	public void addTriangle(Triangle t)
	{
		m_triangulation.add(t);
	}
	
}
