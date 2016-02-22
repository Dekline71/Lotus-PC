package com.ci.lotusFramework;

import java.awt.image.BufferedImage;

public interface Image 
{
	public int getWidth();
	public int getHeight();
	public BufferedImage getImage();
	public int getFormat();
	public void dispose();
	public void setImage(BufferedImage b);
	
}
