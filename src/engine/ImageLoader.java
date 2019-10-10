package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage gun = null;
	public static BufferedImage gunreload = null;
	public static BufferedImage gunaim = null;
	public static BufferedImage aim1 = null;
	public static BufferedImage cross = null;
	public static BufferedImage flash = null;
	public static BufferedImage ammoback = null;
	public static BufferedImage bullet = null;
	public static BufferedImage bulletAim = null;


	public ImageLoader() {

		try {
			gun = ImageIO.read(new File("textures/gun.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			gunreload = ImageIO.read(new File("textures/gun_reload.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			gunaim = ImageIO.read(new File("textures/gun_aim.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			aim1 = ImageIO.read(new File("textures/aim1.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			cross = ImageIO.read(new File("textures/cross.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			flash = ImageIO.read(new File("textures/flash.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			ammoback = ImageIO.read(new File("textures/ammoback.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		
		try {
			bullet = ImageIO.read(new File("textures/bullet.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
		
		try {
			bulletAim = ImageIO.read(new File("textures/bulletAim.png"));

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
