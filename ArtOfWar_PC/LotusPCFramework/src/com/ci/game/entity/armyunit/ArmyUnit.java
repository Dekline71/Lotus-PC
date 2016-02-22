package com.ci.game.entity.armyunit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.ci.game.GameManager;
import com.ci.game.entity.Entity;
import com.ci.game.entity.Peasant;
import com.ci.game.entity.Resource;
import com.ci.game.entity.Entity.Direction;
import com.ci.game.entity.Entity.UnitClass;
import com.ci.game.entity.Resource.ResourceType;
import com.ci.game.entity.particle.Particle;
import com.ci.game.entity.projectile.Arrow;
import com.ci.game.entity.projectile.Projectile;
import com.ci.game.graphics.Animation;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;
import com.ci.game.level.TileMap;
import com.ci.game.level.tile.Tile;

public class ArmyUnit extends Entity
{
	private Entity curPos = null, goalPos = null;// unit position for red movement arrow
	protected int direction = 0;
	private int movementSpeed;
	
	protected boolean walking = false;
	private boolean isAtkReady = false;
	private boolean searched = false;
	
	private Tile last;
	private boolean canMove;
	protected Animation a;
	private int power;
	
	private LinkedBlockingDeque<Tile> pathToTarget;
	private int targetLinkPos;
	//private int gridMoves; // moves entity can move during each turn/day
	private int timer;
	protected boolean isCombatMode = false;
	protected int anim = 0;
	
	/*protected enum Direction
	{
		LEFT, RIGHT, UP, DOWN
	}*/
	
	protected enum UnitState
	{
		ATTACK, DEFEND, IDLE
	}
	
	public ArmyUnit(){}
	
	// Deep Copy constructor
	public ArmyUnit(Entity e)
	{
		this.target2 = null;
		this.target1 = null;
		
		setHealth(e.getHealth());
		this.atkRating = e.getAtkRating();
		this.defRating = e.getDefRating();
		this.unitType = e.getUnitType();
		this.entityType = e.getEntityType();	
		this.gridMoves = e.getGridMoves();
		this.setDirection(e.getDirection());
		this.setCenter32X(e.getCenter32X());
		this.setCenter32Y(e.getCenter32Y());
		this.setCenter64X( e.getCenter64X());
		this.setCenter64Y(e.getCenter64Y());	
		this.setDirection(e.getDirection());
		this.setPosInLink(e.getPosInLink());
		
		setPixel32X ( e.getPixel32X());
		setPixel32Y ( e.getPixel32Y());
		setPixel64X (e.getPixel64X());
		setPixel64Y (e.getPixel64Y());
		setVisible(e.isVisible());
	}
	
	public ArmyUnit(int x, int y, Level l, UnitType t, UnitClass c)
	{
		super(10, 2, 2, x, y, t, c, EntityType.Player, Direction.DOWN);
		//setHealth(10);
		this.level = l;
		if(c == UnitClass.Archer)
		{
			setSprite(Sprite.archerD);
			setImage(Sprite.archerD.getLotus());
		}
		else
		{
			setSprite(Sprite.peasSpriteD);
			setImage(Sprite.peasSpriteD.getLotus());
		}

		setGridMoves(2);
		direction = 2;
	}

	public ArmyUnit(int x, int y)
	{
		super(x, y, EntityType.Player);
		direction = 2;
	}
	
	public ArmyUnit(int x, int y, Entity t)
	{
		super(x, y, EntityType.Player);
		setHealth(5);
		direction = 2;
		setSprite(Sprite.peasSpriteD);

		setImage(Sprite.peasSpriteD.getLotus());
		setGridMoves(2);
	}	


	
	public void render(Camera screen)
	{
		checkForDirChange();
		
		if(direction == 0)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAnimUp;
					}
					else
					{
						this.a = Assets.peasAnimUp;
					}
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAttackAnimUp;
					}
					else
					{
						this.a = Assets.peasAnimUpAtk;
					}
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 1)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAnimRight;
					}
					else
					{
						this.a = Assets.peasAnimRight;
					}
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAttackAnimRight;
					}
					else
					{
						this.a = Assets.peasAnimRightAtk;
					}
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 2)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAnimDown;
					}
					else
					{
						this.a = Assets.peasAnimDown;
					}
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAttackAnimDown;
					}
					else
					{
						this.a = Assets.peasAnimDownAtk;
					}
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 3)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAnimLeft;
					}
					else
					{
						this.a = Assets.peasAnimLeft;
					}
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					if(this.unitClass == UnitClass.Archer)
					{
						this.a = Assets.archerAttackAnimLeft;
					}
					else
					{
						this.a = Assets.peasAnimLeftAtk;
					}
					setImage(a.getImage());
				}
			}
		}
		//screen.renderArmyEntity(getCenter64X(), getCenter64Y(), this);
		screen.renderArmyEntity( this);

	}
	
	public void checkForDirChange()
	{
		if(direction == 0 || getDir() == Direction.UP)
		{
			direction = 0;
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerU.getLotus());
			}
			else if(this.unitClass == UnitClass.Spearman)
			{
				setImage(Sprite.peasSpriteU.getLotus());
			}
		}	
		else if (direction == 1 || getDir() == Direction.RIGHT) 
		{
			direction = 1;
			//setSprite(Sprite.peasSpriteR);
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerR.getLotus());
			}
			else if(this.unitClass == UnitClass.Spearman)
			{
				setImage(Sprite.peasSpriteR.getLotus());
			}
		}
		else if (direction == 3 || getDir() == Direction.LEFT) 
		{
			direction = 3;
			//setSprite(Sprite.peasSpriteL);
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerL.getLotus());
			}	
			else if(this.unitClass == UnitClass.Spearman)
			{
				setImage(Sprite.peasSpriteL.getLotus());
			}
		}
		else if (direction == 2 || getDir() == Direction.DOWN) 
		{
			direction = 2;
			//setSprite(Sprite.peasSpriteD);
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerD.getLotus());
			}
			else if(this.unitClass == UnitClass.Spearman)
			{
				setImage(Sprite.peasSpriteD.getLotus());
			}
		}

	}


	int limit = 180;
	// Behavior Methods
	public void update()
	{
		//this.colliderBounds.set(getPixel32X()-2 ,getPixel32Y()-2, getPixel32X()+34, getPixel32Y() + 34);
		//this.atkColliderBounds.set(getPixel32X()-96, getPixel32Y()-96, getPixel32X() + 96, getPixel32Y() + 96);
		if (anim < 7500){anim++;}
		
		if(isCombatMode())
		{
			if(this.unitClass == UnitClass.Archer)
			{
				limit = 100;
			}
			timer+=1;
			long goal = System.currentTimeMillis() ;
			//System.out.println(timer);
			if(timer > limit)
			{
				timer = 0;
				if(!GameManager.level.isPlayerEntitySelected())
				{
					int dmg = 0;
					dmg = Math.abs(calculateDmg());
					/*if(dmg == 0)
					{
						for(int i = 0; i < 3; i++)
						{
							GameManager.level.getParticleEntities().add(new Particle(this.target1.getPixel64X(), this.target1.getPixel64Y(), 32));
						}
					}
					else */
						if(dmg > 0 )
					{
						for(int i = 0; i < dmg+2; i++)
						{
							GameManager.level.getParticleEntities().add(new Particle(GameManager.level.getPlayer().xScroll + this.target1.getPixel64X(), GameManager.level.getPlayer().yScroll + this.target1.getPixel64Y(), 32));
						}
					}
					endAttack(dmg*-1);
					// if archer, spawn arrow towards direction this unit towards targets pos
					//if(this.unitClass == UnitClass.Archer)
					//{
						//GameManager.level.getProjectileEntities().add(new Projectile(getPixel64X(), getPixel64Y(), 32));
					//}
				}
				else
				{
					int dmg = 0;
					dmg = Math.abs(calculateDmg());
					if(dmg > 0)
					{
						// if archer, spawn arrow towards direction this unit towards targets pos
						if(this.unitClass == UnitClass.Archer)
						{
							//GameManager.level.getProjectileEntities().add(new Arrow(getPixel64X(), getPixel64Y(), 32, this.direction, this.target1));
						}
						for(int i = 0; i < dmg+3; i++)
						{
							GameManager.level.getParticleEntities().add(new Particle(this.target1.getPixel64X(), this.target1.getPixel64Y(), 32));
						}
					}
					endPlayerInitiatedAttack(dmg*-1);

				}
			}
			else
			{
				int i = this.targetLinkPos;
				
				// find direction of target entity
				
				// Check to the right
				if(this.getCenter64X()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
				{					
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimRight.getImage());
					}
					else
					{
						setImage(Assets.peasAnimRightAtk.getImage());
					}
				}
				// Check to the left
				else if(this.getCenter64X()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
				{
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimLeft.getImage());
					}
					else
					{
						setImage(Assets.peasAnimLeftAtk.getImage());
					}
				}
				// Check above
				else if(this.getCenter64Y()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
				{
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimUp.getImage());	
					}
					else
					{
						setImage(Assets.peasAnimUpAtk.getImage());
					}
				}
				// Check below
				else if(this.getCenter64Y()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
				{
					if(this.unitClass == UnitClass.Archer)
					{	
						setImage(Assets.archerAttackAnimDown.getImage());
					}
					else
					{
						setImage(Assets.peasAnimDownAtk.getImage());
					}
				}
				// Check to the right + 2
				else if(this.getCenter64X()+2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
				{					
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimRight.getImage());
					}
					else
					{
						setImage(Assets.peasAnimRightAtk.getImage());
					}
				}
				// Check to the left + 2
				else if(this.getCenter64X()-2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
				{
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimLeft.getImage());
					}
					else
					{
						setImage(Assets.peasAnimLeftAtk.getImage());
					}
				}
				// Check above +2
				else if(this.getCenter64Y()-2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
				{
					if(this.unitClass == UnitClass.Archer)
					{
						setImage(Assets.archerAttackAnimUp.getImage());	
					}
					else
					{
						setImage(Assets.peasAnimUpAtk.getImage());
					}
				}
				// Check below + 2
				else if(this.getCenter64Y()+2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
				{
					if(this.unitClass == UnitClass.Archer)
					{	
						setImage(Assets.archerAttackAnimDown.getImage());
					}
					else
					{
						setImage(Assets.peasAnimDownAtk.getImage());
					}
				}
					
			}

		}
		else 
		{
			timer = 0;
			setPixel64X(this.center64X * 64); 
			setPixel64Y(this.center64Y * 64); 
		}
		
		
		if(walking)
		{
			if (direction == 0)
			{
				// setSprite(Sprite.enemSpriteU);
				if(this.unitClass == UnitClass.Archer)
				{
					this.a = Assets.archerAnimUp;
				}
				else
				{
					this.a = Assets.peasAnimUp;
				}
				setImage(a.getImage());
			
			}

			if (direction == 1) 
			{
				//setSprite(Sprite.peasSpriteR);
				if(this.unitClass == UnitClass.Archer)
				{
					this.a = Assets.archerAnimRight;
				}
				else
				{
					this.a = Assets.peasAnimRight;
				}
				setImage(a.getImage());
			
			}

			if (direction == 2) 
			{
				//setSprite(Sprite.peasSpriteD);
				if(this.unitClass == UnitClass.Archer)
				{
					this.a = Assets.archerAnimDown;
				}
				else
				{
					this.a = Assets.peasAnimDown;
				}
				setImage(a.getImage());
			
			}

			if (direction == 3) 
			{
				//setSprite(Sprite.peasSpriteL);
				if(this.unitClass == UnitClass.Archer)
				{
					this.a = Assets.archerAnimLeft;
				}
				else
				{
					this.a = Assets.peasAnimLeft;
				}
				setImage(a.getImage());
			
			}
		}
		
		if(getHealth() <= 0)
		{
			die();
		}
		else
		{
			if (!searched && this.getTarget() != null )// && getisTargetFound() == false
			{
				pathToTarget = new LinkedBlockingDeque<Tile>( breadthFirstSearch(GameManager.level.getArmyMovementTileMap()));
				
				 last = pathToTarget.peekLast();
				 pathToTarget.removeFirst();

				searched = true;
				
			}		
			
			if(doesEntityHaveGridMoves() && pathToTarget != null)
			{

				if (last != null)// if there is a path
				{
					setisTargetFound(true);

					if(last.getTileX() != getCenter64X() && last.getTileY() != getCenter64Y()  && pathToTarget.peekFirst()!=null && last != null || !pathToTarget.isEmpty() || last != null )
					{
						moveThruPath();
					}
				}
			}
		
		}

	}
	
	public ArrayList<Tile> breadthFirstSearch(TileMap tileMap)
	{
		Tile current;
		Tile startTile = tileMap.getTileGrid()[this.getCenter64X()][this.getCenter64Y()];
		ArrayList<Tile> visited = new ArrayList<Tile>();
		ArrayList<Tile> results = new ArrayList<Tile>();
		ArrayList<Tile> cameFrom = new ArrayList<Tile>();
		ArrayList<Integer> costSoFar = new ArrayList<Integer>();// track of total movement cost from start position
		ArrayList<Tile> path = new ArrayList<Tile>();
		ArrayList<Tile> finalPath = new ArrayList<Tile>();
		//ArrayList<Integer> distance = new ArrayList<Integer>();
		boolean isVisited = false;
		visited.add(startTile);
		startTile.setCameFrom(null);
		cameFrom.add(startTile);
		costSoFar.add(0);
		//distance.add(0);
		
		Queue<Tile> frontier = new LinkedBlockingQueue <Tile>();
		frontier.add(startTile);
		do
		{
			current = frontier.poll();
			// check thru each neighbor, 
			// add to frontier if not visited b4
			System.out.println("Visiting: " + "X: "  + current.getTileX() + " Y: " + current.getTileY());
			int tce = this.getTarget().getCenter64X();
			if (current.getTileX() == tce && current.getTileY() == this.getTarget().getCenter64Y())
			{					
				path.add(current);
			
				break;
			}		
			
			results = new ArrayList<Tile>(tileMap.neighbors(current));// hld neighbors
			System.out.print("\nResult: " );
			for(int i = 0; i < results.size();i++)// loop thru neighbors
			{
				Tile next = results.get(i);
				
				System.out.print("X: " + results.get(i).getTileX() + " Y: " + results.get(i).getTileY()+",");
				int newCost = costSoFar.get(costSoFar.size()-1) + tileMap.cost(current,next); 
				// if(costSoFar.contains(i) || newCost < costSoFar.get(costSoFar.size()-1))
				// {
					 
				// }
					
					// check if neighbors have been visited already
						if( visited.contains(next)								)
						{
							//isVisited = true;
							System.out.print(" Has been visited.");
						}
						else				
						{
							costSoFar.add(newCost);
							 int priority = heuristic(this.getTarget(), next);
							ArrayList<Tile> a = new ArrayList<Tile>(frontier);
							if(a.size() <= priority)
							{
								a.ensureCapacity(priority);
							a.add(next);
							}
							else
							{
								a.add(priority, next);
							}
							//frontier.clear();
							frontier = new LinkedBlockingQueue<Tile>(a);
							//for(int ii = 0; ii < a.size(); ii++)
							//{
								//frontier.add(a.get(ii));
							//}
							
							System.out.print(" +ed to Q/list.");
							next.setCameFrom(current);//linked cameFrom node
							frontier.add(next);
							visited.add(next);
							
							cameFrom.add(next);//prev node to next tile
					//distance.add( 1 + distance.get(i));

						}
						//}		
					//System.out.println();
			}
			System.out.println();
		} while(!frontier.isEmpty());
		
		System.out.println("Visited Result:");
		
		for(int i = 0; i < visited.size(); i++)
		{
			System.out.print("X: " + visited.get(i).getTileX() + " Y: " + visited.get(i).getTileY() + ", ");
		}
		
		// reconstruct path to target
		int h = heuristic(this.getTarget(), startTile);
			
			Tile cur;
			int n = cameFrom.indexOf(tileMap.getTileGrid()[this.getTarget().getCenter64X()][getTarget().getCenter64Y()]);// find goal/index in list
			if(n != -1)
			{
				cur = cameFrom.get(n);
			}
			else
			{
				cur = null;
			}
				
			while( cur !=  null )
			{					
				cur = cur.getCameFrom();//get prev tile from current

				if(cur != null)
				{
			
					System.out.println("Adding " + "X:" + cur.getTileX() + " Y: " + cur.getTileY());
					path.add(cur);
				}else{break;}				
			}
				
			for(int i = path.size()-1; i >= 0; i--)// reverse path to start to goal
			{
				if(path.get(i)!= null)
				{
					finalPath.add( path.remove(i));
				}
			}
			
			System.out.println("Path: ");
			for(int i = 0; i < finalPath.size(); i++)
			{
				System.out.print("X: " + finalPath.get(i).getTileX() + " Y: " + finalPath.get(i).getTileY() + ", ");
			}
		//return visited;
			return finalPath;
	}

	// Check if entity has enough gridMoves to move
	public boolean doesEntityHaveGridMoves() 
	{
		if(this.getGridMoves() <= 3 && this.getGridMoves() > 0)
		{
			return true;
		}
		else
		{
			walking = false;
			return false;
		}
	}
	
	// check if enemy entity is in the path 
	public boolean isSpaceOccupied(Tile cur)
	{
		//Tile cur = pathToTarget.peek();
		boolean isSpaceOccupied = true;
		if (cur != null)
		{
			for(int i = 0; i < GameManager.level.getPlayerArmyEntities().size(); i++)
			{
				if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() == cur.getTileX() && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() == cur.getTileY())
				{
					isSpaceOccupied = true;
					pathToTarget = null;
					break;
				}
				else
				{
					isSpaceOccupied = false;
				}
			}
			if(isSpaceOccupied == false)
			{
				for(int i = 0; i < GameManager.level.getEnemyArmyEntities().size(); i++)
				{
					if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == cur.getTileX() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == cur.getTileY())
					{
						isSpaceOccupied = true;
						pathToTarget = null;

						break;
					}
					else
					{
						isSpaceOccupied = false;
					}
			
				}
			}
		}
		return isSpaceOccupied;
	}
	
	/******************************************************************************
	 *  Move Entity thru found path by one step each time this method is called
	 ******************************************************************************/
	public void moveThruPath()
	{
		Tile curCopy = pathToTarget.peek();
		
		//checkForDroppedItem();
		//System.out.println("X: " + cur.getTileX() + " Y: " + cur.getTileY());
		if(curCopy == null){setisTargetFound(false);walking = false;}
			
		// if no more steps/path, entity has no target & has not found the no target
			
		if(curCopy != null && isSpaceOccupied(curCopy) == false)// if there are steps to take
		{
			Tile cur = pathToTarget.poll();
			if(cur.getTileX() == (getCenter64X()+1) && cur.getTileY() == getCenter64Y())// check if cur to the right of player
			{
				walking = true;
				System.out.println("Moved Right");
				moveRight();
				this.setGridMoves(this.getGridMoves() - 1);
				checkForDroppedItem();
			}
			else if(cur.getTileY() == (getCenter64Y()-1) && cur.getTileX() == getCenter64X() )// check if cur is above player
			{
				walking = true;
				System.out.println("Moved Up");
				moveUp();
				this.setGridMoves(this.getGridMoves() - 1);
				checkForDroppedItem();

			}
			else if(cur.getTileX() == (getCenter64X()-1) && cur.getTileY() == getCenter64Y() )// check if cur is left of plyr
			{
				walking = true;
				System.out.println("Moved Left");
				moveLeft();
				this.setGridMoves(this.getGridMoves() - 1);
				checkForDroppedItem();
			}
			else if(cur.getTileY() == (getCenter64Y()+1) && cur.getTileX() == getCenter64X() ) // check if cur is below plyr
			{
				walking = true;
				System.out.println("Moved Down");
				moveDown();
				this.setGridMoves(this.getGridMoves() - 1);
				checkForDroppedItem();

			}
			else
			{
				walking = false;
			}

			System.out.println("CenterX: " + getCenter64X() + " CenterY: " + getCenter64Y());
		}
	}
		
	private void moveLeft() 
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() - 64);
		direction = 3;
		this.walking = true;
		this.setDirection(Direction.LEFT);
		//move(getCenter64X(), getCenter64Y());
		//checkForDroppedItem();

	}

	private void moveRight() 
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() + 64);
		direction = 1;
		this.walking = true;
		this.setDirection(Direction.RIGHT);
		//move(getCenter64X(), getCenter64Y());
		//checkForDroppedItem();

	}

	private void moveDown() 
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() + 64);
		direction = 2;
		this.walking = true;
		this.setDirection(Direction.DOWN);
		//move(getCenter64X(), getCenter64Y());
		//checkForDroppedItem();

	}

	private void moveUp() 
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() - 64);
		direction = 0;
		this.walking = true;
		this.setDirection(Direction.UP);
		//move(getCenter64X(), getCenter64Y());
		//checkForDroppedItem();
	}

	private void move(int xAxis, int yAxis)
	{
		if(xAxis != 0 && yAxis != 0)// if on axis
		{
			// run for each axis, for separate collision
			move(xAxis,0);
			move(0,yAxis);
			return;
		}
		//if (xAxis > 0){direction = 1;}// Right
		//if (xAxis < 0){direction = 3;}// Left
		//if (yAxis > 0){direction = 2;}// Down
		//if (yAxis < 0){direction = 0;}// Up
		
		if(direction == 0)
		{
			//setSprite(Sprite.sorcSpriteU);
			if (walking)
			{
				this.a = Assets.peasAnimUp;
				setImage((BufferedImage) a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteU.getLotus());
			}
		} 
		else if(direction == 1)
		{
			setSprite(Sprite.peasSpriteR);
			if (walking)
			{
				this.a = Assets.peasAnimRight;
				setImage((BufferedImage) a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteR.getLotus());
			}
		}
				
		else if(direction == 2)
		{
			setSprite(Sprite.peasSpriteD);
			if (walking)
			{
				this.a = Assets.peasAnimDown;
				setImage((BufferedImage) a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteD.getLotus());
			}
		}
			
		else if(direction == 3)
		{ 
			setSprite(Sprite.peasSpriteL);
			if (walking)
			{
				this.a = Assets.peasAnimLeft;
				setImage((BufferedImage) a.getImage());
			}
			else
			{
				setImage(Sprite.peasSpriteL.getLotus());
			}
		}
			/*
			if(!collision(xAxis, yAxis))// if no collision, move in direction
			{				
				x += xAxis;
				y += yAxis;
			}
			else 
			{
				Particle p =  new Particle(x, y, 50);
				level.add(p);
				//Sound.hit.play();
				
			}
		}
		*/
	}
	
	public void setSprite(Sprite s)
	{
		this.sprite = s;
	}

	public int heuristic(Entity t, Tile n) 
	{
		return Math.abs(t.getCenter64X() - n.getTileX())
				+ Math.abs(t.getCenter64Y() - n.getTileY()) ;
	}
	
	public boolean isCanMove()
	{
		return this.canMove;
	}
	
	public void die()
	{
		setVisible(false);
	}
	
	public Animation getAnimation()
	{
		return this.a;
	}
	
	public int getMovementSpeed()
	{
		return this.movementSpeed;
	}
	
	public void setMovementSpeed(int s)
	{
		this.movementSpeed = s;
	}
	
	public void setPower(int power)
	{
		this.power = power;
	}	
	
	public void setSearched(boolean b)
	{
		this.searched = b;
	}
	
	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}
	
	public void nextTurnUpdate()
	{
		// update movement/combat variables for next turn
		if(this.getGridMoves() == 2)
		{
			this.setGridMoves(this.getGridMoves() + 1);
		}
		else if (this.getGridMoves() < 2)
		{
			this.setGridMoves(this.getGridMoves() + 2);
		}
		else if (this.getGridMoves() >= 3)
		{
			this.setGridMoves(3);
		}
		
		if(this.xp >= 10)
		{
			this.vetLvl = 2;
		}
		else if(this.xp >= 20)
		{
			this.vetLvl = 3;
		}
		else if(this.xp >= 30)
		{
			this.vetLvl = 4;
		}
		else if(this.xp >= 40)
		{
			this.vetLvl = 5;
		}
		else if(this.xp >= 50)
		{
			this.vetLvl = 6;
		}
		else if(this.xp >= 60)
		{
			this.vetLvl = 7;
		}
			
	}
	
	public boolean isWalking()
	{
		return this.walking;
	}
	
	public void setWalking(boolean b)
	{
		this.walking = b;
	}
	
	public void checkForDroppedItem()
	{
		for(int i = 0; i < GameManager.level.getDroppedResourceEntities().size(); i++)
		{
			if(GameManager.level.getDroppedResourceEntities().get(i).getCenter64X() == getCenter64X() && GameManager.level.getDroppedResourceEntities().get(i).getCenter64Y() == getCenter64Y())
			{
				
				if(GameManager.level.getDroppedResourceEntities().get(i).getResourceType().equals(ResourceType.Food))
				{
					System.out.println("RECIEVED FOOD");
					GameManager.food += 3;
					GameManager.level.getDroppedResourceEntities().get(i).setVisible(false);
				}
				else if(GameManager.level.getDroppedResourceEntities().get(i).getResourceType().equals(ResourceType.Gold)) 
				{
					System.out.println("RECIEVED GOLD");
					GameManager.gold += 4;
					GameManager.level.getDroppedResourceEntities().get(i).setVisible(false);
				}
				
			}
		}
	}
	
	public void endAttack(int dmg)
	{
		int i = this.targetLinkPos;
		
		GameManager.level.getEnemyArmyEntities().get(i).setTarget(new Entity(this));;

		this.lastTargetX = this.target1.getCenter64X();
		this.lastTargetY = this.target1.getCenter64Y();
		
		if(direction == 1)
		{			
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerR.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()-18);	
			}
		}
		else if(direction == 3)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerL.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()+18);	
			}
		}
		else if(direction == 2)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerU.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()+18);	
			}
		}
		else if(direction == 0)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerD.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteD.getLotus());
				this.setPixel64X(this.getPixel64X()-6);
				this.setPixel64Y(this.getPixel64Y()-18);
			}
	
		}
		
		//GameManager.level.getEnemyArmyEntities().get(i).setTarget(new Entity(this));;
		//GameManager.level.getEnemyArmyEntities().get(i).getTarget().setPixel64X(this.lastTargetX); 
		//GameManager.level.getEnemyArmyEntities().get(i).getTarget().setPixel64Y(this.lastTargetY);
		GameManager.level.getEnemyArmyEntities().get(i).addHealth(dmg);

		setCombatMode(false);
		walking = false;
		GameManager.level.setSelectedPlayerEntity(null);

		//calcPositionDmgBonus(i, getRelativePosToTarget(i), dmg);
		GameManager.level.getPlayer().setCombatMode(false);

	
			//GameManager.level.setIsEntitySelected(false);
			//GameManager.level.setSelectedEntity(null);
		

	}
	
	/************************************************
	 * process damage to player target
	 * dmg: amount of inflicted damage
	 ************************************************/
	public void endPlayerInitiatedAttack(int dmg)
	{
		int i = this.targetLinkPos;
		GameManager.level.getEnemyArmyEntities().get(i).setTarget(new Entity(this));;

		this.lastTargetX = this.target1.getCenter64X();
		this.lastTargetY = this.target1.getCenter64Y();
		
		if(direction == 1)
		{			
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerR.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()-18);
			}
		}
		else if(direction == 3)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerL.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()+18);
			}
		}
		else if(direction == 2)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerU.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()+18);
			}
		}
		else if(direction == 0)
		{
			if(this.unitClass == UnitClass.Archer)
			{
				setImage(Sprite.archerD.getLotus());
			}
			else
			{
				setImage(Sprite.peasSpriteD.getLotus());
				this.setPixel64X(this.getPixel64X()-6);
				this.setPixel64Y(this.getPixel64Y()-18);	

			}
		}
		
		GameManager.level.getEnemyArmyEntities().get(i).getTarget().setPixel64X(this.lastTargetX); 
		GameManager.level.getEnemyArmyEntities().get(i).getTarget().setPixel64Y(this.lastTargetY);
		//GameManager.level.getEnemyArmyEntities().get(i).setTarget(new Entity(this));;
		GameManager.level.getEnemyArmyEntities().get(i).addHealth(dmg);

		setCombatMode(false);
		walking = false;
		GameManager.level.setSelectedPlayerEntity(null);
		GameManager.level.getPlayer().setCombatMode(false);
		//dmg = calcPositionDmgBonus(i, getRelativePosToTarget(i), dmg);


	}
	
	
	public int calculateDmg() 
	{
		int i = this.targetLinkPos;

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive ((max - min) + 1 ) + min
		int r = random.nextInt(atkRating+1);
		

		if(r == 0)
		{
			//return -this.getAtkRating() + GameManager.level.getEnemyArmyEntities().get(targetLinkPos).getDefRating();
			return r;
		}
		else
		{
			//			return ((int) -(calcPositionDmgBonus(i, getRelativePosToTarget(i), r) + GameManager.level.getEnemyArmyEntities().get(targetLinkPos).getDefRating())); 

			return (r * atkRating) - GameManager.level.getEnemyArmyEntities().get(targetLinkPos).getDefRating();
		}
	}
	
	/*****************************************************
	 * Calculate extra damage for angle of attack
	 * i: posInLink for target
	 * dirOfTarget: relative direction on grid from entity
	 *****************************************************/
	public int calcPositionDmgBonus(int i, int dirOfTarget, int dmg)
	{
		// if enemy faces left or right and target is to the left
		if(dirOfTarget == 3)// left
		{
			switch(this.direction)
			{
				case 0: // d
					// check targets direction
					return getTargetsDmgBasedOnDir(0, 3, dmg);
				case 1: // r
					// check targets direction
					return getTargetsDmgBasedOnDir(1, 3, dmg);
				case 2: // u
					// check targets direction
					return getTargetsDmgBasedOnDir(2, 3, dmg);
				case 3: // l
					// check targets direction
					return getTargetsDmgBasedOnDir(3, 3, dmg);
			}
		}
		else if(dirOfTarget == 1)// right
		{
			switch(this.direction)
			{
				case 0: // d
					return getTargetsDmgBasedOnDir(0, 1, dmg);
				case 1: // r
					return getTargetsDmgBasedOnDir(1, 1, dmg);
				case 2: // u
					return getTargetsDmgBasedOnDir(2, 1, dmg);
				case 3: // l
					return getTargetsDmgBasedOnDir(3, 2, dmg);
			}
		}
		else if(dirOfTarget == 0)// down
		{
			switch(this.direction)
			{
				case 0: // d
					// check targets direction
					return getTargetsDmgBasedOnDir(0, 0, dmg);
				case 1: // r
					return getTargetsDmgBasedOnDir(1, 0, dmg);
				case 2: // u
					return getTargetsDmgBasedOnDir(2, 0, dmg);
				case 3: // l
					return getTargetsDmgBasedOnDir(3, 0, dmg);
				
			}
		}
		else if(dirOfTarget == 2)// up
		{
			switch(direction)
			{
				case 0:  // d 
					// check targets direction
					return getTargetsDmgBasedOnDir(0, 2, dmg);
				case 1: // r
					// check targets direction
					return getTargetsDmgBasedOnDir(1, 2, dmg);

				case 2: // u
					// check targets direction
					return getTargetsDmgBasedOnDir(2, 2, dmg);
				case 3: // l
					// check targets direction
					return getTargetsDmgBasedOnDir(3, 2, dmg);							
			}
		}
		return 0;
	}
	
	/*********************************************************************
	 * Find out whether target if above below , left or right of the enemy
	 * i: pos in link of player unit
	 ********************************************************************/
	public int getRelativePosToTarget(int i)
	{
		//  Right
		if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() + 1 == this.center64X && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == this.center64Y)
		{
			return 1;
		}
		// Left
		else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() - 1 == this.center64X && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == this.center64Y)
		{
			return 3;
		}
		// Down
		else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == this.center64X && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() - 1 == this.center64Y)
		{
			return 0;
		}
		// Up
		else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == this.center64X && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() + 1 == this.center64Y)
		{
			return 2;
		}
		else
		{
			return 4;
		}
	}
	
	public void attack(int i) 
	{
		this.targetLinkPos = i;
		this.gridMoves--;
		this.target1 = new Entity(GameManager.level.getEnemyArmyEntities().get(i));

		// find direction of target entity
		
		if(this.unitClass == UnitClass.Archer)
		{
			// Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.archerR.getLotus());
			}
			// Check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.archerL.getLotus());
			}
			// Check above
			else if(this.getCenter64Y()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.archerU.getLotus());
			}	
			// Check below
			else if(this.getCenter64Y()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.archerD.getLotus());
			}
		}
		else
		{
			// Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.peasSpriteR.getLotus());
				//this.setPixel64X(this.getPixel64X()+18);
			
				//setImage(Assets.peasAnimRightAtk.getImage());
			}
			// Check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.peasSpriteL.getLotus());
				//this.setPixel64X(this.getPixel64X()-18);
				//setImage(Assets.peasAnimLeftAtk.getImage());
			}
			// Check above
			else if(this.getCenter64Y()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.peasSpriteU.getLotus());
				//this.setPixel64Y(this.getPixel64Y()-18);
				//setImage(Assets.peasAnimUpAtk.getImage());
			}	
			// Check below
			else if(this.getCenter64Y()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.peasSpriteD.getLotus());
				//this.setPixel64X(this.getPixel64X()+6);
				//this.setPixel64Y(this.getPixel64Y()+16);
				//setImage(Assets.peasAnimDownAtk.getImage());

			}
			//attack();
		}
	}
	
	/****************************************************
	 * dir: direction of current entity
	 * dirOfTarget: direction of current entities target
	 ****************************************************/
	public int getTargetsDmgBasedOnDir(int dir, int dirOfTarget, int dmg)
	{
		 switch(dirOfTarget)
		 {
		 	case 0: // target is below
		 		
		 		// check for target facing down, inflict 50% of bonus dmg
		 		if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing either side, inflict 25% of bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing forward, no bonus dmg.
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return dmg;
		 		}
		 		break;
		 		
		 	case 1: // target is to the right
		 		
		 		// check for target facing right, inflict 50% bonus dmg
		 		if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing down or forward, inflict 25 % bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing left, no bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT)
		 		{
		 			return dmg;
		 		}
		 		break;
		 		
		 	case 2: // target is above
		 		
		 		// check for target facing forward, inflict 50% bonus dmg
		 		if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing either side, inflict 25% bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT && GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing down, no bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN)
		 		{
		 			return dmg;
		 		}
		 		break;
		 		
		 	case 3: // target is to the left
		 		
		 		// check for target facing left, inflict 50% bonus dmg
		 		if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing down or forward, inflict 25 % bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return dmg;
		 		}
		 		// check for target facing right, no bonus dmg
		 		else if(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return dmg;
		 		}
		 		break;
		 }		 
		  return dmg;
		 // check targets dir
		/* Entity.Direction i = GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection();
		 if(i == Entity.Direction.DOWN)
		 {

			 return 5;
		 }
		 else if(i == Entity.Direction.LEFT)
		 {
			 return 4;
		 }
		 else if(i == Entity.Direction.RIGHT)
		 {
			 return 5;
		 }
		 else if(i == Entity.Direction.UP)
		 {
			return 25; 
		 }
		 else
		 {
			 return 0;
		 }*/
	}
	
	/*********************************************************
	 * Init attack sequence for a player initiated attack
	 * i: target link pos in enemyEntities list
	 ********************************************************/
	public void playerInitiatedAttack(int i) 
	{
		this.targetLinkPos = i;
		this.gridMoves--;
		this.target1 = new Entity(GameManager.level.getEnemyArmyEntities().get(i));
		
		//calcPositionDmgBonus(getRelativePosToTarget(i));

		// find direction of target entity
		if(this.unitClass == UnitClass.Archer)
		{
			// Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.archerR.getLotus());
			}
			// Check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.archerL.getLotus());
			}
			// Check above
			else if(this.getCenter64Y()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.archerU.getLotus());
			}
				// Check below
			else if(this.getCenter64Y()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.archerD.getLotus());
			}
			else if(this.getCenter64X()+2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.archerR.getLotus());
			}
			// Check to the left
			else if(this.getCenter64X()-2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				//move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.archerL.getLotus());
			}
			// Check above
			else if(this.getCenter64Y()-2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.archerU.getLotus());
			}
				// Check below
			else if(this.getCenter64Y()+2 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				//move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.archerD.getLotus());
			}
		}
		else
		{
			// Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.peasSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()+18);
				
				//setImage(Assets.peasAnimRightAtk.getImage());
			}
			// Check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.peasSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()-18);
				//setImage(Assets.peasAnimLeftAtk.getImage());
			}
			// Check above
			else if(this.getCenter64Y()-1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.peasSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()-18);
				//setImage(Assets.peasAnimUpAtk.getImage());
			}
			// Check below
			else if(this.getCenter64Y()+1 == GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getEnemyArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.peasSpriteD.getLotus());
				this.setPixel64X(this.getPixel64X()+6);
				this.setPixel64Y(this.getPixel64Y()+16);
				//setImage(Assets.peasAnimDownAtk.getImage());

			}
		}
		//attack();
	}
	
	public boolean isCombatMode()
	{
		return this.isCombatMode;
	}

	public void setCombatMode(boolean b)
	{
		this.isCombatMode  = b;
	}

	/**************************************
	 * Return direction int integer form
	 **************************************/
	public int getDirectional()
	{
		return this.direction;
	}

	public Entity getCurPos() {
		return curPos;
	}

	public void setCurPos(Entity curPos) {
		this.curPos = curPos;
	}

	public Entity getGoalPos() {
		return goalPos;
	}

	public void setGoalPos(Entity goalPos) {
		this.goalPos = goalPos;
	}

}
