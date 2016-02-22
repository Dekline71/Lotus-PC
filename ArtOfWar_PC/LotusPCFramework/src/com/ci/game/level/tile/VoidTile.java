package com.ci.game.level.tile;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;

public class VoidTile extends Tile
{
	public VoidTile(Sprite sprite)
	{
		super(sprite);
	}
		
	public void render(int x, int y, Camera screen)
	{
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid()
	{
		return true;
	}
}
