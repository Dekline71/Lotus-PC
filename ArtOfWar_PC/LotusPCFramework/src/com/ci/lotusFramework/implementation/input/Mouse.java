package com.ci.lotusFramework.implementation.input;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ci.game.graphics.Camera;

public class Mouse implements MouseListener, MouseMotionListener 
{
	private static int mouseX = -1;//store x location of mouse on window
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	private boolean isMouseClicked;
	private boolean isMouseDown;
	private boolean isMouseUp;
	private boolean isMouseDragged;
		
	public int getX()
	{
		return mouseX;
	}

	public int getY()
	{
		return mouseY;
	}
	
	public static int getButton()
	{
		return mouseB;
	}

	
	public Mouse()
	{
		
	}
	
	public void update()
	{
		if(isMouseClicked())
		{
			this.isMouseClicked = false;
			this.isMouseDown = false;
		}
		if(isMouseUp())
		{
			this.isMouseUp = false;
			this.isMouseDown = false;
		}
		if(isMouseDown())
		{
			//this.isMouseDown = false;
		}

	}
	
	public boolean isTouched(Camera screen, int x, int y, int height, int width) 
	{
		int xx = ((getX() / 32) + (screen.getxOffset() / 32));
		int yy = ((getY() / 32) + (screen.getyOffset() / 32)) - 1;
		//System.out.println("X: " + xx + "Y: " + yy);

		if (xx >= x && yy >= y && xx <= x + width && yy <= y + height)
			return true;
		else
			return false;
	}
	
	public boolean inBounds(int x, int y, int width, int height) 
	{
		if ((mouseX) > x && (mouseX) < x + width - 1 && (mouseY) > y
				&& (mouseY) < y + height - 1)
			return true;
		else
			return false;
	}

	public boolean isTouched(int x, int y, int width, int height, Camera screen) 
	{
		int xx = ((mouseX / 32) + (screen.getxOffset() / 32) );
		int yy = ((mouseY / 32) + (screen.getyOffset() / 32)) - 1;
		//System.out.println("X: " + xx + "Y: " + yy);

		if (xx >= x && yy >= y && xx <= x + width && yy <= y + height)
			return true;
		else
			return false;
		
		/*if(xx == 12 && yy == 12)
		{
			return true;
		}
		else
		{
			return false;
		}*/
	}
	
	public void mouseDragged(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		this.setMouseDragged(true);
		System.out.println("Mouse dragged.");
		this.isMouseDown = true;
	}

	
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		this.isMouseClicked = false;
	}

	public void mouseEntered(MouseEvent e) 
	{
		System.out.println("Mouse entered.");
	}

	public void mouseExited(MouseEvent e) 
	{
		System.out.println("Mouse exited.");
	}

	// When mouse is held down
	public void mousePressed(MouseEvent e) 
	{
		mouseB = e.getButton();
		System.out.println("Mouse pressed.");
		this.isMouseDown = true;
		this.isMouseClicked = false;		
		this.isMouseUp = false;

	}

	public void mouseReleased(MouseEvent e) 
	{
		mouseB = -1;//reset button
		System.out.println("Mouse released.");
		this.isMouseUp = true;
		//this.isMouseDown = false;
		this.isMouseClicked = false;	
		this.setMouseDragged(false);

	}
	
	public void mouseClicked(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		
		mouseB = e.getModifiers();
		System.out.println("Mouse clicked.");

		this.isMouseClicked = true;
		this.isMouseDown = false;
		//this.isMouseUp = true;
	}
	
	// When mouse has been held down & released
	public boolean isMouseClicked()
	{
		return this.isMouseClicked;
	}
	
	public boolean isMouseDown() 
	{
		return isMouseDown;
	}

	public void setMouseDown(boolean isMouseDown) 
	{
		this.isMouseDown = isMouseDown;
	}

	public boolean isMouseUp() 
	{
		return isMouseUp;
	}

	public void setMouseUp(boolean isMouseUp) 
	{
		this.isMouseUp = isMouseUp;
	}

	public boolean isMouseDragged() 
	{
		return isMouseDragged;
	}

	public void setMouseDragged(boolean isMouseDragged) 
	{
		this.isMouseDragged = isMouseDragged;
	}
}
