package com.ci.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusFrame;
import com.ci.lotusFramework.implementation.LotusInput;
import com.ci.lotusFramework.implementation.LotusRenderView;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

public class GraphicsOptionsScreen extends Screen
{	
	int backButtX = 0;
	int backButtY = game.getFrame().getHeight()-64;
	int value = 24;
	int res1X = value;
	int res1Y = value;

	public GraphicsOptionsScreen(LotusRenderView game) 
	{
		super(game);
	}

	@Override
	public void update(double deltaTime) 
	{
		if(game.getMouse().inBounds(res1X, 12, 128, value) && game.getMouse().isMouseClicked())
		{
			game.isRenderPaused = true;
			game.getFrame().setVisible(false);
			//game.getFrame().dispose();
	
					
			// Get the graphics environment
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// From that, grabs the screen(s)
			GraphicsDevice[] devices = ge.getScreenDevices();

			game.setFrame(new LotusFrame(devices[0]));

			game.setImage( new BufferedImage(game.getFrame().getWidth(), game.getFrame().getHeight(), BufferedImage.TYPE_INT_RGB));
			
			Canvas canvas = game.getCanvas();

			canvas.setSize(game.getFrame().getWidth(), game.getFrame().getHeight());
			//canvas.createBufferStrategy(1);

			game.getFrame().add(canvas);
			game.getFrame().requestFocus();

			game.getFrame().setLocationRelativeTo(null);//Centers window to screen
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image swordCursor = tk.getImage(game.getClass().getResource("/ui/swordCursor.png"));
			Cursor c = tk.createCustomCursor(swordCursor, new Point(0, 0), "swordCursor");
			game.getFrame().getRootPane().setCursor(c);
			game.getFrame().setIconImage(tk.getImage(game.getClass().getResource("/textures/sheets/jframeIcon.png")));
			//game.getFrame().pack();
			game.isRenderPaused = false;
			//game.getFrame().setUndecorated(true);

			game.setScreen(new MainMenu(game));



		}
		if(game.getMouse().inBounds(backButtX, backButtY, 64, 32) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new OptionsScreen(game));
		}
		
		game.getMouse().update();		
	}

	@Override
	public void paint(double deltaTime) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		g.drawImage(Assets.backButton, backButtX, backButtY, null);
		
		
		if(game.getMouse().inBounds(res1X, 12, 128, value) )
		{
			g.setColor(Color.RED);
			g.drawString("Fullscreen (This will scan your PC for the highest supported resolution and set it.)", res1X, res1Y);
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString("Fullscreen (This will scan your PC for the highest supported resolution and set it.)", res1X, res1Y);
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
