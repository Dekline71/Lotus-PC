package com.ci.game.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation 
{
	private ArrayList frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;
	private boolean isAnimActive;
	
	public Animation()
	{
		frames = new ArrayList();
		totalDuration = 0;
		
		synchronized(this)
		{
			animTime = 0;
			currentFrame =0;
		}
	}
	
	public Animation(BufferedImage f, BufferedImage f2, BufferedImage f3, long duration)
	{
		frames = new ArrayList();
		totalDuration = 0;
		
		synchronized(this)
		{
			animTime = 0;
			currentFrame = 0;
		}
		this.addFrame(f, duration);
		this.addFrame(f2, duration);
		this.addFrame(f3, duration);
		
	}
	
	/***
	 * 	
	 * @param image
	 * @param duration - time to display frame in miliseconds (1000ms = 1s).
	 */
	public synchronized void addFrame(BufferedImage image, long duration) 
	{
		totalDuration += duration;
		this.frames.add(new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime) 
	{
		//System.out.println(isAnimActive());
		if (this.frames.size() > 1) 
		{
			animTime += elapsedTime;
			if (animTime >= totalDuration) 
			{
				animTime = animTime % totalDuration;
				currentFrame = 0;
				setAnimActive(false);
			}
			setAnimActive(false);

			while (animTime > getFrame(currentFrame).endTime) 
			{
				currentFrame++;
				setAnimActive(true);
			}

		}
	}

	public synchronized BufferedImage getImage() 
	{
		if (frames.size() == 0) 
		{
			return null;
		} 
		else
		{
			return getFrame(currentFrame).image;
		}
	}

	private AnimFrame getFrame(int i) 
	{
		return (AnimFrame) frames.get(i);
	}

	public boolean isAnimActive() {
		return isAnimActive;
	}

	public void setAnimActive(boolean isAnimActive) {
		this.isAnimActive = isAnimActive;
	}

	private class AnimFrame 
	{

		BufferedImage image;
		long endTime;

		public AnimFrame(BufferedImage image, long endTime) 
		{
			this.image = image;
			this.endTime = endTime;
		}
	}
}
