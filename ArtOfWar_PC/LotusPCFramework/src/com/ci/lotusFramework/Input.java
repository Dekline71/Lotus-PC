package com.ci.lotusFramework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public interface Input 
{
	public static class InputEvent 
	{
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;
        
		private static int mouseX = -1;//store x location of mouse on window
		private static int mouseY = -1;
		private static int mouseB = -1;
		
		
		
		
			
		public int getX()
		{
			return mouseX;
		}

		public static int getY()
		{
			return mouseY;
		}
		
		public static int getButton()
		{
			return mouseB;
		}
		
		public InputEvent()
		{
			
		}

		public boolean checkForESC()
		{
			return false;
		}
		
	

		
	}
	
	
	
	public boolean isTouchDown(int pointer);
	
	public int getMouseX(int pointer);
	
	public int getMouseY(int pointer);
	
	public List<InputEvent> getInputEvents();
	
	
}
