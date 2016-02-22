package com.ci.game.entity;

import game.ai.CombatEvent;
import game.ai.UnitFSM;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.ci.game.GameManager;
import com.ci.game.entity.Resource.ResourceType;
import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.entity.buildings.BuildingEntity;
import com.ci.game.entity.particle.Particle;
import com.ci.game.entity.projectile.Arrow;
import com.ci.game.graphics.Animation;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;
import com.ci.game.level.TileMap;
import com.ci.game.level.tile.Tile;

public class Enemy extends Entity
{
	
	public enum UnitState
	{
		ATTACK, DEFEND, IDLE, WANDER, MOVE_TO_TARGET, PRESET_TARGET
	}
	
private UnitFSM brain; 

private int gridMoves;
protected int direction = 0;
private int movementSpeed;
private boolean walking = false;
private boolean isAtkReady = false;
private Animation a;
private boolean searched = false;
private LinkedBlockingDeque<Tile> pathToTarget ;

private Queue<CombatEvent> combatEvents = new LinkedBlockingQueue<CombatEvent>();


	public Enemy()
	{

	}

	public Enemy (Entity e)
	{
		this.target2 = null;
		this.target1 = null;
	
		setHealth(e.getHealth());
		this.atkRating = e.getAtkRating();
		this.defRating = e.getDefRating();
		this.unitType = e.getUnitType();
		this.entityType = e.getEntityType();	
		this.gridMoves = e.getGridMoves();
		this.setCenter32X(e.getCenter32X());
		this.setCenter32Y(e.getCenter32Y());
		this.setCenter64X( e.getCenter64X());
		this.setCenter64Y(e.getCenter64Y());	
	
		setPixel32X ( e.getPixel32X());
		setPixel32Y ( e.getPixel32Y());
		setPixel64X (e.getPixel64X());
		setPixel64Y (e.getPixel64Y());
		setVisible(e.isVisible());
	}

	public Enemy(int x, int y, Level l, UnitType t)
	{
		super(10, 3, 0, x, y, t, UnitClass.Swordsman, EntityType.Enemy, Direction.RIGHT);

		this.level = l;
		//this.direction = 1;
		//setHealth(10);
		setImage(Sprite.enemSpriteR.getLotus());
		setSprite(Sprite.enemSpriteR);
		//setTarget(new Entity(12, 12));
		setGridMoves(2);
		brain = new UnitFSM(this);// init AI
		// tell the brain/AI to start looking for a target.
		brain.setState(UnitState.WANDER);
	}

	public void render(Camera screen)
	{
		//screen.renderArmyEntity(getCenter64X(), getCenter64Y() , this);
		screen.renderArmyEntity(this);

	}

	private Tile last;
	private boolean canMove;
	private int targetLinkPos;
	private long timer;
	private boolean isCombatMode = false;

	private Entity wanderTarget;

	public void update() 
	{	
		//brain.update();
		//this.colliderBounds.set(getPixel32X(), getPixel32Y(), getPixel32X() + 32,	getPixel32Y() + 32);
		//this.atkColliderBounds.set(getPixel32X(), getPixel32Y() - 64, getPixel32X() + 400, getPixel32Y() + 400);
		if(isCombatMode())
		{	
			timer += 1;

			long goal = System.currentTimeMillis();
			//System.out.println(timer);
			if(timer > 180)
			{
				timer = 0;
				if(GameManager.level.isPlayerEntitySelected())
				{
					int dmg = 0;
					dmg = Math.abs(calculateDmg());
					if(dmg > 0)
					{
						for(int i = 0; i < dmg*2; i++)
						{
							GameManager.level.getParticleEntities().add(new Particle(this.target1.getPixel64X() + GameManager.level.getPlayer().xScroll, this.target1.getPixel64Y() + GameManager.level.getPlayer().yScroll, 30));
						}
					}
					endAttack(dmg*-1);
				}
				else
				{
					int dmg = 0;
					dmg = Math.abs(calculateDmg());
					if(dmg > 0 && this.target1 != null)
					{
						// if archer, spawn arrow towards direction this unit towards targets pos
						if(this.unitClass == UnitClass.Archer)
						{
							//GameManager.level.getProjectileEntities().add(new Arrow(getPixel64X(), getPixel64Y(), 32, this.direction, this.target1));
						}
						for(int i = 0; i < dmg*2; i++)
						{
							GameManager.level.getParticleEntities().add(new Particle(this.target1.getPixel64X() + GameManager.level.getPlayer().xScroll, this.target1.getPixel64Y() - GameManager.level.getPlayer().yScroll, 30));
						}
					}
					endPlayerInitiatedAttack(dmg*-1);
				}				
			}
			else
			{
				int i = this.targetLinkPos ; // stores link pod of combat target in appropriate entity list
			
				// find direction of target entity
				//Check to the right
				if(this.getCenter64X()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
				{
					setImage(Assets.enemAnimRightAtk.getImage());
				}
				//check to the left
				else if(this.getCenter64X()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
				{	
					setImage(Assets.enemAnimLeftAtk.getImage());
				}
				// check above
				else if(this.getCenter64Y()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
				{
					setImage(Assets.enemAnimUpAtk.getImage());
				}
				// check below
				else if(this.getCenter64Y()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
				{
					setImage(Assets.enemAnimDownAtk.getImage());
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
				setSprite(Sprite.enemSpriteU);
		
				this.a = Assets.enemAnimUp;
				setImage(a.getImage());		
			}
			if(direction == 1) 
			{
				setSprite(Sprite.enemSpriteR);
		
				this.a = Assets.enemAnimRight;
				setImage(a.getImage());		
			}

			if (direction == 2) 
			{
				setSprite(Sprite.enemSpriteD);
		
				this.a = Assets.enemAnimDown;
				setImage(a.getImage());		
			}

			if (direction == 3) 
			{
				setSprite(Sprite.enemSpriteL);
		
				this.a = Assets.enemAnimLeft;
				setImage(a.getImage());		
			}
		}
		// check if barrack has been placed, if so move towards it to attack
	
		// find/atk player barrack/troops
		if (getHealth() <= 0) 
		{
			die();
		} 
		else 
		{
			// checkCollision();
			if (!searched && target1 != null) 
			{

				pathToTarget = new LinkedBlockingDeque<Tile>( breadthFirstSearch(GameManager.level.getArmyMovementTileMap()));
			
				last = pathToTarget.peekLast();
				if(pathToTarget.peekFirst()!= null)
				{
					pathToTarget.removeFirst();
				}
				searched = true;
			}
	

		if(doesEntityHaveGridMoves() && pathToTarget != null && getBrain().getState() == UnitState.MOVE_TO_TARGET)
		{

			if (last != null)
			{

				
				if(last.getTileX() != this.getCenter64X() && last.getTileY() != this.getCenter64Y()  && pathToTarget.peekFirst()!=null && last != null  || !pathToTarget.isEmpty() || last != null )
				{

					moveThruPath();
			//}
			/*
			 * if(t != null && getisTargetFound() == false) {
			 * //System.out.println("Target x: " + target.getCenterX());
			 * 
			 * findPathToTarget();
			 * 
			 * //setCenterX(getCenterX() + speedX);
			 * //setCenterY(getCenterY() + speedX); }
			 */
					if (getisAtkReady()) 
					{
						// attack();
					}
				}


			}	

				walking = false;	
		 		//setCanMove(false);
			}
		
		}
	}

	/*******************************************************
	*  Check if entity has enough gridMoves to move
	********************************************************/
	public boolean doesEntityHaveGridMoves() 
	{
		if(this.gridMoves <= 3 && this.gridMoves >= 0)
		{
			return true;
		}
		else
		{
			walking = false;
			return false;
		}
	}

	
	public boolean isSpaceOccupied(Tile cur)
	{
		//Tile cur = pathToTarget.peek();
		boolean isSpaceOccupied = true;
		if (cur != null)
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
			if(isSpaceOccupied == false)
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
			}
		}
		return isSpaceOccupied;
	}

	public void moveThruPath()
	{
		//System.out.println("YAY!!" + "X: " + cur.getTileX() + " Y: " + cur.getTileY());
		// Check if entities next move is on an existing entities occupied tile.
		Tile curCopy = pathToTarget.peek();
	
		checkForDroppedItem();

		
		if (curCopy != null && isSpaceOccupied(curCopy) == false)
		{

			Tile cur = pathToTarget.poll();
		
			if (cur.getTileX() == (this.getCenter64X()+1) && cur.getTileY() == this.getCenter64Y())// check if cur to the right of player
			{
				walking = true;
				System.out.println("Moved Right");
				moveRight();
				this.gridMoves--;
			}
			else if (cur.getTileY() == this.getCenter64Y()-1 && cur.getTileX() == this.getCenter64X() )// check if cur is above player
			{
				walking = true;
				System.out.println("Moved Up");
				moveUp();
				this.gridMoves--;
			}
			else if (cur.getTileX() == this.getCenter64X()-1 && cur.getTileY() == this.getCenter64Y() )// check if cur is left of plyr
			{
				walking = true;
				System.out.println("Moved Left");
				moveLeft();
				this.gridMoves--;
			}
			else if (cur.getTileY() == this.getCenter64Y()+1 && cur.getTileX() == this.getCenter64X() ) // check if cur is below plyr
			{
				walking = true;
				System.out.println("Moved Down");
				moveDown();
				this.gridMoves--;
			}
			else
			{
				walking = false;
			}

			System.out.println("CenterX: " + this.getCenter64X() + " CenterY: " + this.getCenter64Y());
	
		}
	}

	@SuppressWarnings("unchecked")
	private void findPathToTarget() 
	{
		if (getisTargetFound() == false) 
		{

			if (this.getPixel32Y() == getTarget().getPixel32Y()) 
			{

				if (this.getPixel32X() == getTarget().getPixel32X()) 
				{
					// Check for solid tile

				/*
				 * LinkedList<ArmyUnit> entitiess =
				 * getLevel().getPlayerArmyEntities(); for(int i = 0; i <
				 * entitiess.size(); i++) { ArmyUnit e = entitiess.get(i);
				 * if(e != null) { //e.update(); if (getPixelY() ==
				 * e.getPixelY()) { moveUp(); setisTargetFound(true);
				 * setisAtkReady(true);
				 * 
				 * } } }
				 */
					setisTargetFound(true);
					// setisAtkReady(true);

				} 
				else
				{
				// move left or right toward target

					// this entity is to the right of target, move left
					if (this.getPixel32X() > getTarget().getPixel32X())
					{
						moveLeft();
					}
					else// move right
					{
						moveRight();
					}

				}
			}
			else 
			{
			// move up or down

				// this entity is below target, move up
				if (this.getPixel32Y() > getTarget().getPixel32Y()) 
				{
					moveUp();
				}
				else// move down
				{
					moveDown();
				}
			}

		}
		else
		{
			movementSpeed = 0;
			setCenter64X(getCenter64X() + movementSpeed);
			setCenter64Y(getCenter64Y() + movementSpeed);
			setPixel64X(getPixel64X() + movementSpeed);
			setPixel64Y(getPixel64Y() + movementSpeed);

			move(getCenter64X(), getCenter64Y());
		
			// System.out.println("Target is null");
		}

	}

/*@SuppressWarnings("unchecked")
private void checkCollision() {
	// System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());

	// check/find keep/barrack
	LinkedList<BuildingEntity> bentities = getLevel()
			.getPlayerBuildingEntities();
	for (int i = 0; i < bentities.size(); i++) {
		BuildingEntity e = bentities.get(i);

		// e.update();
		if (Rect.intersects(this.atkColliderBounds, e.buildingAreaBounds)) {
			// System.out.println("Enemy attack barrack collision!");

			//
			if (e != null || this.target1 == null) {
				//this.target1 = e;
				// findPathToTarget();
			}
			//attack();
			// this.isAtkReady = true;
		}

		// Make Enemy list to hold enemies and check and compare against
		// player troops for coll
	}

	bentities = getLevel().getPlayerBuildingEntities();
	for (int i = 0; i < bentities.size(); i++) {
		BuildingEntity e = bentities.get(i);

		// e.update();
		if (Rect.intersects(this.colliderBounds, e.buildingColliderBounds)) {
			// System.out.println("Enemy attack barrack collision!");

			//
			if (e != null) {
				//moveNone();

			}

		}

	}

	LinkedList<ArmyUnit> entities = getLevel().getPlayerArmyEntities();
	for (int i = 0; i < entities.size(); i++) {
		ArmyUnit e = entities.get(i);
		if (e != null) {
			// e.update();
			if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
				// e.setVisible(false);
				// System.out.println("Enemy Collision!");
				// this.isAtkReady = true;
				this.target1 = e;
				// findPathToTarget();

				//attack();
				canMove = false;
				searched = true;
				// Make Enemy list to hold enemies and check and compare
				// against player troops for coll
			}
		} else {
			break;
		}

	}

	//
	 * bentities = Entity.getPlayerBuildingEntities(); for(int i = 0; i <
	 * bentities.size(); i++) { BuildingEntity e = bentities.get(i);
	 * 
	 * //e.update(); if (Rect.intersects(this.atkColliderBounds,
	 * e.buildingColliderBounds)) {
	 * //System.out.println("Enemy attack barrack collision!");
	 * 
	 * this.target1 = e; findPathToTarget();
	 * 
	 * //attack(); //this.isAtkReady = true; }
	 * 
	 * // Make Enemy list to hold enemies and check and compare against
	 * player troops for coll }
	 //

	// Check for atk collision
	LinkedList<ArmyUnit> entitiess = getLevel().getPlayerArmyEntities();
	for (int i = 0; i < entitiess.size(); i++) {
		ArmyUnit e = entitiess.get(i);
		if (e != null) {
			// e.update();
			if (Rect.intersects(this.colliderBounds, e.atkColliderBounds)) {
				// System.out.println("Enemy atk player Collision!");
				if (getTarget() == null) {
					//this.target1 = e;
					// findPathToTarget();
					// setisAtkReady(true);
					// moveNone();
					// attack();
				}

				// Make Enemy list to hold enemies and check and compare
				// against player troops for coll
			}
		} else {
			break;
		}

	}
	LinkedList<Enemy> eEntities = getLevel().getEnemyArmyEntities();
	for (int i = 0; i < eEntities.size(); i++) {
		Enemy e = eEntities.get(i);

		// e.update();
		if (this.target1 == e) {
			// System.out.println("Enemy atk player Collision!");

			// Entity target1, target2;
			// this.target1 = null;
			// this.target2 = null;

			// Make Enemy list to hold enemies and check and compare against
			// player troops for coll
		}

	}

	//
	 * OG collision code if (Rect.intersects(r, et.colliderBounds)||
	 * Rect.intersects(r, Robot.rect2) || Rect.intersects(r, Robot.rect3) ||
	 * Rect.intersects(r, Robot.rect4)) {
	 * 
	 * }
	 //
}*/

	private void setisAtkReady(boolean b) 
	{
		this.isAtkReady = b;

	}

	public void move(int xAxis, int yAxis) 
	{
		if (xAxis > 0 && yAxis > 0)// if on axis
		{
			// run for each axis, for seperate collision
			move(xAxis, 0);
			move(0, yAxis);
			return;
		}
		// if (xAxis > 0){direction = 1;}// Right
		// if (xAxis < 0){direction = 3;}// Left
		// if (yAxis > 0){direction = 2;}// Down
		// if (yAxis < 0){direction = 0;}// Up

		if (direction == 0)
		{
			// setSprite(Sprite.sorcSpriteU);
			if (walking) 
			{
				this.a = Assets.enemAnimUp;
				setImage(a.getImage());
			}
			else 
			{
				setImage(Sprite.enemSpriteU.getLotus());
			}
		}

		if (direction == 1) 
		{
			setSprite(Sprite.enemSpriteR);
			if (walking)
			{
				this.a = Assets.enemAnimRight;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.enemSpriteR.getLotus());
			}
		}

		if (direction == 2) 
		{
			setSprite(Sprite.enemSpriteD);
			if (walking)
			{
				this.a = Assets.enemAnimDown;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.enemSpriteD.getLotus());
			}
		}

		if (direction == 3) 
		{
			setSprite(Sprite.enemSpriteL);
			if (walking)
			{
				this.a = Assets.enemAnimLeft;
				setImage(a.getImage());
			}
			else
			{
				setImage(Sprite.enemSpriteL.getLotus());
			}
		}
	/*
	 * if(!collision(xAxis, yAxis))// if no collision, move in direction { x
	 * += xAxis; y += yAxis; } else { Particle p = new Particle(x, y, 50);
	 * level.add(p); //Sound.hit.play();
	 * 
	 * } }
	 */
	}

	private void moveLeft() 
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() - 64);
		direction = 3;
		move(getCenter64X(), getCenter64Y());
		this.setDirection(Direction.LEFT);
	}

	private void moveRight()
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64X(getCenter64X() + movementSpeed);
		setPixel64X(getPixel64X() + 64);
		direction = 1;
		move(getCenter64X(), getCenter64Y());
		this.setDirection(Direction.RIGHT);
	}
	
	private void moveDown()
	{
		setMovementSpeed(1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() + 64);
		direction = 2;
		move(getCenter64X(), getCenter64Y());
		this.setDirection(Direction.DOWN);
	}

	private void moveUp()
	{
		setMovementSpeed(-1);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() - 64);
		direction = 0;
		move(getCenter64X(), getCenter64Y());
		this.setDirection(Direction.UP);
	}

/*private boolean getCollision()
{
	// Entity et = (Entity)Entity.getEntities().get(0);
	boolean b = false;

	// System.out.println("X: " + e.getCenterX() +" Y: " + e.getCenterY());

	ArrayList<ArmyUnit> entities = Entity.getPlayerArmyEntities();
	for (int i = 0; i < entities.size(); i++) {
		ArmyUnit e = entities.get(i);

		// e.update();
		if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
			// e.setVisible(false);
			// System.out.println("Collision!");
			this.target1 = e;
			this.isAtkReady = true;
			// this.target = e;
			b = true;
			return b;
			// Make Enemy list to hold enemies and check and compare against
			// player troops for coll
			} else {
				b = false;
				return b;
			}
		}
		ArrayList<BuildingEntity> bEntities = Entity
				.getPlayerBuildingEntities();
	
		for (int i = 0; i < bEntities.size(); i++) {
			BuildingEntity e = bEntities.get(i);

			// e.update();
			if (Rect.intersects(this.colliderBounds, e.colliderBounds)) {
				// e.setVisible(false);
				// 	System.out.println("Collision!");
				// e.setVisible(false);
				this.isAtkReady = true;
				// this.target = e;
				b = true;
				return b;
				// Make Enemy list to hold enemies and check and compare against
				// player troops for coll
			} else {
				b = false;
				return b;
			}
		}
		return b;

	}*/

	private void moveNone()
	{
		setMovementSpeed(0);
		// speedX = movementSpeed;
		setCenter64Y(getCenter64Y() + movementSpeed);
		setPixel64Y(getPixel64Y() + movementSpeed);
		// direction = 0;
		move(getCenter64X(), getCenter64Y());

	}

	public ArrayList<Tile> breadthFirstSearch(TileMap tileMap)// graph, start/cur pos, goal/target pos
	{
		//TileMap tileMap = new TileMap(50,25);
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
		
		//if(this.getTarget().getCenter64X() < GameManager.level.getWidth() && this.getTarget().getCenter64Y() < GameManager.level.getHeight())
		//{
		do
		{
			current = frontier.poll();
			// check thru each neighbor, 
			// add to frontier if not visited b4
			// System.out.println("Visiting: " + "X: "  + current.getTileX() + " Y: " + current.getTileY());
		
			if (current.getTileX() == this.getTarget().getCenter64X()+1 && current.getTileY() == this.getTarget().getCenter64Y())
			{break;}		
		
			results = new ArrayList<Tile>(tileMap.neighbors(current));// hld neighbors
			// System.out.print("\nResult: " );
			for(int i = 0; i < results.size(); i++)// loop thru neighbors
			{
				Tile next = results.get(i);

				// System.out.print("X: " + results.get(i).getTileX() + " Y: " + results.get(i).getTileY()+",");
				int newCost = costSoFar.get(costSoFar.size()-1) + tileMap.cost(current,next); 
				// if(costSoFar.contains(i) || newCost < costSoFar.get(costSoFar.size()-1))
				// {
			
				// }
				
					// check if neighbors have been visited already
						if( visited.contains(next))
						{
							//isVisited = true;
							// System.out.print(" Has been visited.");
						}
						else				
						{
							costSoFar.add(newCost);
						 	int priority = heuristic(this.getTarget(), next);
						 	ArrayList<Tile> a = new ArrayList<Tile>(frontier);
							if(a.size() < priority)
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
						
							// System.out.print(" +ed to Q/list.");
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
		}while(!frontier.isEmpty());
	
		/*	System.out.println("Visited Result:");
		for(int i = 0; i < visited.size(); i++)
		{
			System.out.print("X: " + visited.get(i).getTileX() + " Y: " + visited.get(i).getTileY() + ", ");
		}*/
	
		// reconstruct path to target
		int h = heuristic(this.getTarget(), startTile);
		
			Tile cur;
				//Tile goal = tileMap.getTileGrid()[this.getTarget().getCenter64X()-1][this.getTarget().getCenter64Y()];
				int n = cameFrom.indexOf(tileMap.getTileGrid()[this.getTarget().getCenter64X()+1][this.getTarget().getCenter64Y()]);// find goal/index in list
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
		
				// System.out.println("Adding " + "X:" + cur.getTileX() + " Y: " + cur.getTileY());
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
			/* System.out.println("Path: ");
			for(int i = 0; i < finalPath.size(); i++)
			{
				// System.out.print("X: " + finalPath.get(i).getTileX() + " Y: " + finalPath.get(i).getTileY() + ", ");
			}*/
		//return visited;
		
			return finalPath;
		//}else{ finalPath = null; return finalPath; }
	}

	public int heuristic(Entity t, Tile n) 
	{
		return Math.abs(t.getCenter64X()+1 - n.getTileX())
				+ Math.abs(t.getCenter64Y() - n.getTileY());
	}
	
	public void checkForDroppedItem()
	{
		for(int i = 0; i < GameManager.level.getDroppedResourceEntities().size(); i++)
		{
			if(GameManager.level.getDroppedResourceEntities().get(i).getCenter64X() == getCenter64X() && GameManager.level.getDroppedResourceEntities().get(i).getCenter64Y() == getCenter64Y())
			{				
					if(GameManager.level.getDroppedResourceEntities().get(i).getResourceType().equals(ResourceType.Food))
					{
						//System.out.println("RECIEVED FOOD");
						GameManager.level.getDroppedResourceEntities().get(i).setVisible(false);
					}
					else if(GameManager.level.getDroppedResourceEntities().get(i).getResourceType().equals(ResourceType.Gold)) 
					{
						//System.out.println("RECIEVED GOLD");

						GameManager.level.getDroppedResourceEntities().get(i).setVisible(false);
					}
				
			}
		}
	}
	
	public void die() 
	{
		Random r = new Random();
		int i;
		i = r.nextInt(10);
		if(i > 5)
		{
			// add resource
			Resource gold = new Resource(this.getCenter64X()*2, this.getCenter64Y()*2, GameManager.level, UnitType.Melee, ResourceType.Gold);
			//System.out.println("meat X:" + this.getCenter64X() + " Y : " + this.getCenter64Y());
			gold.setImage(Sprite.goldResource.getLotus());
			GameManager.level.getDroppedResourceEntities().add(gold);
		}
		//else if (i <= 5 && i > 2)
		else
		{
			// add resource
			Resource meat = new Resource(this.getCenter64X()*2, this.getCenter64Y()*2, GameManager.level, UnitType.Melee, ResourceType.Food);
			//System.out.println("meat X:" + this.getCenter64X() + " Y : " + this.getCenter64Y());
			meat.setImage(Sprite.meatResource.getLotus());
			GameManager.level.getDroppedResourceEntities().add(meat);
		}
		
		
		GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).xp += 4;
		
		setVisible(false);

			
		//GameManager.gold += 20;
	}
	
	public void setWanderTarget(Entity e) 
	{
		this.wanderTarget = e;
	}

	private boolean getisAtkReady() 
	{
		return this.isAtkReady;
	}

	public void setMovementSpeed(int s) 
	{
		this.movementSpeed = s;
	}

	public boolean isCanMove() 
	{
		return this.canMove;
	}

	public void setCanMove(boolean canMove) 
	{
		this.canMove = canMove;
	}

	public boolean isWalikng()
	{
		return this.walking;
	}

	public void setWalking(boolean b)
	{
		this.walking = b;
	}

	public void nextTurnUpdate() 
	{
		if (this.gridMoves == 2)
		{
			this.gridMoves++;
		}
		else if (this.gridMoves < 2)
		{
			this.gridMoves += 2;
		}
		else if (this.gridMoves >= 3)
		{
			this.gridMoves = 3;
		}	
		getBrain().update();
		//searched = false;
	}
	
	public void endAttack(int dmg) 
	{
		int i = this.targetLinkPos;
		//this.lastTargetX = this.target1.getCenter64X();
		//this.lastTargetY = this.target1.getCenter64Y();

			//calculateDmg();
		//GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).setPixel64X(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getPixel64X()-24);	
			System.out.println("Timer complete.");
			if(target1 != null)
			{
				if(direction == 1)
				{
					this.setPixel64X(this.getPixel64X()-18);
					this.target1.setPixel64X(this.target1.getPixel64X()+18);
					this.setImage(Sprite.enemSpriteR.getLotus());
				}
				else if(direction == 3)
				{
					this.setPixel64X(this.getPixel64X()+18);
					this.target1.setPixel64X(this.target1.getPixel64X()-18);
					this.setImage(Sprite.enemSpriteL.getLotus());
				}
				else if(direction == 2)
				{
					this.setPixel64Y(this.getPixel64Y()+18);	
					this.target1.setPixel64Y(this.target1.getPixel64Y()-18);	
					this.setImage(Sprite.enemSpriteU.getLotus());
				}
				else if(direction == 0)
				{
					this.setPixel64Y(this.getPixel64Y()-18);	
					this.target1.setPixel64Y(this.target1.getPixel64Y()+18);	
					this.setImage(Sprite.enemSpriteD.getLotus());
				}
			}
			else
			{
				if(direction == 1)
				{
					this.setPixel64X(this.getPixel64X()-18);

					this.setImage(Sprite.enemSpriteR.getLotus());
				}
				else if(direction == 3)
				{
					this.setPixel64X(this.getPixel64X()+18);

					this.setImage(Sprite.enemSpriteL.getLotus());
				}
				else if(direction == 2)
				{
					this.setPixel64Y(this.getPixel64Y()+18);	

					this.setImage(Sprite.enemSpriteU.getLotus());
				}
				else if(direction == 0)
				{
					this.setPixel64Y(this.getPixel64Y()-18);	

					this.setImage(Sprite.enemSpriteD.getLotus());
				}
			}
			
			//calcPositionDmgBonus(i, getRelativePosToTarget(i));
			GameManager.level.getPlayerArmyEntities().get(i).setTarget(new Entity(this));;

			GameManager.level.getPlayerArmyEntities().get(i).addHealth(dmg);
			setCombatMode(false);
			walking = false;
			GameManager.level.setIsEntitySelected(false);
			GameManager.level.setSelectedEntity(null);
			GameManager.level.getPlayer().setCombatMode(false);
	}
	
	/************************************************
	 * process damage to enemy target
	 * dmg: amount of inflicted damage
	 ************************************************/
	public void endPlayerInitiatedAttack(int dmg) 
	{
		int i = this.targetLinkPos;
		
		//this.lastTargetX = this.target1.getPixel64X();
		//this.lastTargetY = this.target1.getPixel64Y();
		//calculateDmg();
		//GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).setPixel64X(GameManager.level.getEnemyArmyEntities().get(this.targetLinkPos).getPixel64X()-24);	
			System.out.println("Timer complete.");
	
			if(GameManager.level.getPlayerArmyEntities().get(i).unitClass == UnitClass.Archer)
			{
				if(direction == 1)
				{
					//this.setPixel64X(this.getPixel64X()-37);
					this.setImage(Sprite.enemSpriteR.getLotus());
				}
				else if(direction == 3)
				{
					//this.setPixel64X(this.getPixel64X()+37);
					this.setImage(Sprite.enemSpriteL.getLotus());
				}
				else if(direction == 2)
				{
					//this.setPixel64Y(this.getPixel64Y()+37);	
					this.setImage(Sprite.enemSpriteU.getLotus());
				}
				else if(direction == 0)
				{
					//this.setPixel64Y(this.getPixel64Y()-37);	
					this.setImage(Sprite.enemSpriteD.getLotus());
				}
			}
			else
			{
				if(direction == 1)
				{
					this.setPixel64X(this.getPixel64X()-18);
					this.setImage(Sprite.enemSpriteR.getLotus());
				}
				else if(direction == 3)
				{
					this.setPixel64X(this.getPixel64X()+18);
					this.setImage(Sprite.enemSpriteL.getLotus());
				}
				else if(direction == 2)
				{
					this.setPixel64Y(this.getPixel64Y()+18);	
					this.setImage(Sprite.enemSpriteU.getLotus());
				}
				else if(direction == 0)
				{
					this.setPixel64Y(this.getPixel64Y()-18);	
					this.setImage(Sprite.enemSpriteD.getLotus());
				}
			
			}	
			
			//calcPositionDmgBonus(i, getRelativePosToTarget(i));
			//GameManager.level.getPlayerArmyEntities().get(i).getTarget().setPixel64X(this.lastTargetX); 
			//GameManager.level.getPlayerArmyEntities().get(i).getTarget().setPixel64Y(this.lastTargetY); 


			GameManager.level.getPlayerArmyEntities().get(i).addHealth(dmg);
			setCombatMode(false);
			walking = false;
			GameManager.level.setIsEntitySelected(false);
			GameManager.level.setSelectedEntity(null);
			GameManager.level.getPlayer().setCombatMode(false);
	}
	
	/***************************
	* Calculate battle damage
	***************************/
	public int calculateDmg() 
	{
		int i = this.targetLinkPos;
		//int bonus = calcPositionDmgBonus(i, getRelativePosToTarget(i), );
		
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive ((max - min) + 1 ) + min
		int r = random.nextInt(atkRating+1);
		
		if(r == 0)
		{
			//return -this.getAtkRating() + GameManager.level.getPlayerArmyEntities().get(targetLinkPos).getDefRating();
			return r;
		}
		else
		{	
			//return ((int) -(calcPositionDmgBonus(i, getRelativePosToTarget(i), r) + GameManager.level.getPlayerArmyEntities().get(targetLinkPos).getDefRating())); 

			return (r * atkRating) - GameManager.level.getPlayerArmyEntities().get(targetLinkPos).getDefRating();		
		}
	}
	
	/******************************************************
	 * Calculate extra damage for angle of attack
	 * i: posInLink for target
	 * dirOfTarget: relative direction on grid from entity
	 ******************************************************/
	public int calcPositionDmgBonus(int i, int dirOfTarget, int dmg)
	{
		// if enemy faces left or right and target is to the left
		if(dirOfTarget == 3)// left of me
		{
			switch(this.direction) // what dir is the this enemy facing
			{
				case 0:  // d 
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
			switch(direction)
			{
				case 0:  // d 
					// check targets direction
					return getTargetsDmgBasedOnDir(0, 1, dmg);
				case 1: // r
					// check targets direction
					return getTargetsDmgBasedOnDir(1, 1, dmg);

				case 2: // u
					// check targets direction
					return getTargetsDmgBasedOnDir(2, 1, dmg);
				case 3: // l
					// check targets direction
					return getTargetsDmgBasedOnDir(3, 1, dmg);						
			}
			
		}
		else if(dirOfTarget == 0)// down
		{
			switch(direction)
			{
				case 0:  // d 
					// check targets direction
					return getTargetsDmgBasedOnDir(0, 0, dmg);
				case 1: // r
					// check targets direction
					return getTargetsDmgBasedOnDir(1, 0, dmg);

				case 2: // u
					// check targets direction
					return getTargetsDmgBasedOnDir(2, 0, dmg);
				case 3: // l
					// check targets direction
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
	 * Find out whether target if above below, left or right of the enemy
	 * i: pos in link of player unit
	 ********************************************************************/
	public int getRelativePosToTarget(int i)
	{
		//  Right
		if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() + 1 == this.center64X && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() == this.center64Y)
		{
			return 1;
		}
		// Left
		else if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() - 1 == this.center64X && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() == this.center64Y)
		{
			return 3;
		}
		// Down
		else if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() == this.center64X && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() - 1 == this.center64Y)
		{
			return 0;
		}
		// Up
		else if(GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() == this.center64X && GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() + 1 == this.center64Y)
		{
			return 2;
		}
		else
		{
			return 4;
		}		
	}
	
	/*****************************************************
	 * 
	 * dir: direction of current entity
	 * dirOfTarget: direction of current entities target
	 ****************************************************/
	 public int getTargetsDmgBasedOnDir(int dir, int dirOfTarget, int dmg)
	 {
		 switch(dirOfTarget)
		 {
		 	case 0: // target is below
		 		
		 		// check for target facing down, inflict 50% of bonus dmg
		 		if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN)
		 		{
		 			return (int) ((int) dmg + (.5 * dmg));
		 		}
		 		// check for target facing either side, inflict 25% of bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return (int) ((int) dmg + (.25 * dmg));
		 		}
		 		// check for target facing forward, no bonus dmg.
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return dmg;

		 		}
		 		break;
		 		
		 	case 1: // target is to the right
		 		
		 		// check for target facing right, inflict 50% bonus dmg
		 		if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return (int) ((int) dmg + (.5 * dmg));
		 		}
		 		// check for target facing down or forward, inflict 25 % bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return (int) ((int) dmg + (.25 * dmg));
		 		}
		 		// check for target facing left, no bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT)
		 		{
		 			return dmg;
		 		}
		 		break;
		 		
		 	case 2: // target is above
		 		
		 		// check for target facing forward, inflict 50% bonus dmg
		 		if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return (int) ((int) dmg + (.5 * dmg));
		 		}
		 		// check for target facing either side, inflict 25% bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT && GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return (int) ((int) dmg + (.25 * dmg));
		 		}
		 		// check for target facing down, no bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN)
		 		{
		 			return dmg;
		 		}
		 		break;
		 		
		 	case 3: // target is to the left
		 		
		 		// check for target facing left, inflict 50% bonus dmg
		 		if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.LEFT)
		 		{
		 			return (int) ((int) dmg + (.5 * dmg));
		 		}
		 		// check for target facing down or forward, inflict 25 % bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.DOWN || GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.UP)
		 		{
		 			return (int) ((int) dmg + (.25 * dmg));
	 			}
		 		// check for target facing right, no bonus dmg
		 		else if(GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection() == Direction.RIGHT)
		 		{
		 			return dmg;
		 		}
		 		break;
		 }
		 
		 return dmg;		 
		 
		 // check targets dir
		// Entity.Direction i = GameManager.level.getPlayerArmyEntities().get(this.targetLinkPos).getDirection();
		 /*if(i == Entity.Direction.DOWN)
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
	
	/********************************************************************
	 * initiate enemy dominated attack seq on animation, pos, and target
	 * i: targetLinkPos
	 ********************************************************************/
	public void enemyInitiatedAttack(int i) 
	{
		this.targetLinkPos = i; // stores link pod of combat target in appropriate entity list
		
		// find direction of target entity
		//Check to the right
		if(target1 != null)
		{			
			//calcPositionDmgBonus(getRelativePosToTarget(i));
			
			if(this.getCenter64X()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.enemSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()+18);
				this.target1.setPixel64X(this.target1.getPixel64X()-18);	

				//setImage(Assets.enemAnimRightAtk.getImage());
			}
			//check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.enemSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()-18);	
				this.target1.setPixel64X(this.target1.getPixel64X()+18);	

				//setImage(Assets.enemAnimLeftAtk.getImage());
			}
			// check above
			else if(this.getCenter64Y()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.enemSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()-18);
				this.target1.setPixel64Y(this.target1.getPixel64Y()+18);
				//setImage(Assets.enemAnimUpAtk.getImage());
			}
			// check below
			else if(this.getCenter64Y()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.enemSpriteD.getLotus());
				this.setPixel64Y(this.getPixel64Y()+18);
				this.target1.setPixel64Y(this.target1.getPixel64Y()-18);
				//setImage(Assets.enemAnimDownAtk.getImage());
			}	
		}
		
	}
	
	public void attack(int i) 
	{
		this.targetLinkPos = i; // stores link pod of combat target in appropriate entity list
		
		// find direction of target entity
		if(GameManager.level.getPlayerArmyEntities().get(i).unitClass == UnitClass.Archer)
		{
			//Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.enemSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()+37);

					//setImage(Assets.enemAnimRightAtk.getImage());
			}
			//check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.enemSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()-37);	
			
				//setImage(Assets.enemAnimLeftAtk.getImage());
			}
			// check above
			else if(this.getCenter64Y()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.enemSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()-37);
				//setImage(Assets.enemAnimUpAtk.getImage());
			}
			// check below
			else if(this.getCenter64Y()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.enemSpriteD.getLotus());
				this.setPixel64Y(this.getPixel64Y()+37);
				//setImage(Assets.enemAnimDownAtk.getImage());
			}
		}
		else
		{
			//Check to the right
			if(this.getCenter64X()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()+1, this.getCenter64Y());
				direction = 1;
				setImage(Sprite.enemSpriteR.getLotus());
				this.setPixel64X(this.getPixel64X()+18);
					//setImage(Assets.enemAnimRightAtk.getImage());
			}
			//check to the left
			else if(this.getCenter64X()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y())
			{
				move(this.getCenter64X()-1, this.getCenter64Y());
				direction = 3;
				setImage(Sprite.enemSpriteL.getLotus());
				this.setPixel64X(this.getPixel64X()-18);	
		
				//setImage(Assets.enemAnimLeftAtk.getImage());
			}
			// check above
			else if(this.getCenter64Y()-1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()-1);
				direction = 2;
				setImage(Sprite.enemSpriteU.getLotus());
				this.setPixel64Y(this.getPixel64Y()-18);
				//setImage(Assets.enemAnimUpAtk.getImage());
			}
			// check below
			else if(this.getCenter64Y()+1 == GameManager.level.getPlayerArmyEntities().get(i).getCenter64Y() && this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(i).getCenter64X())
			{
				move(this.getCenter64X(), this.getCenter64Y()+1);
				direction = 0;
				setImage(Sprite.enemSpriteD.getLotus());
				this.setPixel64Y(this.getPixel64Y()+18);
				//setImage(Assets.enemAnimDownAtk.getImage());
			}	
		}
	}
	
	public boolean isCombatMode()
	{
		return this.isCombatMode;
	}

	public void setCombatMode(boolean b)
	{
		this.isCombatMode = b;
	}
	
	public UnitFSM getBrain()
	{
		return this.brain;
	}

	//*****************************************************************************************************
	// AI State Methods
	
	public void PRESET_TARGET()
	{
		if(!level.getPlayerCityEntities().isEmpty())
		{
			setTarget(GameManager.level.getPlayerCityEntities().get(0));

			getBrain().setState(UnitState.MOVE_TO_TARGET);
		}
	}
	
	public void WANDER() 
	{
		System.out.println("FindTarget");
		// search for target, if target received set state to MOVE_TO_TARGET
		// if no target set state to idle.
		Random r = new Random();

		int i = r.nextInt(12);
		int rX = r.nextInt(GameManager.level.getAMMW());
		int rY = r.nextInt(GameManager.level.getAMMH());
		
	

		if(getTarget() == null)
		{
			//setWanderTarget(new Entity(rX, rY));
			
				setTarget(new Entity(rX, rY, EntityType.Void));

			// set to wander			
				getBrain().setState(UnitState.MOVE_TO_TARGET);			
		}		
	}	

	public void MOVE_TO_TARGET()
	{
		Random r = new Random();

		int i = r.nextInt(12);
		int rX = r.nextInt(GameManager.level.getAMMW());
		int rY = r.nextInt(GameManager.level.getAMMH());
		searched = false;
		
		System.out.println("MoveToTarget");
			
		for(int ii = 0; ii < GameManager.level.getPlayerArmyEntities().size(); ii++)
		{			
			if(this.getCenter64X() + 1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y())  
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.ATTACK); 
				ATTACK();

				break;
			}
			else if(this.getCenter64X() - 1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y())
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.ATTACK); 
				ATTACK();

				break;

			}
			else if(this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() +1  == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y()  )
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.ATTACK); 
				ATTACK();

				break;

			}
			else if(this.getCenter64X() == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() -1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y()  )
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.ATTACK); 
				ATTACK();

				break;

			}
			else if(this.getCenter64X()-1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() -1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y()  )
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.MOVE_TO_TARGET); 
				searched = false;

				break;

			}
			else if(this.getCenter64X() + 1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y()-1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y())  
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.MOVE_TO_TARGET); 
				searched = false;

				break;
			}
			else if(this.getCenter64X() - 1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y()+1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y())
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);				
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.MOVE_TO_TARGET); 
				searched = false;

				break;

			}
			else if(this.getCenter64X()+1 == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64X() && this.getCenter64Y() +1  == GameManager.level.getPlayerArmyEntities().get(ii).getCenter64Y()  )
			{
				//System.out.println("ATTACK");
				GameManager.level.getPlayerArmyEntities().get(ii).setPosInLink(ii);
				setTarget(GameManager.level.getPlayerArmyEntities().get(ii));
				getBrain().setState(UnitState.MOVE_TO_TARGET); 
				searched = false;

				break;
			}					
			else
			{
				if(i < 4)
				{
					//this.pathToTarget.clear();
					searched = false;
					setTarget(new Entity(rX, rY, EntityType.Void));
					//getBrain().setState(UnitState.MOVE_TO_TARGET);
				}
			}
					
		}
					
		for( int ii = 0; ii < GameManager.level.getPlayerCityEntities().size(); ii++)
		{
			if(GameManager.level.getPlayerCityEntities().get(ii).getCenter64X()+1 == this.getCenter64X() && GameManager.level.getPlayerCityEntities().get(ii).getCenter64Y() == this.getCenter64Y())
			{
				setTarget(GameManager.level.getPlayerCityEntities().get(ii));
				ATTACK();
				getBrain().setState(UnitState.ATTACK); 
				break;
							
			}
			else if(GameManager.level.getPlayerCityEntities().get(ii).getCenter64X()-1 == this.getCenter64X() && GameManager.level.getPlayerCityEntities().get(ii).getCenter64Y() == this.getCenter64Y())
			{
				setTarget(GameManager.level.getPlayerCityEntities().get(ii));
				ATTACK();
				getBrain().setState(UnitState.ATTACK); 
				break;
							
			}
			else if(GameManager.level.getPlayerCityEntities().get(ii).getCenter64X() == this.getCenter64X() && GameManager.level.getPlayerCityEntities().get(ii).getCenter64Y()+1 == this.getCenter64Y())
			{
				setTarget(GameManager.level.getPlayerCityEntities().get(ii));
				ATTACK();
				getBrain().setState(UnitState.ATTACK); 
				break;
							
			}
			else if(GameManager.level.getPlayerCityEntities().get(ii).getCenter64X() == this.getCenter64X() && GameManager.level.getPlayerCityEntities().get(ii).getCenter64Y()-1 == this.getCenter64Y())
			{
				setTarget(GameManager.level.getPlayerCityEntities().get(ii));
				ATTACK();
				getBrain().setState(UnitState.ATTACK); 
				break;
							
			}
		}
					//getBrain().setState(UnitState.IDLE);
				// Check for building and units entities in a range of two grid spaces, if so set target and set state movetotarget

		     // allow enemy to walk/move to target, if target reached set state to ATTACK
			// if attacked, set state to defend
		   // check left, right up, up for if entity is close enough to target to attack
	}
	
	public void IDLE()
	{
		System.out.println("IDLE");
	}
	
	public void ATTACK()
	{
		System.out.println("ATTACK");
		if(target1 != null)
		{
			if(getTarget().getEntityType() != EntityType.Void)
			{
				// if target is dead, set state to idle
				EntityType t = getTarget().getEntityType(); 
				switch(t)
				{
					case Player:
			
					//GameManager.level.engagePlayerCombat( getPosInLink(), getTarget().getPosInLink());
					for(int i = 0; i < GameManager.level.getEnemyArmyEntities().size(); i++)
					{
						if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == this.getCenter64X() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == this.getCenter64Y())
						{
							for(int j = 0; j < GameManager.level.getPlayerArmyEntities().size(); j++)
							{
								if(GameManager.level.getPlayerArmyEntities().get(j).getCenter64X() == this.target1.getCenter64X() && GameManager.level.getPlayerArmyEntities().get(j).getCenter64Y() == this.target1.getCenter64Y())
								{
									if(target1 != null)
									{
										GameManager.level.getCombatEvents().add(new CombatEvent(i, j));
									}
								//System.out.println("***********************************************************");
								//break;
								}
							}
							
						}
					}
			

					break;
			
			
					case P_Building:
			
						if(this.gridMoves > 0)
						{
							if( GameManager.level.getPlayerCityEntities().size() != 0 )
							{	
								GameManager.level.getPlayerCityEntities().get(getTarget().getPosInLink()).addHealth(-5);
								System.out.println("Building looses health");
								gridMoves--;
								//target1 = null;
							}
						}
			
						break;
		
					default:
					break;
				}
			}
		}
		
		if(target1 != null)
		{
			// If target dies, set UnitState to WANDER to find a new target
			if(!this.getTarget().isVisible || this.target1.getHealth() <= 0)
			{
				this.target1 = null;
				getBrain().setState(UnitState.WANDER);
			}
		}
	}
	
	public void DEFEND()
	{
		
	}

	public Queue<CombatEvent> getCombatEvents() 
	{
		return combatEvents;
	}

	public void setCombatEvents(Queue<CombatEvent> combatEvents)
	{
		this.combatEvents = combatEvents;
	}
	
	public int getDirectional()
	{
		return this.direction;
	}
	
}
