package com.ci.lotusFramework.implementation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.tile.Tile;
import com.ci.lotusFramework.Graphics;
import com.ci.lotusFramework.Image;

public class LotusGraphics extends Graphics 
{
	GraphicsConfiguration framebuffer;
	Canvas canvasLayer_1;
	//private BufferedImage image = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);//main image obj/view

	LotusGraphics(GraphicsConfiguration framebuffer)
	{
		this.framebuffer = framebuffer;
		this.canvasLayer_1 = new Canvas(framebuffer);
		//this.canvasLayer_1.c
	}
	
	
}
