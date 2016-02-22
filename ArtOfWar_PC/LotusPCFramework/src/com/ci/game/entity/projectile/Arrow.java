package com.ci.game.entity.projectile;

import com.ci.game.entity.Entity;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class Arrow extends Projectile
{
	
	public Arrow(int x, int y, double ang, int dir, Entity e )
	{
		super(x, y, ang, dir, e);
	}
	
	public void update()
	{
		if(direction == 0)
		{
			if(y <= this.target1.getCenter64X() * 64)
			{
				//this.isVisible = false;
			}
		}
		else if(direction == 1)
		{
			if(x >= this.target1.getCenter64Y() * 64)
			{
				//this.isVisible = false;
			}
		}
		else if(direction == 2)
		{
			if(y >= this.target1.getCenter64X() * 64)
			{
				//this.isVisible = false;
			}
		}
		else if(direction == 3)
		{
			if(x <= this.target1.getCenter64Y() * 64)
			{
				//this.isVisible = false;
			}
		}
		move();
	}
	
	protected void move()
	{
		if(direction == 0)// up
		{
			setImage(Sprite.arrowU.getLotus());

			y-=0.01;
			setPixel64Y((int) y);
		}
		else if(direction == 1)// right
		{
			setImage(Sprite.arrowR.getLotus());

			x+=.01;
			setPixel64X((int) x);
		}
		else if(direction == 2)// down
		{
			setImage(Sprite.arrowD.getLotus());

			y+=.01;
			setPixel64Y((int) y);
		}
		else if(direction == 3)// left
		{
			setImage(Sprite.arrowL.getLotus());

			x-=.01;
			setPixel64X((int) x);
		}
		
		//x += nx;
		//y += ny;
	}
	
	public void render(Camera screen)
	{
		screen.renderProjectileEntity((int) x,(int) y, this);
	}
	
}
