package bouncingball;

import java.awt.Color;
import java.security.SecureRandom;
import javax.swing.JPanel;

public class Ball{
    SecureRandom sr = new SecureRandom();
    private int dirX;
    private int dirY;
    private double ballX, ballY, ballW, ballH, dyBallW, dyBallH;
    private double shadowH = 10, dyShadowW, dyShadowH = shadowH, shadowRate = 1.0;
    private double gridHeight = 0, gridWidth = 0;
    private Color ballColor;
   
    // constructor with input values
    public Ball(int x, int y, JPanel grid)
    {
	this.gridWidth = grid.getWidth();
	this.gridHeight = grid.getHeight()-(int)(this.shadowH);
	this.dirX = (sr.nextBoolean()) ? sr.nextInt(3)+1 : ((sr.nextInt(3)+1) *(-1));
	this.dirY = sr.nextInt(3)+1;
	this.ballX = x;
	this.ballY = y;
	this.ballW = sr.nextInt(20)+100;
	this.ballH = sr.nextInt(20)+100;
	tryToBounce();
	calculateAttributes();
    } 
    // calculate new ball data
    public void calculateAttributes()
    {
	this.dyBallH = this.getNewHeight();
	this.dyBallW = this.getNewWidth();
	this.ballColor = new Color(sr.nextInt(255) , sr.nextInt(255), sr.nextInt(255));
	this.shadowRate = calculateShadowRate();
    }
    
    public void moveBall() {
	tryToBounce();
	this.ballX += this.dirX;
	this.ballY += this.dirY;
        this.dyBallH = this.getNewHeight();
	this.dyBallW = this.getNewWidth();
	this.shadowRate = calculateShadowRate();
	calculateDynamicShadowHeight();
	calculateDynamicShadowWidth();
    }
    
    private void tryToBounce()
    {
	// when edge is reached bounce back
	if(this.ballX <= 0 && this.dirX < 0 || this.ballX+this.dyBallW >= this.gridWidth && this.dirX > 0) 
	    this.dirX *= -1; // go to opposite direction
	// next lines are used only when the ball is growing; to pervent the ball from going off edge
	if (this.ballX <= 0) // if its passing the left edge
	    this.ballX = 0;
	if (this.ballX+this.dyBallW >= this.gridWidth) // if its passing the right edge
	    this.ballX = this.gridWidth-this.dyBallW;
	
	// when edge is reached bounce back
	if(this.ballY <= 0 && this.dirY < 0 || this.ballY+this.dyBallH >= this.gridHeight && this.dirY > 0) 
	    this.dirY *= -1; // go to opposite direction
	// when top edge is met
	if(this.ballY <= 0)
	    this.ballY = 0;
	// when bottom edge is met
	if((this.ballY+this.dyBallH) >= this.gridHeight)
	    this.ballY = this.gridHeight-this.dyBallH; 
	
	
    }
    // calculate new height for the ball, based on current Y location
    private int getNewHeight()
    {
	return (int)((this.ballY)/(this.gridHeight-this.shadowH)*this.ballH);
    }
    // calculate new width for the ball, based on current Y location
    private int getNewWidth()
    {
	return(int)((this.ballY)/(this.gridHeight-this.shadowH)*this.ballW);
    }
    // calculate new height for the shadow based on shadow rate number
    private void calculateDynamicShadowHeight()
    {
	this.dyShadowH = (this.shadowRate > 0) ? (int)(this.shadowH * this.shadowRate) : 0;
    }
    // calculate new width for the shadow based on shadow rate number
    private void calculateDynamicShadowWidth()
    {
	this.dyShadowW = (this.shadowRate > 0) ? (int)(this.dyBallW * this.shadowRate) : 0;
    }
    // calculate new shadow rate based on current balls bottom location(Y loc + height)
    // shadow is shown only when the ball is in the area of bottom 100 pixles 
    // on Y axis
    private double calculateShadowRate()
    {
	double result = (((this.ballY+this.dyBallH)-(this.gridHeight-100))/100);
	return (result < 0) ? 0 : (result > 1) ? 1 : result;
    }
    
    public double getShadowHeight()
    {
	return this.shadowH;
    }
    
    public void setGridHeight(int height)
    {
	if(height > 0)
	    this.gridHeight = height;
	else
	    throw new IllegalArgumentException("Height must be highter than 0");
    }
    
    public void setGridWidth(int width)
    {
	if(width > 0)
	    this.gridWidth = width;
	else
	    throw new IllegalArgumentException("Width must be higher than 0");
    }

    public int[] getBallData()
    {
	int[] fillBall= {
	    (int)this.ballX,
	    (int)this.ballY,
	    (int)this.dyBallW,
	    (int)this.dyBallH
	};
	return fillBall;
    }
    
    public int[] getShadowData()
    { 
	int[] fillShadow= {
	    (int)this.ballX+(((int)this.dyBallW-((int)(this.dyBallW*this.shadowRate)))/2),
	    (int)(this.gridHeight-(int)(this.dyShadowH/2)),
	    (int)this.dyShadowW,
	    (int)this.dyShadowH
	};
	return fillShadow;
    }
    
    public Color getBallColor()
    {
	return this.ballColor;
    }
    
    public Color getShadowColor()
    {
	return new Color(0,0,0,(int)(this.shadowRate*255));
    }
}
