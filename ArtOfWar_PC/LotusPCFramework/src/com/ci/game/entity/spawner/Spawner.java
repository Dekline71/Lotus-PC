package com.ci.game.entity.spawner;

import com.ci.game.entity.Entity;
import com.ci.game.entity.particle.Particle;
import com.ci.game.level.Level;

public class Spawner extends Entity
{

	
	public Spawner(int x, int y) {
		//super(x, y);
		// TODO Auto-generated constructor stub
	}

	public enum Type
	{
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level)
	{
		super(x, y, type, amount, level);
		init(level);
		setPixel32X( x);
		setPixel32Y (y);
		this.type = type;
		
		
	}
}
