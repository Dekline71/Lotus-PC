package com.ci.lotusFramework.implementation;

import java.awt.Canvas;//import Canvas class
import java.awt.Color;//import Color class
import java.awt.Cursor;
import java.awt.Dimension;//import Dimension class
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;//import Graphics class
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;//BufferStrategy class
import java.awt.image.BufferedImage;//import BufferedImage class
import java.awt.image.DataBufferInt;//import DataBufferInt class
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;//import JFrame class

import com.ci.audio.Audio;
import com.ci.game.GameManager;
import com.ci.game.GameScreen;
import com.ci.game.LoadingScreen;
import com.ci.game.SplashLoadingScreen;
import com.ci.game.entity.Player;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.graphics.Sprite;
import com.ci.game.level.Level;
import com.ci.game.level.TileCoordinate;
import com.ci.lotusFramework.FileIO;
import com.ci.lotusFramework.Game;
import com.ci.lotusFramework.Input;
import com.ci.lotusFramework.Screen;
import com.ci.lotusFramework.implementation.input.Keyboard;
import com.ci.lotusFramework.implementation.input.Mouse;

public class LotusRenderView implements Runnable, Game
{
	private static final long serialVersionUID = 1L;
	
	private static LotusFrame frame;
	LotusRenderView renderView;
	Graphics graphics;
	Audio audio;
	LotusInput input;
	FileIO fileIO;
	Screen screen;
	private Canvas canvas;
	BufferedImage framebuffer;
	
	static Keyboard key;
	Mouse mouse;
	
	public static String map;
	boolean firstTimeCreate = true;
	
	LotusGame game;
	
	private static int width;
	private static int height;// (168.75)
	private static int scale = 1;
	private static String title = "Art of War: AT.THE.GATES.OF.MIDDLE.EARTH.";
	private Thread thread;
	private Level level;
	private Player player;
	//private NPC npc;
	
	
	//private Item item;
	volatile boolean running = false;// Indicate if program is running
	
	private Camera camScreen;
	
	public static Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	//Dimension screenSize = new Dimension(800, 800);

	
	private BufferedImage image;//main image obj/view
	//private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//public static String p = "/textures/character.png";
	public int windowWidth = getScreenSize().width;
	public int windowHeight = getScreenSize().height;

	public boolean isRenderPaused;


	private static GraphicsDevice vc; // Gives an interface to graphics card/video card.

	public LotusRenderView(int i) throws IOException
	{	
		//System.setProperty("sun.java2d.opengl", "true");
			
		// Get the graphics environment
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// From that, grabs the screen(s)
		GraphicsDevice[] devices = ge.getScreenDevices();
		frame = new LotusFrame();
	

		this.image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
		// game = new Game();
				
		input = new LotusInput();
		screen = getInitScreen();
		key = new Keyboard();
		mouse = new Mouse();
		canvas = new Canvas();
		
		canvas.addKeyListener(key);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		frame.add(canvas);

		//canvas.setSize(900, 800);
		//setPreferredSize(screenSize);

		//level = Level.spawn;
		
		TileCoordinate playerSpawn = new TileCoordinate(19, 62);
		TileCoordinate npcSpawn = new TileCoordinate(28, 55);
		TileCoordinate bookSpawn = new TileCoordinate(28, 55);
		TileCoordinate signSpawn = new TileCoordinate(12, 22);
		TileCoordinate potionSpawn = new TileCoordinate(14, 12);
		TileCoordinate bucketSpawn = new TileCoordinate(32,27);		

		// getPlayer().init(level);
	}
	
	public static void main(String[] args) throws IOException
	{
		//frame = new LotusFrame(devices[0]);
		//Mouse mouse = new Mouse();
		//canvas.addMouseListener(mouse);
		//canvas.addMouseMotionListener(mouse);
		LotusRenderView window = new LotusRenderView(2);
		//window.getFrame().setTitle("Lotus");
		//window.getFrame().add(window.getCanvas());//Adds a Canvas class component into frame(window)
		//window.getFrame().setUndecorated(false);
		//window.getFrame().setBounds(0, 0, 1280, 800);
		//window.getFrame().pack();//Sets size of frame to component
		//window.getFrame().setVisible(true);
		//window.getFrame().setResizable(false);

		window.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Close(entire process) on X termination
		window.getFrame().setLocationRelativeTo(null);//Centers window to screen
		window.getFrame().requestFocus();
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image swordCursor = tk.getImage(window.getClass().getResource("/ui/swordCursor.png"));
		Cursor c = tk.createCustomCursor(swordCursor, new Point(0, 0), "swordCursor");
		window.getFrame().getRootPane().setCursor(c);
		
		//ImageIcon icon = new ImageIcon();
		frame.setIconImage(tk.getImage(window.getClass().getResource("/textures/sheets/jframeIcon.png")));
		//window.getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);

		window.start();
	}
	
	public LotusRenderView(LotusGame game, BufferedImage framebuffer)
	{
		this.game = game;
		this.framebuffer = framebuffer;
	}
	
	public static int getWindowWidth()
	{
		return width * scale;
	}
	
	public static int getWindowHeight()
	{
		return height * scale;
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Display");
		thread.start();// Call run() of current Thread instance
	}
	
	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void resume()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void pause()
	{
		running = false;
		while(true)
		{
			try
			{
				thread.join();
				break;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
        long gmtimer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;// Amount of frames we have time to render every second
		int updates = 0;// Amount of times update() is called a second
		canvas.requestFocus();// Focus active Window for input
		getFrame().requestFocus();
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;// add difference 
			lastTime = now;
			
			while(delta >= 1)
			{
				getCurrentScreen().update(frames);
				updates++;
				
				if(frames > 61)
				{
					frames = 0;
				}
				else
				{
					//if(!this.isRenderPaused)
					//{
						render(frames);
						frames++;
					//}					
				}
				if(System.currentTimeMillis() - timer > 1000)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
				{
					timer += 1000;
					System.out.println(updates + " ups, " + frames + " fps");
					if(!this.isRenderPaused )
					{
						frame.setTitle(title + "  |  " + updates + " Updates, " + "Fps: " + frames);
					}
						//this.getCurrentScreen().frames = frames;
					frames = 0;
					updates = 0;				
				}	
				delta--;
			}
			
			if(System.currentTimeMillis() - gmtimer > 12000)//if the time since we ran this method took more than a sec(everything inside runs once per sec)
			{
				
				this.getCurrentScreen().update12EverySec();

				gmtimer += 12000;

				
			}
			
		
		}
		//stop();
	}
	
    // called once per frame
	public void update()
	{
		//key.update();
		//player.update();
		//GameManager.level.update();	
		//System.out.println(screenSize.width +  " " + screenSize.height);		
	}
	
	public void render(int frames)
	{		

		BufferStrategy bs = getCanvas().getBufferStrategy();//Create Buffer object to access Buffer of Canvas
		
		if(bs == null)
		{
			getCanvas().requestFocus();
			getCanvas().createBufferStrategy(3);//Create triple buffering
			return;
		}
		
		//camScreen.clear();
		int xScroll = 0;
		int yScroll = 0;
		
		//player.render(camScreen);
		
		/*	Gui that moves with player
		    Sprite sprite = new Sprite(40, height, 0xff);
			screen.renderSprite(0, 0, sprite, false);
		 */
		
		/* Particle effect
	    Sprite sprite = new Sprite(2, 2, 0xff);
		Random random = new Random();
		for(int i = 0; i < 100; i++)
		{
			int x = random.nextInt(20);
			int y = random.nextInt(20);
			screen.renderSprite(240 + x , 700 + y, sprite, true);
		}
		*/
		
		//screen.render(x, y);
		
		//for(int i = 0; i < pixels.length; i++)
		//{
			//pixels[i] = screen.pixels[i];
		//}
	
		Graphics g = bs.getDrawGraphics();
		
		if(g != null)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", 0, 15));
		
			g.drawImage(image, 0, 0, getCanvas().getWidth(), getCanvas().getHeight(), null);//draw
		
			getCurrentScreen().paint(frames);		

			//g.drawString("X: " + player.x + " : " + player.y, 10, 15);

			//g.fillRect(Mouse.getX()-10, Mouse.getY()-10, 10, 10);
			//if (Mouse.getButton() != -1) g.drawString("Button: " + Mouse.getButton(), 40, 40);
			g.dispose();//Remove drawn graphics
			bs.show();//Display next calculated buffer/frame
		}
		
	}

	public LotusFrame getFrame() 
	{
		return frame;
	}

	public void setFrame(LotusFrame f) 
	{
		frame = f;
	}

	public Canvas getCanvas() 
	{
		return canvas;
	}

	public void setCanvas(Canvas canvas)
	{
		this.canvas = canvas;
	}
	
	@Override
	public Audio getAudio() 
	{
		return audio;
	}

	@Override
	public LotusInput getInput() 
	{
		return input;
	}

	@Override
	public FileIO getFileIO() 
	{
		return fileIO;
	}

	@Override
	public Graphics getGraphics() 
	{
		return graphics;
	}

	@Override
	public void setScreen(Screen screen)
	{
	     if (screen == null)
	            throw new IllegalArgumentException("Screen must not be null");

	        this.screen.pause();
	        this.screen.dispose();
	        screen.resume();
	        screen.update(0);
	        this.screen = screen;		
	}

	@Override
	public Screen getCurrentScreen() 
	{
		return screen;
	}

	@Override
	public Screen getInitScreen() 
	{
		if (firstTimeCreate) 
		{
			//Assets.load(this);
			firstTimeCreate = false;
		}
		//InputStream is = getResources().openRawResource(R.raw.map3);
		//map = convertStreamToString(is);

		return new LoadingScreen(this);
	}

	@Override
	public Keyboard getKeyboard()
	{
		return this.key;
	}

	@Override
	public Mouse getMouse() 
	{
		return this.mouse;
	}
	
	public synchronized Thread getThread()
	{
		return this.thread;
	}

	public Dimension getScreenSize()
	{
		return screenSize;
	}

	public void setScreenSize(Dimension s) 
	{
		screenSize = s;
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public void setImage(BufferedImage i)
	{
		this.image = i;
	}
}
