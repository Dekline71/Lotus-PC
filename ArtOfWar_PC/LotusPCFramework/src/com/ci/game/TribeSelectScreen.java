package com.ci.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Sprite;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class TribeSelectScreen extends Screen 
{
	int cantiaciX = 800;
	int cantiaciY = 640;
	int regniX = 620;
	int regniY = 658;
	int iceniX = 820;
	int iceniY = 450;
	int trinavantesX = 800;
	int trinavantesY = 540;
	int atrebatesX = 700;
	int atrebatesY = 550;
	int durotrigesX;
	int durotrigesY;
	int cornovilX;
	int cornovilY;
	

	public TribeSelectScreen(LotusRenderView game) 
	{
		super(game);
	}

	@Override
	public void update(double deltaTime) 
	{
		if(game.getMouse().inBounds(cantiaciX, cantiaciY, 34, 34) && game.getMouse().isMouseClicked())
		{
			// you chose cantiaci tribe
			GameManager.tribe = "Cantiaci";
			game.setScreen(new GameScreen(game));
		}
		
		if(game.getMouse().inBounds(regniX, regniY, 34, 34) && game.getMouse().isMouseClicked())
		{
			// you chose regni tribe
			//load/init shit
			GameManager.tribe = "Regni";
			game.setScreen(new GameScreen(game));
		}
		
		if(game.getMouse().inBounds(iceniX, iceniY, 34, 34) && game.getMouse().isMouseClicked())
		{
			// you chose iceni tribe
			GameManager.tribe = "Iceni";
			
			game.setScreen(new GameScreen(game));
		}	
		
		if(game.getMouse().inBounds(trinavantesX, trinavantesY, 34, 34) && game.getMouse().isMouseClicked())
		{
			// you chose iceni tribe
			GameManager.tribe = "Trinavantes";
			game.setScreen(new GameScreen(game));
			//game.getCurrentScreen().
		}
		
		if(game.getMouse().inBounds(atrebatesX, atrebatesY, 34, 34) && game.getMouse().isMouseClicked())
		{
			// you chose iceni tribe
			GameManager.tribe = "Atrebates";
			game.setScreen(new GameScreen(game));
			
		}
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		g.drawImage(Assets.mapScaled, -20, -55, null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 32));
		g.drawString("Select a tribe", game.getFrame().getWidth() / 2, 40);
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), cantiaciX, cantiaciY, null);
		if(game.getMouse().inBounds(cantiaciX, cantiaciY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 24));
			g.drawString("Cantiaci", cantiaciX, cantiaciY);			
		}
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), trinavantesX, trinavantesY, null);
		if(game.getMouse().inBounds(trinavantesX, trinavantesY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 24));
			g.drawString("Trinavantes", trinavantesX, trinavantesY);			
		}
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), iceniX, iceniY, null);
		if(game.getMouse().inBounds(iceniX, iceniY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 24));
			g.drawString("Iceni", iceniX, iceniY);			
		}
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), regniX, regniY, null);
		if(game.getMouse().inBounds(regniX, regniY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 24));
			g.drawString("Regni", regniX, regniY);			
		}
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), atrebatesX, atrebatesY, null);
		if(game.getMouse().inBounds(atrebatesX, atrebatesY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Viner Hand ITC", Font.ITALIC | Font.BOLD, 24));
			g.drawString("Atrebates", atrebatesX, atrebatesY);			
		}	
		
		g.drawImage(Sprite.selectedEntityPoint.getLotus(), atrebatesX, atrebatesY, null);
		if(game.getMouse().inBounds(atrebatesX, atrebatesY, 34, 34))
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 24));
			g.drawString("", atrebatesX, atrebatesY);			
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
