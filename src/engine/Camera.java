package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Camera implements KeyListener, MouseListener, MouseMotionListener {
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left;
	public static boolean right;
	public static boolean forward;
	public static boolean back;
	public static boolean aim;
	public static boolean reload;
	public static boolean shoot;
	public static boolean rock;
	public static boolean sprint;
	public static boolean noaim = true;
	public static double MOVE_SPEED = .04;
	public static double ROTATION_SPEED = .02;

	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}

	public void keyPressed(KeyEvent key) {

		if ((key.getKeyCode() == KeyEvent.VK_LEFT) && !right) {
			left = true;
		}
		if ((key.getKeyCode() == KeyEvent.VK_RIGHT) && !left) {
			right = true;
		}
		if ((key.getKeyCode() == KeyEvent.VK_W) && !back) {
			forward = true;
		}
		if ((key.getKeyCode() == KeyEvent.VK_S) && !forward) {
			back = true;
		}
		if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
			aim = true;
		}
		if ((key.getKeyCode() == KeyEvent.VK_ENTER) && !(Screen.ammo == 100)) {
			reload = true;
			Screen.loop = 0;
		}
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = true;

		}
		if ((key.getKeyCode() == KeyEvent.VK_Q)) {
			sprint = true;
		}
	}

	public void keyReleased(KeyEvent key) {
		if ((key.getKeyCode() == KeyEvent.VK_LEFT) && !right) {
			left = false;
		}
		if ((key.getKeyCode() == KeyEvent.VK_RIGHT) && !left) {
			right = false;
		}
		if ((key.getKeyCode() == KeyEvent.VK_W) && !back) {
			forward = false;
		}
		if ((key.getKeyCode() == KeyEvent.VK_S) && !forward) {
			back = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
			aim = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_Q) {
			sprint = false;
		}
	}

	public void update(int[][] map) {
		if (forward) {
			if (map[(int) (xPos + xDir * MOVE_SPEED)][(int) yPos] == 0) {
				xPos += xDir * MOVE_SPEED;
			}
			if (map[(int) xPos][(int) (yPos + yDir * MOVE_SPEED)] == 0)
				yPos += yDir * MOVE_SPEED;
		}
		if (back) {
			if (map[(int) (xPos - xDir * MOVE_SPEED)][(int) yPos] == 0)
				xPos -= xDir * MOVE_SPEED;
			if (map[(int) xPos][(int) (yPos - yDir * MOVE_SPEED)] == 0)
				yPos -= yDir * MOVE_SPEED;
		}
		if (right) {
			double oldxDir = xDir;
			xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
			yDir = oldxDir * Math.sin(-ROTATION_SPEED) + yDir * Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane = xPlane * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
			yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
		}
		if (left) {
			double oldxDir = xDir;
			xDir = xDir * Math.cos(ROTATION_SPEED) - yDir * Math.sin(ROTATION_SPEED);
			yDir = oldxDir * Math.sin(ROTATION_SPEED) + yDir * Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane = xPlane * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
			yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);

		}

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}