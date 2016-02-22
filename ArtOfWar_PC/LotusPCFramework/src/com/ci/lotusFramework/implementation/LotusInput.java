package com.ci.lotusFramework.implementation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import com.ci.lotusFramework.Input;


public class LotusInput implements Input
{
	LotusInputHandler mouseHandler;
	
	public LotusInput()
	{
		mouseHandler = new LotusInputHandler();
	}

	@Override
	public boolean isTouchDown(int pointer) 
	{
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

	@Override
	public List<InputEvent> getInputEvents() 
	{
		// TODO Auto-generated method stub
		return mouseHandler.getInputEvents();
	}
	
	



}	

	

		


