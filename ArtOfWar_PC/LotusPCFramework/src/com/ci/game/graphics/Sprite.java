package com.ci.game.graphics;


import java.awt.image.BufferedImage;

import com.ci.lotusFramework.Image;
import com.ci.lotusFramework.implementation.LotusImage;

public class Sprite 
{
	private Image image;
	public final int SIZE;// Size of sprite
	private int x, y;
	private int width, height;
	public int[] pixels;
	private SpriteSheet sheet;
	
	// UI
	public static Sprite gold = new Sprite(64, 3,1,SpriteSheet.sheet);
	public static Sprite meat = new Sprite(48, 3, 0, SpriteSheet.grassSheet);

	public static Sprite num_0 = new Sprite(16, 8,0,SpriteSheet.sheet);
	public static Sprite num_1 = new Sprite(16, 9,0,SpriteSheet.sheet);
	public static Sprite num_2 = new Sprite(16, 10,0,SpriteSheet.sheet);
	public static Sprite num_3 = new Sprite(16, 11,0,SpriteSheet.sheet);
	public static Sprite num_4 = new Sprite(16, 12,0,SpriteSheet.sheet);
	public static Sprite num_5 = new Sprite(16, 13,0,SpriteSheet.sheet);
	public static Sprite num_6 = new Sprite(16, 8,1,SpriteSheet.sheet);
	public static Sprite num_7 = new Sprite(16, 9,1,SpriteSheet.sheet);
	public static Sprite num_8 = new Sprite(16, 10,1,SpriteSheet.sheet);
	public static Sprite num_9 = new Sprite(16, 11,1,SpriteSheet.sheet);
	public static Sprite goldText = new Sprite(48, 1, 2, SpriteSheet.sheet);
	public static Sprite daysText = new Sprite(64, 1, 0, SpriteSheet.grassSheet);	
	public static Sprite leftArrowDe = new Sprite(32, 2, 2, SpriteSheet.grassSheet);
	public static Sprite rightArrowDe = new Sprite(32, 3, 2, SpriteSheet.grassSheet);
	public static Sprite leftArrowSe = new Sprite(32, 2, 3, SpriteSheet.grassSheet);
	public static Sprite rightArrowSe = new Sprite(32, 3, 3, SpriteSheet.grassSheet);
	public static Sprite masterVolString = new Sprite(64, 1, 2, SpriteSheet.grassSheet);
	
	public static Sprite combatArrowU = new Sprite(20, 0, 0, SpriteSheet.arrowSheet);
	public static Sprite combatArrowU_2 = new Sprite(20, 1, 0, SpriteSheet.arrowSheet);
	public static Sprite combatArrowU_3 = new Sprite(20, 2, 0, SpriteSheet.arrowSheet);

	public static Sprite combatArrowD = new Sprite(20, 0, 2, SpriteSheet.arrowSheet);
	public static Sprite combatArrowD_2 = new Sprite(20, 1, 2, SpriteSheet.arrowSheet);
	public static Sprite combatArrowD_3 = new Sprite(20, 2, 2, SpriteSheet.arrowSheet);

	public static Sprite combatArrowL = new Sprite(20, 0, 3, SpriteSheet.arrowSheet);
	public static Sprite combatArrowL_2 = new Sprite(20, 1, 3, SpriteSheet.arrowSheet);
	public static Sprite combatArrowL_3 = new Sprite(20, 2, 3, SpriteSheet.arrowSheet);
	
	public static Sprite combatArrowR = new Sprite(20, 0, 1, SpriteSheet.arrowSheet);
	public static Sprite combatArrowR_2 = new Sprite(20, 1, 1, SpriteSheet.arrowSheet);
	public static Sprite combatArrowR_3 = new Sprite(20, 2, 1, SpriteSheet.arrowSheet);
	
	public static Sprite archerUnitIcon = new Sprite(32, 0, 0, SpriteSheet.unitIconsSheet); 
	public static Sprite spearUnitIcon = new Sprite(32, 1, 0, SpriteSheet.unitIconsSheet); 
	public static Sprite swordUnitIcon = new Sprite(32, 2, 0, SpriteSheet.unitIconsSheet); 
	
	// positioning arrows
	public static Sprite positionArrowU = new Sprite(32, 1, 0, SpriteSheet.positionArrowSheet);
	public static Sprite positionArrowR = new Sprite(32, 1, 1, SpriteSheet.positionArrowSheet);
	public static Sprite positionArrowD = new Sprite(32, 1, 2, SpriteSheet.positionArrowSheet);
	public static Sprite positionArrowL = new Sprite(32, 1, 3, SpriteSheet.positionArrowSheet);

	public static Sprite selectPositionArrowU = new Sprite(32, 2, 0, SpriteSheet.positionArrowSheet);
	public static Sprite selectPositionArrowR = new Sprite(32, 2, 1, SpriteSheet.positionArrowSheet);
	public static Sprite selectPositionArrowD = new Sprite(32, 2, 2, SpriteSheet.positionArrowSheet);
	public static Sprite selectPositionArrowL = new Sprite(32, 2, 3, SpriteSheet.positionArrowSheet);

	// town/city sprites
	public static Sprite town_1 = new Sprite (64, 2, 0, SpriteSheet.townsSheet);
	public static Sprite city_1 = new Sprite (64, 0, 0, SpriteSheet.citySheet);
	public static Sprite city_2 = new Sprite (64, 1, 0, SpriteSheet.citySheet);
	public static Sprite city_3 = new Sprite (64, 0, 1, SpriteSheet.citySheet);
	public static Sprite city_4 = new Sprite (64, 1, 1, SpriteSheet.citySheet);
	public static Sprite city_5 = new Sprite (70, 2, 0, SpriteSheet.citySheet);



	
	
	// Item Sprites
	public static Sprite meatResource = new Sprite(32, 4, 2, SpriteSheet.grassSheet);
	public static Sprite goldResource = new Sprite(32, 4, 3, SpriteSheet.grassSheet);

	
// Environment Sprites:
	
	public static Sprite grass = new Sprite(32, 0, 6, SpriteSheet.grassSheet);//create grass sprite
	public static Sprite grass_2 = new Sprite(32, 0, 6, SpriteSheet.grassSheet);
	public static Sprite flower = new Sprite(16, 0, 2, SpriteSheet.sheet);
	public static Sprite shrub = new Sprite(32, 3, 7, SpriteSheet.grassSheet);
	public static Sprite sand = new Sprite(32, 0, 3, SpriteSheet.grassSheet);

// Entity Sprites	
	public static Sprite enemSpriteR = new Sprite(60, 2, 0, SpriteSheet.bandit);
	public static Sprite enemSpriteR2 = new Sprite(60, 2, 1, SpriteSheet.bandit);
	public static Sprite enemSpriteR3 = new Sprite(60, 2, 2, SpriteSheet.bandit);
	public static Sprite enemSpriteR4 = new Sprite(60, 2, 3, SpriteSheet.bandit);
	public static Sprite enemSpriteR5 = new Sprite(60, 2, 4, SpriteSheet.bandit);
	
	public static Sprite enemSpriteU = new Sprite(60, 0, 0, SpriteSheet.bandit);
	public static Sprite enemSpriteU2 = new Sprite(60, 0, 1, SpriteSheet.bandit);
	public static Sprite enemSpriteU3 = new Sprite(60, 0, 2, SpriteSheet.bandit);
	public static Sprite enemSpriteU4 = new Sprite(60, 0, 3, SpriteSheet.bandit);
	public static Sprite enemSpriteU5 = new Sprite(60, 0, 4, SpriteSheet.bandit);
	
	public static Sprite enemSpriteD = new Sprite(60, 1, 0, SpriteSheet.bandit);
	public static Sprite enemSpriteD2 = new Sprite(60, 1, 1, SpriteSheet.bandit);
	public static Sprite enemSpriteD3 = new Sprite(60, 1, 2, SpriteSheet.bandit);
	public static Sprite enemSpriteD4 = new Sprite(60, 1, 3, SpriteSheet.bandit);
	public static Sprite enemSpriteD5 = new Sprite(60, 1, 4, SpriteSheet.bandit);
	
	public static Sprite enemSpriteL = new Sprite(60, 3, 0, SpriteSheet.bandit);
	public static Sprite enemSpriteL2 = new Sprite(60, 3, 1, SpriteSheet.bandit);
	public static Sprite enemSpriteL3 = new Sprite(60, 3, 2, SpriteSheet.bandit);
	public static Sprite enemSpriteL4 = new Sprite(60, 3, 3, SpriteSheet.bandit);
	public static Sprite enemSpriteL5 = new Sprite(60, 3, 4, SpriteSheet.bandit);
	
	public static Sprite enemySpriteR_Atk =  new Sprite (60, 6, 0, SpriteSheet.bandit);
	public static Sprite enemySpriteR2_Atk =  new Sprite (60, 6, 1, SpriteSheet.bandit);
	public static Sprite enemySpriteR3_Atk =  new Sprite (60, 6, 2, SpriteSheet.bandit);
	public static Sprite enemySpriteR4_Atk =  new Sprite (60, 6, 3, SpriteSheet.bandit);
	public static Sprite enemySpriteR5_Atk =  new Sprite (60, 6, 4, SpriteSheet.bandit);
	public static Sprite enemySpriteR6_Atk =  new Sprite (60, 6, 5, SpriteSheet.bandit);
	
	public static Sprite enemySpriteU_Atk =  new Sprite (60, 4, 0, SpriteSheet.bandit);
	public static Sprite enemySpriteU2_Atk =  new Sprite (60, 4, 1, SpriteSheet.bandit);
	public static Sprite enemySpriteU3_Atk =  new Sprite (60, 4, 2, SpriteSheet.bandit);
	public static Sprite enemySpriteU4_Atk =  new Sprite (60, 4, 3, SpriteSheet.bandit);
	public static Sprite enemySpriteU5_Atk =  new Sprite (60, 4, 4, SpriteSheet.bandit);
	public static Sprite enemySpriteU6_Atk =  new Sprite (60, 4, 5, SpriteSheet.bandit);

	public static Sprite enemySpriteD_Atk =  new Sprite (60, 5, 0, SpriteSheet.bandit);
	public static Sprite enemySpriteD2_Atk =  new Sprite (60, 5, 1, SpriteSheet.bandit);
	public static Sprite enemySpriteD3_Atk =  new Sprite (60, 5, 2, SpriteSheet.bandit);
	public static Sprite enemySpriteD4_Atk =  new Sprite (60, 5, 3, SpriteSheet.bandit);
	public static Sprite enemySpriteD5_Atk =  new Sprite (60, 5, 4, SpriteSheet.bandit);
	public static Sprite enemySpriteD6_Atk =  new Sprite (60, 5, 5, SpriteSheet.bandit);

	public static Sprite enemySpriteL_Atk =  new Sprite (60, 7, 0, SpriteSheet.bandit);
	public static Sprite enemySpriteL2_Atk =  new Sprite (60, 7, 1, SpriteSheet.bandit);
	public static Sprite enemySpriteL3_Atk =  new Sprite (60, 7, 2, SpriteSheet.bandit);
	public static Sprite enemySpriteL4_Atk =  new Sprite (60, 7, 3, SpriteSheet.bandit);
	public static Sprite enemySpriteL5_Atk =  new Sprite (60, 7, 4, SpriteSheet.bandit);
	public static Sprite enemySpriteL6_Atk =  new Sprite (60, 7, 5, SpriteSheet.bandit);

	
	public static Sprite peasSpriteU = new Sprite(60, 0, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteU2 = new Sprite(60, 0, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteU3 = new Sprite(60, 0, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteU4 = new Sprite(60, 0, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteU5 = new Sprite(60, 0, 4, SpriteSheet.peasant);
    
	public static Sprite peasSpriteR = new Sprite(60, 1, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteR2 = new Sprite(60, 1, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteR3 = new Sprite(60, 1, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteR4 = new Sprite(60, 1, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteR5 = new Sprite(60, 1, 4, SpriteSheet.peasant);

	public static Sprite peasSpriteD = new Sprite(60, 2, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteD2 = new Sprite(60, 2, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteD3 = new Sprite(60, 2, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteD4 = new Sprite(60, 2, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteD5 = new Sprite(60, 2, 4, SpriteSheet.peasant);

	public static Sprite peasSpriteL = new Sprite(60, 3, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteL2 = new Sprite(60, 3, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteL3 = new Sprite(60, 3, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteL4 = new Sprite(60, 3, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteL5 = new Sprite(60, 3, 4, SpriteSheet.peasant);
	
	public static Sprite peasSpriteU_Atk = new Sprite(60, 4, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteU2_Atk = new Sprite(60, 4, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteU3_Atk = new Sprite(60, 4, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteU4_Atk = new Sprite(60, 4, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteU5_Atk = new Sprite(60, 4, 4, SpriteSheet.peasant);
	public static Sprite peasSpriteU6_Atk = new Sprite(60, 4, 5, SpriteSheet.peasant);
    
	public static Sprite peasSpriteR_Atk = new Sprite(60, 5, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteR2_Atk = new Sprite(60, 5, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteR3_Atk = new Sprite(60, 5, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteR4_Atk = new Sprite(60, 5, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteR5_Atk = new Sprite(60, 5, 4, SpriteSheet.peasant);
	public static Sprite peasSpriteR6_Atk = new Sprite(60, 5, 5, SpriteSheet.peasant);


	public static Sprite peasSpriteD_Atk = new Sprite(60, 6, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteD2_Atk = new Sprite(60, 6, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteD3_Atk = new Sprite(60, 6, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteD4_Atk = new Sprite(60, 6, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteD5_Atk = new Sprite(60, 6, 4, SpriteSheet.peasant);
	public static Sprite peasSpriteD6_Atk = new Sprite(60, 6, 5, SpriteSheet.peasant);


	public static Sprite peasSpriteL_Atk = new Sprite(60, 7, 0, SpriteSheet.peasant);
	public static Sprite peasSpriteL2_Atk = new Sprite(60, 7, 1, SpriteSheet.peasant);
	public static Sprite peasSpriteL3_Atk = new Sprite(60, 7, 2, SpriteSheet.peasant);
	public static Sprite peasSpriteL4_Atk = new Sprite(60, 7, 3, SpriteSheet.peasant);
	public static Sprite peasSpriteL5_Atk = new Sprite(60, 7, 4, SpriteSheet.peasant);
	public static Sprite peasSpriteL6_Atk = new Sprite(60, 7, 5, SpriteSheet.peasant);
	
	public static Sprite archerU = new Sprite(32, 0, 0, SpriteSheet.archer);
	public static Sprite archerU2 = new Sprite(32, 0, 1, SpriteSheet.archer);
	public static Sprite archerU3 = new Sprite(32, 0, 2, SpriteSheet.archer);
	public static Sprite archerU4 = new Sprite(32, 0, 3, SpriteSheet.archer);
	
	public static Sprite archerR = new Sprite(32, 1, 0, SpriteSheet.archer);
	public static Sprite archerR2 = new Sprite(32, 1, 1, SpriteSheet.archer);
	public static Sprite archerR3 = new Sprite(32, 1, 2, SpriteSheet.archer);
	public static Sprite archerR4 = new Sprite(32, 1, 3, SpriteSheet.archer);

	public static Sprite archerD = new Sprite(32, 2, 0, SpriteSheet.archer);
	public static Sprite archerD2 = new Sprite(32, 2, 1, SpriteSheet.archer);
	public static Sprite archerD3 = new Sprite(32, 2, 2, SpriteSheet.archer);
	public static Sprite archerD4 = new Sprite(32, 2, 3, SpriteSheet.archer);

	public static Sprite archerL = new Sprite(32, 3, 0, SpriteSheet.archer);
	public static Sprite archerL2 = new Sprite(32, 3, 1, SpriteSheet.archer);
	public static Sprite archerL3 = new Sprite(32, 3, 2, SpriteSheet.archer);
	public static Sprite archerL4 = new Sprite(32, 3, 3, SpriteSheet.archer);

	public static Sprite archerU_Atk = new Sprite(32, 4, 0, SpriteSheet.archer);
	public static Sprite archerU2_Atk = new Sprite(32, 4, 1, SpriteSheet.archer);
	public static Sprite archerU3_Atk = new Sprite(32, 4, 2, SpriteSheet.archer);
	public static Sprite archerU4_Atk = new Sprite(32, 4, 3, SpriteSheet.archer);
	public static Sprite archerU5_Atk = new Sprite(32, 4, 4, SpriteSheet.archer);
	public static Sprite archerU6_Atk = new Sprite(32, 4, 5, SpriteSheet.archer);
	public static Sprite archerU7_Atk = new Sprite(32, 4, 6, SpriteSheet.archer);
	public static Sprite archerU8_Atk = new Sprite(32, 4, 7, SpriteSheet.archer);

	
	public static Sprite archerR_Atk = new Sprite(32, 5, 0, SpriteSheet.archer);
	public static Sprite archerR2_Atk = new Sprite(32, 5, 1, SpriteSheet.archer);
	public static Sprite archerR3_Atk = new Sprite(32, 5, 2, SpriteSheet.archer);
	public static Sprite archerR4_Atk = new Sprite(32, 5, 3, SpriteSheet.archer);
	public static Sprite archerR5_Atk = new Sprite(32, 5, 4, SpriteSheet.archer);
	public static Sprite archerR6_Atk = new Sprite(32, 5, 5, SpriteSheet.archer);
	public static Sprite archerR7_Atk = new Sprite(32, 5, 6, SpriteSheet.archer);
	public static Sprite archerR8_Atk = new Sprite(32, 5, 7, SpriteSheet.archer);

	public static Sprite archerD_Atk = new Sprite(32, 6, 0, SpriteSheet.archer);
	public static Sprite archerD2_Atk = new Sprite(32, 6, 1, SpriteSheet.archer);
	public static Sprite archerD3_Atk = new Sprite(32, 6, 2, SpriteSheet.archer);
	public static Sprite archerD4_Atk = new Sprite(32, 6, 3, SpriteSheet.archer);
	public static Sprite archerD5_Atk = new Sprite(32, 6, 4, SpriteSheet.archer);
	public static Sprite archerD6_Atk = new Sprite(32, 6, 5, SpriteSheet.archer);
	public static Sprite archerD7_Atk = new Sprite(32, 6, 6, SpriteSheet.archer);
	public static Sprite archerD8_Atk = new Sprite(32, 6, 7, SpriteSheet.archer);

	public static Sprite archerL_Atk = new Sprite(32, 7, 0, SpriteSheet.archer);
	public static Sprite archerL2_Atk = new Sprite(32, 7, 1, SpriteSheet.archer);
	public static Sprite archerL3_Atk = new Sprite(32, 7, 2, SpriteSheet.archer);
	public static Sprite archerL4_Atk = new Sprite(32, 7, 3, SpriteSheet.archer);
	public static Sprite archerL5_Atk = new Sprite(32, 7, 4, SpriteSheet.archer);
	public static Sprite archerL6_Atk = new Sprite(32, 7, 5, SpriteSheet.archer);
	public static Sprite archerL7_Atk = new Sprite(32, 7, 6, SpriteSheet.archer);
	public static Sprite archerL8_Atk = new Sprite(32, 7, 7, SpriteSheet.archer);

	
	//projectiles
	public static Sprite arrowL = new Sprite(32,0,0,SpriteSheet.arrowProjectileSheet);
	public static Sprite arrowR = new Sprite(32,0,1,SpriteSheet.arrowProjectileSheet);
	public static Sprite arrowU = new Sprite(32,0,2,SpriteSheet.arrowProjectileSheet);
	public static Sprite arrowD = new Sprite(32,0,3,SpriteSheet.arrowProjectileSheet);

	
	public static Sprite rock = new Sprite(16, 1, 2, SpriteSheet.sheet);
	public static Sprite voidSprite = new Sprite(16, 0, 8,SpriteSheet.grassSheet);
	
	//Spawn Level Sprites here:
	
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);	
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);	
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);	
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);	
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);	
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);	
	
	//Player Sprites here:
	
	public static Sprite player_forward = new Sprite(32, 0, 5, SpriteSheet.sheet);
	public static Sprite player_back = new Sprite(32, 2, 5, SpriteSheet.sheet);
	public static Sprite player_left = new Sprite(32, 3, 5, SpriteSheet.sheet);
	public static Sprite player_right = new Sprite(32, 1, 5, SpriteSheet.sheet);
	
	public static Sprite player_forward_1 = new Sprite(32, 0, 6, SpriteSheet.sheet);
	public static Sprite player_forward_2 = new Sprite(32, 0, 7, SpriteSheet.sheet);
	
	public static Sprite player_left_1 = new Sprite(32, 3, 6, SpriteSheet.sheet);
	public static Sprite player_left_2 = new Sprite(32, 3, 7, SpriteSheet.sheet);
	
	public static Sprite player_right_1 = new Sprite(32, 1, 6, SpriteSheet.sheet);
	public static Sprite player_right_2 = new Sprite(32, 1, 7, SpriteSheet.sheet);
	
	public static Sprite player_back_1 = new Sprite(32, 2, 6, SpriteSheet.sheet);
	public static Sprite player_back_2 = new Sprite(32, 2, 7, SpriteSheet.sheet);
	
	//NPC Sprites here:
	public static Sprite npc_forward = new Sprite(32, 4, 5, SpriteSheet.sheet);
	public static Sprite npc_back = new Sprite(32, 6, 5, SpriteSheet.sheet);
	public static Sprite npc_left = new Sprite(32, 7, 5, SpriteSheet.sheet);
	public static Sprite npc_right = new Sprite(32, 5, 5, SpriteSheet.sheet);
	public static Sprite npc_forward_1 = new Sprite(32, 4, 6, SpriteSheet.sheet);
	public static Sprite npc_forward_2 = new Sprite(32, 4, 7, SpriteSheet.sheet);
	public static Sprite npc_left_1 = new Sprite(32, 7, 6, SpriteSheet.sheet);
	public static Sprite npc_left_2 = new Sprite(32, 7, 7, SpriteSheet.sheet);
	public static Sprite npc_right_1 = new Sprite(32, 5, 6, SpriteSheet.sheet);
	public static Sprite npc_right_2 = new Sprite(32, 5, 7, SpriteSheet.sheet);
	public static Sprite npc_back_1 = new Sprite(32, 6, 6, SpriteSheet.sheet);
	public static Sprite npc_back_2 = new Sprite(32, 6, 7, SpriteSheet.sheet);
	
	//Items
	public static Sprite item_book = new Sprite(32, 0, 0, SpriteSheet.items);
	public static Sprite item_sign = new Sprite(32, 1, 0, SpriteSheet.items);
	public static Sprite item_potion = new Sprite(32, 2, 0, SpriteSheet.items);
	public static Sprite item_sword = new Sprite(32, 0, 1, SpriteSheet.items);
	public static Sprite item_bucket = new Sprite(32, 1, 1, SpriteSheet.items);
	public static Sprite item_page = new Sprite(32, 2, 1, SpriteSheet.items);
	public static Sprite item_gold = new Sprite(32, 0, 2, SpriteSheet.items);
	public static Sprite item_fish1 = new Sprite(32, 1, 2, SpriteSheet.items);
	public static Sprite item_fish2 = new Sprite(32, 2, 2, SpriteSheet.items);
	
	//Projectile sprites
	public static Sprite projectile_wizard = new Sprite(16,0,0, SpriteSheet.projectile_wizard);
	
	//Particles
	public static Sprite particle_normal = new Sprite(3, 0xFF3300);
	
	// Building sprites
	public static Sprite barrack = new Sprite(64, 0, 0, SpriteSheet.barrack);
	public static Sprite selectedEntityPoint = new Sprite(32, 1, 2, SpriteSheet.grassSheet);
	public static Sprite castleWall = new Sprite(32, 4, 4,SpriteSheet.sheet);
	public static Sprite stoneWallH = new Sprite(32,3,4,SpriteSheet.sheet);
	public static Sprite stoneWallV = new Sprite(32,3,3,SpriteSheet.sheet);

// Font Sprites
	// Uppercase letters
	public static Sprite upperA = new Sprite(32, 0, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperB = new Sprite(32, 1, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperC = new Sprite(32, 2, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperD = new Sprite(32, 3, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperE = new Sprite(32, 4, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperF = new Sprite(32, 5, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperG = new Sprite(32, 6, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperH = new Sprite(32, 7, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperI = new Sprite(32, 8, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperJ = new Sprite(32, 9, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperK = new Sprite(32, 10, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperL = new Sprite(32, 11, 0, SpriteSheet.scribeFontSheet);
	public static Sprite upperM = new Sprite(32, 0, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperN = new Sprite(32, 1, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperO = new Sprite(32, 2, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperP = new Sprite(32, 3, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperQ = new Sprite(32, 4, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperR = new Sprite(32, 5, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperS = new Sprite(32, 6, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperT = new Sprite(32, 7, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperU = new Sprite(32, 8, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperV = new Sprite(32, 9, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperW = new Sprite(32, 10, 1, SpriteSheet.scribeFontSheet);
	public static Sprite upperX = new Sprite(32, 11, 2, SpriteSheet.scribeFontSheet);
	public static Sprite upperY = new Sprite(32, 0, 2, SpriteSheet.scribeFontSheet);
	public static Sprite upperZ = new Sprite(32, 1, 2, SpriteSheet.scribeFontSheet);
	
	// Lowercase
	public static Sprite lowerA = new Sprite(32, 0, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerB = new Sprite(32, 1, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerC = new Sprite(32, 2, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerD = new Sprite(32, 3, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerE = new Sprite(32, 4, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerF = new Sprite(32, 5, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerG = new Sprite(32, 6, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerH = new Sprite(32, 7, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerI = new Sprite(32, 8, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerJ = new Sprite(32, 9, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerK = new Sprite(32, 10, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerL = new Sprite(32, 11, 3, SpriteSheet.scribeFontSheet);
	public static Sprite lowerM = new Sprite(32, 0, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerN = new Sprite(32, 1, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerO = new Sprite(32, 2, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerP = new Sprite(32, 3, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerQ = new Sprite(32, 4, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerR = new Sprite(32, 5, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerS = new Sprite(32, 6, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerT = new Sprite(32, 7, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerU = new Sprite(32, 8, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerV = new Sprite(32, 9, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerW = new Sprite(32, 10, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerX = new Sprite(32, 11, 4, SpriteSheet.scribeFontSheet);
	public static Sprite lowerY = new Sprite(32, 0, 5, SpriteSheet.scribeFontSheet);
	public static Sprite lowerZ = new Sprite(32, 1, 5, SpriteSheet.scribeFontSheet);
	
	// Font numbers
	public static Sprite scribe_num_1 = new Sprite(32, 0, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_2 = new Sprite(32, 1, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_3 = new Sprite(32, 2, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_4 = new Sprite(32, 3, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_5 = new Sprite(32, 4, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_6 = new Sprite(32, 5, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_7 = new Sprite(32, 6, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_8 = new Sprite(32, 7, 6, SpriteSheet.scribeFontSheet);
	public static Sprite scribe_num_9 = new Sprite(32, 8, 6, SpriteSheet.scribeFontSheet);
	
	// History channel combat arrows
	public static Sprite combatArrowUp = new Sprite(64, 0, 1, SpriteSheet.combatArrowSheet);
	public static Sprite combatArrowRight = new Sprite(64, 1, 1, SpriteSheet.combatArrowSheet);
	public static Sprite combatArrowDown = new Sprite(64, 2, 1, SpriteSheet.combatArrowSheet);
	public static Sprite combatArrowLeft = new Sprite(64, 3, 1, SpriteSheet.combatArrowSheet);
	public static Sprite combatArrowHorEx = new Sprite(64, 1, 0, SpriteSheet.combatArrowSheet);
	public static Sprite combatArrowVertEx = new Sprite(64, 0, 0, SpriteSheet.combatArrowSheet);
	
	// faction contested territory color
	public static Sprite factionBlue = new Sprite(64, 0, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionBlack = new Sprite(64, 1, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionGrey = new Sprite(64, 2, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionRed = new Sprite(64, 3, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionOrange = new Sprite(64, 4, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionYellow = new Sprite(64, 5, 0, SpriteSheet.factionTerritoryColorSheet);
	public static Sprite factionPurp = new Sprite(64, 2, 1, SpriteSheet.factionTerritoryColorSheet);
	
	public static Sprite factionWhite = new Sprite(64, 5, 1, SpriteSheet.factionTerritoryColorSheet);

	public static Sprite factionLightBlue = new Sprite(64, 0, 1, SpriteSheet.factionTerritoryColorSheet);


	
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];//create new pixel arry size of the sprite
		this.x = x * size;//set x location of target sprite in sprite sheet
		this.y = y * size;//set y location of target sprite in sprite sheet
		this.sheet = sheet;//load targeted instance of SpriteSheet
		load(x, y);//load targeted sprite
	}
	
	public Sprite(int width, int height, int color)
	{
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color)
	{
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE*SIZE];
		setColor(color);
	}
	
	private void setColor(int color)
	{
		for (int i = 0; i < width * height; i++)
		{
			pixels[i] = color;
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	// Extracts a single sprite from targeted sprite sheet
	private void load(int xX, int yY)
	{
		//loop thru all pixels in sprite and copy color data from sprite sheet
		/*for(int y = 0; y < SIZE; y++)
		{
			for(int x = 0; x < SIZE; x++)
			{
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];//Set pixel to sprites pixel in sheet
			}
		}*/
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		img = this.sheet.getImage().getImage().getSubimage(xX * SIZE, yY *SIZE , this.getWidth(), this.getHeight());
		this.image = new LotusImage(img, BufferedImage.TYPE_INT_RGB);
		//this.image.getImage()

		// this.sheet.getImage().getImage().getRGB(0 , 0  , 32, 32, pixels , 0, 32);
		 //System.out.println("" + pixels[0]);
		//this.image.getImage().setRGB(0, 0, 32, 32, pixels, 0, 0);

	}
	
	public Image getLotusImage()
	{
		return this.image;
	}
	
	public BufferedImage getLotus()
	{
		return this.image.getImage();
	}
}