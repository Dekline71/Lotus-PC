package com.ci.game.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.ci.audio.Audio;
import com.ci.game.entity.Entity;
import com.ci.game.entity.particle.Particle;
import com.ci.game.entity.projectile.Projectile;
import com.ci.game.entity.projectile.WizardProjectile;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public abstract class Mob extends Entity
{


	public Mob(int x, int y) 
	{
		//super(x, y);
		// TODO Auto-generated constructor stub
	}

	protected Sprite sprite;
	protected int direction = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	
	protected enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}
	
	
	public void move(int xAxis, int yAxis)
	{
		if(xAxis != 0 && yAxis != 0)// if on axis
		{
			// run for each axis, for seperate collision
			move(xAxis,0);
			move(0,yAxis);
			return;
		}
		
		if (xAxis > 0){direction = 1;}// Right
		if (xAxis < 0){direction = 3;}// Left
		if (yAxis > 0){direction = 2;}// Down
		if (yAxis < 0){direction = 0;}// Up
		
		
		/*if(!collision(xAxis, yAxis))// if no collision, move in direction
		{				
			pixel32X += xAxis;
			setPixel32Y() += yAxis;
		}
		else 
		{
			Particle p =  new Particle(pixel32X, pixel32Y, 50);
			level.add(p);
			//Sound.hit.play();
			
		}*/
	}
	
	public abstract void update();
		
	
	protected void shoot(int x, int y, double dir)
	{
		//dir = dir * (180 / Math.PI);
		//System.out.println("Angle: " + dir);
		Projectile p = new WizardProjectile(x, y, dir);// Create new projectile
		level.add(p);// Add projectile to level
	}
	
	/*private boolean collision(int xAxis, int yAxis)// check future positions
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = ((pixel32X+xAxis) + c % 2 * 12 - 7) / 16;
			int yt = ((pixel32Y+yAxis)+ c / 2 * 12 + 3) / 16;
		
		if (level.getTile(xt, yt).solid()){ solid = true;}// check future x,y locations for solids
		}
		return solid;
	}
	*/
	public abstract void render(Camera screen);

}
