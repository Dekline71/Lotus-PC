package com.ci.lotusFramework;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.implementation.LotusRenderView;

public abstract class Screen 
{
	protected final LotusRenderView game;
	public int frames;
	protected static float clipVol = -12.0f;
	protected static int masterVol = 4;

	
	public Screen(LotusRenderView game)
	{
		this.game = game;
	}	
	

    public abstract void update(double deltaTime);

    public abstract void paint(double deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
	public abstract void backButton();

	public abstract void update12EverySec();

	//public abstract void drawEntityLayer(Graphics g);

	public void updateMovement() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void reinitAudio(float vol) 
	{
		clipVol = vol;
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
				volume.setValue(vol);// -75 off
	          
	          url = this.getClass().getResource("/audio/ui/select.wav");
	          audioIn = AudioSystem.getAudioInputStream(url);
	          // Get a sound clip resource.
	          clip = AudioSystem.getClip();
	          // Open audio clip and load samples from the audio input stream.
	          clip.open(audioIn);
		
	          Assets.uiItemSelect = clip;
	      	volume = (FloatControl) Assets.uiItemSelect.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(vol);
	        
	       } catch (UnsupportedAudioFileException e) {
	          e.printStackTrace();
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (LineUnavailableException e) {
	          e.printStackTrace();
	       }
	    
	}
}
