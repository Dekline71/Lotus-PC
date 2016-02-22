package com.ci.game.entity;

import com.ci.game.entity.Entity.UnitType;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;

public class Resource extends Entity
{
	private ResourceType resourceType;
	
	public enum ResourceType 
	{
		Gold, Food
	}
	
	Resource()
	{
		super();
	}
	
	public Resource(int x, int y)
	{
		super(x, y, EntityType.Resource);
		//setImage(Sprite.goldResource.getLotus());

	}
	
	public Resource (int x, int y, Level l, UnitType t, ResourceType rt)
	{
		//super(10, 2, 2, x, y, t, EntityType.Resource);
		this.resourceType = rt;
	}
	
	public void update()
	{
		
	}
	
	public void render(Camera screen)
	{
		screen.render64Entity( this.getCenter64X(), this.getCenter64Y(), this);
	}

	public ResourceType getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	
}
