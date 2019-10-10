package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	public int mapWidth = 15;
	public int mapHeight = 30;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public static int WIDTH = 1450;
	public static int HEIGHT = 820;
	public ArrayList<Texture> textures;
	public static Rectangle gun;
	public static Rectangle aim1;
	public Camera camera;
	public static Screen screen;
	public static Game game;
	public static int[][] map = { { 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3 },
			{ 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 }, { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 4, 1, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3 }

	

	};

	public Game() {
		thread = new Thread(this);
		image = new BufferedImage(1450, 820, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, 1450, 820);
		addKeyListener(camera);
		addMouseListener(camera);
		addMouseMotionListener(camera);
		setSize(1450, 900);
		setType(javax.swing.JWindow.Type.UTILITY);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		gun = new Rectangle(700, 370, 550, 450);
		aim1 = new Rectangle(0, 0, WIDTH, HEIGHT);
		new ImageLoader();
		start();
	}

	private synchronized void start() {
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render() { // repaint method
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

		if (Camera.aim) {

			if (!(gun.x == WIDTH / 2 - gun.width)) {

				g.drawImage(ImageLoader.gunaim, gun.x, gun.y, gun.width + 150, gun.height, null);

			} else if (gun.x == WIDTH / 2 - gun.width) {

				if (Camera.shoot) {

					if (Screen.loop % 3 == 0) {

						g.drawImage(ImageLoader.flash, aim1.x - 50, aim1.y + 540, 1660, 200, null);

					}

				}

				g.drawImage(ImageLoader.aim1, aim1.x, aim1.y, aim1.width, aim1.height, null);

			}

		}

		else if (!Camera.aim) {

			g.drawImage(ImageLoader.cross, WIDTH / 2 - 50, HEIGHT / 2 - 50, 100, 100, null);

		}

		if (!Camera.reload && !Camera.aim) {

			if (Camera.shoot) {

				if (Screen.loop % 5 == 0) {

					g.drawImage(ImageLoader.flash, gun.x + 180, gun.y + 150, 500, 250, null);

				}

			}

			g.drawImage(ImageLoader.gun, gun.x, gun.y, gun.width + 150, gun.height, null);

		}

		else if (Camera.reload) {

			g.drawImage(ImageLoader.gunreload, gun.x, gun.y, gun.width + 600, gun.height, null);

		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 820, WIDTH, 80);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.BOLD, 70));
		g.drawImage(ImageLoader.ammoback, 0, 670, 350, 110, null);

		if (Screen.ammo == 100) {

			g.drawString(String.valueOf(Screen.ammo) + "/100", 50, 750);

		}

		else if (Screen.ammo < 100 && Screen.ammo >= 10) {

			g.drawString("0" + String.valueOf(Screen.ammo) + "/100", 50, 750);

		}

		else {

			g.drawString("00" + String.valueOf(Screen.ammo) + "/100", 50, 750);

		}

		bs.show();

	}

	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0; // 60 times per second
		double delta = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta = delta + ((now - lastTime) / ns);
			lastTime = now;
			while (delta >= 1)// Make sure update is only happening 60 times a second
			{
				// handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render(); // displays to the screen unrestricted time
		}
	}

	public static void main(String[] args) {
		
		game = new Game();
		

	}

}