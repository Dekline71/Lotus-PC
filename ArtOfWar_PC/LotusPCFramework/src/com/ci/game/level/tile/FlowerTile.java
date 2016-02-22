package com.ci.game.level.tile;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class FlowerTile extends Tile 
{

	public FlowerTile(Sprite sprite) 
	{
		super(sprite);
		
	}

	public void render (int x, int y, Camera screen)
	{
		//Do render tile here!
		screen.renderTile(x << 4, y << 4, this);
	}
}


