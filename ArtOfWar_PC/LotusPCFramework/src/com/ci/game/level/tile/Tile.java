package com.ci.game.level.tile;

import java.awt.image.BufferedImage;

import com.ci.game.entity.Node;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.tile.spawn_level.SpawnFloorTile;
import com.ci.game.level.tile.spawn_level.SpawnGrassTile;
import com.ci.game.level.tile.spawn_level.SpawnHedgeTile;
import com.ci.game.level.tile.spawn_level.SpawnWallTile;
import com.ci.game.level.tile.spawn_level.SpawnWaterTile;
import com.ci.lotusFramework.Image;

public class Tile 
{
	// Position & size members
	public int tileX, tileY;
	private int SIZE;
	
	public Sprite sprite;
	private BufferedImage image;
	
	// AI members
	private Node node;
	private Tile cameFrom;
	
	private boolean isSolid;
		
	public static Tile grass = new GrassTile(Sprite.grass);// create grass tile, grass extends tile
	public static Tile flower = new GrassTile(Sprite.flower);
	public static Tile grass_2 = new GrassTile(Sprite.grass_2);
	public static Tile voidTile =  new Tile(Sprite.voidSprite, false);
	public static Tile rock = new RockTile(Sprite.rock);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	public static Tile sand = new Tile(Sprite.sand);
	public static Tile shrub = new Tile(Sprite.shrub);
	
	public static final int col_spawn_grass = 0xFF4CFF00;//green
	public static final int col_spawn_hedge = 0;
	public static final int col_spawn_water = 0xFF0094FF;
	public static final int col_spawn_wall1 =  0xFF808080;//gray
	public static final int col_spawn_wall2 = 0xFF000000;//black
	public static final int col_spawn_floor = 0xFF7F6A00;//brown
	
	public static String col_grass = "ff15ff00";
	public static String col_shrub = "ff267f00";
	public static String col_dirt  = "ffffd800";
	
	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
		this.image = sprite.getLotus();
	}
	
	public Tile(Sprite sprite, boolean b)
	{
		this.sprite = sprite;
		this.isSolid = b;
		this.image = sprite.getLotus();

	}
	
	public Tile(int x, int y)
	{
		this.tileX = x;
		this.tileY = y;
		setSolid(false);
	}
	
	public Tile(boolean solid, int size, int x, int y, Sprite sprite)
	{
		this.isSolid = solid;
		this.setSprite(sprite);
		//image = this.sprite.getLotus();
		setSize(size);
		this.tileX = x;
		this.tileY = y;
	}
	
	public Tile(boolean isSolid, Sprite sprite)
	{
		setSize(sprite.SIZE);
		this.setSprite(sprite);
		this.isSolid = isSolid;
	}
	
	public int getSize()
	{
		return this.SIZE;
	}
	
	public int getTileX()
	{
		return this.tileX;	
	}
	
	public int getTileY()
	{
		return this.tileY;
	}
	
	public void setSize(int size)
	{
		this.SIZE = size;
	}
	
	public Tile getCameFrom()
	{
		return this.cameFrom;
	}
	
	public Tile setCameFrom(Tile cameFrom)
	{
		this.cameFrom = cameFrom;
		return this.cameFrom;
	}
	
	public void setSolid(boolean b)
	{
		this.isSolid = b;
	}
	
	public void render (int x, int y, Camera screen)
	{
		screen.renderTile(x * 32, y * 32, this);
	}
	
	public boolean solid(){return this.isSolid;}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
