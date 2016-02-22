package com.ci.lotusFramework.implementation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;



import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ci.game.graphics.Assets;
import com.ci.lotusFramework.Audio;
import com.ci.lotusFramework.Music;
import com.ci.lotusFramework.Sound;



public class LotusAudio implements Audio
{
	int soundId;

	@Override
	public Music createMusic(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sound createSound(String file) 
	{
		
 
            return null;
     
	}


}

