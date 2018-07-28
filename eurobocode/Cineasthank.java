package eurobocode;
import robocode.*;
import java.util.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Cineasthank - a robot by (your name here)
 */
public class Cineasthank extends Robot
{
	int estrategia;
	/**
	 * run: Cineasthank's default behavior
	 */
	public void run() {
		Random gerador = new Random();
		estrategia = gerador.nextInt(4);
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			switch (estrategia) {
				case 0:
					turnRight(360);
					ahead(50);
					turnGunRight(360);			
					break;
				case 1:
					back(50);
					turnLeft(360);
					turnGunLeft(360);	
					break;
				case 2:
					ahead(50);
					turnRight(360);
					turnGunRight(360);
					break;
				case 3:
					turnLeft(360);
					back(50);
					turnGunLeft(360);					
			}
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		Random gerador = new Random();
		estrategia = gerador.nextInt(4);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
