package com.ci.game.entity.particle;

import com.ci.game.entity.Entity;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class Particle extends Entity
{
	
	private Sprite sprite;

	private int life;
	private int time = 0;
	
	protected double xx, yy, xa, ya;// Amount of pixels it moves in relative axis
	
	public Particle (int x, int y, int life)
	{
		super(x,y,life);
		this.life = life + (random.nextInt(20) - 10);
		setPixel32X (x);
		setPixel32Y( y);
		setPixel64X(x);
		setPixel64Y(y);
		this.xx = x;
		this.yy = y;
		//sprite = Sprite.particle_normal;
		setImage(Assets.bloodParticle);
		this.xa = random.nextGaussian();// returns number between -1 and 1
		this.ya = random.nextGaussian();
	}
	
	
	
	public void update()
	{
		time++;
		if(time >= 7400) time = 0;
		if(time > life)
		{
			remove();
			this.isVisible = false;
		}
		
		this.xx += xa;
		this.yy += ya;
		setPixel64X((int)this.xx);
		setPixel64Y((int)this.yy);
	}
	
	public void render(Camera screen)
	{
		screen.renderParticleEntity((int) xx,(int) yy, this);
	}
}
