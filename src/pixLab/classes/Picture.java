package pixLab.classes;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.lang.Math;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  public void zeroRed2()
  {
	  Pixel[][] pixels = this.getPixels2D();
	 
  }
  
  public void zerRed()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
	  Pixel[][] pixels = this.getPixels2D();
	    Pixel topPixel = null;
	    Pixel bottomPixel = null;
	    int height = pixels.length;
	    for (int row = 0; row < pixels.length; row++)
	    {
	      for (int col = 0; col < height / 2; col++)
	      {
	        topPixel = pixels[row][col];
	        bottomPixel = pixels[row][height - 1 - col];
	        bottomPixel.setColor(topPixel.getColor());
	      }
	    } 
  }
  
 
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  public void mirrorSeagull()
  {
	  
  }
  
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  public void glitchIt(Picture fromPic, 
          int startRow, int startCol, int edgeDist)
  {
	  
	  
	  
	  
	  
	  
	   
	  int rand = (int)(Math.random() * 2) + 1;
	
	  edgeDist = (int)(Math.random() * 20) + 2;
	  
	  Pixel leftPixel = null;
	    Pixel rightPixel = null;
	    Pixel[][] pixels = this.getPixels2D();
	    Color rightColor = null;
	    for (int row = 0; row < pixels.length; row++)
	    {
	      for (int col = 0; 
	           col < pixels[0].length-1; col++)
	      {
	        leftPixel = pixels[row][col];
	        rightPixel = pixels[row][col+1];
	        rightColor = rightPixel.getColor();
	        if (leftPixel.colorDistance(rightColor) > 
	            edgeDist)
	          leftPixel.setColor(Color.BLACK);
	        else
	          leftPixel.setColor(Color.WHITE);
	      }
	    }
	  
	  
	  
	  int a = 0;
	  
	  while(a < 100)
	  {
		  startRow += rand;
		  startCol += rand;	
		  
		  a++;
	  }
	  
	
	  
		  	Pixel fromPixel = null;
		    Pixel toPixel = null;
		    Pixel[][] copiedImage = this.getPixels2D();
		    Pixel[][] fromPixels = fromPic.getPixels2D();
		    for (int fromRow = 0, toRow = startRow; 
		         fromRow < fromPixels.length &&
		         toRow < copiedImage.length; 
		         fromRow++, toRow++)
		    {
		      for (int fromCol = 0, toCol = startCol; 
		           fromCol < fromPixels[0].length &&
		           toCol < copiedImage[0].length;  
		           fromCol++, toCol++)
		      {
		        fromPixel = fromPixels[fromRow][fromCol];
		        toPixel = copiedImage[toRow][toCol];
		        toPixel.setColor(fromPixel.getColor());
		      }
		    }   
		    

		   
		    
		    
		    //change color method
		    Pixel[][] pixels1 = this.getPixels2D();
		    for (Pixel[] rowArray : pixels1)
		    {
		      for (Pixel pixelObj : rowArray)
		      {
		        pixelObj.setRed((int)(Math.random() * 120) - 0);
		        //pixelObj.setGreen((int)(Math.random() * 120) - 0);
		        //pixelObj.setBlue((int)(Math.random() * 120) - 0);
		      }
		    }
		  //seagull.glitchIt(seagull, rand % 10, rand/10);
	  

		    
		   
		    
		    
  }
  
  
	
  public void chromakey(Picture replacement, Color changeColor)
  {
	  Pixel[][] mainPixels = this.getPixels2D();
	  Pixel[][] replacementPixels = replacement.getPixels2D();
	  
	  for(int row = 0; row < mainPixels.length; row++)
	  {
		  for(int col = 0; col < mainPixels[0].length; col++)
		  {
			  if(mainPixels[row][col].colorDistance(changeColor) > 50)
			  {
				  mainPixels[row][col].setColor(replacementPixels[row][col].getColor());
			  }
		  }
	  }
  }
  
  public void sheftLeftRight(int amount)
  {
	  Pixel[][] pixels = this.getPixels2D();
	  Picture temp = new Picture(this);
	  Pixel [][] copied = temp.getPixels2D();
	  
	  int shiftedValue = amount;
	  int width = pixels[0].length;
	  
	  for (int row = 0; row < pixels.length; row++)
	  {
		  for (int col = 0; col < pixels[0].length; col++)
		  {
			  shiftedValue = (col + amount) % width;
			  copied[row][col].setColor(pixels[row][shiftedValue].getColor());
		  }
	  } 
	  for (int row = 0; row < pixels.length; row++)
	  {
		  for (int col = 0; col < pixels[0].length; col++)
		  {
			  pixels[row][col].setColor(copied[row][col].getColor());
		  }
	  }
  }

  public void shiftUpDown(int amount)
  {
 	  Pixel [][] pixels = this.getPixels2D();
 	  Picture temp = new Picture(this);
 	  Pixel [][] copied = temp.getPixels2D();
 	  
 	  int shiftedValue = amount;
 	  int height = pixels.length;
 	  
 	  for (int row = 0; row < pixels.length; row++)
 	  {
 		  for (int col = 0; col < pixels[0].length; col++)
 		  {
 			  shiftedValue = Math.abs((row + amount) % height);
 			  copied [row][col].setColor(pixels[shiftedValue][col].getColor());
 		  }
 	  }
 		  
 		  for (int row = 0; row < pixels.length; row ++)
 		  {
 			  for (int col = 0; col < pixels[0].length; col++)
 			  {
 				  pixels[row][col].setColor(copied[row][col].getColor());
 			  }
 		  }
 	  }
  
  
  public void Steganography()
  {
	  
  }
  
  public void hidePicture(Picture hidden)
  {
	  Pixel[][] pixels = this.getPixels2D();
	  Pixel[][] hiddenPixels = hidden.getPixels2D();
	  
	  for(int row = 0; row < pixels.length && row < hiddenPixels.length; row++)
	  {
		  for(int col = 0; col < pixels[0].length && col < hiddenPixels[0].length; col++)
		  {
			  //There is a message to hide
			  if(hiddenPixels[row][col].colorDistance(Color.WHITE) > 5)
			  {
				  if (pixels[row][col].getRed() > 0 && pixels[row][col].getRed() % 2 != 1)
				  {
					  pixels[row][col].setRed(pixels[row][col].getRed() - 1);
				  }
			  }
			  else if (pixels[row][col].getRed() > 0 && pixels[row][col].getRed() % 2 == 1)
			  {
				  pixels[row][col].setRed(pixels[row][col].getRed() - 1);
			  }
		  }
	  }
  }
  
  public void revealPicture()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  
	  for(int row = 0; row < pixels.length; row++)
	  {
		  for(int col = 0; col < pixels[0].length; col++)
		  {
			  //There is a message to reveal
			  if(pixels[row][col].getRed() > 0 && pixels[row][col].getRed() % 2 != 1)
			  {
				  pixels[row][col].setColor(Color.ORANGE);
			  }
			  else if(pixels[row][col].getRed() % 2 == 1)
			  {
				  pixels[row][col].setColor(Color.BLUE);
			  }
		  }
	  }
  }
  
  
  

  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture mandelOne = new Picture("mandelbrot1.jpg");
    Picture mandelTwo = new Picture("mandelbrot2.jpg");
    mandelOne.explore();
    mandelOne.glitchIt(mandelOne, 200, 192, 10);
    //seagull.edgeDetection(10);
    mandelOne.explore();
    
    
    Picture source = new Picture("mandelbrot.jpg");
	  Picture background = new Picture("mandelbrot.jpg");
	  source.explore();
	  background.explore();
	  source.chromakey(background, Color.BLUE);
	  source.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
