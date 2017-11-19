/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bouncingball;

import java.awt.Color;
import java.awt.Graphics;
import java.security.SecureRandom;

/**
 *
 * @author markoc
 */
public class Ball extends Playground{
    SecureRandom sr = new SecureRandom();
    Color color;
    private int dirX = (sr.nextBoolean()) ? sr.nextInt(2)+3 : ((sr.nextInt(2)+3) *(-1)), dirY = sr.nextInt(3)+1;
    private int R = sr.nextInt(255), G = sr.nextInt(255), B = sr.nextInt(255);
    private int x, y, w, h, oh, ow, centerX, centerY;
    private int shadowHeight = 10, shadowHeightDynamic = shadowHeight;
    private double shadowRate = 0;
    // constructor with no arguments sets coordinates to 0, color to black
    // and fill to false
   
    // constructor with input values
    public Ball(int x, int y, int w, int h, int panelHeight, int panelWidth)
    {
	super.setWidth(panelWidth-(this.shadowHeight/2));
	super.setHeight(panelHeight-(this.shadowHeight/2));
	this.x = x;
	this.y = y;
	this.ow = this.w = w;
	this.oh = this.h = h;
	this.h = this.getNewHeight();
	this.w = this.getNewWidth();
	this.centerX = this.x + this.w/2;
	this.centerY = this.y + this.h/2;
	this.color = new Color(this.R , this.G, this.B);
	this.shadowRate = (double)(((double)((this.y+this.h) - (double)(super.getPlaygroundHeight()*0.3)))/((double)(super.getPlaygroundHeight() - (double)(super.getPlaygroundHeight()*0.3))));
    } 
    
    public void moveBall() {
	this.x += this.dirX;
	this.y += this.dirY;
	this.centerX = this.x + this.w/2;
	this.centerY = this.y + this.h/2;
        this.h = this.getNewHeight();
	this.w = this.getNewWidth();
	this.shadowRate = calculateShadowRate();
	calculateShadowDynamicHeight();
	tryToBounce();
    }
    
    public void paint(Graphics g)
    {
	int shadowAlpha = (int)(this.shadowRate*255);
	this.moveBall();
	g.setColor(new Color(0,0,0, shadowAlpha));
	int shadowWidth = (int)(this.w*this.shadowRate);
	g.fillOval(this.x+((this.w-shadowWidth)/2), super.getPlaygroundHeight()-(this.shadowHeightDynamic/2), shadowWidth, this.shadowHeightDynamic);
	g.setColor(this.color);
	g.fillOval(this.x, this.y, this.w, this.h);
    } 
    
    private void tryToBounce()
    {
	// when edge is reached bounce back
	if(this.x <= 0 && this.dirX < 0 || this.x+this.w >= super.getPlaygroundWidth() && this.dirX > 0) 
	    this.dirX *= -1; // go to opposite direction
	// next lines are used only when the ball is growing; to pervent the ball from going off edge
	else if (this.x <= 0 && this.dirX > 0) // if its passing the left edge
	    this.x = 0;
	else if (this.x+this.w >= super.getPlaygroundWidth() && this.dirX < 0) // if its passing the right edge
	    this.x = super.getPlaygroundWidth()-this.w;
	
	// when edge is reached bounce back
	if(this.y <= 0 && this.dirY < 0 || this.y+this.h >= super.getPlaygroundHeight() && this.dirY > 0) 
	    this.dirY *= -1; // go to opposite direction
	else if(this.y+this.h >= super.getPlaygroundHeight() && this.dirY > 0)
	    this.y = super.getPlaygroundHeight()-this.h;
	
	
    }
    
    private int getNewHeight()
    {
	return (this.centerY > 10 && super.getPlaygroundHeight() > 0) ? (int)(((double)this.y+(double)this.h)/(double)(super.getPlaygroundHeight()-this.shadowHeight)*this.oh) : this.h;
    }
    
    private int getNewWidth()
    {
	return (this.centerY > 10 && super.getPlaygroundHeight() > 0) ? (int)(((double)this.y+(double)this.h)/(double)(super.getPlaygroundHeight()-this.shadowHeight)*this.ow) : this.w;
    }
    
    public int getShadowHeight()
    {
	return this.shadowHeight;
    }
    
    private void calculateShadowDynamicHeight()
    {
	this.shadowHeightDynamic = (this.shadowRate > 0) ? (int)(this.shadowHeight * this.shadowRate) : 0;
    }
    
    private double calculateShadowRate()
    {
	double result = (double)(((double)(this.y+this.h)-(double)(super.getPlaygroundHeight()-100))/100);
	return (result < 0) ? 0 : (result > 1) ? 1 : result;
    }
}
