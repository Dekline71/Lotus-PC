package com.ci.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ci.game.entity.mob.Dummy;
import com.ci.game.level.tile.Tile;
import com.ci.lotusFramework.implementation.LotusRenderView;

public class SpawnLevel extends Level
{
	
	public SpawnLevel(LotusRenderView path1)
	{
		super(path1);
	}
	
	
	protected void loadLevel(String path)
	{
		try
		{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			//BufferedImage layer2 = ImageIO.read(SpawnLevel.class.getResource(path2));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
		
			tiles = new int [w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);//convert image into arry of pixels which tells which color the image is
			//layer2.getRGB(0, 0, w, h, tiles, 0, w);//convert image into arry of pixels which tells which color the image is
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Exception! could not load level file.");
		}
		
		add(new Dummy(20, 40));
	}
	
	// Grass = 0xFF00
	// Flower = 0xFFFF00
	// Rock = 0x7F7F00
	protected void generateLevel()
	{
		
	
		
	}
}
