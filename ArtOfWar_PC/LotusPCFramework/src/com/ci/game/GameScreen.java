package com.ci.game;

import game.ai.BanditAI;
import game.ai.CombatEvent;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ci.game.entity.Enemy;
import com.ci.game.entity.Entity;
import com.ci.game.entity.Entity.Direction;
import com.ci.game.entity.Entity.UnitClass;
import com.ci.game.entity.Resource;
import com.ci.game.entity.Entity.UnitType;
import com.ci.game.entity.Player;
import com.ci.game.entity.armyunit.Archer;
import com.ci.game.entity.armyunit.ArmyUnit;
import com.ci.game.entity.armyunit.Spearman;
import com.ci.game.entity.buildings.CastleWallV;
import com.ci.game.entity.particle.Particle;
import com.ci.game.graphics.Animation;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;
import com.ci.game.level.tile.Tile;
import com.ci.lotusFramework.Game;
import com.ci.lotusFramework.Input.InputEvent;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusAudio;
import com.ci.lotusFramework.implementation.LotusGame;
import com.ci.lotusFramework.implementation.LotusRenderView;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

/**
 * All gameplay will take place in this class
 * @author Jake Galletta Conscious Interactive (c) 2014
 *
 */
public class GameScreen extends Screen
{
	enum GameState
	{
		Ready, Running, Paused, GameOver, KeepMenu, GameMapMenu, BuildingPrompt, NonPlayerTurn
	}

	public static Tile[][] tileMap;
	
	GameState state = GameState.Running;//Ready
	
	// Variable Setup
	int xScrll, yScrll;
	private Level level;
	
	private Animation anim, hanim, sorcAnimDown,sorcAnimUp,sorcAnimLeft, sorcAnimRight;
	private Camera screen;

	private BanditAI banditFaction = new BanditAI();
	private Player player;

	private Keyboard key;

	private Mouse mouse;
	Random r;	
	
	public GameScreen(LotusRenderView game)  
	{		
		super(game);
		
		// init
		level = new Level(game);
		//GameManager.level = level;
		this.player = new Player(0, 0);
		this.player.init(GameManager.level);
		level.setPlayer(this.player);
		screen = new Camera(game.getFrame().getWidth(), game.getFrame().getHeight());
		screen.init(game);
		
		setScreen();
		
		// if new game
		this.player.setCurTurn(1);
		this.player.setPrevTurn(1);
		this.player.setTotalTurns(1);
		
		r = new Random();
		prePaintCombatGrid();
		prePaintBuildGrid();
				
		this.player.setGold(100);
		
		setAnims();	
		
		level.getEnemyArmyEntities().add(new Enemy(55, 85, GameManager.level, UnitType.Melee));
		level.getEnemyArmyEntities().add(new Enemy(56, 86, GameManager.level, UnitType.Melee));
		level.getEnemyArmyEntities().add(new Enemy(60, 90, GameManager.level, UnitType.Melee));
		level.getEnemyArmyEntities().add(new Enemy(60, 88, GameManager.level, UnitType.Melee));
		
		level.getPlayerArmyEntities().add(new Spearman(62, 91, GameManager.level, UnitType.Melee, UnitClass.Spearman));
		level.getPlayerArmyEntities().add(new Spearman(60, 87, GameManager.level, UnitType.Melee, UnitClass.Spearman));
		level.getPlayerArmyEntities().add(new Spearman(66, 88, GameManager.level, UnitType.Melee, UnitClass.Spearman));
		level.getPlayerArmyEntities().add(new Spearman(66, 98, GameManager.level, UnitType.Melee, UnitClass.Spearman));
		level.getPlayerArmyEntities().add(new Archer(68, 92, GameManager.level, UnitType.Ranged, UnitClass.Archer));
		level.getPlayerArmyEntities().add(new Archer(68, 90, GameManager.level, UnitType.Ranged, UnitClass.Archer));
		level.getPlayerArmyEntities().add(new Archer(68, 98, GameManager.level, UnitType.Ranged, UnitClass.Archer));
		//GameManager.level.getEnemyArmyEntities().add(new Enemy(4, 11, GameManager.level, UnitType.Melee));

	}
	
	/*************************************************************
	 * Set Camera/Screen position when game starts based on tribe
	 ************************************************************/
	private void setScreen() 
	{		
		// Check if new or saved game..
		
		
		switch(this.player.getTribe())
		{
			case "Cantiaci":
				screen.setOffset(135*32, 115*32);
				break;
				
			case "Regni":
				screen.setOffset(106*32, 120*32);
				break;
				
			case "Trinavantes":
				screen.setOffset(132*32, 96*32);
				break;
				
			case "Atrebates":
				screen.setOffset(110*32, 100*32);
				break;
				
			case "Iceni":
				screen.setOffset(138*32, 86*32);
				break;
		}		
	}

	private void updateReady(List<InputEvent> inputEvents)
	{
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (inputEvents.size() > 0)
			state = GameState.Running;
	}
	
	private void updateRunning(List<InputEvent> inputEvents)
	{
		//System.out.println("screenX:" + screen.getxOffset());		

		int value = 25;
		int left = value;
		int right = game.getFrame().getWidth() - 25;
		int top = value;
		int bottom = game.getFrame().getHeight() - 45;
		int scrllSpeed = 64;
		
		// bring up pause menu
		if(game.getMouse().inBounds(0,0, 35, 35) && game.getMouse().isMouseDown())
		{
			System.out.println("PAUSE");
			pause();
		}
	
		scrollScreen(value, left, right, top, bottom, scrllSpeed);// scroll level		
		
	// Mouse Input Checks
		
		if(!level.isProcessingNextTurn())
		{
			mouseDownInputCheck();
		}
			
		mouseUpInputCheck();		

		mouseClickedInputCheck();			
				
		if (game.getMouse().isTouched(level.getBarrackX(), level.getBarrackY(), 1, 1, screen)) //112,314,160,128
		{
			//System.out.println("Barrack");
		}
	
		//System.out.println("MouseX: " + ((game.getMouse().getX() + screen.getxOffset()) / 32) + " MouseY: " + ((game.getMouse().getY() + screen.getyOffset()) / 32-1));
		// GameManager.level.getPlayer().update();

		int len = inputEvents.size();
		for (int i = 0; i < len; i++)
		{
			InputEvent input = inputEvents.get(i);
			if(input.type == InputEvent.TOUCH_DOWN)
			{
				System.out.println("gbkuik");
			}
			if(input.checkForESC())
			{
				pause();
				/*game.getFrame().setVisible(false);
				game.getFrame().dispose();
				game.stop();
				System.exit(0);*/
			}
		}	

		//GameManager.level.getPlayer().update();
		
	    level.update();// update according to rate of ups		
		animate();// updates/call animation refs update()
		game.getMouse().update();
		game.getKeyboard().update();
	
		//banditFaction.update();
	}
	
	/**********************************************************************************************
	 * Scrolls level approx to mouse and arrow input
	 **********************************************************************************************/
	private void scrollScreen(int value, int left, int right, int top, int bottom, int scrllSpeed)
	{
		// Screen Scroll conditions	
		//if(game.getMouse().getX() <= left && game.getMouse().getX() >= 0 && game.getMouse().isMouseClicked())
		if(game.getMouse().inBounds(0, 25, 25, game.getFrame().getHeight()) && game.getMouse().isMouseDown() || game.getKeyboard().left)
		{
			if((screen.getxOffset()) > 0)
			{
				System.out.println("LEFT");		

				screen.setxOffset(screen.getxOffset() - scrllSpeed);
			}
		}
		//else if(game.getMouse().getX() >= right && game.getMouse().getX() <= right + value  && game.getMouse().isMouseDown())
		if(game.getMouse().inBounds(right, 20, 20, game.getFrame().getHeight()) && game.getMouse().isMouseDown() || game.getKeyboard().right)
		{			
			if(( screen.getxOffset()) < level.getWidth()-game.getFrame().getWidth() )
			{
				System.out.println("RIGHT");
				
				screen.setxOffset(screen.getxOffset() + scrllSpeed);				
			}
		}
		//else if(game.getMouse().getY() <= top && game.getMouse().getY() >= 0 && game.getMouse().isMouseDown())
		else if(game.getMouse().inBounds(0, top, game.getFrame().getWidth(), value) && game.getMouse().isMouseDown() || game.getKeyboard().up)
		{
			if((screen.getyOffset() + game.getFrame().getHeight()) > game.getFrame().getHeight())
			{
				System.out.println("TOP");

				screen.setyOffset(screen.getyOffset() - scrllSpeed);
			}
		}
		//else if(game.getMouse().getY() >= bottom && game.getMouse().getY() <= bottom + value && game.getMouse().isMouseClicked())
		else if(game.getMouse().inBounds(96, bottom, game.getFrame().getWidth(), value) && game.getMouse().isMouseDown() || game.getKeyboard().down)
		{
			if(screen.getyOffset() < (level.getHeight() -game.getFrame().getHeight())-32)
			{
				System.out.println("BOTTOM");

				screen.setyOffset(screen.getyOffset() + scrllSpeed);
			}
		}
	}
	
	/******************************************************************************
	 * Check for mouse up input
	 ******************************************************************************/
	private void mouseUpInputCheck()
	{
		if(game.getMouse().isMouseUp())
		{
			
			System.out.println("UP");
			
			level.combatEntityPointer();
			
			if(level.isPlayerEntitySelected() && level.isEntitySelected() && !level.checkNotTouchingPlyrEntity())
			{
				level.movePlayerEntity();
			}
			else if (level.isEnemyEntitySelected() && level.isEntitySelected() && !level.checkNotTouchingEnemEntity())
			{
				level.moveEnemyEntity();
			}	
			
			if (level.getPlayer().isBuildMode() && this.player.getGold() >= 10 && 
					level.getPlayer().isUiItem_1() && level.getPlayer().isPanelOn() && 
					game.getMouse().getX() >= 200)
			{
				CastleWallV wall;
				int x = (((game.getMouse().getX() - 200) / 32) + (level.getPlayer().xScroll / 32 ));
				int y = (((game.getMouse().getY() - 32) / 32) + (level.getPlayer().yScroll / 32));
				System.out.println("X: " + x + " Y: " + y);//int x = event.x;

				if(game.getMouse().getX() >= 192 && game.getMouse().getY() > 38 && !checkIfPlacementStacks(x, y))// chk if plyr is not touching within left res pane
				{
					// global variable for storing the building placement position, then use 
					// that position to prompt player confirm its placement during updateBuildingPrompt 
					   //this.tempTouchX = x;
					   //this.tempTouchY = y;
					
					wall = new CastleWallV(x, y);
					//GameManager.level.getPlayerCityEntities().add(wall);
					//state = GameState.BuildingPrompt;
					
				}
				else
				{
					//break;
				}

			}
			else if(level.getPlayer().isBuildMode() && this.player.getGold() >= 10 && level.getPlayer().isUiItem_1() && !level.getPlayer().isPanelOn())
			{
				CastleWallV wall;
				int x = (((game.getMouse().getX()) / 32) + (level.getPlayer().xScroll / 32 ));
				int y = (((game.getMouse().getY() - 32) / 32) + (level.getPlayer().yScroll / 32));
				//System.out.println("X: " + x + " Y: " + y);//int x = event.x;

				if(game.getMouse().getX() >= 192 && game.getMouse().getY() > 38 && checkIfPlacementStacks(x, y))// chk if plyr is touching within left res pane
				{
					//int xx = x * 2;// precision calc for placement on 16x16 tile grid
					//int yy = y * 2;
					//System.out.println("X: " + xx + " Y: " + yy);//int x = event.x;
					//this.tempTouchX = x;
					//this.tempTouchY = y;
				    wall = new CastleWallV(x, y);
					//GameManager.level.getPlayerBuildingEntities().add(wall);
				
					/*// add wall to tileMap for solid colli on Ai
					Tile t = new Tile(x, y);
					t.setSolid(true);
					level.getBuildingTileMap().getTileGrid()[x][y] = t;
					*/
				}
				else
				{
					level.getPlayer().setBuildMode(false);
					//GameManager.level.getPlayer().setUiItem_1(false);
					//break;
				}
				/*int rX = x + 1;
				if(rX < 59 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
				{
					t = new Tile(rX,yy);// right x tile
					t.setSolid(true);
					level.getTileMap().getTileGrid()[rX][y] = t;
				}
				int dY = y + 1;
				if(dY < 47 && dY >= 0 && rX < 59)// right down tile
				{				
				t = new Tile(rX,dY);
				t.setSolid(true);
				level.getTileMap().getTileGrid()[rX][dY] = t;
				}
				int ndY = y + 1;
				if(ndY < 47 && ndY >= 0)// down
				{
				t = new Tile(x,ndY);
				t.setSolid(true);
				level.getTileMap().getTileGrid()[x][ndY] = t;
				}
				*/
				//this.isBuildMode = false;
			}
			/*else if (this.isBuildMode == true && !barrackUIPaneActive && GameManager.gold >= 10 && uiItem_2)
			{
				GameManager.gold -= 10;
				Assets.build_fx.play(0.75f); // play sfx
				CastleWall wall;
				int x = (event.x / 32);
				int y = (event.y / 32);
				int xx = x * 2;// precision calc for placement on 16x16 tile grid
				int yy = y * 2;
				System.out.println("X: " + xx + " Y: " + yy);//int x = event.x;
				wall = new CastleWall(x, y);
				level.getPlayerBuildingEntities().add(wall);
				
				// add wall to tileMap for solid colli on Ai
				Tile t = new Tile(xx,yy);
				t.setSolid(true);
				//level.getTileMap().getTileGrid()[xx][yy] = t;
				
				int rX = xx + 1;
				if(rX < 49 && rX >= 0)// check if off screen/index < 0, to prevent -1 index error
				{
				t = new Tile(rX,yy);// right x tile
				t.setSolid(true);
				//level.getTileMap().getTileGrid()[rX][yy] = t;
				}
				int dY = yy + 1;
				if(dY < 24 && dY >= 0 && rX < 49)// right down tile
				{				
				t = new Tile(rX,dY);
				t.setSolid(true);
				//level.getTileMap().getTileGrid()[rX][dY] = t;
				}
				int ndY = yy + 1;
				if(ndY < 24 && ndY >= 0)// down
				{
				t = new Tile(xx,ndY);
				t.setSolid(true);
				//level.getTileMap().getTileGrid()[xx][ndY] = t;
				}
				//this.isBuildMode = false;
			}*/
			
	
			//game.getMouse().setMouseUp(false);
		}
	}
	
	/************************************************************
	 * Check for mouse down input 
	 ***********************************************************/
	private void mouseDownInputCheck()
	{
		if(game.getMouse().isMouseDown())
		{
			System.out.println("Down");
			if(!level.getPlayer().isPanelOn())
			{
				
				if(game.getMouse().isMouseDragged() && !level.getPlayer().isPanelOn() && !level.getPlayer().isBuildMode())
				{
					level.gridEntityPointerDragged((game.getMouse().getX()  / 64) + ((screen.getxOffset() / 64) ) , (game.getMouse().getY() / 64) + ((screen.getyOffset() / 64) ));
				}
				else if(!level.getPlayer().isPanelOn() && !level.getPlayer().isBuildMode() )
				{
					//GameManager.level.setIsEntitySelected(false);

					level.gridEntityPointer((game.getMouse().getX()  / 64) + ((screen.getxOffset() / 64) ) , (game.getMouse().getY() / 64) + ((screen.getyOffset() / 64) ), screen.getxOffset(), screen.getyOffset());
				}
				else
				{
					level.setIsEntitySelected(false);
					level.setIsTouchingDown(false);
				}
				
			
				
				if(level.isPlayerEntitySelected() && game.getMouse().inBounds(15, 245, 48, 48) && level.getPlayer().isPanelOn())
				{
					level.setIsUIItem_1Selected(true);
				}
				else
				{
					level.setIsUIItem_1Selected(false);
				}
		
				if(!level.isEntitySelected() && level.getPlayer().isPanelOn())// check i
				{
					//GameManager.level.getPlayer().setPanelOn(false);
					level.setUiPaneDrawTrig(false);

					level.setPlayerEntitySelected(false);
					level.setIsEntitySelected(false);
					level.setEnemyEntitySelected(false);
					level.setSelectedEntity(null);
				}			
			}
		}
	}
	
	/*************************************************************
	 * Check for mouse clicked input
	 *************************************************************/
	private void mouseClickedInputCheck()
	{
		if( game.getMouse().isMouseClicked())
		{	
			level.setIsTouchingDown(false);
			
			if(!level.getPlayer().isPanelOn())
			{
				int x = game.getMouse().getX() + screen.getxOffset() + (screen.getxOffset() % 64);
				int y = game.getMouse().getY() + screen.getyOffset() + (screen.getyOffset() % 64);
			

				level.tileEntityPointer(x, y, screen.getxOffset(), screen.getyOffset() );

			}
			else
			{
				//GameManager.level.getPlayer().setPanelOn(false);				
				// GameManager.level.getPlayer().setTownManagerMode(false);// new flag for tile town mode gui
			}
			// find town tile
			/*for(int i = 0; i < GameManager.level.getPlayerBuildingEntities().size(); i++)
			{
				if(game.getMouse().getX() + screen.getxOffset() > (GameManager.level.getPlayerBuildingEntities().get(i).getCenter64X() * 64) + xOffset   && 
				   game.getMouse().getX() + screen.getxOffset() < (GameManager.level.getPlayerBuildingEntities().get(i).getCenter64X() * 64) + xOffset + 55 && 
				   game.getMouse().getY() + screen.getyOffset() > (GameManager.level.getPlayerBuildingEntities().get(i).getCenter64Y() * 64) + yOffset + 32  &&    
				   game.getMouse().getY() + screen.getyOffset() < (GameManager.level.getPlayerBuildingEntities().get(i).getCenter64Y() * 64) + yOffset + 55 + 32)				   
				   {
					 System.out.println("Fuck me");
				   }
			}*/	
			
			// old isbuildingSelected check now flag isArcherUnitSelected
			if(game.getMouse().inBounds( 16, 64, 32, 32) && level.getPlayer().isPanelOn() && level.getPlayer().isTownManagerMode())
			{
				level.getPlayer().setArcherUnitSelected(true);
				level.getPlayer().setSpearmanUnitSelected(false);
				level.getPlayerCityEntities().get(level.getSelectedCity().getPosInLink()).setSelectedUnit(1);
				level.getPlayerCityEntities().get(level.getSelectedCity().getPosInLink()).setCurTurnOnProduction(5);
				//GameManager.level.getPlayer().setBuildingSelected(true);
				//GameManager.level.getPlayer().setMilitarySelected(false);
				System.out.println("Archer Unit Selected");
	
				Assets.uiItemSelect.setFramePosition(0);
				Assets.uiItemSelect.start();
			}				
			else if (game.getMouse().inBounds( 0, 42, 200, game.getCanvas().getHeight()) && level.getPlayer().isPanelOn() && level.getPlayer().isBuildingSelected())// when panelOn player can click in area and not close it
			{
				level.getPlayer().setBuildingSelected(true);
				level.setUiPaneDrawTrig(false);
			}
			else 
			{
				//GameManager.level.getPlayer().setBuildingSelected(false);
			}
			
			if(game.getMouse().inBounds(48, 128, 32, 32) && level.getPlayer().isPanelOn() && level.getPlayer().isBuildingSelected() && level.getPlayer().isUiItem_1() )
			{
				System.out.println("Build!!");
				level.getPlayer().setBuildMode(true);
				level.getPlayer().setUiItem_1(true);
			}
			
			
			// old isMilitarySelected check, now it spearman unit check
			if(game.getMouse().inBounds( 16, 96, 32, 32) && level.getPlayer().isPanelOn() && level.getPlayer().isTownManagerMode())
			{
				Assets.uiItemSelect.setFramePosition(0);
				Assets.uiItemSelect.start();
				level.getPlayer().setArcherUnitSelected(false);
				level.getPlayer().setSpearmanUnitSelected(true);
				level.getPlayerCityEntities().get(level.getSelectedCity().getPosInLink()).setSelectedUnit(2);
				level.getPlayerCityEntities().get(level.getSelectedCity().getPosInLink()).setCurTurnOnProduction(4);
				
				System.out.println("Spearman unit selected");
				//GameManager.level.getPlayer().setMilitarySelected(true);
				//GameManager.level.getPlayer().setBuildingSelected(false);

			}	
			else if (game.getMouse().inBounds( 0, 42, 200, game.getCanvas().getHeight()) && level.getPlayer().isPanelOn() && level.getPlayer().isMilitarySelected())// when panelOn player can click in area and not close it
			{
				level.getPlayer().setMilitarySelected(true);
				level.setUiPaneDrawTrig(false);
			}
			else 
			{
				level.getPlayer().setMilitarySelected(false);
			}
			
			if (game.getMouse().inBounds( 40, 0, 118, 36 )  )// check if plyr touches between/on the left res pane
			{
				Assets.sound.setFramePosition(0);
				Assets.sound.start();
				
				// null selected entities, so they dnt show up over panel
				level.setSelectedEntity(null);
				level.setSelectedPlayerEntity(null);

				level.getPlayer().setBuildMode(false);
				level.setIsEntitySelected(false);
				
				// Prob eventually set uiitems to false as a reset for selected objects

				if(!level.getPlayer().isPanelOn())// if plyr has not enabled GUIpanel, enable it.
				{
					level.getPlayer().setPanelOn(true);// set panel true
					level.setUiPaneDrawTrig(false);
					level.getPlayer().setBuildMode(false);
					level.getPlayer().setUiItem_1(false);
					level.getPlayer().setUiItem_2(false);
				}
				else // keep GUIPanel toggled off if not touched
				{
					level.getPlayer().setPanelOn(false);
					level.setUiPaneDrawTrig(false);
					level.getPlayer().setBuildMode(false);
					level.getPlayer().setUiItem_1(false);
					level.getPlayer().setUiItem_2(false);
				}		
			}
			else if(level.isEntitySelected() && !level.getPlayer().isPanelOn())// check if plyr selects a entity, and toggle panel for info on entity
			{
				System.out.println(level.getSelectedEntity().getPosInLink() + " ");
				level.getPlayer().setPanelOn(true);// set panel true
				level.setUiPaneDrawTrig(false);
			}	
			else if (game.getMouse().inBounds(0, 42, 200, game.getFrame().getHeight()) && level.getPlayer().isPanelOn())// when panelOn player can click in area and not close it
			{
				level.getPlayer().setPanelOn(true);
				level.setUiPaneDrawTrig(false);
			}
			else
			{
				//GameManager.level.getPlayer().setPanelOn(false);
				level.setUiPaneDrawTrig(false);

				level.setPlayerEntitySelected(false);
				level.setIsEntitySelected(false);
				level.setEnemyEntitySelected(false);
				level.setIsUnitUIItem_1Selected(false);
				//GameManager.level.set;
			}
			
			if(level.isPlayerEntitySelected() && this.player.getFood() >= 3 && game.getMouse().inBounds(15, 245, 48, 48) && level.getPlayer().isPanelOn())
			{
				if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getHealth() < 10);
				{
					level.setIsUnitUIItem_1Selected(true);
					level.setIsUIItem_1Selected(false);
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).addHealth(5);
					this.player.setFood(-3);
					//System.out.println("ate food");
				}
			}
			else
			{
				level.setIsUnitUIItem_1Selected(false);
			}
			level.setIsUIItem_1Selected(false);


			if(game.getMouse().inBounds(0, game.getCanvas().getHeight() - 32, 96, 32) && !level.getPlayer().isPanelOn())
			{
				if(state == GameState.Running && state != GameState.NonPlayerTurn)
				{
					System.out.println("Next Turn");
					endTurn();
				}
			}		

			// during military tab is selected, check for soldier selection
			if (game.getMouse().inBounds(48, 128, 32, 32) && level.getPlayer().isPanelOn() && level.getPlayer().isMilitarySelected() && level.getPlayer().isUiItem_1() && this.player.getGold() >= 5)
			{				
				// Spawn spearman troop
				
				Entity e;
				ArmyUnit sorc;
				int last = r.nextInt(25);
		
				if(last < 10)
				{
					last = 19;
				}
				sorc = new Spearman(last, 16, this.level, Entity.UnitType.Melee, UnitClass.Spearman);
				
				System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

				level.getPlayerArmyEntities().add(sorc);
				this.player.setGold(-5);

			}
			else if (game.getMouse().inBounds(16, 128, 32, 32) && level.getPlayer().isPanelOn() && level.getPlayer().isMilitarySelected() && level.getPlayer().isUiItem_2() && this.player.getGold() >= 5)
			{				
				// Spawn Archer troop
				
				ArmyUnit sorc;
				int last = r.nextInt(25);
		
				if(last < 10)
				{
					last = 19;
				}
				sorc = new Archer(last, 16, this.level, Entity.UnitType.Ranged, UnitClass.Archer);
				
				System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

				level.getPlayerArmyEntities().add(sorc);
				this.player.setGold(-5);
			}	
			
			// Unit position/direction
			if (game.getMouse().inBounds(32, 200, 32, 32) && level.isPlayerEntitySelected() && level.getPlayer().isPanelOn())
			{
				level.getSelectedEntity().setDirection(Direction.UP);
				level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setDirection(Direction.UP);
				if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Archer)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.archerU.getLotus());
				}
				else if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Spearman)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.peasSpriteU.getLotus());
				}
			}
			else if (game.getMouse().inBounds(68, 200, 32, 32) && level.isPlayerEntitySelected() && level.getPlayer().isPanelOn())
			{
				level.getSelectedEntity().setDirection(Direction.RIGHT);
				level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setDirection(Direction.RIGHT);
				if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Archer)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.archerR.getLotus());
				}
				else if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Spearman)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.peasSpriteR.getLotus());
				}
			}
			else if (game.getMouse().inBounds(102, 200, 32, 32) && level.isPlayerEntitySelected() && level.getPlayer().isPanelOn())
			{
				level.getSelectedEntity().setDirection(Direction.DOWN);
				level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setDirection(Direction.DOWN);
				if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Archer)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.archerD.getLotus());
				}
				else if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Spearman)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.peasSpriteD.getLotus());
				}
			}
			else if (game.getMouse().inBounds(134, 200, 32, 32) && level.isPlayerEntitySelected() && level.getPlayer().isPanelOn())
			{
				level.getSelectedEntity().setDirection(Direction.LEFT);
				level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setDirection(Direction.LEFT);
				if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Archer)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.archerL.getLotus());
				}
				else if(level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).getUnitClass() == UnitClass.Spearman)
				{
					level.getPlayerArmyEntities().get(level.getSelectedEntity().getPosInLink()).setImage(Sprite.peasSpriteL.getLotus());
				}
			}
			//else
			//{
				
			//}
			
			if(game.getMouse().getButton() == 16)
			{
				System.out.println("Left");
			}
			else if(game.getMouse().getButton() == 4)
			{
				System.out.println("Right");
				level.getPlayer().setPanelOn(false);				
			}
			
		}	
		
		

	}	
	
	/*********************************************************
	 * Check if selection stacks on a previous built building
	 * x: x index in tile grid
	 * y: y index in tile grid
	 *********************************************************/
	private boolean checkIfPlacementStacks(int x, int y)
	{
		boolean b = false;
		
		// Loop thru all building entities for entity in 
		for(int i = 0; i < level.getPlayerCityEntities().size(); i++)
		{
			if(level.getPlayerCityEntities().get(i).getCenter32X() == x && level.getPlayerCityEntities().get(i).getCenter32Y() == y)// +1
			{
				b = true;
				System.out.println("Cannot build here.");
				break;
			}
			else
			{
				b = false;
			}			
		}
		
		return b;
	}
	
	private void updatePaused(List<InputEvent> inputEvents)
	{
		
		if(game.getMouse().inBounds(game.getFrame().getWidth()/2, 9*32, 185, 45) && game.getMouse().isMouseDown())
		{
			resume();
		}
		else if(game.getMouse().inBounds(game.getFrame().getWidth()/2, 14*32, 100, 35) && game.getMouse().isMouseDown())
		{		
			game.getFrame().setVisible(false);
			game.getFrame().dispose();
			//game.getThread().stop();
			//game.stop();
			//System.exit(0);
		}
		else if(game.getMouse().inBounds(game.getFrame().getWidth()/2, 4*32, 100, 35) && game.getMouse().isMouseDown())
		{
			nullify();
			game.setScreen(new MainMenu(game));
		}
		
		int len = inputEvents.size();
		for(int i = 0; i < len; i++)
		{
			InputEvent event = inputEvents.get(i);
			if (event.type == InputEvent.TOUCH_UP)
			{
				/*if (inBounds(event, 0, 0, 800, 240)) 
				{
					if (!inBounds(event, 0, 0, 35, 35)) 
					{
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) 
				{
					nullify();
					goToMenu();
				}*/
			}
		}
	}
	
	private void updateGameOver(List<InputEvent> inputEvents)
	{
		int len = inputEvents.size();
		for (int i = 0; i < len; i++) 
		{
			InputEvent event = inputEvents.get(i);
			if (event.type == InputEvent.TOUCH_DOWN) 
			{
				//if (inBounds(event, 0, 0, 800, 480)) 
				//{
					//nullify();
					//game.setScreen(new MainMenuScreen(game));
					return;
				//}
			}
		}
	}
	
	private void updateKeepMenu(List<InputEvent> inputEvents)
	{
		/*int len = touchEvents.size();
		for (int i = 0; i < len; i++)
		{
			InputEvent event = touchEvents.get(i);
			if (!event.getIsMultiT() && event.type == InputEvent.TOUCH_DOWN)
			{
				if (inBounds(event, 0, 0, 800, 240)) 
				{
					resume();
				}

				if (inBounds(event, 0, 240, 800, 240) && !isTouched(event, GameManager.level.getBarrackX(), GameManager.level.getBarrackY() + 1, 3, 3))
				{
					endTurn();
					
				}
			}
		}*/
	}
	
	private void updateGameMapMenu(List<InputEvent> inputEvents)
	{
		/*int len = inputEvents.size();
		for (int i = 0; i < len; i++)
		{
			InputEvent event = inputEvents.get(i);
			if (event.type == InputEvent.TOUCH_DOWN)
			{
				if (inBounds(event, 0, 0, 800, 240)) 
				{
					
				}

				if (inBounds(event, 0, 240, 800, 240)) 
				{					
					resume();
				}
			}
		}*/
	}
	
	private void updateBuildingPrompt(List<InputEvent> inputEvents)
	{
		
	}
	
	@Override
	public void update(double deltaTime) 
	{
		List<InputEvent> inputEvents = game.getInput().getInputEvents();

		
		//System.out.println("Canvas " + game.getCanvas().getWidth() + " " + game.getCanvas().getHeight());
		//System.out.println("Frame " + game.getFrame().getWidth() + " " + game.getFrame().getHeight());

		
		// Depending on the state of the game, we call different update methods.
		if (state == GameState.Ready)
			updateReady(inputEvents);
		if (state == GameState.Running)
			updateRunning(inputEvents);
		if (state == GameState.Paused)
			updatePaused(inputEvents);
		if (state == GameState.GameOver)
			updateGameOver(inputEvents);
		if (state == GameState.GameMapMenu)
			updateGameMapMenu(inputEvents);
		if (state == GameState.KeepMenu)
			updateKeepMenu(inputEvents);
		if (state == GameState.BuildingPrompt)
			updateBuildingPrompt(inputEvents);
		if(state == GameState.NonPlayerTurn)
			updateNonPlayerTurn();
		
		if(game.getKeyboard().checkForESC())
		{
			pause();
			//game.getFrame().cleanAndQuit();
		}
		
		//System.out.println("X: " + game.getMouse().getX() + " Y: " + game.getMouse().getY());
	}
	
	int timer = 0;
	private void updateNonPlayerTurn() 
	{
		timer++;
		if(timer > 120)
		{
			timer = 0;
			processNextTurn(); 
			
			System.out.println("Cur:" + this.player.getCurTurn());
			resume();
			level.setProcessingNextTurn(false);

		}
		else
		{
			level.update();// update according to rate of ups

			animate();// updates/call animation refs update()

		}
	}

	private void processNextTurn()
	{
		//banditFaction.processTurn();

		level.spawn();

		//update entities
		for(int i = 0; i < level.getPlayerCityEntities().size(); i++)
		{
			//if(GameManager.level.getPlayerBuildingEntities().get(i).isVisible())
			//{			
				level.getPlayerCityEntities().get(i).nextTurnUpdate();
			//}
			//if (playerBuildingEntities.get(i).isBarrack())// if building is a barrack
		}
		
		for(int i = 0; i < level.getPlayerArmyEntities().size(); i++)
		{
			//if(GameManager.level.getPlayerArmyEntities().get(i).doesEntityHaveGridMoves())
			//{
				level.getPlayerArmyEntities().get(i).nextTurnUpdate();
			//}
		}
		
		for(int i = 0; i < level.getEnemyArmyEntities().size(); i++)
		{
			//if(GameManager.level.getEnemyArmyEntities().get(i).doesEntityHaveGridMoves())
			//{
				level.getEnemyArmyEntities().get(i).nextTurnUpdate();
			//}
		}

		
		while(!level.getCombatEvents().isEmpty() && level.getCombatEvents() != null)
		{
								// Trigger combat between the two entities
								//CombatEvent e = GameManager.level.getCombatEvents().poll();
			if(!level.getPlayer().isCombatMode())
			{
				CombatEvent e = level.getCombatEvents().poll();
				level.engageEnemyCombat(e.getAttackerIndex(), e.getAttackeeIndex());
				level.getPlayer().setCombatMode(false);
			}
		}

		level.getCombatEvents().clear();		
	}

	public void endTurn() 
	{		
		if(state == GameState.Running)
		{
			state = GameState.NonPlayerTurn;
			if(!level.isProcessingNextTurn())
			{
				level.setProcessingNextTurn(true);
				
				if(this.player.getCurTurn() == 1)
				{
					this.player.setTotalTurns(this.player.getTotalTurns() + 1);
					this.player.setCurTurn(this.player.getCurTurn() + 1);
				}
				else
				{
					this.player.setTotalTurns(this.player.getTotalTurns() + 1);
					this.player.setCurTurn(this.player.getCurTurn() + 1);
					this.player.setPrevTurn(this.player.getPrevTurn() + 1);
				}
			}
		}
	}

	@Override
	public void paint(double deltaTime) 
	{
		//int yScroll;
		//int xScroll;
		
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 10));
		
		xScrll = screen.getxOffset();
		yScrll = screen.getyOffset();
		
		//screen.setGraphics(g);
		//g.clearScreen(0);
	
		level.render(xScrll, yScrll, screen, g);
		
		// g.drawImage(Assets.combatModeGrid, 0, 0, null);
		
		/*if( game.getMouse().isMouseClicked())
		{
			g.drawString("mouseX: " + this.game.getMouse().getX() + " MouseY: " + this.game.getMouse().getY(), 2 * 32, 10 * 32);
		}*/
		//g.drawString("fps: " + this.frames, 48 * 32, 28 * 32);
		
		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
		if(state == GameState.KeepMenu)
			drawKeepUI();
		if(state == GameState.GameMapMenu)
			drawGameMapUI();
		if(state == GameState.BuildingPrompt)
			drawBuildingPromptUI();
		if(state == GameState.NonPlayerTurn)
			drawNonPlayerTurn();
	}

	private void drawNonPlayerTurn()
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		g.drawImage(Assets.processingTurnAnim.getImage(), game.getCanvas().getWidth()/2 - 96 , 48, null);
	}
	
	private void drawBuildingPromptUI() 
	{
		
	}

	private void drawGameMapUI()
	{
		
	}

	private void drawKeepUI() 
	{
		
	}

	private void drawGameOverUI()
	{
		
	}

	private void drawPausedUI()
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		g.drawImage(Assets.transLayer,0,0,game.getFrame().getWidth(), game.getFrame().getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century", 0, 45));
		
		g.drawString("Menu", game.getFrame().getWidth()/2, 5*32);
		g.drawString("Resume", game.getFrame().getWidth()/2, 10*32);
		g.drawString("Quit", game.getFrame().getWidth()/2, 15*32);
	}

	private void drawRunningUI() 
	{
		
	}

	private void drawReadyUI()
	{
		
	}
	
	public void animate() 
	{
		//anim.update(10);
		//hanim.update(50);
		long l = 55;
		
		Assets.enemAnimDown.update(l);
		Assets.enemAnimLeft.update(l);
		Assets.enemAnimRight.update(l);
		Assets.enemAnimUp.update(l);
		
		Assets.enemAnimDownAtk.update(l);
		Assets.enemAnimLeftAtk.update(l);
		Assets.enemAnimRightAtk.update(l);
		Assets.enemAnimUpAtk.update(l);
		
		Assets.peasAnimDown.update(50);
		Assets.peasAnimLeft.update(50);
		Assets.peasAnimRight.update(50);
		Assets.peasAnimUp.update(50);
		
		Assets.peasAnimDownAtk.update(l);
		Assets.peasAnimLeftAtk.update(l);
		Assets.peasAnimRightAtk.update(l);
		Assets.peasAnimUpAtk.update(l);
		
		Assets.processingTurnAnim.update(l);
		
		Assets.combatArrowDAnim.update(l);
		Assets.combatArrowLAnim.update(l);
		Assets.combatArrowRAnim.update(l);
		Assets.combatArrowUAnim.update(l);
		
		Assets.archerAnimDown.update(l);
		Assets.archerAnimLeft.update(l);
		Assets.archerAnimRight.update(l);
		Assets.archerAnimUp.update(l);
		
		Assets.archerAttackAnimDown.update(l);
		Assets.archerAttackAnimLeft.update(l);
		Assets.archerAttackAnimRight.update(l);
		Assets.archerAttackAnimUp.update(l);	
		
	}

	@Override
	public void pause() 
	{
		if (state == GameState.Running)
			state = GameState.Paused;
	}

	@Override
	public void resume() 
	{
		if (state == GameState.Paused)
			state = GameState.Running;
		else if (state == GameState.GameMapMenu)
			state = GameState.Running;
		else if (state == GameState.KeepMenu)
			state = GameState.Running;
		else if (state == GameState.BuildingPrompt)
			state = GameState.Running;	
		else if (state == GameState.NonPlayerTurn)
			state = GameState.Running;
	}

	private void nullify()
	{
		// Set all variables to null. You will be recreating them in the
		// constructor.		
		
		System.gc();
	}
	
	@Override
	public void dispose() 
	{
		
	}
	 
	public static BufferedImage makeImageTranslucent(BufferedImage source, double alpha) 
	{
		BufferedImage target = new BufferedImage(source.getWidth(),
		source.getHeight(), java.awt.Transparency.TRANSLUCENT);
		// Get the images graphics
		Graphics2D g = target.createGraphics();
		// Set the Graphics composite to Alpha
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		(float) alpha));
		// Draw the image into the prepared reciver image
		g.drawImage(source, null, 0, 0);
		// let go of all system resources in this Graphics
		g.dispose();
		// Return the image
		return target;
	}		
	
	public void prePaintBuildGrid()
	{
		BufferedImage img = (BufferedImage) game.getCanvas().createImage(game.getCanvas().getWidth(), game.getCanvas().getHeight());
		img = makeImageTranslucent(img, 0.0);// make entire img transparent

		Graphics2D g = (Graphics2D) img.getGraphics().create();		   
		
		g.setColor(Color.ORANGE);
		int w = game.getFrame().getWidth()/32;
		int h =  game.getFrame().getHeight()/32;		
		
		for(int j = 1; j < h; j++)
		{
			for(int i = 0; i <= w; i++)
			{
				if(i >= 0)
				{
					g.drawLine(i*32, 32, i*32, game.getCanvas().getHeight());
				}
				else
				{
					g.drawLine(i*32, 40, i*32, game.getCanvas().getHeight());
				}
				g.drawLine(0, j*32+32, game.getCanvas().getWidth(), j*32+32);

			}
		}
		
		Assets.buildModeGrid = img;
		
		// Create small grid for panelOn mode
		img = (BufferedImage) game.getCanvas().createImage(game.getCanvas().getWidth(), game.getCanvas().getHeight());
		img = makeImageTranslucent(img, 0.0);// make entire img transparent

		g = (Graphics2D) img.getGraphics().create();
		   
		g.setColor(Color.RED);	
		
		for(int j = 1; j < h; j++)
		{
			for(int i = 0; i <= w; i++)
			{
			
				g.drawLine(i*64, 32, i*64, game.getCanvas().getHeight());
				
				g.drawLine(0, j*64+32, game.getCanvas().getWidth()-200, j*64+32);

			}
		}
		
		Assets.buildModeGridWide = img;
	}
	
	public void prePaintCombatGrid()
	{
		BufferedImage img = (BufferedImage) game.getCanvas().createImage(game.getCanvas().getWidth(), game.getCanvas().getHeight());
		img = makeImageTranslucent(img, 0.0);// make entire img transparent

		Graphics2D g = (Graphics2D) img.getGraphics().create();		   
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2)); // line thickness

		int w = game.getFrame().getWidth()/64;
		int h =  game.getFrame().getHeight()/64;		
		
		for(int j = 1; j < h; j++)
		{
			for(int i = 0; i <= w; i++)
			{
				if(i >= 4)
				{
					g.drawLine(i*64, 32, i*64, game.getCanvas().getHeight());
				}
				else
				{
					g.drawLine(i*64, 40, i*64, game.getCanvas().getHeight());
				}
				g.drawLine(0, j*64+32, game.getCanvas().getWidth(), j*64+32);

			}
		}
		
		Assets.combatModeGrid = img;
		
		// Create small grid for panelOn mode
		img = (BufferedImage) game.getCanvas().createImage(game.getCanvas().getWidth(), game.getCanvas().getHeight());
		img = makeImageTranslucent(img, 0.0);// make entire img transparent

		g = (Graphics2D) img.getGraphics().create();
		   
		g.setColor(Color.BLACK);	
		g.setStroke(new BasicStroke(2)); // line thickness

		for(int j = 1; j < h; j++)
		{
			for(int i = 0; i <= w; i++)
			{
			
				g.drawLine(i*64, 32, i*64, game.getCanvas().getHeight());
				
				g.drawLine(0, j*64+32, game.getCanvas().getWidth()-200, j*64+32);

			}
		}
		
		Assets.combatModeGridWide = img;
	}

	@Override
	public void backButton() 
	{
		
	}

	@Override
	public void update12EverySec() 
	{
		if(state == GameState.Running)
		{
			//GameManager.update();			
		}
	}
	
    /********************************************************************
	 * Set Animations
	 ********************************************************************/
	public void setAnims()
	{
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD.getLotus(), 0);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD2.getLotus(), 400);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD3.getLotus(), 800);
		Assets.enemAnimDown.addFrame(Sprite.enemSpriteD4.getLotus(), 1200);
		//Assets.enemAnimDown.addFrame(Sprite.enemSpriteD5.getLotus(), 1600);

		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL.getLotus(), 0);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL2.getLotus(), 400);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL3.getLotus(), 800);
		Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL4.getLotus(), 1200);
		//Assets.enemAnimLeft.addFrame(Sprite.enemSpriteL5.getLotus(), 1600);
		
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU.getLotus(), 0);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU2.getLotus(), 400);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU3.getLotus(), 800);
		Assets.enemAnimUp.addFrame(Sprite.enemSpriteU4.getLotus(), 1200);
		//Assets.enemAnimUp.addFrame(Sprite.enemSpriteU5.getLotus(), 1600);
		
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR.getLotus(), 0);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR2.getLotus(), 400);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR3.getLotus(), 800);
		Assets.enemAnimRight.addFrame(Sprite.enemSpriteR4.getLotus(), 1200);
		//Assets.enemAnimRight.addFrame(Sprite.enemSpriteR5.getLotus(), 1600);		
		
		// enemy atk frames
		int ef = 350;
		Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL_Atk.getLotus(), 0);
		Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL2_Atk.getLotus(), 200);
		Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL3_Atk.getLotus(), 600);
		Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL4_Atk.getLotus(), 800);
		//Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL5_Atk.getLotus(), 1200);
		//Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL5_Atk.getLotus(), 1600);
		//Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL6_Atk.getLotus(), 1900);
		//Assets.enemAnimLeftAtk.addFrame(Sprite.enemySpriteL6_Atk.getLotus(), 2100);


		Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD_Atk.getLotus(), 0);
		Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD2_Atk.getLotus(), 200);
		Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD3_Atk.getLotus(), 600);
		Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD4_Atk.getLotus(), 800);
		//Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD5_Atk.getLotus(), 1200);
		//Assets.enemAnimDownAtk.addFrame(Sprite.enemySpriteD6_Atk.getLotus(), 1600);

		
		Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU_Atk.getLotus(), 0);
		Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU2_Atk.getLotus(), 200);
		Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU3_Atk.getLotus(), 600);
		Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU4_Atk.getLotus(), 800);
		//Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU5_Atk.getLotus(), 1200);
		//Assets.enemAnimUpAtk.addFrame(Sprite.enemySpriteU6_Atk.getLotus(), 1600);

		
		Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR_Atk.getLotus(), 0);
		Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR2_Atk.getLotus(), 400);
		Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR3_Atk.getLotus(), 800);
		Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR4_Atk.getLotus(), 1200);
		//Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR5_Atk.getLotus(), 1200);
		//Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR6_Atk.getLotus(), 1600);
		//Assets.enemAnimRightAtk.addFrame(Sprite.enemySpriteR5_Atk.getLotus(), 700);
		
		// peasant frames
		int l = 350;
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU.getLotus(), 0);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU2.getLotus(), 400);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU3.getLotus(), 800);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU4.getLotus(), 1200);
		Assets.peasAnimUp.addFrame(Sprite.peasSpriteU5.getLotus(), 1600);		
		
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD.getLotus(), 0);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD2.getLotus(), 400);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD3.getLotus(), 800);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD4.getLotus(), 1200);
		Assets.peasAnimDown.addFrame(Sprite.peasSpriteD5.getLotus(), 1600);


		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL.getLotus(), 0);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL2.getLotus(), 400);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL3.getLotus(), 800);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL4.getLotus(), 1200);
		Assets.peasAnimLeft.addFrame(Sprite.peasSpriteL5.getLotus(), 1600);

		
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR.getLotus(), 0);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR2.getLotus(), 400);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR3.getLotus(), 800);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR4.getLotus(), 1200);
		Assets.peasAnimRight.addFrame(Sprite.peasSpriteR5.getLotus(), 1600);

		//System.out.println("Soldier Spawned! X: " + sorc.getCenter64X() + "Y: " + sorc.getCenter64Y());

		// peasant atk frames
		int pf = 350;
		Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR_Atk.getLotus(), 0);
		Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR2_Atk.getLotus(), 400);
		Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR3_Atk.getLotus(), 800);
		Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR4_Atk.getLotus(), 2000);
		//Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR5_Atk.getLotus(), 1600);
		//Assets.peasAnimRightAtk.addFrame(Sprite.peasSpriteR6_Atk.getLotus(), 2000);
		
		Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU_Atk.getLotus(), 0);
		Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU2_Atk.getLotus(), 400);
		Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU3_Atk.getLotus(), 800);
		Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU4_Atk.getLotus(), 1200);
		//Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU5_Atk.getLotus(), 1600);
		//Assets.peasAnimUpAtk.addFrame(Sprite.peasSpriteU6_Atk.getLotus(), 2000);
		

		Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL_Atk.getLotus(), 0);
		Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL2_Atk.getLotus(), 400);
		Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL3_Atk.getLotus(), 800);
		Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL4_Atk.getLotus(), 2000);
		//Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL5_Atk.getLotus(), 1600);
		//Assets.peasAnimLeftAtk.addFrame(Sprite.peasSpriteL6_Atk.getLotus(), 2000);
		
		Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD_Atk.getLotus(), 0);
		Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD2_Atk.getLotus(), 400);
		Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD3_Atk.getLotus(), 800);
		Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD4_Atk.getLotus(), 1200);
		Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD5_Atk.getLotus(), 1600);
		//Assets.peasAnimDownAtk.addFrame(Sprite.peasSpriteD6_Atk.getLotus(), 2000);

		Assets.processingTurnAnim.addFrame(Assets.scrollLoad1, 1000);
		Assets.processingTurnAnim.addFrame(Assets.scrollLoad2, 2000);
		Assets.processingTurnAnim.addFrame(Assets.scrollLoad3, 3000);
		Assets.processingTurnAnim.addFrame(Assets.scrollLoad4, 4000);
		
		Assets.combatArrowDAnim.addFrame(Sprite.combatArrowD.getLotus(), 100);
		Assets.combatArrowDAnim.addFrame(Sprite.combatArrowD_2.getLotus(), 400);
		Assets.combatArrowDAnim.addFrame(Sprite.combatArrowD_3.getLotus(), 800);
		Assets.combatArrowDAnim.addFrame(Sprite.combatArrowD_2.getLotus(), 1000);
		Assets.combatArrowDAnim.addFrame(Sprite.combatArrowD.getLotus(), 1200);

		Assets.combatArrowUAnim.addFrame(Sprite.combatArrowU.getLotus(), 100);
		Assets.combatArrowUAnim.addFrame(Sprite.combatArrowU_2.getLotus(), 400);
		Assets.combatArrowUAnim.addFrame(Sprite.combatArrowU_3.getLotus(), 800);
		Assets.combatArrowUAnim.addFrame(Sprite.combatArrowU_2.getLotus(), 1000);
		Assets.combatArrowUAnim.addFrame(Sprite.combatArrowU.getLotus(), 1200);
		
		Assets.combatArrowRAnim.addFrame(Sprite.combatArrowR.getLotus(), 100);
		Assets.combatArrowRAnim.addFrame(Sprite.combatArrowR_2.getLotus(), 400);
		Assets.combatArrowRAnim.addFrame(Sprite.combatArrowR_3.getLotus(), 800);
		Assets.combatArrowRAnim.addFrame(Sprite.combatArrowR_2.getLotus(), 1000);
		Assets.combatArrowRAnim.addFrame(Sprite.combatArrowR.getLotus(), 1200);
		
		Assets.combatArrowLAnim.addFrame(Sprite.combatArrowL.getLotus(), 100);
		Assets.combatArrowLAnim.addFrame(Sprite.combatArrowL_2.getLotus(), 400);
		Assets.combatArrowLAnim.addFrame(Sprite.combatArrowL_3.getLotus(), 800);
		Assets.combatArrowLAnim.addFrame(Sprite.combatArrowL_2.getLotus(), 1000);
		Assets.combatArrowLAnim.addFrame(Sprite.combatArrowL.getLotus(), 1200);
		
	// Archer walking	
		Assets.archerAnimDown.addFrame(Sprite.archerD.getLotus(), 100);
		Assets.archerAnimDown.addFrame(Sprite.archerD2.getLotus(), 400);
		Assets.archerAnimDown.addFrame(Sprite.archerD3.getLotus(), 800);
		Assets.archerAnimDown.addFrame(Sprite.archerD4.getLotus(), 1000);
		
		Assets.archerAnimUp.addFrame(Sprite.archerU.getLotus(), 100);
		Assets.archerAnimUp.addFrame(Sprite.archerU2.getLotus(), 400);
		Assets.archerAnimUp.addFrame(Sprite.archerU3.getLotus(), 800);
		Assets.archerAnimUp.addFrame(Sprite.archerU4.getLotus(), 1000);

		Assets.archerAnimLeft.addFrame(Sprite.archerL.getLotus(), 100);
		Assets.archerAnimLeft.addFrame(Sprite.archerL2.getLotus(), 400);
		Assets.archerAnimLeft.addFrame(Sprite.archerL3.getLotus(), 800);
		Assets.archerAnimLeft.addFrame(Sprite.archerL4.getLotus(), 1000);

		Assets.archerAnimRight.addFrame(Sprite.archerR.getLotus(), 100);
		Assets.archerAnimRight.addFrame(Sprite.archerR2.getLotus(), 400);
		Assets.archerAnimRight.addFrame(Sprite.archerR3.getLotus(), 800);
		Assets.archerAnimRight.addFrame(Sprite.archerR4.getLotus(), 1000);
		
   // Archer Atk 
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD_Atk.getLotus(), 100);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD2_Atk.getLotus(), 400);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD3_Atk.getLotus(), 800);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD4_Atk.getLotus(), 1000);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD5_Atk.getLotus(), 1200);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD6_Atk.getLotus(), 1400);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD7_Atk.getLotus(), 1600);
		Assets.archerAttackAnimDown.addFrame(Sprite.archerD8_Atk.getLotus(), 1800);

		Assets.archerAttackAnimUp.addFrame(Sprite.archerU_Atk.getLotus(), 100);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU2_Atk.getLotus(), 400);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU3_Atk.getLotus(), 800);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU4_Atk.getLotus(), 1000);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU5_Atk.getLotus(), 1200);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU6_Atk.getLotus(), 1400);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU7_Atk.getLotus(), 1600);
		Assets.archerAttackAnimUp.addFrame(Sprite.archerU8_Atk.getLotus(), 1800);

		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL_Atk.getLotus(), 100);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL2_Atk.getLotus(), 400);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL3_Atk.getLotus(), 800);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL4_Atk.getLotus(), 1000);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL5_Atk.getLotus(), 1200);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL6_Atk.getLotus(), 1400);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL7_Atk.getLotus(), 1600);
		Assets.archerAttackAnimLeft.addFrame(Sprite.archerL8_Atk.getLotus(), 1800);

		Assets.archerAttackAnimRight.addFrame(Sprite.archerR_Atk.getLotus(), 100);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR2_Atk.getLotus(), 400);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR3_Atk.getLotus(), 800);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR4_Atk.getLotus(), 1000);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR5_Atk.getLotus(), 1200);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR6_Atk.getLotus(), 1400);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR7_Atk.getLotus(), 1600);
		Assets.archerAttackAnimRight.addFrame(Sprite.archerR8_Atk.getLotus(), 1800);
		
	}
}