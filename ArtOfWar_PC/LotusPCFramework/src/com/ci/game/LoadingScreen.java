package com.ci.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class LoadingScreen extends Screen
{
	private String path;
	public LoadingScreen(LotusRenderView game) 
	{
		super(game);
	}
	
	long timer = System.currentTimeMillis();

	@Override
	public void update(double deltaTime)
	{

		System.out.println("Loading!!!");

		BufferedImage img;
		this.path =  "res/textures/misc/splash.jpg";		
		
		try 
		{
			img = ImageIO.read(this.getClass().getResource("/textures/misc/splash2.jpg"));
			Assets.splash = img;

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(System.currentTimeMillis() - timer > 500)
		{
			//timer += 10000;
			//System.out.println("Bam!!");
			game.setScreen(new SplashLoadingScreen(game));		
		}		
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = this.game.getCanvas().getBufferStrategy().getDrawGraphics();
		//screen.setGraphics(g);
		if(game.getFrame().getWidth() > 800)
		{
			g.drawImage(Assets.splash, game.getFrame().getWidth() / 10, game.getCanvas().getHeight() / 10, null);		
			g.setColor(Color.WHITE);
			g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth() / 3, (int)(game.getCanvas().getHeight() / 1.5));
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth() / 3, (int)(game.getCanvas().getHeight() / 1.5));

		}	
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		
	}

	@Override
	public void backButton()
	{
		
	}

	@Override
	public void update12EverySec() 
	{
		
	}
}