package com.ci.game.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import com.ci.lotusFramework.implementation.LotusAudio;

public class Assets 
{
	public static Image fish;
	public static Image splash;
	
	public static BufferedImage lvlmap;
	public static BufferedImage test;
	
	//ui
	public static BufferedImage resPane;
	public static BufferedImage resPanel;
	public static BufferedImage militaryIcon;
	public static BufferedImage militaryIcon_select;
	public static BufferedImage buildingIcon;
	public static BufferedImage buildingIcon_select;
	public static BufferedImage buildModeGrid;
	public static BufferedImage buildModeGridWide;
	public static BufferedImage resWidget;
	public static BufferedImage transLayer;
	public static BufferedImage playButton;
	public static BufferedImage menuScrollBackground;
	public static BufferedImage optionsDeselected;
	public static BufferedImage optionsSelected;
	public static BufferedImage playButtonDeselected;
	public static BufferedImage graphicsDeselected;
	public static BufferedImage graphicsSelected;
	public static BufferedImage soundDeselected;
	public static BufferedImage soundSelected;

	// unit
	public static Animation peasAnimLeft;
	public static Animation peasAnimDown;
	public static Animation peasAnimRight;
	public static Animation peasAnimUp;
	public static Animation peasAnimLeftAtk;
	public static Animation peasAnimDownAtk;
	public static Animation peasAnimRightAtk;
	public static Animation peasAnimUpAtk;
	public static Animation enemAnimUp;
	public static Animation enemAnimDown;
	public static Animation enemAnimLeft;
	public static Animation enemAnimRight;
	public static Animation enemAnimUpAtk;
	public static Animation enemAnimDownAtk;
	public static Animation enemAnimLeftAtk;
	public static Animation enemAnimRightAtk;
	public static Animation processingTurnAnim;

	
	public static Image gridHorPointer;
	public static Image gridVerPointer;
	public static BufferedImage nextTurnButton;
	public static Image combatModeGrid;
	public static Image combatModeGridWide;

	public static Clip sound;
	public static Clip uiItemSelect;

	public static BufferedImage backButton;
	public static BufferedImage quitButton;
	public static BufferedImage quitButtonDeselected;
	public static BufferedImage eatIconSelected;
	public static BufferedImage eatIcon;
	public static BufferedImage scrollLoad1;
	public static BufferedImage scrollLoad2;
	public static BufferedImage scrollLoad3;
	public static BufferedImage scrollLoad4;
	public static BufferedImage bloodParticle;
	
	public static Animation combatArrowRAnim;
	public static Animation combatArrowLAnim;
	public static Animation combatArrowUAnim;
	public static Animation combatArrowDAnim;
	
	public static Animation archerAnimUp;
	public static Animation archerAnimDown;
	public static Animation archerAnimLeft;
	public static Animation archerAnimRight;
	
	public static Animation archerAttackAnimUp;
	public static Animation archerAttackAnimDown;
	public static Animation archerAttackAnimLeft;
	public static Animation archerAttackAnimRight;
	public static BufferedImage positionArrowSheet;
	public static BufferedImage map;
	public static BufferedImage unitIconSheet;
	public static BufferedImage mapTop;
	public static BufferedImage mapBot;
	
	// Main Menu
	public static Animation mainMenuTapestry;
	public static BufferedImage tapestry_1;
	public static BufferedImage tapestry_2;
	public static BufferedImage tapestry_3;
	public static BufferedImage tapestry_4;
	public static BufferedImage tapestry_5;
	public static BufferedImage tapestry_6;
	public static BufferedImage tapestry_7;
	public static BufferedImage tapestry_8;
	public static BufferedImage tapestry_9;
	public static BufferedImage contGameButtonSelected;
	public static BufferedImage contGameButtonDeselected;
	public static BufferedImage newGameButtonSelected;
	public static BufferedImage newGameButtonDeselected;
	public static BufferedImage mapScaled;
	public static BufferedImage parchment;


	public static Image getFish() 
	{
		return fish;
	}

	public void setFish(Image fish) 
	{
		this.fish = fish;
	}
}
