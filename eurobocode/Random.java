package eurobocode;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Random - a robot by (your name here)
 */
public class Random extends Robot
{
	/**
	 * run: Random's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would likeb
			turnGunLeft(360);
			ahead(200);
			back(100);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		//String nome = e.getName();
		double anguloOponente = e.getBearing();
		fire(3);		
		turnGunRight(anguloOponente); 	
		fire(1);	
	}

	public void onHitRobot(HitRobotEvent e){
		double anguloOponente = e.getBearing();
		turnLeft(-anguloOponente);
		back(200);
	}
	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		double anguloBala = e.getBearing();
		turnRight(-anguloBala);
		ahead(180);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		double distMuro = e.getBearing();
    	turnRight(-distMuro); 
    	ahead(100);
	}	
}
