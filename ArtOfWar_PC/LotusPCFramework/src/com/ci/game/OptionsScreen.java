package com.ci.game;

import java.awt.Graphics;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class OptionsScreen extends Screen
{
	int graphicsButtX = game.getFrame().getWidth()/2+19; 
	int graphicsButtY = game.getFrame().getHeight()/2;
	int soundButtX = game.getFrame().getWidth()/2+19;
	int soundButtY = game.getFrame().getHeight()/2+40;	
	int backButtX = 0;
	int backButtY = game.getFrame().getHeight()-64;

	public OptionsScreen(LotusRenderView game)
	{
		super(game);
	}
	
	@Override
	public void update(double deltaTime)
	{
		if(game.getMouse().inBounds(graphicsButtX, graphicsButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new GraphicsOptionsScreen(game));
		}
		else if(game.getMouse().inBounds(backButtX, backButtY, 64, 32) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new MainMenu(game));
		}
		else if(game.getMouse().inBounds(soundButtX, soundButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new SoundOptionsScreen(game));
		}
		
		game.getMouse().update();
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		g.drawImage(Assets.menuScrollBackground,game.getFrame().getWidth()/2+22, game.getFrame().getHeight()/2 -18, null);

		g.drawImage(Assets.backButton, backButtX, backButtY, null);
		
		if(game.getMouse().inBounds(graphicsButtX, graphicsButtY, 96, 35))
		{
			g.drawImage(Assets.graphicsSelected, graphicsButtX, graphicsButtY, null);
		}
		else
		{
			g.drawImage(Assets.graphicsDeselected, graphicsButtX, graphicsButtY, null);
		}
		
		if(game.getMouse().inBounds(soundButtX, soundButtY, 96, 35))
		{
			g.drawImage(Assets.soundSelected, soundButtX, soundButtY, null);
		}
		else
		{
			g.drawImage(Assets.soundDeselected, soundButtX, soundButtY, null);
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
