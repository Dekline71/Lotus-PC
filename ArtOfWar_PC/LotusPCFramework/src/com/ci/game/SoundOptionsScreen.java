package com.ci.game;

import java.awt.Graphics;

import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Sprite;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class SoundOptionsScreen extends Screen
{
	int leftArrowButtX = game.getFrame().getWidth()/2+25; 
	int leftArrowButtY = game.getFrame().getHeight()/2+19; 
	int rightArrowButtX = game.getFrame().getWidth()/2+75; 
	int rightArrowButtY = game.getFrame().getHeight()/2+19; 
	int masterButtX = game.getFrame().getWidth()/2+35;
	int masterButtY = game.getFrame().getHeight()/2-25;
	int masterValueButtX = game.getFrame().getWidth()/2+58;
	int masterValueButtY =  game.getFrame().getHeight()/2+25;


	int backButtX = 0;
	int backButtY = game.getFrame().getHeight()-64;

	public SoundOptionsScreen(LotusRenderView game) 
	{
		super(game);
	}

	@Override
	public void update(double deltaTime) 
	{
		if(game.getMouse().inBounds(backButtX, backButtY, 64, 32) && game.getMouse().isMouseClicked())
		{
			game.setScreen(new MainMenu(game));
		}	
		
		if(game.getMouse().inBounds(leftArrowButtX, leftArrowButtY, 32, 32) && game.getMouse().isMouseClicked())
		{
			if(masterVol == 0)
			{		
				clipVol = -75.0f;
				reinitAudio(clipVol);
				masterVol = 0;
			}
			else if (masterVol == 1)
			{
				clipVol = -75.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if (masterVol == 2)
			{
				clipVol = -33.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if(masterVol == 3) 
			{
				clipVol = -21.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if(masterVol == 4)
			{
				clipVol = -18.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if (masterVol ==  5)
			{
				clipVol = -12.0f; 
				reinitAudio(clipVol);
				masterVol--;
			}
			else if(masterVol == 6)
			{
				clipVol = -4.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if (masterVol == 7) 
			{
				clipVol = -1.50f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if (masterVol == 8)
			{
				clipVol = 0.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if(masterVol == 9)
			{
				clipVol = 3.0f;
				reinitAudio(clipVol);
				masterVol--;
			}
			else if(masterVol == 10)
			{
				clipVol = 6.0f;
				reinitAudio(clipVol);
				masterVol--;
		
			}
		}
		else if(game.getMouse().inBounds(rightArrowButtX, rightArrowButtY, 32, 32) && game.getMouse().isMouseClicked())
		{
			if(masterVol == 0)
			{			
				clipVol = -75.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if (masterVol == 1)
			{
				clipVol = -75.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if (masterVol == 2)
			{
				clipVol = -32.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if(masterVol == 3) 
			{
				clipVol = -21.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if(masterVol == 4)
			{
				clipVol = -18.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if (masterVol ==  5)
			{
				clipVol = -12.0f; 
				reinitAudio(clipVol);
				masterVol++;
			}
			else if(masterVol == 6)
			{
				clipVol = -4.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if (masterVol == 7) 
			{
				clipVol = -1.5f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if (masterVol == 8)
			{
				clipVol = 0.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if(masterVol == 9)
			{
				clipVol = 3.0f;
				reinitAudio(clipVol);
				masterVol++;
			}
			else if(masterVol == 10)
			{
				clipVol = 6.0f;
				reinitAudio(clipVol);
			
			}
		}
		
		game.getMouse().update();

	}

	@Override
	public void paint(double deltaTime)
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		
		g.drawImage(Assets.menuScrollBackground,game.getFrame().getWidth()/2+22, game.getFrame().getHeight()/2 -18, null);

		String s = new String();
		int mv = (int) masterVol;
		s = String.valueOf(mv);


			for(int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				
				if(c == '0')
				{
					g.drawImage(Sprite.num_0.getLotus(), masterValueButtX + (16 * i), masterValueButtY, null);
				}
				else if (c == '1')
				{
					g.drawImage(Sprite.num_1.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '2')
				{
					g.drawImage(Sprite.num_2.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '3')
				{
					g.drawImage(Sprite.num_3.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '4')
				{
					g.drawImage(Sprite.num_4.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if (c == '5')
				{
					g.drawImage(Sprite.num_5.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if (c == '6')
				{
					g.drawImage(Sprite.num_6.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '7')
				{
					g.drawImage(Sprite.num_7.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '8')
				{
					g.drawImage(Sprite.num_8.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
				else if(c == '9')
				{
					g.drawImage(Sprite.num_9.getLotus(), (masterValueButtX) + (16 * i), masterValueButtY, null);
				}
			}


		g.drawImage(Assets.backButton, backButtX, backButtY, null);


		

		if(game.getMouse().inBounds(leftArrowButtX, leftArrowButtY, 32, 32))
		{
			g.drawImage(Sprite.leftArrowSe.getLotus(), leftArrowButtX, leftArrowButtY, null);
		}
		else
		{
			g.drawImage(Sprite.leftArrowDe.getLotus(), leftArrowButtX, leftArrowButtY, null);
		}
		
		if(game.getMouse().inBounds(rightArrowButtX, rightArrowButtY, 32, 32))
		{
			g.drawImage(Sprite.rightArrowSe.getLotus(), rightArrowButtX, rightArrowButtY, null);
		}
		else
		{
			g.drawImage(Sprite.rightArrowDe.getLotus(), rightArrowButtX, rightArrowButtY, null);
		}
		
		g.drawImage(Sprite.masterVolString.getLotus(), (masterButtX), masterButtY, null);

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
