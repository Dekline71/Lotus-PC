package com.ci.game;

import java.awt.Graphics;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class PlayScreen extends Screen
{
	int newButtX = game.getFrame().getWidth() /2 + 8; 
	int newButtY = game.getFrame().getHeight() /2;
	int loadButtX = game.getFrame().getWidth() /2 + 22;
	int loadButtY = game.getFrame().getHeight() /2 + 34;
	
	public PlayScreen(LotusRenderView game) 
	{
		super(game);
	}
	
	@Override
	public void update(double deltaTime) 
	{
		if(game.getMouse().inBounds(loadButtX, loadButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			// load and retrieve save state from last saved game.
			game.setScreen(new SavedGameScreen(game));
		}
		else if(game.getMouse().inBounds(newButtX, newButtY, 96, 40) && game.getMouse().isMouseClicked())
		{
			// flag game as a new state
			GameManager.isNewGame = true;
			game.setScreen(new TribeSelectScreen(game));
		}
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		
		g.drawImage(Assets.menuScrollBackground,game.getFrame().getWidth()/2+22, game.getFrame().getHeight()/2 -18, null);
		
		if(game.getMouse().inBounds(newButtX, newButtY, 96, 35))
		{
			g.drawImage(Assets.newGameButtonSelected, newButtX, newButtY, null);
		}
		else
		{
			g.drawImage(Assets.newGameButtonDeselected, newButtX, newButtY, null);
		}
		
		if(game.getMouse().inBounds(loadButtX, loadButtY, 96, 35))
		{
			g.drawImage(Assets.contGameButtonSelected, loadButtX, loadButtY, null);
		}
		else
		{
			g.drawImage(Assets.contGameButtonDeselected, loadButtX, loadButtY, null);
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
