package eurobocode;

import robocode.*;

import java.awt.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Burtetanque - a robot by Juan Burtet
 */
public class EurobocodeBurtet extends AdvancedRobot
{
	private int mode; // Qual estilo vai ser usado pelo tanque
	private int i;

	// Usado pelo crazy
	boolean movingForward; // Bolean para garantir que está indo pra frente

	// Usado pelo tracer
	int count = 0; // Quanto tempo sem encontrar o inimigo
	double gunTurnAmt; // Quanto girar a arma
	String trackName; // Nome do Robo que esta seguindo



	/**
	 * run: Burtetanque's default behavior
	 */
	public void run() {
		i = 0;
		mode = 1; // Inicializa com o modo

		// Modo Tracker
		trackName = null; // Initialize to not tracking anyone
		//setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		gunTurnAmt = 10; // Initialize gunTurn to 10

		// Set colors
		setBodyColor(new Color(0, 0, 0));
		setGunColor(new Color(0, 0, 0));
		setRadarColor(new Color(0, 0, 0));
		setBulletColor(new Color(0, 0, 0));
		setScanColor(new Color(0, 0, 0));

		// Robot main loop
		while(true) {
			switch (mode){
				case 0:
				// Tell the game that when we take move,
				// we'll also want to turn right... a lot.
				setTurnRight(10000);
				// Limit our speed to 5
				setMaxVelocity(5);
				// Start moving (and turning)
				ahead(10000);
				// Repeat.
								break;
				case 1:
				// turn the Gun (looks for enemy)
				turnGunRight(gunTurnAmt);
				// Keep track of how long we've been looking
				count++;
				// If we've haven't seen our target for 2 turns, look left
				if (count > 2) {
					gunTurnAmt = -10;
				}
				// If we still haven't seen our target for 5 turns, look right
				if (count > 5) {
					gunTurnAmt = 10;
				}
				// If we *still* haven't seen our target after 10 turns, find another target
				if (count > 11) {
					trackName = null;
				}
								break;
				case 2:
				// Tell the game we will want to move ahead 40000 -- some large number
				setAhead(40000);
				movingForward = true;
				// Tell the game we will want to turn right 90
				setTurnRight(90);
				// At this point, we have indicated to the game that *when we do something*,
				// we will want to move ahead and turn right.  That's what "set" means.
				// It is important to realize we have not done anything yet!
				// In order to actually move, we'll want to call a method that
				// takes real time, such as waitFor.
				// waitFor actually starts the action -- we start moving and turning.
				// It will not return until we have finished turning.
				waitFor(new TurnCompleteCondition(this));
				// Note:  We are still moving ahead now, but the turn is complete.
				// Now we'll turn the other way...
				setTurnLeft(180);
				// ... and wait for the turn to finish ...
				waitFor(new TurnCompleteCondition(this));
				// ... then the other way ...
				setTurnRight(180);
				// .. and wait for that turn to finish.
				waitFor(new TurnCompleteCondition(this));
				// then back to the top to do it all again
								break;
			}
		}
	}

	// o que fazer no Run do modo Crazy
	public void runCrazy(){
		// Tell the game we will want to move ahead 40000 -- some large number
		setAhead(40000);
		movingForward = true;
		// Tell the game we will want to turn right 90
		setTurnRight(90);
		// At this point, we have indicated to the game that *when we do something*,
		// we will want to move ahead and turn right.  That's what "set" means.
		// It is important to realize we have not done anything yet!
		// In order to actually move, we'll want to call a method that
		// takes real time, such as waitFor.
		// waitFor actually starts the action -- we start moving and turning.
		// It will not return until we have finished turning.
		waitFor(new TurnCompleteCondition(this));
		// Note:  We are still moving ahead now, but the turn is complete.
		// Now we'll turn the other way...
		setTurnLeft(180);
		// ... and wait for the turn to finish ...
		waitFor(new TurnCompleteCondition(this));
		// ... then the other way ...
		setTurnRight(180);
		// .. and wait for that turn to finish.
		waitFor(new TurnCompleteCondition(this));
		// then back to the top to do it all again
	}//runCrazy

	// O que fazer no run do modo Tracker
	public void runTracker(){
		// turn the Gun (looks for enemy)
		turnGunRight(gunTurnAmt);
		// Keep track of how long we've been looking
		count++;
		// If we've haven't seen our target for 2 turns, look left
		if (count > 2) {
			gunTurnAmt = -10;
		}
		// If we still haven't seen our target for 5 turns, look right
		if (count > 5) {
			gunTurnAmt = 10;
		}
		// If we *still* haven't seen our target after 10 turns, find another target
		if (count > 11) {
			trackName = null;
		}
	}//runTracker

	public void runSpinBot(){
		// Tell the game that when we take move,
		// we'll also want to turn right... a lot.
		setTurnRight(10000);
		// Limit our speed to 5
		setMaxVelocity(5);
		// Start moving (and turning)
		ahead(10000);
		// Repeat.
	}//runSpinBot

// -----------------------------------------------------------------------------

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		switch (mode){
			case 0:	onScannedRobotSpinBot(e);
							break;
			case 1: onScannedRobotTracker(e);
							break;
			case 2:	onScannedRobotCrazy(e);
							break;
		}
	}

	// onScannedRobotSpinBot no modo SpinBot
	public void onScannedRobotSpinBot(ScannedRobotEvent e) {
		if(!(e.getName().contains("Eurobocode")))
			fire(3);
	}


	// onScannedRobotCrazy no modo Crazy
	public void onScannedRobotCrazy(ScannedRobotEvent e) {
		if(!(e.getName().contains("Eurobocode")))
			fire(3);
	}

	// onScannedRobotTracker no modo Tracker
	public void onScannedRobotTracker(ScannedRobotEvent e) {
		setMaxVelocity(1000);
		// If we have a target, and this isn't it, return immediately
		// so we can get more ScannedRobotEvents.
		if (trackName != null && !e.getName().equals(trackName)) {
			return;
		}

		// If we don't have a target, well, now we do!
		if (trackName == null) {
			trackName = e.getName();
			out.println("Tracking " + trackName);
		}
		// This is our target.  Reset count (see the run method)
		count = 0;
		// If our target is too far away, turn and move toward it.
		if (e.getDistance() > 150) {
			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

			turnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
			turnRight(e.getBearing()); // and see how much Tracker improves...
			// (you'll have to make Tracker an AdvancedRobot)
			ahead(e.getDistance());
			return;
		}

		// Our target is close.
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		if(!(e.getName().contains("Eurobocode")))
			fire(300);

		// Our target is too close!  Back up.
		if (e.getDistance() < 100) {
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
				back(40);
			} else {
				ahead(40);
			}
		}
		scan();
	}


// -----------------------------------------------------------------------------
	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
			i++;

			if(i > 3){
				i = 0;
				mode++;
				if(mode > 2)
					mode = 0;
			}
	}

// -----------------------------------------------------------------------------
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		switch (mode){
			case 0:	reverseDirection();
							break;
			case 1:	reverseDirection();
							break;
			case 2:	onHitWallCrazy(e);
							break;
		}
	}

	// OnHitWall do modo Crazy
	public void onHitWallCrazy(HitWallEvent e) {
		// Bounce off!
		reverseDirection();
	}


// -----------------------------------------------------------------------------
	/**
	 * onHitRobot:  Back up!
	 */
	public void onHitRobot(HitRobotEvent e) {
		switch (mode){
			case 0:	onHitRobotSpinBot(e);
							break;
			case 1:	onHitRobotTracker(e);
							break;
			case 2: onHitRobotCrazy(e);
							break;
		}
	}

	// onHitRobotTracker no modoTracker
	public void onHitRobotTracker(HitRobotEvent e) {
		// Only print if he's not already our target.
		if (trackName != null && !trackName.equals(e.getName())) {
			out.println("Tracking " + e.getName() + " due to collision");
		}
		// Set the target
		trackName = e.getName();
		// Back up a bit.
		// Note:  We won't get scan events while we're doing this!
		// An AdvancedRobot might use setBack(); execute();
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		if(!(e.getName().contains("Eurobocode")))
			fire(3);
		back(50);
	}


	// onHitRobotCrazy no modo Crazy
	public void onHitRobotCrazy(HitRobotEvent e) {
		// If we're moving the other robot, reverse!
		if (e.isMyFault()) {
			reverseDirection();
		}
	}

	// onHitRobotSpinBot no modo SpinBot
	public void onHitRobotSpinBot(HitRobotEvent e) {
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			if(!(e.getName().contains("Eurobocode")))
				fire(3);
		}
		if (e.isMyFault()) {
			turnRight(10);
		}
	}


// -----------------------------------------------------------------------------
	/**
	 * reverseDirection:  Switch from ahead to back & vice versa
	 */
	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false;
		} else {
			setAhead(40000);
			movingForward = true;
		}
	}

	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}

	public static double normalRelativeAngleDegrees(double angle) {
		return (angle %= 360) >= 0 ? (angle < 180) ? angle : angle - 360 : (angle >= -180) ? angle : angle + 360;
	}


}
