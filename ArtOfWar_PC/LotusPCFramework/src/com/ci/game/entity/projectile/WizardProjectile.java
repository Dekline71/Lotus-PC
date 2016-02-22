package com.ci.game.entity.projectile;

import com.ci.game.entity.spawner.ParticleSpawner;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;


public class WizardProjectile extends Projectile
{
	public static final int FIRE_RATE = 15;//Higher the number, the slower it fires
	
	public WizardProjectile(int x, int y, double dir)
	{
		super(x, y, dir);
		range = random.nextInt(100) + 150;
		speed = 4;
		damage = 20;
		//rateOfFire = 15;
		sprite = Sprite.projectile_wizard;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update()
	{
		if(level.tileCollision(x, y, nx, ny, 7))
		{
			level.add(new ParticleSpawner((int) x, (int) y, 33, 10, level));
			remove();
		}
		
		move();
	}
	
	protected void move()
	{
		x += nx;
		y += ny;
		
		if(distance() > range)
		{
			remove();
		}
	}
	
	private double distance()
	{
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin- x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
	public void render(Camera screen)
	{
		screen.renderProjectile((int)x - 8,(int) y, this);
	}
}
