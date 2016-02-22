package com.ci.game.entity.buildings;

import com.ci.game.graphics.Sprite;

public class CastleWallV extends BuildingEntity
{
	private int buildingHealth;
	
	public CastleWallV()
	{
		//super();
		this.buildingHealth = 100;
	}
	
	public CastleWallV(int x, int y)
	{
		super(Sprite.stoneWallV, x, y);
	}
	
	public void update()
	{
		if(getHealth() <= 0)
		{
			die();
		}
	}
}
