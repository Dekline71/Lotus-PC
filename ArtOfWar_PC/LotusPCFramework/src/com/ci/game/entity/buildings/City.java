package com.ci.game.entity.buildings;

import java.util.Random;

import com.ci.game.GameManager;
import com.ci.game.entity.Entity;
import com.ci.game.entity.Entity.EntityType;
import com.ci.game.entity.Entity.UnitClass;
import com.ci.game.entity.armyunit.Archer;
import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.entity.armyunit.Spearman;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class City extends BuildingEntity
{
	private int curTurnOnProduction;
	private String cityName;
	private int curUnitInProduction;

	
	public City(){}
	
	public City(int x, int y)
	{
		this.target2 = null;
		this.target1 = null;
		setHealth(100);
		this.setCenter32X(x);
		this.setCenter32Y(y);
		this.setCenter64X(x / 2);
		this.setCenter64Y(y / 2);
		
		setPixel32X( x * 32);
		setPixel32Y ( y * 32);
		setPixel64X ( x * 64);
		setPixel64Y ( y * 64);
		this.isVisible = true;
	}

	public City (String cityName, Sprite sprite, int x, int y)
	{
		setSprite(sprite);
		setImage(sprite.getLotusImage().getImage());
		this.cityName = cityName;
		this.target2 = null;
		this.target1 = null;
		setHealth(100);
		this.setCenter32X(x);
		this.setCenter32Y(y);
		this.setCenter64X(x / 2);
		this.setCenter64Y( y / 2);
		
		setPixel32X(x * 32);
		setPixel32Y(y * 32);
		setPixel64X(x * 64);
		setPixel64Y(y * 64);
		this.isVisible = true;
		this.entityType = EntityType.P_Building;
	}
	
	public void render(Camera screen)
	{
		screen.renderEntity(this);
	}
	
	public void update()
	{
		if(getHealth() <= 0)
		{
			die();
		}
	}

	public void die() 
	{
		setVisible(false);		
	}

	public void nextTurnUpdate() 
	{
		Random r = new Random();
		// Update variables for next turn
		if(	this.curTurnOnProduction > 0)
		{
			this.curTurnOnProduction--;
		}
		else
		{
			this.curUnitInProduction = -1;
		}
		
		if( this.curTurnOnProduction == 0 && this.curUnitInProduction == 1)
		{
			System.out.println("Archerspawned");
			
			// Spawn Archer troop
			
			ArmyUnit sorc;
			int last = r.nextInt(25);
	
			if(last < 10)
			{
				last = 19;
			}
			sorc = new Archer(last, 16, this.level, Entity.UnitType.Ranged, UnitClass.Archer);
			
			System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

			GameManager.level.getPlayerArmyEntities().add(sorc);
			GameManager.gold -= 5;
		}
		else if( this.curTurnOnProduction == 0 && this.curUnitInProduction == 2)
		{
			System.out.println("Spearman spawned");
			
			// Spawn spearman troop
			
			Entity e;
			ArmyUnit sorc;
			int last = r.nextInt(25);
	
			if(last < 10)
			{
				last = 19;
			}
			sorc = new Spearman(last, 16, this.level, Entity.UnitType.Melee, UnitClass.Spearman);
			
			System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

			GameManager.level.getPlayerArmyEntities().add(sorc);
			GameManager.gold -= 5;
		}
	}
	
	public int getCurTurnOnProduction() 
	{
		return curTurnOnProduction;
	}

	public void setCurTurnOnProduction(int curTurnOnProduction) 
	{
		this.curTurnOnProduction = curTurnOnProduction;
	}

	public String getCityName() 
	{
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getSelectedUnit() {
		return curUnitInProduction;
	}

	public void setSelectedUnit(int selectedUnit) {
		this.curUnitInProduction = selectedUnit;
	}
}
