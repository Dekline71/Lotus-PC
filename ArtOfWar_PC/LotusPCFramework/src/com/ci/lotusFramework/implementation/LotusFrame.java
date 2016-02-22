package com.ci.lotusFramework.implementation;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class LotusFrame extends JFrame
{
	// Set the resolution and make accessible to entire game   
    public static final int FRAMEWIDTH = 1000;//1000
    public static final int FRAMEHEIGHT = 794;//794
	 
    // List of supported resolutions
    public static Dimension modes[] = 
    {
        new Dimension(800, 600),
        new Dimension(1024, 768),
        new Dimension(1280, 720),
        new Dimension(1280, 800),
        new Dimension(1360, 768),
        new Dimension(1600, 900),
        new Dimension(1920, 1080)
    }; 

      // Set up the Graphics objects
    private GraphicsDevice graphicsDevice;
    private DisplayMode old;

	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	public int j;
		
	public LotusFrame()
	{
		setSize(FRAMEWIDTH, FRAMEHEIGHT);
        // The normal methods...
		setResizable(false);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//repaint();
	}
	
	public LotusFrame(GraphicsDevice gd) 
	{
		DisplayMode dm = null;
		System.out.println(screenSize.width);

		
	
        // set gd as object object graphicsDevice so it can be used in other methods,
		graphicsDevice = gd;

        // The old mode (saves original resolution, bit depth, and refresh rate)
		old = gd.getDisplayMode();
	
        // Add panels or other components you want/need here...

        // If the computer supports fullscreen,
		if (graphicsDevice.isFullScreenSupported()) 
		{
			// Then take away the borders and make it non-resizable
			setUndecorated(true);
			setResizable(false);               
			DisplayMode[] da = gd.getDisplayModes();
			for( j = 0; j < modes.length; j++)
			{
				if(screenSize.width == LotusFrame.modes[j].width && screenSize.height == LotusFrame.modes[j].height)
				{
					setSize(LotusFrame.modes[j].width, LotusFrame.modes[j].height);
					 // This is the new display mode...As you can see, it 
	                // takes our global resolution and makes that the new screen 
	                // resolution. We keep the same bit depth and refresh rate
	                // So there are no problems arising from that.
						
				
				 	for(int m = 0; m < da.length; m++)
					{
						//System.out.println("Width: " + da[m].getWidth() + " H: " + da[m].getHeight() + " BitDepth: " + da[m].getBitDepth() + "Refresh: " + da[m].getRefreshRate());
					}
						
							
					dm = new DisplayMode(modes[j].width, modes[j].height, gd.getDisplayMode().getBitDepth(), gd.getDisplayMode().getRefreshRate());
				}			
				else
				{
					dm = new DisplayMode(LotusFrame.modes[1].width, LotusFrame.modes[1].height, gd.getDisplayMode().getBitDepth(), gd.getDisplayMode().getRefreshRate());
					setSize(LotusFrame.modes[1].width, LotusFrame.modes[1].height);
				}
					
					//dm = new DisplayMode(da[da.length-1].getWidth(), da[da.length-1].getHeight(), da[da.length-1].getBitDepth(), da[da.length-1].getRefreshRate());

                	// Set this window to fullscreen
	    			graphicsDevice.setFullScreenWindow(this);

                
					// Give our new graphics mode to our screen.
	    			graphicsDevice.setDisplayMode(dm);
				}
			}
   
            // If can't go fullscreen...
            else 
            { 
                    // Print a friendly message...
            	System.out.println("Your graphics card sucks!");

                    // In reality, you would want to set up a 
                    // non-fullscreen JFrame to use instead.

            }

	
			//setSize(FRAMEWIDTH, FRAMEHEIGHT);
            // The normal methods...
		//	setResizable(false);
		//GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
		//setMaximizedBounds(graphicsConfiguration.getBounds());
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);

			setVisible(true);
			revalidate();
			//repaint();
			
}	
	    
	public LotusFrame(GraphicsDevice gd, Dimension d) 
	{
		DisplayMode dm = null;
		System.out.println(screenSize.width);	
		
        // set gd as object object graphicsDevice so it can be used in other methods,
		graphicsDevice = gd;

        // The old mode (saves original resolution, bit depth, and refresh rate)
		old = gd.getDisplayMode();
	
        // Add panels or other components you want/need here...

        // If the computer supports fullscreen,
		if (graphicsDevice.isFullScreenSupported()) 
		{
            // Then take away the borders and make it non-resizable
			setUndecorated(true);
			setResizable(false);               
			DisplayMode[] da = gd.getDisplayModes();
				
			if(screenSize.width == d.width && screenSize.height == d.height)
			{
				//setSize(LotusFrame.modes[j].width, LotusFrame.modes[j].height);
				setSize((int)d.getWidth(), (int)d.getHeight());

				// This is the new display mode...As you can see, it 
		        // takes our global resolution and makes that the new screen 
		        // resolution. We keep the same bit depth and refresh rate
		        // So there are no problems arising from that.
						
						
				for(int m = 0; m < da.length; m++)
				{
					//System.out.println("Width: " + da[m].getWidth() + " H: " + da[m].getHeight() + " BitDepth: " + da[m].getBitDepth() + "Refresh: " + da[m].getRefreshRate());
				}
						
							
				dm = new DisplayMode(d.width, d.height, old.getBitDepth(), old.getRefreshRate());
			}			
			else
			{
				dm = new DisplayMode(LotusFrame.modes[1].width, LotusFrame.modes[1].height, old.getBitDepth(), old.getRefreshRate());
				//setSize(LotusFrame.modes[1].width, LotusFrame.modes[1].height);
			}
				//dm = new DisplayMode(da[da.length-1].getWidth(), da[da.length-1].getHeight(), da[da.length-1].getBitDepth(), da[da.length-1].getRefreshRate());

            // Set this window to fullscreen
	    	graphicsDevice.setFullScreenWindow(this);

                
			// Give our new graphics mode to our screen.
	    	graphicsDevice.setDisplayMode(dm);
				
		}
        // If can't go fullscreen...
        else 
        { 
        	// Print a friendly message...
            System.out.println("Your graphics card sucks!");

            // In reality, you would want to set up a 
            // non-fullscreen JFrame to use instead.

        }

		//setUndecorated(true);
	
        // The normal methods...
		//setResizable(true);

		setVisible(true);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
		//repaint();
	}
	
	

    public void cleanAndQuit()
    {
	        // Called when the game is done and original mode restored.
        // This call actually makes it non-fullscreen and disassociates
        // GraphicsModes that were attatched to it, so now it is safe to exit.
    	if(graphicsDevice != null)
    	{	
    		if(graphicsDevice.getFullScreenWindow() != null)
    		{
    			graphicsDevice.setFullScreenWindow(null);
    		}
    		else
    		{
    			// Exit!
    			System.exit(0);
    		}
    	}
    	else
    	{
    		System.exit(0);
    	}
    }
}
