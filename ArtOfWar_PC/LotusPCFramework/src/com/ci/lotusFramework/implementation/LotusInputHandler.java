package com.ci.lotusFramework.implementation;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.ci.lotusFramework.Input;
import com.ci.lotusFramework.Input.InputEvent;
import com.ci.lotusFramework.Pool;



public class LotusInputHandler implements MouseHandler, KeyHandler, Input
{
	
	private boolean[] keys = new boolean[120];// stores keys
	public boolean up, down, left, right, esc;
	
	Pool<InputEvent> mouseEventPool;
	List<InputEvent> mouseEvents = new ArrayList<InputEvent>();
	List<InputEvent> mouseEventsBuffer = new ArrayList<InputEvent>();
	
	Pool<InputEvent> KeyEventPool;
	List<InputEvent> KeyEvents = new ArrayList<InputEvent>();
	List<InputEvent> KeyEventsBuffer = new ArrayList<InputEvent>();
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseTouchingDown(int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<InputEvent> getInputEvents() 
	{
		   synchronized(this)
	        {     
	            int len = mouseEvents.size();
	            for( int i = 0; i < len; i++)
	                mouseEventPool.free(mouseEvents.get(i));
	            mouseEvents.clear();
	            mouseEvents.addAll(mouseEventsBuffer);
	            mouseEventsBuffer.clear();
	            return mouseEvents;
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

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMouseX(int pointer) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMouseY(int pointer) 
	{
		// TODO Auto-generated method stub
		return 0;
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
