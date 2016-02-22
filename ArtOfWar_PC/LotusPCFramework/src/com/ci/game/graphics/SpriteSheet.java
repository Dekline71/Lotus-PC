package com.ci.game.graphics;

import java.awt.image.BufferedImage;// import BufferedImage class
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.ci.lotusFramework.Image;
import com.ci.lotusFramework.implementation.LotusImage;

public class SpriteSheet 
{
	private URL path;// Path to spriteSheet
	public final int SIZE;// Size of spritesheet
	public int[] pixels;
	private Image image;
	public static SpriteSheet sheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/grass_spritesheet.png"), 256);
	public static SpriteSheet peasant = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/entity/spearmenSheet.png"), 512);
	public static SpriteSheet bandit = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/entity/banditSheet.png"), 512);
	public static SpriteSheet archer = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/entity/archerSheet.png"), 260);

	
	public static SpriteSheet spawn_level = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/spawn_level.png"), 48);
	public static SpriteSheet barrack = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/building/barrack_64.png"), 64);	
	public static SpriteSheet arrowSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/arrowSheet.png"), 96);	
	public static SpriteSheet arrowProjectileSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/entity/arrowAtkSheet.png"), 128);

	public static SpriteSheet townsSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/building/cityBuildingSheet.png"), 256);
	public static SpriteSheet citySheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/building/citySheet.png"), 360);

	
	public static SpriteSheet combatArrowSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/combatArrowSheet.png"), 256);
	public static SpriteSheet scribeFontSheet = new SpriteSheet(SpriteSheet.class.getResource("/ui/fonts/scribeFontSheet.png"), 512);
	public static SpriteSheet unitIconsSheet = new SpriteSheet(SpriteSheet.class.getResource("/ui/unitIconSheet.png"), 256);
	//public static SpriteSheet spawn_level_ObjLayer = new SpriteSheet("/textures/sheets/spawnLayer2.png", 48);
	public static SpriteSheet items = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/Item_set1.png"), 96);
	public static SpriteSheet projectile_wizard = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/particles/wizard.png"), 48);
	public static SpriteSheet grassSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/grass_SpriteSheet_2.png"), 256);
	public static SpriteSheet positionArrowSheet = new SpriteSheet(SpriteSheet.class.getResource("/textures/sheets/positionArrowSheet.png"), 128);
	public static SpriteSheet factionTerritoryColorSheet = new SpriteSheet(SpriteSheet.class.getResource("/factions/factionsTerritoryColorSheet.png"), 512);

	
	public SpriteSheet(URL url, int size)
	{
		this.path = url;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();//set pixels to spritesheet
	}
	
	private void load()
	{
		try
		{
			BufferedImage img = ImageIO.read(path);//Set Bufferedimg obj set to pathed image(get spritesheet)
			this.image = new LotusImage(img, BufferedImage.TYPE_INT_RGB);
			int w = img.getWidth();
			int h = img.getHeight();
			img.getRGB(0, 0, w, h, pixels, 0, w);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public Image getImage() 
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}
}
