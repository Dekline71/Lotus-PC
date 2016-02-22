package com.ci.game.entity.projectile;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class TestProjectile extends Projectile
{
	public TestProjectile(int x, int y, double dir)
	{
		super(x, y, dir);
		range = 200;
		speed = 2;
		damage = 20;
		//rateOfFire = 15;
		sprite = Sprite.grass;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update()
	{
		move();
	}
	
	protected void move()
	{
		x += nx;
		y += ny;
	}
	
	public void render(Camera screen)
	{
		screen.renderProjectile((int)x - 8,(int) y, this);
	}
}
