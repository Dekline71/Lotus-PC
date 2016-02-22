package com.ci.lotusFramework.implementation.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{
	
	private boolean[] keys = new boolean[120];// stores keys
	public boolean up, down, left, right, esc, space;
	
	
	public void update()
	{
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];//Check if Up arrow or W is pressed
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		
		for(int i = 0; i < keys.length; i++)//scan thru every key
		{
			if(keys[i])
			{
				System.out.println("Key: " + keys[i]);//identify ascii decimal code key 
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;//get id of key being pressed
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public boolean[] getKey()
	{
		return this.keys;
	}

	public boolean checkForESC() 
	{
		esc = keys[KeyEvent.VK_ESCAPE];//Check if Up arrow or W is pressed
		if(esc)
		{
				return true;
		}
		else
		{
			return false;
		}
	}
}
