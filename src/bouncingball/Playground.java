/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bouncingball;

/**
 *
 * @author markoc
 */
public class Playground extends Thread{
    private int height, width;
    
    public void setPlaygroundHeight(int height)
    {
	if(height > 0)
	    this.height = height;
	else
	    throw new IllegalArgumentException("Height must be highter than 0");
    }
    
    public void setPlaygroundWidth(int width)
    {
	if(width > 0)
	    this.width = width;
	else
	    throw new IllegalArgumentException("Width must be higher than 0");
    }

    
    public int getPlaygroundHeight()
    {
	return this.height;
    }
    
    public int getPlaygroundWidth()
    {
	return this.width;
    }
    
    public void setHeight(int h){
	this.height = h;
    }
    public void setWidth(int w){
	this.width = w;
    }
}
