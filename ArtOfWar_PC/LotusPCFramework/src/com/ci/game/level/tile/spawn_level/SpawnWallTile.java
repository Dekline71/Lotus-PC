package com.ci.game.level.tile.spawn_level;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.tile.Tile;

public class SpawnWallTile extends Tile 
{

	public SpawnWallTile(Sprite sprite) 
	{
		super(sprite);
		
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
}
