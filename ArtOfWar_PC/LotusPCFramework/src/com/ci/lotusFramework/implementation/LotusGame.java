package com.ci.lotusFramework.implementation;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import com.ci.audio.Audio;
import com.ci.game.GameScreen;
import com.ci.game.SplashLoadingScreen;
import com.ci.lotusFramework.FileIO;
import com.ci.lotusFramework.Game;
import com.ci.lotusFramework.Input;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

public class LotusGame implements Game
{
	private LotusFrame frame;
	LotusRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	BufferedImage framebuffer;
	private Canvas canvas;
	
	static Keyboard key;
	Mouse mouse;
	
	public static String map;
	boolean firstTimeCreate = true;
	//LotusRenderView window;
	
	public LotusGame() throws IOException, InterruptedException
	{
		frame = new LotusFrame();
		canvas = new Canvas();
		screen = getInitScreen();
		
		renderView = new LotusRenderView(this,  framebuffer);
	}
	
	public void createWindow()
	{
		
	}
	
	@Override
	public Audio getAudio() 
	{
		return audio;
	}

	@Override
	public Input getInput() 
	{
		return input;
	}

	@Override
	public FileIO getFileIO() 
	{
		return fileIO;
	}

	@Override
	public Graphics getGraphics() 
	{
		return graphics;
	}

	@Override
	public void setScreen(Screen screen)
	{
	     if (screen == null)
	            throw new IllegalArgumentException("Screen must not be null");

	        this.screen.pause();
	        this.screen.dispose();
	        screen.resume();
	        screen.update(0);
	        this.screen = screen;		
	}

	@Override
	public Screen getCurrentScreen() 
	{
		return screen;
	}

	@Override
	public Screen getInitScreen() 
	{
		if (firstTimeCreate) 
		{
			//Assets.load(this);
			firstTimeCreate = false;
		}

		//InputStream is = getResources().openRawResource(R.raw.map3);
		//map = convertStreamToString(is);

		return new GameScreen(renderView);
	}

	@Override
	public Keyboard getKeyboard() 
	{
		return this.key;
	}
	
	@Override
	public Mouse getMouse()
	{
		return this.mouse;
	}

}
