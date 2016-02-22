package com.ci.lotusFramework.implementation;

import java.awt.image.BufferedImage;

import com.ci.lotusFramework.Image;

public class LotusImage implements Image
{
	BufferedImage image;
	int imgFormat;
	
	public LotusImage(BufferedImage b, int format)
	{
		this.image = b;
		this.imgFormat = format;
	}
	@Override
	public int getWidth() 
	{
		return this.image.getWidth();
	}

	@Override
	public int getHeight() 
	{
		return this.image.getHeight();
	}

	@Override
	public BufferedImage getImage() 
	{
		return this.image;
	}

	@Override
	public int getFormat() 
	{
		return this.imgFormat;
	}

	@Override
	public void dispose() 
	{
	//this.image.
	}

	@Override
	public void setImage(BufferedImage b)
	{
		this.image = b;
	}

}
