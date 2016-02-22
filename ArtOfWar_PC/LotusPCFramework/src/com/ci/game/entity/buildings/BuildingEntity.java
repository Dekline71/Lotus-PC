package com.ci.game.entity.buildings;

import com.ci.game.entity.Entity;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class BuildingEntity extends Entity
{
	enum BuildingSizeType
	{
		grid32, grid64, grid128, grid128x64;
	}
	
	//public Rect buildingColliderBounds = new Rect(0,0,0,0);

	public BuildingEntity()
	{
		
	}
	
	public BuildingEntity(int x, int y)
	{
		this.target2 = null;
		this.target1 = null;
		setHealth(100);
		this.setCenter32X(x);
		this.setCenter32Y(y);
		this.setCenter64X(x / 2);
		this.setCenter64Y( y / 2);
		
		setPixel32X( x * 32);
		setPixel32Y ( y * 32);
		setPixel64X ( x * 64);
		setPixel64Y ( y * 64);
		this.isVisible = true;
	}
	
	public BuildingEntity(Sprite sprite, int x, int y)
	{
		//super(100, x, y);
		
		setSprite(sprite);
		setImage(sprite.getLotusImage().getImage());
		this.target2 = null;
		this.target1 = null;
		setHealth(100);
		this.setCenter32X(x);
		this.setCenter32Y(y);
		this.setCenter64X(x / 2);
		this.setCenter64Y( y / 2);
		
		setPixel32X( x * 32);
		setPixel32Y ( y * 32);
		setPixel64X ( x * 64);
		setPixel64Y ( y * 64);
		this.isVisible = true;
		this.entityType = EntityType.P_Building;
	}
	
	public void render(Camera screen)
	{
		screen.renderEntity(getCenter64X(), getCenter64Y() , this);
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
		// Update variables for next turn
		
	}

}
