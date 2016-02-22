package com.ci.game.entity.projectile;

import java.util.Random;

import com.ci.game.entity.Entity;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class Projectile extends Entity
{
	protected final int xOrigin, yOrigin;// spawn
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;//update
	protected double distance;
	protected double speed, range, damage;
	protected final Random random = new Random();
	protected int direction;
	
	public Projectile(int x, int y, double ang)
	{
		super(x, y, ang);
		xOrigin = x;
		yOrigin = y;
		angle = ang;
		//direction = dir;
		this.x = x;
		this.y = y;
	}
	
	public Projectile(int x, int y, double ang, int dir, Entity e)
	{
		super(x, y, ang);
		xOrigin = x;
		yOrigin = y;
		angle = ang;
		direction = dir;
		this.x = x;
		this.y = y;
		this.target1 = e;
	}
	
	//public Projectile() {}

	public Sprite getSprite()
	{
		return sprite;
	}
	
	public int getSpriteSize()
	{
		return sprite.SIZE;
	}
	
	public void update()
	{
		move();
	}
	
	protected void move()
	{
		if(direction == 0)
		{
			
		}
		else if(direction == 1)
		{
			
		}
		else if(direction == 2)
		{
			
		}
		else if (direction == 3)
		{
			
		}
		//x += nx;
		//y += ny;
	}
	
	public void render(Camera screen)
	{
		screen.renderProjectileEntity((int) x,(int) y, this);
	}
}
