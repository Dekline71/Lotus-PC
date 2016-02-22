package com.ci.game.entity.buildings;

import com.ci.game.graphics.Sprite;

public class Barrack extends BuildingEntity
{
	private int buildingHealth;
	
	public Barrack()
	{
		super();
		this.setBuildingHealth(100);
	}
	
	public Barrack(Sprite sprite, int x, int y)
	{
		super(sprite, x, y);
	}
	

	public int getBuildingHealth() {
		return buildingHealth;
	}

	public void setBuildingHealth(int buildingHealth) {
		this.buildingHealth = buildingHealth;
	}
}
