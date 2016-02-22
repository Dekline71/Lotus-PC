package com.ci.game.entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.entity.buildings.BuildingEntity;
import com.ci.game.entity.spawner.Spawner.Type;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;
import com.ci.lotusFramework.Image;

public class Entity 
{
	//public int x, y;//current position
	protected int center32X, center64X;
	protected int center32Y, center64Y;
	
	private int pixel32X, pixel64X;// store pos on screen in pixels
	private int pixel32Y, pixel64Y;
	private int health;
	protected int gridMoves;
	
	protected Entity target1;
	protected Entity target2;
	protected Level level; // instance of level entity exists in
	protected boolean isVisible;
	
	public static ArrayList<ArmyUnit> playerArmyEntities = new ArrayList<ArmyUnit>(); 
	public static ArrayList<Enemy> enemyArmyEntities = new ArrayList<Enemy>(); 
	public static ArrayList<BuildingEntity> playerBuildingEntities = new ArrayList<BuildingEntity>(); 
	private BufferedImage image;
	protected Sprite sprite;
	
	private boolean removed = false;// has it been removed from level?

	protected final Random random = new Random();			
	
	private int startX;
	private int startY;
	
	private int posInLink;
	private boolean isTargetFound;
	protected int atkRating;
	protected int defRating;
	protected int xp;
	protected int vetLvl;

	public static enum UnitClass {Swordsman, Spearman, Archer}
	
	public static enum UnitType { Ranged, Hybrid, Melee } 
	
	public static enum EntityType { P_Building, Player, Enemy, Resource, Void}
	
	public static enum Direction {LEFT,RIGHT,UP,DOWN}
	
	protected UnitType unitType;
	protected EntityType entityType;
	protected UnitClass unitClass;
	private Direction dir;
	protected int lastTargetX;
	protected int lastTargetY;

	
	public Entity()	{}
	
	/* 
	 * Target Entity Constructor
	 
	public Entity(int x, int y, int targPosInLink)
	{
		this.center64X = x;
		this.center64Y = y;
		this.pixel64X = x*64;
		this.pixel64Y = x*64;
		this.posInLink = targPosInLink;
	}*/
	
	// Deep Copy constructor
	public Entity(Entity e)
	{
		this.target2 = null;
		this.target1 = null;
		this.xp = e.xp;
		this.vetLvl = e.vetLvl;
		this.health = e.health;
		this.atkRating = e.atkRating;
		this.defRating = e.defRating;
		this.entityType = e.getEntityType();
		this.unitType = e.unitType;
		this.gridMoves = e.getGridMoves();
		this.center32X = e.getCenter32X();
		this.center32Y = e.getCenter32Y();
		this.center64X = e.getCenter64X();
		this.center64Y = e.getCenter64Y();	
		this.posInLink = e.getPosInLink();
		this.setDir(e.getDirection());
		
		this.pixel32X = e.getPixel32X();
		this.pixel32Y = e.getPixel32Y();
		this.pixel64X = e.getPixel64X();
		this.pixel64Y = e.getPixel64Y();
		setVisible(e.isVisible());
	}
	
	// Main Entity init
	public Entity(int x, int y, EntityType t)
	{
		this.entityType = t;
		this.target2 = null;
		this.target1 = null;
		this.health = 100;
		this.center32X = x;
		this.center32Y = y;
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		this.setStartX(x / 2);
		this.setStartY(y / 2);
		
		this.pixel32X = getCenter32X() * 32;
		this.pixel32Y = getCenter32Y() * 32;
		this.pixel64X = getCenter64X() * 64;
		this.pixel64Y = getCenter64Y() * 64;
		this.isVisible = true;
	}
	
	/*
	 * h: health of entity
	 * atk: atk rating of entity
	 * def: def rating of entity
	 * x: x pos 
	 * y: y pos
	 * t: type of unit (melee, ranged, etc)
	 * c: class of unit (archer, axe man)
	 * et: type of entity (building, unit, etc)
	 * d: direction of entity (left, right, up,..etc)
	 */
	public Entity(int h, int atk, int def, int x, int y, UnitType t, UnitClass c, EntityType et, Direction d)
	{
		this.unitClass = c;
		this.entityType = et;
		this.unitType = t;
		this.setDirection(d);
		this.xp = 0;
		this.vetLvl = 1;
		this.atkRating = atk;
		this.defRating = def;
		this.target1 = null;
		this.target2 = null;
		this.health = h;
		
		this.center32X = x;
		this.center32Y = y;
		this.center64X = x / 2;
		this.center64Y = y / 2;
		this.setStartX(x / 2);
		this.setStartY(y / 2);
		
		this.pixel32X = getCenter32X() * 32;
		this.pixel32Y = getCenter32Y() * 32;
		this.pixel64X = getCenter64X() * 64;
		this.pixel64Y = getCenter64Y() * 64;
		this.isVisible = true;
	}
	
	public Entity(int x, int y, double dir)
	{
		this.isVisible = true;
		this.pixel64X = x;
		this.pixel64Y = y;
	}
	
	// old spawner
	protected Entity(int x, int y, Type type, int amount, Level level)
	{
		
	}

	public void update()
	{
		
	}
	
	public void render(Camera screen)
	{
		
	}
	
	public void remove()
	{
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
	public void init(Level level)
	{
		this.level = level;
	}

	public boolean isVisible() 
	{
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	public int getPixel32X()
	{
		return this.pixel32X;
	}
	
	public int getPixel32Y()
	{
		return this.pixel32Y;
	}
	
	public int getPixel64X()
	{
		return this.pixel64X;
	}
	
	public int getPixel64Y()
	{
		return this.pixel64Y;
	}
	
	public int getCenter32X()
	{
		return this.center32X;
	}
	
	public int getCenter32Y()
	{
		return this.center32Y;
	}
	
	public int getCenter64X()
	{
		return this.center64X;
	}
	
	public int getCenter64Y()
	{
		return this.center64Y;
	}

	public int getStartX() 
	{
		return startX;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public Level getLevel() 
	{
		return this.level;
	}
		
	public Entity getTarget()
	{
		return this.target1;
	}
	
	public boolean getisTargetFound()
	{
		return isTargetFound;
	}
	
	public void addHealth(int i)
	{		
		this.health += i;
		if(this.health > 10)
		{
			this.health = 10;
		}
	}

	public void setHealth(int i)
	{
		this.health += i;
	}
	
	public void setStartX(int startX) 
	{
		this.startX = startX;
	}
	
	public void setStartY(int startY) 
	{
		this.startY = startY;
	}
	
	public void setCenter32X(int x)
	{
		this.center32X = x;
	}
	
	public void setCenter32Y(int y)
	{
		this.center32Y = y;
	}
	
	public void setCenter64X(int x)
	{
		this.center64X = x;
	}
	
	public void setCenter64Y(int y)
	{
		this.center64Y = y;
	}
	
	public void setPixel64X(int x)
	{
		this.pixel64X = x;
	}
	
	public void setPixel64Y(int y)
	{
		this.pixel64Y = y;
	}
	
	public void setPixel32X(int x)
	{
		this.pixel32X = x;
	}
	
	public void setPixel32Y(int y)
	{
		this.pixel32Y = y;
	}

	public void setisTargetFound(boolean b)
	{
		this.isTargetFound = b;
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public void setImage(BufferedImage i)
	{
		this.image = i;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) 
	{
		this.sprite = sprite;
	}		
	
	public static ArrayList getPlayerArmyEntities()
	{
		return playerArmyEntities;
	}
	
	public static ArrayList getEnemyArmyEntities()
	{
		return enemyArmyEntities;
	}
	
	public static ArrayList getPlayerBuildingEntities()
	{
		return playerBuildingEntities;
	}
	
	public void setPosInLink(int i)
	{
		this.posInLink = i;
	}

	public int getPosInLink() 
	{
		return this.posInLink;
	}
	
	public int getGridMoves()
	{
		return this.gridMoves;
	}
	
	public UnitType getUnitType()
	{
		return this.unitType;
	}
	
	public void setTarget(Entity t)
	{
		this.target1 = t;
	}

	public void setGridMoves(int curMoves)
	{
		if (curMoves > 3)
		{
			this.gridMoves = 3;
		}
		else if (curMoves < 0)
		{
			curMoves = 0;
		}
		else
		{
			this.gridMoves = curMoves;
		}
	}
	
	public int getAtkRating()
	{
		return this.atkRating;
	}
	
	public int getDefRating()
	{
		return this.defRating;
	}
	
	public int getXP()
	{
		return this.xp;
	}
	
	public int getVetLvl()
	{
		return this.vetLvl;
	}
	
	public UnitClass getUnitClass() 
	{
		return this.unitClass;
	}

	public void setUnitClass(UnitClass unitClass) 
	{
		this.unitClass = unitClass;
	}

	public EntityType getEntityType() 
	{
		return this.entityType;
	}

	public void setEntityType(EntityType entityType) 
	{
		this.entityType = entityType;
	}

	public Direction getDirection() 
	{
		return getDir();
	}

	public void setDirection(Direction direction)
	{
		this.setDir(direction);
	}

	public Direction getDir() 
	{
		return dir;
	}

	public void setDir(Direction dir) 
	{
		this.dir = dir;
	}
	
	public int getLastTargetX()
	{
		return this.lastTargetX;
	}
	
	public int getLastTargetY()
	{
		return this.lastTargetY;
	}
	
}
