package engine;

import java.util.ArrayList;
import java.awt.Color;

public class Screen {
	public int[][] map;
	public static int loop;
	public static int ammo;
	public int mapWidth, mapHeight, width, height;
	public ArrayList<Texture> textures;
	Color color = new Color(0.2f, 0f, 0f, 0.3f);

	public Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) {
		map = m;
		mapWidth = mapW;
		mapHeight = mapH;
		textures = tex;
		width = w;
		height = h;
		ammo = 100;
		loop = 0;
	}

	public int[] update(Camera camera, int[] pixels) {
		
		

		if (Camera.reload) {

			Camera.aim = false;
			Camera.noaim = false;
			Game.gun.x -= 50;

			if (Game.gun.x <= -700) {

				Game.gun.x = -700;
				loop++;

				if(loop == 20) {
					
					Music.play("sounds/reload.wav");

				}
				if (loop % 10 == 0) {

					Game.gun.y = Game.gun.y + 10;

				}

				else {

					Game.gun.y = 370;
				}

				if (ammo < 100 && loop % 10 == 0) {

					ammo = ammo + 25;
				}

				else if (ammo >= 100) {

					ammo = 100;
					
					
				}

				if (loop >= 80 && ammo == 100) {

					loop = 0;
					Camera.reload = false;

				}

			}
		}

		else if (Camera.aim) {

			Camera.noaim = false;
			Camera.reload = false;

			Game.gun.x -= 50;
			Game.gun.width += 10;
			Game.gun.height += 10;
			Game.gun.y -= 10;

			if (Game.gun.width >= 700 && Game.gun.height >= 600) {

				Game.gun.width = 700;
				Game.gun.height = 600;
				Game.gun.y = 280;
			}

			if (Game.gun.x <= Game.WIDTH / 2 - Game.gun.width) {

				Game.gun.x = Game.WIDTH / 2 - Game.gun.width;

			}

			if (Camera.shoot) {


				loop++;

				if (loop >= 100) {

					loop = 0;
				}

				if (loop % 3 == 0) {

					ammo--;
					Game.aim1.y = Game.aim1.y + 5;
					Music.play("sounds/shoot.wav");

				}

				else {

					Game.aim1.y = 0;
				}

				if (ammo <= 0) {

					ammo = 0;
					loop = 0;
					Camera.shoot = false;
					Camera.reload = true;
				}
			}

		}

		else if (!Camera.aim && !Camera.reload && !Camera.noaim) {

			Game.gun.x += 50;
			Game.gun.y += 10;
			Game.gun.width -= 10;
			Game.gun.height -= 10;

			if (Game.gun.x >= 700 && Game.gun.width >= 550 && Game.gun.height >= 450 & Game.gun.y <= 370) {

				Game.gun.x = 700;
				Game.gun.width = 550;
				Game.gun.height = 450;
				Game.gun.y = 370;
				Camera.noaim = true;

			}

			else if (Game.gun.x >= 700) {

				Game.gun.x = 700;
				Game.gun.y = 370;
				Game.gun.width = 550;
				Game.gun.height = 450;
				Camera.noaim = true;

			}
			
			

		}

		else if (Camera.shoot && !Camera.aim) {

			loop++;

			if (loop >= 100) {

				loop = 0;
			}

			if (loop % 3 == 0) {

				ammo--;
				Game.gun.y = Game.gun.y + 10;
				Music.play("sounds/shoot.wav");

			}

			else {

				Game.gun.y = 370;
			}

			if (ammo <= 0) {

				ammo = 0;
				loop = 0;
				Camera.shoot = false;
				Camera.reload = true;
			}
		}

		else if ((Camera.forward || Camera.back) && !Camera.aim) {

			loop++;

			if (loop >= 100) {

				loop = 0;
			}

			if (loop % 2 == 0) {

				if (!Camera.rock) {

					Game.gun.y = Game.gun.y + 15;
					Game.gun.x = Game.gun.x - 15;

				}

				if (Game.gun.y >= 485 || Game.gun.x <= 585) {

					Camera.rock = true;

				}

				if (Camera.rock) {

					Game.gun.y = Game.gun.y - 15;
					Game.gun.x = Game.gun.x + 15;

					if (Game.gun.y <= 370 || Game.gun.x >= 700) {

						Game.gun.x = 700;
						Game.gun.y = 370;
						Camera.rock = false;
					}
				}

			}

		}

		else {

			Game.gun.y = 370;

		}

		if (Camera.sprint) {

			Camera.MOVE_SPEED = 0.1;
			Camera.aim = false;

		}

		else {

			Camera.MOVE_SPEED = 0.04;
		}

		for (int n = 0; n < pixels.length / 2; n++) {

			if (pixels[n] != Color.gray.darker().getRGB()) // Roof
				pixels[n] = Color.BLACK.getRGB();
		}
		
		for (int i = pixels.length / 2; i < pixels.length; i++) {
			if (pixels[i] != Color.GRAY.brighter().getRGB()) // Floor
				pixels[i] = Color.GRAY.darker().getRGB();
		}

		for (int x = 0; x < width; x = x + 1) {
			double cameraX = 2 * x / (double) (width) - 1;
			double rayDirX = camera.xDir + camera.xPlane * cameraX;
			double rayDirY = camera.yDir + camera.yPlane * cameraX;
			// Map position
			int mapX = (int) camera.xPos;
			int mapY = (int) camera.yPos;
			// length of ray from current position to next x or y-side
			double sideDistX;
			double sideDistY;
			// Length of ray from one side to next in map
			double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
			double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
			double perpWallDist;
			// Direction to go in x and y
			int stepX, stepY;
			boolean hit = false;// was a wall hit
			int side = 0;// was the wall vertical or horizontal
			// Figure out the step direction and initial distance to a side
			if (rayDirX < 0) {
				stepX = -1;
				sideDistX = (camera.xPos - mapX) * deltaDistX;
			} else {
				stepX = 1;
				sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
			}
			if (rayDirY < 0) {
				stepY = -1;
				sideDistY = (camera.yPos - mapY) * deltaDistY;
			} else {
				stepY = 1;
				sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
			}
			// Loop to find where the ray hits a wall
			while (!hit) {
				// Jump to next square
				if (sideDistX < sideDistY) {
					sideDistX += deltaDistX;
					mapX += stepX; // Draws Side
					side = 0;
				} else {
					sideDistY += deltaDistY;
					mapY += stepY; // Draws Side
					side = 1;
				}
				// Check if ray has hit a wall
				//System.out.println(mapX + ", " + mapY + ", " + map[mapX][mapY]);
				if (map[mapX][mapY] > 0)
					hit = true;
			}
			// Calculate distance to the point of impact
			if (side == 0)
				perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
			else
				perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
			// Now calculate the height of the wall based on the distance from the camera
			int lineHeight;
			if (perpWallDist > 0)
				lineHeight = Math.abs((int) (height / perpWallDist));
			else
				lineHeight = height;
			// calculate lowest and highest pixel to fill in current stripe
			int drawStart = - lineHeight / 2 + height / 2;
			if (drawStart < 0)
				drawStart = 0;
			int drawEnd = lineHeight / 2 + height / 2;
			if (drawEnd >= height)
				drawEnd = height - 1;
			// add a texture
			int texNum = map[mapX][mapY] - 1;
			double wallX;// Exact position of where wall was hit
			if (side == 1) {// If its a y-axis wall
				wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
			} else {// X-axis wall
				wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
			}
			wallX -= Math.floor(wallX);
			// x coordinate on the texture
			int texX = (int) (wallX * (textures.get(texNum).SIZE));
			if (side == 0 && rayDirX > 0)
				texX = textures.get(texNum).SIZE - texX - 1;
			if (side == 1 && rayDirY < 0)
				texX = textures.get(texNum).SIZE - texX - 1;
			// calculate y coordinate on texture
			for (int y = drawStart; y < drawEnd; y++) {
				int texY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
				int color;
				if (side == 0)
					color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
				else
					color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)] >> 1) & 8355711;// Make
																													// y
																													// sides

				// darker
				pixels[x + y * (width)] = color;
			}

		}
		return pixels;
	}
}
