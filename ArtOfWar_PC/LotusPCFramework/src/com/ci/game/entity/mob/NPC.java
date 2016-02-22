package com.ci.game.entity.mob;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class NPC extends Mob
{
	private int xPos, yPos,lastSec = 0;//detect entity/npc position/movement
	private int anim = 0;
	private int seconds = 0;
	private boolean walking = false;
	private static long start;
	
	
	
	public NPC(int x, int y)
	{
		super(x,y);
		setPixel32X(x);
		setPixel32Y( y);
		start = 0;
		sprite = Sprite.npc_forward;
	}
	
	public void update()
	{		
		//lastSec = 0;
		xPos = yPos = 0;
		start = System.currentTimeMillis();
				
		
		//if (anim < 7500){anim++;}
		//if (input.up){ yPos--;}
		//if (input.down){ yPos++;}
		//if (input.left){ xPos--;}
		//if (input.right){ xPos++;}
				
		 //seconds = (int) ((start) / 1000) % 60;
		 	
		//System.out.println("NPC has been active for: " + seconds + " seconds.");
		//lastSec = (seconds - lastSec) ;
		//System.out.println("Counting: " + lastSec + " seconds.");
				
		//Move AI
	/*
		//if(y > 800)
		//{
			//if(x < 440)
			//{				
				//if(y > 900)
				{
					xPos++;//Right
				}
				else
				{
					yPos++;//down	
				}
				
			}
			else
			{
				yPos--;//up
			}
		
		}
		else if(y <= 800)
		{
			if( x > 320)
			{
				xPos--;//left
			}
			else if(x <= 400)
			{				
				yPos++;//down
				
			}
			
		}
			
		lastSec = seconds;				
	
		if (xPos != 0 || yPos != 0)
		{
			
				move(xPos, yPos);
				walking = true;
		}
		else
		{
			walking = false;
		}			
		*/
	}
		
	public int MoveRight(int amount)
	{
		
		return xPos += amount;
				
	}
	
	public int MoveLeft(int amount)
	{
		return xPos -= amount;
	}
	
	public int turnLeft(int xPos)
	{
		return xPos--;
	}
			
	public void render(Camera screen)
	{
		if(direction == 0)
		{
			sprite = Sprite.npc_forward;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.npc_forward_1;
				}
				else
				{
					sprite = Sprite.npc_forward_2;
				}
			}
		}
		
		if(direction == 1)
		{
			sprite = Sprite.npc_right;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.npc_right_1;
				}
				else
				{
					sprite = Sprite.npc_right_2;
				}
			}
		}
			
		if(direction == 2)
		{
			sprite = Sprite.npc_back;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.npc_back_1;
				}
				else
				{
					sprite = Sprite.npc_back_2;
				}
			}
		}
		
		if(direction == 3)
		{ 
			sprite = Sprite.npc_left;
			if (walking)
			{
				if (anim % 20 > 10)
				{
					sprite = Sprite.npc_left_1;
				}
				else
				{
					sprite = Sprite.npc_left_2;
				}
			}
		}
		
		//screen.renderNPC(pixel32X - 16, pixel32Y - 16, sprite);
	
	}
	
	
	
}
