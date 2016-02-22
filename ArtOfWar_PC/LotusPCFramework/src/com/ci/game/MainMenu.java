package com.ci.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class MainMenu extends Screen
{
	int playButtX = game.getFrame().getWidth()/2+8; 
	int playButtY = game.getFrame().getHeight()/2;
	int optButtX = game.getFrame().getWidth()/2+22;
	int optButtY = game.getFrame().getHeight()/2 + 34;
	int quitButtX = game.getFrame().getWidth()/2+35;
	int quitButtY = game.getFrame().getHeight()/2 + 65;

	long timer = System.currentTimeMillis();

	public MainMenu(LotusRenderView game)
	{
		super(game);
	}

	@Override
	public void update(double deltaTime)
	{
		Assets.mainMenuTapestry.update(10);

		if(game.getMouse().inBounds(playButtX, playButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new PlayScreen(game));
		}
		else if(game.getMouse().inBounds(optButtX, optButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new OptionsScreen(game));
		}
		else if(game.getMouse().inBounds(quitButtX, quitButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			game.getFrame().setVisible(false);
			game.getFrame().dispose();
			game.stop();
			System.exit(0);
		}
		game.getMouse().update();
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		//g.setColor(Color.RED);
		//g.setFont(new Font("Century", 0, 45));
		//g.drawString("Play", game.getFrame().getWidth()/2, game.getFrame().getHeight()/2);
		
		//g.drawImage(Assets.parchment, 50, 300, null);
		g.drawImage(Assets.mainMenuTapestry.getImage(), -30, -25, null);
		
		//g.drawImage(Assets.mainMenuTapestry.getImage(), -30, 475, null);

		
		g.drawImage(Assets.menuScrollBackground,game.getFrame().getWidth()/2+22, game.getFrame().getHeight()/2 -18, null);
		
		if(game.getMouse().inBounds(playButtX, playButtY, 96, 35))
		{
			g.drawImage(Assets.playButton, playButtX, playButtY, null);
		}
		else
		{
			g.drawImage(Assets.playButtonDeselected, playButtX, playButtY, null);
		}
		
		if(game.getMouse().inBounds(optButtX, optButtY, 96, 35))
		{
			g.drawImage(Assets.optionsSelected, optButtX, optButtY, null);
		}
		else
		{
			g.drawImage(Assets.optionsDeselected, optButtX, optButtY, null);
		}
		
		if(game.getMouse().inBounds(quitButtX, quitButtY, 96, 35))
		{
			g.drawImage(Assets.quitButton, quitButtX, quitButtY, null);
		}
		else
		{
			g.drawImage(Assets.quitButtonDeselected, quitButtX, quitButtY, null);
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
