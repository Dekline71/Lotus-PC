package com.ci.lotusFramework;
import java.awt.Graphics;

import com.ci.audio.Audio;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;


/* The Architecture of the game is 3 parts:
 * 1. The game interface
 * 2. The implementation of the interface
 * 3. The source code
 	
 	The architecture is like a human body with components designed to come together to create one.
 	
 	1. In the interface, abstract components are formed, such as Audio or Graphics.
 	In these components, create the variables and properties that instances of each component will have,
 	but do not define them. Basically this is the "skeleton" of the game.
 	
 	2. In the implementation, implement the interface above and define the methods and variables created in the above interface.
 		Think of it like using the above interface to create an instance of it, such as AndroidAudio and AndroidGraphics implementations using the Audio and Graphics interfaces). 
	In other words, building on top of the "skeleton" above by adding "muscle."

3. In the game source code, create the classes that will together form the game. 
	In these classes, call the methods that are defined in the implementation to interact with. 
	 (Think of this as the skin above the skeleton and muscle).
 */
public interface Game 
{
	public Audio getAudio();
	
	public Input getInput();
	
	public FileIO getFileIO();
	
	public Graphics getGraphics();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getInitScreen();
	
	public Keyboard getKeyboard();
	
	public Mouse getMouse();
	//public Resources GetResources();
	
}
