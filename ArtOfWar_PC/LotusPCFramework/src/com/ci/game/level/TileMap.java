package com.ci.game.level;

import java.util.ArrayList;

import com.ci.game.level.tile.Tile;

public class TileMap 
{
	private int width, height;
	private static ArrayList<Tile> walls = new ArrayList<Tile>();
	private Tile[][] tileGrid;
	
	public TileMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		setTileGrid(new Tile[width][height]);
	}
	
	public TileMap()
	{
		
	}
	
	ArrayList<Tile> in_bounds(ArrayList<Tile> r) // get cur position in grid struct
	{
		ArrayList<Tile> newR = new ArrayList<Tile>();
		// is each item in index a part of the map?
		for(int i = 0; i < r.size(); i++)
		{
			int x = r.get(i).getTileX();
			int y = r.get(i).getTileY();
			if( x < 0 || x < width)// if x pos of tile is off grid, remove tile
			{
				r.remove(i);
			}
			else if (y < 0 || y < height)// if y pos of tile is off grid, remove tile
			{
				r.remove(i);
			}
			else if(r.get(i) != null)
			{
				// add instance to newR list
				newR.add(r.get(i));
			}
		}
		
		return newR;
	}
	
	public boolean passable(Tile r)// check for walls/solid objects
	{
		ArrayList<Tile> newR = new ArrayList<Tile>();
		boolean isSolid = false;
		for(int w = 0; w < getWalls().size(); w++)// lopp thru walls
		{
			System.out.println("wX: " + getWalls().get(w).getTileX() + "wY: " + getWalls().get(w).getTileY() +"X: " +  r.getTileX()+ " Y: " + r.getTileY());
			if(r.getTileX() == getWalls().get(w).getTileX() && r.getTileY() == getWalls().get(w).getTileY())
			{
				isSolid = true;
			}
			else
			{
				isSolid = false;
			}
		}
		
		return isSolid;
	}
	
	public ArrayList<Tile> neighbors(Tile currentTile)// get neighbor for current Tile
	{
		int x, y;
		
		x = currentTile.getTileX();
		y = currentTile.getTileY();
		
		// find neighbor tiles from cur tile
		
		ArrayList<Tile> results = new ArrayList<Tile>();
		ArrayList<Tile> t = new ArrayList<Tile>();
		
		// Right
		if(x < this.width-1)
		{
			if(!getTileGrid()[x+1][y].solid())// if passable add to list
			{
				t.add(getTileGrid()[x+1][y]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if(y < this.height-1)
		{
			if(!getTileGrid()[x][y+1].solid())
			{
				t.add(getTileGrid()[x][y+1]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if( x > 0)
		{
			if(!getTileGrid()[x-1][y].solid())
			{
				t.add(getTileGrid()[x-1][y]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		if(y > 0)
		{
			if(!getTileGrid()[x][y-1].solid())
			{
				t.add(getTileGrid()[x][y-1]);
			}
			else
			{
				System.out.println("solid");
			}
		}
		
		return t;
	}
	
	public Tile[][] getTileGrid()
	{
		return this.tileGrid;
	}
	
	public void setTileGrid(Tile[][] tileGrid)
	{
		this.tileGrid = tileGrid;
	}
	
	public int cost(Tile c, Tile n)
	{
		return Math.abs(c.getTileX() - n.getTileX()) + Math.abs(c.getTileY() - n.getTileY());
	}
	
	public ArrayList<Tile> getWalls()
	{
		return this.walls;
	}
	
	public void setWalls(ArrayList<Tile> walls)
	{
		this.walls = walls;
	}
	
}
