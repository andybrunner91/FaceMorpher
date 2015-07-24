package edu.du.cs.geometry;
import javax.imageio.ImageIO;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *This class takes two pictures and morphs one into the other using
 *Delaunay Triangulations.  I consulted some of the oracle swing tutorials 
 *to help write this code.  They can be found here http://docs.oracle.com/javase/tutorial/uiswing/examples/painting/index.html#SwingPaintDemo1
 * @author Andy Brunner 
 *
 */

class MyPanel extends JPanel {

//////////////////////////////////////////////////////////////////////////
	//MODIFY THESE VARIABLES TO YOUR HEART'S CONTENT
	
	
	/*paths to the two pictures you want to morph
	they must have exactly the same pixel dimensions
	*/
    private String dir = "FacePics/";  
    private String dir2 = "FacePics/";
    
    /*Number of points you want to be in your Delaunay Triangulation.
    4 will automatically go to the 4 corners of the picture.
    That is, if numDelPoints = 13, then you will be able to click
    on each picture 9 times
    */
    private int numDelPoints = 13;
    
    /*this determines how smooth you want the transformation to be
    It should be a value between 0 and 1.  
    For example, if you want the transformation to be done in 20 steps,
    set granularity to be .05 (i.e. 1/20).
    If you want the transformation to be done in 100 steps, set 
    granularity to be .01 (ie. 1/100).
    */
    private float granularity = (float).05;
    
    /*Pause time is the time in milliseconds you want the transformation to
    pause between iterations
    */
    private int pauseTime = 150;
    
    //Default value should be 1 - granularity;
    private float maxGran = 1 - granularity;
 
//////////////////////////////////////////////////////////////////////////
    
    RedCircle redCircle = new RedCircle();
    DelaunayTri myTri = null;
    DelaunayTri myTri2 = null;
    DelaunayTri middleTri = null;
    BufferedImage image = null;
    BufferedImage image2 = null;
    BufferedImage image3 = null;
    private boolean firstPicDone = false;
    private boolean allDone = false;
    private float totalGran = 0;
    private ArrayList<String> validNames = new ArrayList<String>();
    private static final long serialVersionUID = 1L;
    private int profNum = 17;
    private int taNum = 7;
    private int miscNum = 14;
    private Scanner theScanner = new Scanner(System.in);


    public void printChoices()
    {
    	/*
    	System.out.println("Your choices are: ");
    	for(int i = 0; i < validNames.size(); i++)
    	{
    		System.out.println(i + 1 + " - " + validNames.get(i));
    	}
    	*/
    	
    	
    	System.out.println("FACULTY:");
    	for(int i = 0; i < profNum/2; i++)
    	{
    		System.out.printf("%-30.30s  %-30.30s%n", i + 1 + " - " + validNames.get(i), i + 1 + profNum/2 + " - " + validNames.get(i+profNum/2));
    		if(i == profNum/2 - 1 && profNum % 2 == 1)
    		{
    			System.out.printf("%-30.30s\n",  profNum + " - " + validNames.get(profNum - 1));
    		}
    	}    
    	
    	System.out.println("\nTA's:");
    	for(int i = 0; i < taNum/2; i++)
    	{
    		System.out.printf("%-30.30s  %-30.30s%n", i + profNum + 1 + " - " + validNames.get(i + profNum), i + 1 + profNum + taNum/2 + " - " + validNames.get(i+profNum + taNum/2));
    		if(i == taNum/2 - 1 && taNum % 2 == 1)
    		{
    			System.out.printf("%-30.30s\n",  taNum + profNum + " - " + validNames.get(profNum + taNum - 1));
    		}
    	}  
    	
    	System.out.println("\nMISC:");
    	for(int i = 0; i < miscNum/2; i++)
    	{
    		System.out.printf("%-30.30s  %-30.30s%n", i + profNum + taNum + 1 + " - " + validNames.get(i + profNum + taNum), i + 1 + profNum + taNum + miscNum/2 + " - " + validNames.get(i+profNum + taNum + miscNum/2));
    		if(i == miscNum/2 - 1 && miscNum % 2 == 1)
    		{
    			System.out.printf("%-30.30s\n",  taNum + profNum + miscNum + " - " + validNames.get(profNum + taNum + miscNum - 1));
    		}
    	}  
    	
    	
    }
    
    public String getImageFileName(int index)
    {
    	String secondPicName = "andrei";
    	if(index <= profNum)
    	{
	    	switch(index)
	    	{
	    	case 1:
	    		secondPicName = "andrei";
	    		break;
	    	case 2:
	    		secondPicName = "anneliese";
	    		break;
	    	case 3:
	    		secondPicName = "cathy";
	    		break;
	    	case 4:
	    		secondPicName = "chris";
	    		break;
	    	case 5:
	    		secondPicName = "jeff";
	    		break;
	    	case 6:
	    		secondPicName = "joel";
	    		break;
	    	case 7:
	    		secondPicName = "kimon";
	    		break;
	    	case 8:
	    		secondPicName = "mario";
	    		break;
	    	case 9:
	    		secondPicName = "matt";
	    		break;
	    	case 10:
	    		secondPicName = "mike";
	    		break;
	    	case 11:
	    		secondPicName = "mohammad";
	    		break;
	    	case 12:
	    		secondPicName = "nathan";
	    		break;
	    	case 13:
	    		secondPicName = "ramki";
	    		break;
	    	case 14:
	    		secondPicName = "rinku";
	    		break;
	    	case 15:
	    		secondPicName = "scott";
	    		break;
	    	case 16:
	    		secondPicName = "susan";
	    		break;
	    	case 17:
	    		secondPicName = "susanne";
	    		break;
	    	default:
	    		secondPicName = "mario";
	    		break;
	    	}
    	}
    	else if(index <= profNum + taNum)
    	{
    		switch(index - profNum)
    		{
    			case 1:
    				secondPicName = "andy";
    				break;
    			case 2:
    				secondPicName = "stephen";
    				break;
    			case 3:
    				secondPicName = "zach";
    				break;	
    			case 4:
    				secondPicName = "tanarat";
    				break;
    			case 5:
    				secondPicName = "will";
    				break;
    			case 6:
    				secondPicName = "amanda";
    				break;
    			case 7:
    				secondPicName = "sally";
    				break;
    			//case 8:
    				//secondPicName = "andrew";
    				//break;
    			default:
    				secondPicName = "andy";
    				break;
    		}
    	}
    	
    	else
    	{
    		switch(index - profNum - taNum)
    		{
		    	case 1:
		    		secondPicName = "voronoi";
		    		break;
		    	case 2:
		    		secondPicName = "busey";
		    		break;
		    	case 3:
		    		secondPicName = "cheese";
		    		break;
		    	case 4:
		    		secondPicName = "chewbacca";
		    		break;
		    	case 5:
		    		secondPicName = "delaunay";
		    		break;
		    	case 6:
		    		secondPicName = "elvis";
		    		break;
		    	case 7:
		    		secondPicName = "henryiv";
		    		break;
		    	case 8:
		    		secondPicName = "jesus";
		    		break;
		    	case 9:
		    		secondPicName = "lassie";
		    		break;
		    	case 10:
		    		secondPicName = "napoleon";
		    		break;
		    	case 11:
		    		secondPicName = "nikki";
		    		break;
		    	case 12:
		    		secondPicName = "obama";
		    		break;
		    	case 13:
		    		secondPicName = "sean";
		    		break;
		    	case 14:
		    		secondPicName = "alan";
		    		break;
		    		
		    	default:
		    		secondPicName = "alan";
		    		break;
    		}
    	}
    	
    	return secondPicName;
    		
    }
    public void getInput()
    {
    	
    	printChoices();
    	System.out.println("\nWho would you like to morph into someone else?");
    	System.out.print("Type the number:");
    	
    	int secondPicIndex = -1;
    	boolean secondValid = false;
    	while(!secondValid)
    	{
	    	try{
	    		secondPicIndex = theScanner.nextInt();
	    		secondValid = true;
	    	}
	    	catch (InputMismatchException e)
	    	{
	    		theScanner.next();
	    		System.out.println("Invalid input");
	        	printChoices();
	        	System.out.println("\nWho would you like to morph into someone else?");
	        	System.out.print("Type the number:");
	    	}
    	}
    	dir2 += getImageFileName(secondPicIndex) + ".jpg";
    	
    	
    	printChoices();
    	System.out.println("\nWho would you like to morph that person into?");
    	System.out.print("Type the number:");
    	int firstPicIndex = -1;
    	boolean firstValid = false;
    	while(!firstValid)
    	{
	    	try{
	    		firstPicIndex = theScanner.nextInt();
	    		firstValid = true;
	    	}
	    	catch (InputMismatchException e)
	    	{
	    		theScanner.next();
	    		System.out.println("Invalid input");
	    		System.out.println("\nWho would you like to morph that person into?");
	    		System.out.print("Type the number:");
	        	printChoices();
	    	}
    	}
    	dir += getImageFileName(firstPicIndex) + ".jpg";
    	
    	/*
    	String secondPicName = theScanner.next().toLowerCase();
    	while(!validNames.contains(secondPicName))
    	{
    		System.out.println("Please enter a valid name");
    		secondPicName = theScanner.next();
    	}
    	dir2 += secondPicName + ".jpg";
    	
    	
    	
    	String firstPicName = theScanner.next().toLowerCase();
    	while(!validNames.contains(firstPicName))
    	{
    		System.out.println("Please enter a valid name");
    		firstPicName = theScanner.next();
    	}
    	dir += firstPicName + ".jpg";
    	*/
    	
    	/*System.out.println("Creepy Mode?(y/n)");
    	String creepy = theScanner.next();
    	if(creepy.equals("y") || creepy.equals("Y"))
    	{
    		maxGran = 4;
    	}
    	*/
    	
    	//theScanner.close();
    }
    public void initialize()
    {
    	resetValues();
    	validNames.add("Andrei Roudik");
    	validNames.add("Anneliese Andrews");
    	validNames.add("Cathy Durso");
    	validNames.add("Chris Gauthier Dickey");
    	validNames.add("Jeff Edgington");
    	validNames.add("Joel Cohen");
    	validNames.add("Kimon Valavanis");
    	validNames.add("Mario Lopez");
    	validNames.add("Matt Rutherford");
    	validNames.add("Mike Goss");
    	validNames.add("Mohammad Mahoor");
    	validNames.add("Nathan Sturtevant");
    	validNames.add("Ramki Thurimella");
    	validNames.add("Rinku Dewri");
    	validNames.add("Scott Leutenegger");
    	validNames.add("Susan Bolton");
    	validNames.add("Susanne Sherba");
    	
    	validNames.add("Andy Brunner");
    	validNames.add("Stephen Rice");
    	validNames.add("Zach Azar");
    	validNames.add("Tanarat Dityam");
    	validNames.add("Will Mitchell");
    	validNames.add("Amanda Kirk");
    	validNames.add("Sally Lee");
    	//validNames.add("Andrew Hannum");
    	
    	
    	validNames.add("Georgy Voronoi");
    	validNames.add("Gary Busey");
    	validNames.add("Cheese");
    	validNames.add("Chewbacca");
    	validNames.add("Boris Delaunay");
    	validNames.add("Elvis Presley");
    	validNames.add("Henry IV of France");
    	validNames.add("Jesus");
    	validNames.add("Lassie");
    	validNames.add("Napoleon");
    	validNames.add("Nikki Minaj");
    	validNames.add("Barak Obama");
    	validNames.add("Sean Connery");
    	validNames.add("Alan Turning");
    	
    	
    	

    	getInput();
    	//theScanner.close();
    	 try {  
             /** 
              * ImageIO.read() returns a BufferedImage object, decoding the supplied   
              * file with an ImageReader, chosen automatically from registered files  
              * The File is wrapped in an ImageInputStream object, so we don't need 
              * one. Null is returned, If no registered ImageReader claims to be able 
              * to read the resulting stream. 
              */  
              image = ImageIO.read(new File(dir));  
              image2 = ImageIO.read(new File(dir2));
              image3 = ImageIO.read(new File(dir));
          } catch (IOException e) {  
              //Let us know what happened  
              System.out.println("Error reading dir: " + e.getMessage());  
          }  
    	 
  
    	    myTri = new DelaunayTri();
    	    myTri2 = new DelaunayTri();
    	    middleTri = new DelaunayTri();
    	   
    	    //add the 4 corners of the first picture to the first triangulation.  The -1 is so the 4 points aren't cocircular
    	    myTri.addPoint(new Point3(image.getMinX(), image.getMinY()));    	  
    	    myTri.addPoint(new Point3(image.getMinX(), image.getHeight()));
    	    myTri.addPoint(new Point3(image.getWidth(), image.getMinY()));
    	    myTri.addPoint(new Point3(image.getWidth(), image.getHeight()-1));

    	   
    	    //add the 4 corners of the first picture to the middle triangulation
    	    middleTri.addPoint(new Point3(image.getMinX(), image.getMinY()));
    	    middleTri.addPoint(new Point3(image.getMinX(), image.getHeight()));
    	    middleTri.addPoint(new Point3(image.getWidth(), image.getMinY()));
    	    middleTri.addPoint(new Point3(image.getWidth(), image.getHeight()-1));
    
    	    
    	    //add the 4 corners of the second image to the second triangulation
    	    myTri2.addPoint(new Point3(image2.getMinX(), image2.getMinY()));
    	    myTri2.addPoint(new Point3(image2.getMinX(), image2.getHeight()));
    	    myTri2.addPoint(new Point3(image2.getWidth(), image2.getMinY()));
    	    myTri2.addPoint(new Point3(image2.getWidth(), image2.getHeight()-1));
    	    
    	    outputInstructions(firstPicDone);
    	    
    }
    public MyPanel() 
    {

        setBorder(BorderFactory.createLineBorder(Color.black));

        initialize();
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
            	
            	//place a red circle where the user just clicked
                moveCircle(e.getX(),e.getY());
                
                //If the first image's triangulation does not have the desired
                //number of points yet, add the clicked point to the triangulation
                if(myTri.getPoints().size() < numDelPoints)
                {
                	addDelPoint(e.getX(), e.getY(), myTri);
                	addDelPoint(e.getX(), e.getY(), middleTri);
                	outputInstructions(firstPicDone);
                }
                
                //if the first image's triangulation has the desired number of 
                //points, go to the second pic
                else if(!firstPicDone)
                {
                	firstPicDone = true;
                	outputInstructions(firstPicDone);
                }
                
                //add the clicked point to the second image's triangulation
                //if it does not yet have the desired number of points
                else if(myTri2.getPoints().size() < numDelPoints)
                {
                	firstPicDone = true;
                	addDelPoint(e.getX(), e.getY(), myTri2);
                	outputInstructions(firstPicDone);

                }
                
                //Once all the points have been clicked, start the transformation
                else
                {
                	allDone = true;
                	boolean noCreep = true;

                	//keep transforming until the second picture has been reached
                	while(totalGran <= maxGran || noCreep)
                	{
                		
                		if(totalGran > maxGran)
                		{
                			System.out.println("\nMOPRHED!  A mysterious red button appears in front of you.  Do you push it? (y/n)");
                			String creepy = theScanner.next();
                	    	if(creepy.equals("y") || creepy.equals("Y"))
                	    	{
                	    		System.out.println("\nLooks like a dangerous button.  I wouldn't press it if I were you...You really want to press it?(y/n)");
                	    		creepy = theScanner.next();
                	    		if(creepy.equals("y") || creepy.equals("Y"))
                    	    	{
                	    			maxGran = 4;
                	    			pauseTime = 100;
                	    			System.out.println("\nYOU COULD HAVE PREVENTED THIS!");
                    	    	}
                	    		else
                	    		{
                	    			System.out.println("\nWhew.  That was close...");
                	    			try {
										Thread.sleep(1000);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                	    			noCreep = false;
                	    			break;
                	    		}
                	    	}
                	    	else
                	    	{
                	    		System.out.println("\nA wise choice.  Fortune favors the cautious.");
                	    		try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                	    		noCreep = false;
                	    		break;
                	    	}
                	    	noCreep = false;
                	    	
                		}
                		
                		
                		try{
                		transform();
                		}
                		catch(Exception e2)
                		{
                			System.out.println("\nWHOOPS!  Something went wrong.  This usually happens if you click really fast or click outside the picture.");
                			System.out.println("Please type restart FaceMorph.jar");
                			System.exit(ERROR);
                		}
                		try {
							Thread.sleep(pauseTime);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                		
                		
						
                	}
                	initialize();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                moveCircle(e.getX(),e.getY());
            }
        });
        
    	
    	
    }
    
    /**
     * Increments the transformation by one step
     */
    private void transform()
    {
    	
    	totalGran += granularity;
    	float[] baryCo = new float[3];
    	
    	
    	int k;
    	Point3 firstPixelPoint;
    	Point3 secondPixelPoint;
    	int firstPixelColor;
    	int secondPixelColor;
    	int thirdPixelColor;
    	

    	int firstRed, firstGreen, firstBlue, secondRed, secondGreen, secondBlue, thirdRed, thirdGreen, thirdBlue;
    	int newMiddleTriX, newMiddleTriY;
    	
    	//update middle triangulation (i.e. so it is the next step in the transformation)
    	// the middle triangulation will be a little closer to picture 2 than picture 1 after this
		middleTri.reset();
    	for(int i = 0; i < myTri.getPoints().size(); i++)
    	{
    		newMiddleTriX = (int)((float)myTri.getPoints().get(i).getX()*totalGran + (1-totalGran)*(float)myTri2.getPoints().get(i).getX());
    		newMiddleTriY = (int)((float)myTri.getPoints().get(i).getY()*totalGran + (1-totalGran)*(float)myTri2.getPoints().get(i).getY());
    		middleTri.addPoint( new Point3(newMiddleTriX, newMiddleTriY));
    		

    	}
    	
    	
    	//These for loops look through every pixel in the picture and finds which triangle that pixel is in
    	for(int i = 0; i < image.getWidth(); i++)
    	{
    		for(int j = 0; j < image.getHeight(); j++)
    		{
    			//find the triangle that this pixel is in
    			//if each barycentric coordinate is positive, then the pixel is inside this triangle
    			for(k = 0; k < middleTri.getTriangulation().size(); k++)
    			{
    				baryCo = middleTri.getTriangulation().get(k).getBary(new Point3(i,j));
    				
    				//these -.1's should be 0's but this sort of accounts for precision error
    				//break because we know that this pixel is inside triangle middleTri.getTriangulation.get(k)
    				if(baryCo[0] >= -.1 && baryCo[1] >= -.1 && baryCo[2] >= -.1)
    				{
    					break;
    				}
    			}
    			
    			//if the pixel did not belong in any triangle
    			//then set this pixel value to 0 (these are corner cases)
    			if(k == middleTri.getTriangulation().size())
    			{
    				image3.setRGB(i, j, 0);
    			}
    			
    			else
    			{
    				//get the corresponding pixel locations in the initial and final image
    				firstPixelPoint = myTri.getTriangulation().get(k).reverseBary(baryCo);
	    			secondPixelPoint = myTri2.getTriangulation().get(k).reverseBary(baryCo);
    				int[] triangleMid = middleTri.getTriangulation().get(k).getOrderPoints();
    				int[] triangleFirst;
    				int[] triangleSecond;
    				for(int m = 0; m < myTri.getTriangulation().size(); m++)
    				{
    					triangleFirst = myTri.getTriangulation().get(m).getOrderPoints();
    					triangleSecond = myTri2.getTriangulation().get(m).getOrderPoints();
    					if(triangleFirst[0] == triangleMid[0] && triangleFirst[1] == triangleMid[1] && triangleFirst[2] == triangleMid[2])
    					{
    						firstPixelPoint = myTri.getTriangulation().get(m).reverseBary(baryCo);
    					}
    					if(triangleSecond[0] == triangleMid[0] && triangleSecond[1] == triangleMid[1] && triangleSecond[2] == triangleMid[2])
    					{
    						secondPixelPoint = myTri2.getTriangulation().get(m).reverseBary(baryCo);
    					}
    				}
    				
    				
	    			//if the pixel is out of range 
	    			try{
	    			firstPixelColor = image.getRGB(firstPixelPoint.getX(), firstPixelPoint.getY());
	    			}
	    			
	    			catch(Exception e)
	    			{
	    				firstPixelColor = 0;
	    			}
	    			
	    			try{
	    				secondPixelColor = image2.getRGB(secondPixelPoint.getX(), secondPixelPoint.getY());
	    			}
	    			catch(Exception e)
	    			{
	    				secondPixelColor = 0;
	    			}
	    			
	
	    			//get RGB values of this pixel
	    			firstRed = (firstPixelColor >> 16) & 0xff;
	    			firstGreen = (firstPixelColor >> 8) & 0xff;
	    			firstBlue = firstPixelColor & 0xFF;
	
	    			
	    			secondRed = (secondPixelColor >> 16) & 0xff;
	    			secondGreen = (secondPixelColor >> 8) & 0xff;
	    			secondBlue = secondPixelColor & 0xFF;
	    			
	    			//interpolate what this pixel should be in the morphing image, based on the initial and final images
	    			thirdRed = (int)((float)firstRed*totalGran + (1-totalGran)*(float)secondRed);
	    			thirdGreen = (int)((float)firstGreen*totalGran + (1-totalGran)*(float)secondGreen);
	    			thirdBlue = (int)((float)firstBlue*totalGran + (1-totalGran)*(float)secondBlue);
	    			
	    			thirdPixelColor = thirdRed;
	    			thirdPixelColor = (thirdPixelColor << 8) + thirdGreen;
	    			thirdPixelColor = (thirdPixelColor << 8) + thirdBlue;
	    			image3.setRGB(i, j, thirdPixelColor);
    			}
    			
    			
   			}
    	}
    	

    	paintComponent(getGraphics());
    	
    	
    }
    private void addDelPoint(int x, int y, DelaunayTri theDelTri)
    {
    	theDelTri.addPoint(new Point3(x,y));
    }

    
    /**
     * Moves red circle to x y location
     * @param x
     * @param y
     */
    private void moveCircle(int x, int y){

        // Current Circle state, stored as final variables 
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redCircle.getX();
        final int CURR_Y = redCircle.getY();
        

        if ((CURR_X!=x) || (CURR_Y!=y)) {

            // The Circle is moving, repaint background 
            // over the old Circle location. 
            //repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates.
            redCircle.setX(x - redCircle.getWidth()/2);
            redCircle.setY(y - redCircle.getHeight()/2);
            
            repaint();
                    
        }
    }
    
    
    //Unfinished method that gives the user instructions
    //not implemented because the number of points desired in the
    //triangulation is tweakable, so a different set of instructions
    //would be needed for each value of numDelPoints
    public void outputInstructions(boolean firstPicDone)
    {
    	String outString;
    	int checkValue;
    	if(!firstPicDone)
    	{
    		checkValue = myTri.getPoints().size();
    	}
    	else
    	{
    		checkValue = myTri2.getPoints().size();
    	}
    		
		switch(checkValue){
		case 4: outString = "top of forehead";
		break;
		case 5: outString = "left ear (your left)";
		break;
		case 6: outString = "left eye (your left)";
		break;
		case 7: outString = "right eye (your right)";
		break;
		case 8: outString = "right ear (your right)";
		break;
		case 9: outString = "tip of nose";
		break;
		case 10: outString = "leftmost point of mouth (your left)";
		break;
		case 11: outString = "rightmost point of mouth (your right)";
		break;
		case 12: outString = "chin";
		break;
		case 13: outString = "anywhere to advance to next picture";
		break;
		default: outString = "uhhh....";
		break;
		}
    	
		System.out.println("Click: " + outString);
    }
    

    //We set our preferred size if we succeeded in loading image  
    public Dimension getPreferredSize() {  
        if (image == null) {  
             return new Dimension(100,100);  
        } else {  
           return new Dimension(image.getWidth(null), image.getHeight(null));  
       }  
    }  
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if(allDone)
        {
        	g.drawImage(image3,0,0,null);
        }
        else if(firstPicDone)
        {
        	g.drawImage(image2, 0, 0, null);
        	myTri2.paintTri(g);
        }
        else
        {
        	g.drawImage(image,0,0,null);
        	myTri.paintTri(g);
        }
        redCircle.paintCircle(g);
    }  
    
    public void resetValues()
    {
    	dir = "FacePics/";  
        dir2 = "FacePics/";
        maxGran = 1 - granularity;
        myTri = null;
        myTri2 = null;
        middleTri = null;
        image = null;
        image2 = null;
        image3 = null;
        firstPicDone = false;
        allDone = false;
        totalGran = 0;
        validNames.clear();
        granularity = (float).05;
        pauseTime = 150;
    }
}

public class FaceMorpher {
    
    public static void main(String[] args) {

    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
                createAndShowGUI(); 
            }
        });
        //System.out.println("WHOOPS!  The program terminated and I don't know why.  Would you please restart the program by typing \"./FaceMorph.jar\" and hitting Enter?");
    }

    private static void createAndShowGUI() {
       // System.out.println("Created GUI on EDT? "+
       // SwingUtilities.isEventDispatchThread());
    	
        JFrame f = new JFrame("Face Morpher");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        MyPanel aPanel = new MyPanel();
        f.add(aPanel);
        f.setSize(550,550);
        f.setVisible(true);
    	
    } 

}
//shows where the user clicked
class RedCircle{

    private int xPos = -1;
    private int yPos = -1;
    private int width = 10;
    private int height = 10;

    public void setX(int xPos){ 
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    } 

    public int getHeight(){
        return height;
    }

    public void paintCircle(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawOval(xPos,yPos,width,height);  
    }
}
