package com.ci.game.graphics;


import java.awt.Graphics;
import java.awt.Image;

import com.ci.game.GameManager;
import com.ci.game.entity.Entity;
import com.ci.game.entity.particle.Particle;
import com.ci.game.entity.projectile.Projectile;
import com.ci.game.level.tile.Tile;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class Camera 
{
	public int width, height;
	public int[] pixels;
	public static final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public final int SPRITE_SIZE_MASK = Sprite.grass.SIZE - 1;
	
	private int xOffset, yOffset;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];// 4096 (map size)
	private LotusRenderView game;
		
	public Camera(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];// 48,600
				
	//	for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++)//randomly regenerate color to titleIndex
		//{
			//tiles[i] = random.nextInt(0xffffff);
		//}
		
		//tiles[0] = 0x000000;
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}
	
	public void init(LotusRenderView game)
	{
		this.game = game;
	}
	
	/******************************************************* 
	 * Render Entity to screen
	 *******************************************************/
	public void renderEntity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		int mapX = (e.getPixel64X() );
		int mapY = e.getPixel64Y() ;
		int mapXoffset = this.getxOffset() % 64;
		int mapYoffset = this.getyOffset() % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.getyOffset();
		int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		/*if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn() )
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 200, (mapY-mapYoffset)-this.yOffset+32, null);
		}
		else*/ 
		//if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX) - this.getxOffset() > -e.getSprite().SIZE && (mapY ) - this.getyOffset() > -64 )
		//{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.getxOffset()), (mapY-mapYoffset)-this.getyOffset() + 32, null);
		//}
	}
	
	/******************************************************* 
	 * Render Entity to screen
	 *******************************************************/
	public void renderEntity(Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		int mapX = (e.getPixel32X() );
		int mapY = e.getPixel32Y() ;
		int mapXoffset = this.getxOffset() % 64;
		int mapYoffset = this.getyOffset() % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
	//	int yabsolute = yPos + this.getyOffset();
		//int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		/*if (this.xOffset <= xabsolute && this.yOffset <= yabsolute && (mapX - mapXoffset) - this.xOffset > 0 && (mapY - mapYoffset) - this.yOffset > 0 && GameManager.level.getPlayer().isPanelOn() )
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.xOffset) + 200, (mapY-mapYoffset)-this.yOffset+32, null);
		}
		else*/ 
		//if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX) - this.getxOffset() > -e.getSprite().SIZE && (mapY ) - this.getyOffset() > -64 )
		//{
			g.drawImage((Image) e.getImage(), ((mapX+mapXoffset)-this.getxOffset()), ((mapY+mapYoffset)-this.getyOffset()) + 32, null);
		//}
	}
	
	/*********************************************************
	 * Render player army unit to screen
	 * xPos:
	 * yPos: 
	 * e: entity to render
	 *********************************************************/
	public void renderArmyEntity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		int mapX = xPos * 64;
		int mapY = yPos * 64;
		int mapXoffset = this.getxOffset() % 64;
		int mapYoffset = this.getyOffset() % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.getyOffset();
		int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		/*if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > 0 && (mapY - mapYoffset) - this.getyOffset() > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.getxOffset()) + 193, ((mapY-mapYoffset)-this.getyOffset()) + 14, null);
		}
		else*/
		if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > -16 && (mapY - mapYoffset) - this.getyOffset() > -16 )
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.getxOffset()) - 8, ((mapY-mapYoffset)-this.getyOffset()) + 14, null);
		}
	}
	
	public void renderArmyEntity( Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		int mapX = e.getPixel64X();
		int mapY = e.getPixel64Y();
		int mapXoffset = this.getxOffset() % 64;
		int mapYoffset = this.getyOffset() % 64;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = e.getCenter64Y() + this.getyOffset();
		int xabsolute = e.getCenter64X() + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		/*if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > 0 && (mapY - mapYoffset) - this.getyOffset() > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage((Image) e.getImage(), ((mapX+mapXoffset)-this.getxOffset()) + 185, ((mapY+mapYoffset)-this.getyOffset()) + 6, null);
		}
		else*/
		
		//if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > -16 && (mapY - mapYoffset) - this.getyOffset() > -16 )
		//{
			g.drawImage((Image) e.getImage(), ((mapX+mapXoffset)-this.getxOffset()) - 30, ((mapY+mapYoffset)-this.getyOffset()) - 12, null);
		//}
	}
	
	public void renderProjectileEntity(int xPos, int yPos, Projectile p) 
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		int mapX = (xPos * 64);
		int mapY = (yPos * 64);
		int mapXoffset = this.getxOffset() % 32;
		int mapYoffset = this.getyOffset() % 32;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.getyOffset();
		int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		if (GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage( p.getImage(), (p.getPixel64X()) + 187, p.getPixel64Y(), null);
		}
		else if (!GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage( p.getImage(), (p.getPixel64X()) , (p.getPixel64Y()), null );
		}
	}
	
	public void renderParticleEntity(int xPos, int yPos, Particle p)
	{
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();
		int mapX = (xPos * 64);
		int mapY = (yPos * 64);
		int mapXoffset = this.getxOffset() % 32;
		int mapYoffset = this.getyOffset() % 32;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.getyOffset();
		int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		if ( GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage( p.getImage(), (p.getPixel64X()) + 187, p.getPixel64Y(), null);
		}
		else if (!GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage( p.getImage(), (p.getPixel64X()) , (p.getPixel64Y()), null );
		}
	}
	
	public void render64Entity(int xPos, int yPos, Entity e)
	{
		//xPos -= s.xOffset;
		//yPos -= s.yOffset;

		//xPos += 13;
		Graphics g = game.getCanvas().getBufferStrategy().getDrawGraphics();

		int mapX = (xPos * 64);
		int mapY = (yPos * 64);
		int mapXoffset = this.getxOffset() % 32;
		int mapYoffset = this.getyOffset() % 32;
		
		// Get absolute x & y positions for entity/sprite in pixels 
		int yabsolute = yPos + this.getyOffset();
		int xabsolute = xPos + this.getxOffset();
								
		// draw entity/sprite if between the scroll offset range
		if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > 0 && (mapY - mapYoffset) - this.getyOffset() > 0 && GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.getxOffset()) + 187, ((mapY-mapYoffset)-this.getyOffset()), null);
		}
		else if (this.getxOffset() <= xabsolute && this.getyOffset() <= yabsolute && (mapX - mapXoffset) - this.getxOffset() > -16 && (mapY - mapYoffset) - this.getyOffset() > -16 && !GameManager.level.getPlayer().isPanelOn())
		{
			g.drawImage((Image) e.getImage(), ((mapX-mapXoffset)-this.getxOffset()) , ((mapY-mapYoffset)-this.getyOffset()), null );
		}
	}
	
	
	public void renderSprite(int xPos, int yPos, Sprite sprite, boolean fixed)
	{
		if(fixed)
		{
			yPos -= getyOffset();
			xPos -= getxOffset();//render sprite to the portion of the map we see currently
		}
		
		for(int y = 0; y < sprite.getHeight(); y++)
		{
			int ya = y + yPos;
			for(int x = 0; x < sprite.getWidth(); x++)
			{
				int xa = x + xPos;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;// if breaks screen pos, break loop iteration
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
		}
	
	/*public void render(int xOffset, int yOffset)
	{		
		for(int y = 0; y < height; y++)
		{		
			int yPixel = y + yOffset;
			
			if (yPixel < 0 || yPixel >= width)continue;
			
			for(int x = 0; x < width; x++)
			{
				int xPixel = x + xOffset;
				
				if (xPixel < 0 || xPixel >= width)continue;
								
				pixels[xPixel + yPixel * width] = Sprite.grass.pixels[(x & SPRITE_SIZE_MASK) + (y & SPRITE_SIZE_MASK) * Sprite.grass.SIZE]; //renders
		
			}
		}
	}*/

	// factor in players position and move tiles accordingly	
	public void renderTile(int xPos, int yPos, Tile tile)
	{

	}
	
	public void renderProjectile(int xPos, int yPos, Projectile p)
	{
		xPos -= getxOffset();
		yPos -= getyOffset();
		for (int y = 0; y < p.getSpriteSize(); y++)
		{
			int yabsolute = y + yPos;// y position relative to object
			
			for (int x = 0; x < p.getSpriteSize(); x++)
			{
				int xabsolute = x + xPos;// x position relative to object
				if (xabsolute < -p.getSpriteSize() || xabsolute >= width || yabsolute < 0 || yabsolute >= height) break;//if tiles exists screen, stop rendering tile
					if (xabsolute < 0){xabsolute = 0;}	
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (col != 0xFFFF00DC)
					pixels[xabsolute + yabsolute * width] = col;
			}
			
		}
	}
	
	public void renderPlayer(int xPos, int yPos, Sprite sprite)
	{
		xPos -= getxOffset();
		yPos -= getyOffset();
		for(int y = 0; y < 32; y++)
		{
			int yabsolute = y + yPos;
			for(int x = 0; x < 32; x++)
			{
				int xabsolute = x + xPos;
				if (xabsolute < -32 || xabsolute >= width || yabsolute < 0 || yabsolute >= height)break;
				if (xabsolute < 0){ xabsolute = 0;}
				int col = sprite.pixels[x + y * 32];
				if(col != 0x00000000)
				pixels[xabsolute + yabsolute * width] = col;
			}
		}
	}
	
	public void renderNPC(int xPos, int yPos, Sprite sprite)
	{
		xPos -= getxOffset();
		yPos -= getyOffset();
		for(int y = 0; y < 32; y++)
		{
			int yabsolute = y + yPos;
			for(int x = 0; x < 32; x++)
			{
				int xabsolute = x + xPos;
				if (xabsolute < -32 || xabsolute >= width || yabsolute < 0 || yabsolute >= height)break;
				if (xabsolute < 0){ xabsolute = 0;}
				int col = sprite.pixels[x + y * 32];
				if(col != 0x00506BA5 && col != 0x00FFFFFF)
				pixels[xabsolute + yabsolute * width] = col;
			}
		}
	}
	
	public void renderObject(int xPos, int yPos, Sprite sprite)
	{
		xPos -= getxOffset();
		yPos -= getyOffset();
		for(int y = 0; y < 32; y++)
		{
			int yabsolute = y + yPos;
			for(int x = 0; x < 32; x++)
			{
				int xabsolute = x + xPos;
				if (xabsolute < -32 || xabsolute >= width || yabsolute < 0 || yabsolute >= height)break;
				if (xabsolute < 0){ xabsolute = 0;}
				int col = sprite.pixels[x + y * 32];
				if(col != 0xFFFFFFFF)
				pixels[xabsolute + yabsolute * width] = col;
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset)
	{
		this.setxOffset(xOffset);
		this.setyOffset(yOffset);
	}

	public int getxOffset() 
	{
		return xOffset;
	}

	public void setxOffset(int xOffset) 
	{
		this.xOffset = xOffset;
	}

	public int getyOffset() 
	{
		return yOffset;
	}

	public void setyOffset(int yOffset) 
	{
		this.yOffset = yOffset;
	}	
}
