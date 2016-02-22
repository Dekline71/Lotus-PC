package com.ci.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ci.game.graphics.Animation;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Sprite;
import com.ci.game.graphics.SpriteSheet;
import com.ci.lotusFramework.Game;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.LotusAudio;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class SplashLoadingScreen extends Screen
{
	private String path;
	private boolean isAssetsLoaded = false;
	private boolean isAssetsLoadStarted = false;
	public SplashLoadingScreen(LotusRenderView game) 
	{
		super(game);
		//clipVol = 0.0f;
		//initAssets();
	}
	
	private void initAssets()
	{
		BufferedImage img;				
		
		try 
		{
			img = ImageIO.read(this.getClass().getResource("/levels/lvlmap.png"));
			Assets.lvlmap = img;
			
			//img = ImageIO.read(this.getClass().getResource("/res/levels/britainMap_Layers.png"));
			Assets.map = ImageIO.read(this.getClass().getResource("/levels/britainMap_Layers.png"));
		//	Assets.mapTop = ImageIO.read(this.getClass().getResource("/levels/mapTop.png"));
			//Assets.mapBot = ImageIO.read(this.getClass().getResource("/levels/mapBot.png"));
			
		// Main Menu Assets
			initMainMenuAssets();			
	
		// UI				
			initUIAssets();
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/scroll.png"));
			Assets.menuScrollBackground = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/optionsDe.png"));
			Assets.optionsDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/optionsSe.png"));
			Assets.optionsSelected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/playSe.png"));
			Assets.playButton = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/playDe.png"));
			Assets.playButtonDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/quitSe.png"));
			Assets.quitButton = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/quitDe.png"));
			Assets.quitButtonDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/newDe.png"));
			Assets.newGameButtonDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/newSe.png"));
			Assets.newGameButtonSelected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/continueDe.png"));
			Assets.contGameButtonDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/mainmenu/continueSe.png"));
			Assets.contGameButtonSelected = img;			
			
			img = ImageIO.read(this.getClass().getResource("/ui/optionsmenu/graphicsSe.png"));
			Assets.graphicsSelected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/optionsmenu/graphicsDe.png"));
			Assets.graphicsDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/optionsmenu/soundSe.png"));
			Assets.soundSelected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/optionsmenu/soundDe.png"));
			Assets.soundDeselected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/scrollBack.png"));
			Assets.backButton = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/eatIconSelected.png"));
			Assets.eatIconSelected = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/eatIcon.png"));
			Assets.eatIcon = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/scrollLoad1.png"));
			Assets.scrollLoad1 = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/scrollLoad2.png"));
			Assets.scrollLoad2 = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/scrollLoad3.png"));
			Assets.scrollLoad3 = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/scrollLoad4.png"));
			Assets.scrollLoad4 = img;
			
			img = ImageIO.read(this.getClass().getResource("/textures/sheets/particles/blood.png"));
			Assets.bloodParticle = img;
			
			img = ImageIO.read(this.getClass().getResource("/textures/sheets/buildGridDefault.png"));
			Assets.buildModeGridWide = img;
			
			img = ImageIO.read(this.getClass().getResource("/textures/sheets/buildGridDefaultShort.png"));
			Assets.buildModeGrid = img; 
			
			img = ImageIO.read(this.getClass().getResource("/textures/sheets/buildGridDefaultShort.png"));
			Assets.positionArrowSheet = img;
			
			img = ImageIO.read(this.getClass().getResource("/ui/playMenu/britainMapDecolored.png"));
			Assets.mapScaled = img;
			
			img = ImageIO.read(this.getClass().getResource("/textures/misc/oldPaper.jpg"));
			Assets.parchment = img;
			
			


			//System.out.println("Width: " + img.getWidth() + " Height: " + img.getHeight());
		
			//Assets.sound = new LotusAudio("/audio/build_fx.wav",false);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Assets.enemAnimUp = new Animation(Sprite.enemSpriteU.getLotus(),Sprite.enemSpriteU2.getLotus(),Sprite.enemSpriteU3.getLotus(), 500);
		//Assets.enemAnimDown = new Animation(Sprite.enemSpriteD.getLotus(),Sprite.enemSpriteD2.getLotus(),Sprite.enemSpriteD3.getLotus(), 500);
		//Assets.enemAnimLeft = new Animation(Sprite.enemSpriteL.getLotus(),Sprite.enemSpriteL2.getLotus(),Sprite.enemSpriteL3.getLotus(), 500);
		//Assets.enemAnimRight = new Animation(Sprite.enemSpriteR.getLotus(),Sprite.enemSpriteR2.getLotus(),Sprite.enemSpriteR3.getLotus(), 500);
		
		// Animation init
		Assets.enemAnimUp = new Animation();
		Assets.enemAnimDown = new Animation();
		Assets.enemAnimLeft = new Animation();
		Assets.enemAnimRight = new Animation();
		
		Assets.enemAnimUpAtk = new Animation();
		Assets.enemAnimDownAtk = new Animation();
		Assets.enemAnimLeftAtk = new Animation();
		Assets.enemAnimRightAtk = new Animation();
		
		Assets.peasAnimUp = new Animation();
		Assets.peasAnimLeft = new Animation();
		Assets.peasAnimRight = new Animation();
		Assets.peasAnimDown = new Animation();
		Assets.peasAnimUpAtk = new Animation();
		Assets.peasAnimLeftAtk = new Animation();
		Assets.peasAnimRightAtk = new Animation();
		Assets.peasAnimDownAtk = new Animation();
		
		Assets.archerAnimUp = new Animation();
		Assets.archerAnimDown = new Animation();
		Assets.archerAnimLeft = new Animation();
		Assets.archerAnimRight = new Animation();
		
		Assets.archerAttackAnimUp = new Animation();
		Assets.archerAttackAnimDown = new Animation();
		Assets.archerAttackAnimLeft = new Animation();
		Assets.archerAttackAnimRight = new Animation();
		
		Assets.processingTurnAnim = new Animation();
		
		Assets.combatArrowUAnim = new Animation();
		Assets.combatArrowDAnim = new Animation();
		Assets.combatArrowLAnim = new Animation();
		Assets.combatArrowRAnim = new Animation();
		Assets.mainMenuTapestry = new Animation();
		
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_1, 100);
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_1, 10000);
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_2, 11000);
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_3, 14000);
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_4, 17000);
		Assets.mainMenuTapestry.addFrame(Assets.tapestry_5, 20000);

		
		initAudio();
		
		long timer = System.currentTimeMillis();
	}
	
	private void initAudio() 
	{
	      try 
	      {
	          // Open an audio input stream.
	          URL url = this.getClass().getResource("/audio/ui/onUI_2.wav");
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	          // Get a sound clip resource.
	          Clip clip = AudioSystem.getClip();
	          // Open audio clip and load samples from the audio input stream.
	          clip.open(audioIn);
	          Assets.sound = clip;
			  FloatControl volume = (FloatControl) Assets.sound.getControl(FloatControl.Type.MASTER_GAIN);
			  volume.setValue(clipVol);// -75 off
	          
	          url = this.getClass().getResource("/audio/ui/select.wav");
	          audioIn = AudioSystem.getAudioInputStream(url);
	          // Get a sound clip resource.
	          clip = AudioSystem.getClip();
	          // Open audio clip and load samples from the audio input stream.
	          clip.open(audioIn);
		
	          Assets.uiItemSelect = clip;
	      	  volume = (FloatControl) Assets.uiItemSelect.getControl(FloatControl.Type.MASTER_GAIN);
			  volume.setValue(clipVol);
			  
			  isAssetsLoaded = true;
	        
	       } 
	      	catch (UnsupportedAudioFileException e) 
	       {
	          e.printStackTrace();
	       }
	      	catch (IOException e) 
	       {
	          e.printStackTrace();
	       }
	      	catch (LineUnavailableException e) 
	       {
	          e.printStackTrace();
	       }
	    
	}	
	
	public void initUIAssets()
	{
		try
		{
			Assets.resPane = ImageIO.read(this.getClass().getResource("/ui/stoneHor_GUI.png"));
			
			Assets.resPanel = ImageIO.read(this.getClass().getResource("/ui/stone_leftui.png"));				
			
			Assets.militaryIcon = ImageIO.read(this.getClass().getResource("/ui/militaryIcon.png"));		
			
			Assets.militaryIcon_select = ImageIO.read(this.getClass().getResource("/ui/militaryIcon_selected.png"));
			
			Assets.buildingIcon = ImageIO.read(this.getClass().getResource("/ui/buildingIcon.png"));
			
			Assets.buildingIcon_select = ImageIO.read(this.getClass().getResource("/ui/buildingIcon_selected.png"));
			
			Assets.buildModeGrid = ImageIO.read(this.getClass().getResource("/ui/buildModeGrid.png"));
			
			Assets.buildModeGridWide = ImageIO.read(this.getClass().getResource("/ui/buildModeGridWide.png"));
			
			Assets.resWidget = ImageIO.read(this.getClass().getResource("/ui/l_GUIWidget.png"));
			
			Assets.transLayer = ImageIO.read(this.getClass().getResource("/ui/argb.png"));
			
			//img = ImageIO.read(this.getClass().getResource("/ui/play.png"));
			//Assets.playButton = img;
			
			Assets.resPanel = ImageIO.read(this.getClass().getResource("/ui/stonePanel.png"));
			
			Assets.nextTurnButton = ImageIO.read(this.getClass().getResource("/ui/nextTurnButt.png"));
			
			Assets.gridHorPointer = ImageIO.read(this.getClass().getResource("/ui/gridHorPointer.png"));
						
			Assets.gridVerPointer = ImageIO.read(this.getClass().getResource("/ui/gridVertPointer.png"));
			
	

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initMainMenuAssets()
	{
		try
		{
			Assets.tapestry_1 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_1.png"));
			
			Assets.tapestry_2 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_2.png"));
			
			Assets.tapestry_3 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_3.png"));
			
			Assets.tapestry_4 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_4.png"));
					
			Assets.tapestry_5 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_5.png"));
		
			Assets.tapestry_6 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_6.jpg"));
			
			Assets.tapestry_7 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_7.jpg"));
		
			Assets.tapestry_8 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_8.jpg"));
		
			Assets.tapestry_9 = ImageIO.read(this.getClass().getResource("/textures/misc/mM_Tapestry/bayeuxTapestry_9.jpg"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	long startTimer = System.currentTimeMillis();

	/**
	 * update() method, you load all the resources that will use in the game 
	 * (i.e. all the resources created in the Assets class). 
	 * Do not need anything in paint() method, unless you would like to have an image while 
	 * the game loads these resources (make sure you load this in another class).
	 
	 * @see com.CI.lotusFramework.Screen#update(float)
	 */
	@Override 
	public void update(double deltaTime) 
	{
		System.out.println("Spalsh!!!");

		//  the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC.
		//	To start from zero, you need to define a start time. Then you can print the time elapsed after that start time.
		long curTime = (System.currentTimeMillis() - startTimer) / 1000; // time in seconds
		System.out.println(curTime);

		if(!isAssetsLoadStarted )
		{
			initAssets();
		}
		
		if(curTime > 2) // if 5 seconds hav passed on this screen, go to main menu
		{
			game.setScreen(new MainMenu(game));
		}
	}

	int i = 1;
	@Override
	public void paint(double deltaTime)
	{
		long timer = System.currentTimeMillis();

		Graphics g = this.game.getCanvas().getBufferStrategy().getDrawGraphics();
		//screen.setGraphics(g);
		if(game.getFrame().getWidth() > 800)
		{
			g.drawImage(Assets.splash, game.getFrame().getWidth()/10, game.getCanvas().getHeight()/10, null);		
			g.setColor(Color.WHITE);
			
			if(System.currentTimeMillis() - timer > 2000)
			{		
				switch(i)
				{
					case 1:
						g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
						break;
					case 2:
						g.drawString("Please Wait while assets are being fetched & loaded ..", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
						break;
					case 3:
						g.drawString("Please Wait while assets are being fetched & loaded .", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
						default:
							break;
				}
				i++;
				if(i <= 4)
				{
					i = 1;
				}
				timer += 1000;
			}
		}
		else
		{
			g.drawImage(Assets.splash, 0, 0, null);		
			g.setColor(Color.WHITE);
			if(System.currentTimeMillis() - timer > 2000)
			{
				switch(i)
				{
					case 1:
						g.drawString("Please Wait while assets are being fetched & loaded ...", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
						break;
					case 2:
						g.drawString("Please Wait while assets are being fetched & loaded ..", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
						break;
					case 3:
						g.drawString("Please Wait while assets are being fetched & loaded .", game.getCanvas().getWidth()/3, (int)(game.getCanvas().getHeight()/1.5));
					default:
						break;
				}	
				i++;
				if(i <= 3)
			{
				i = 0;
			}		
			timer += 1000;
		  }
		}

	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void dispose() 
	{
		
	}

	@Override
	public void backButton() 
	{
		
	}

	@Override
	public void update12EverySec() 
	{
		
	}	
}
