package com.ci.game.level.tile;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class RockTile extends Tile 
{

	public RockTile(Sprite sprite) 
	{
		super(sprite);
		
	}
	
	public void render (int x, int y, Camera screen)
	{
		//Do render tile here!
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid(){return true;}// tile is not solid by default.

}
