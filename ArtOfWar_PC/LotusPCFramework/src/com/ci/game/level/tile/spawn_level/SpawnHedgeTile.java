package com.ci.game.level.tile.spawn_level;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.tile.Tile;

public class SpawnHedgeTile extends Tile 
{

	public SpawnHedgeTile(Sprite sprite) 
	{
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public void render (int x, int y, Camera screen)
	{
		//Do render tile here!
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid()
	{
		return true;
	}
	
	public boolean breakable()
	{
		return true;
	}
}
