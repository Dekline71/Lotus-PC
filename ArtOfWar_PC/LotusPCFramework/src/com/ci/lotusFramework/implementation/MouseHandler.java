package com.ci.lotusFramework.implementation;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import com.ci.lotusFramework.Input.InputEvent;

public interface MouseHandler extends MouseListener, MouseMotionListener
{

	public boolean isMouseTouchingDown(int pointer);
	
	public int getTouchX(int pointer);
	
	public int getTouchY(int pointer);
	
	public List<InputEvent> getInputEvents();
	
}
