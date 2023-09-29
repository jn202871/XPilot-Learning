//Justin Anderson - May 2012
//Xpilot-AI Team 2011
import java.util.*;
public class javaAI {
//Native method declarations -JTO
	native void headlessMode();//allows xpilot to run without game window -EGG -CJG
	native int start(int argc, String[] args); //Initialize AI interface and start XPilot -JRA
//Movement methods -JNE
	native void turnLeft(int flag); //Turns left -JRA
	native void turnRight(int flag); //Turns right -JRA
	native void turn(int deg); //Turns to an inputed Speed of Degree -JRA
	native void turnToDeg(int deg); //Turns the ship to the inputed Degree -JRA
	native void thrust(int flag); //Thrust the ship -JRA
	native void setTurnSpeed(double s); //Sets the speed the ship will turn by -JRA
	native void setTurnSpeedDeg(int s); //Sets the speed the ship will turn by -JRA
	native void setPower(double s); //Sets the speed the ship will thrust by, the minimum power level is 5.0 and the maximum power is 55.0 -JRA
	native void fasterTurnrate(); //Increases the ship's Turn Rate -JRA
	native void slowerTurnrate(); //Decreases the ship's Turn Rate -JRA
	native void morePower(); //Increases the ship's Thrusting Power -JRA
	native void lessPower(); //Decreases the ship's Thrusting Power -JRA
//Shooting methods -JNE
	native void fireShot(); //Fires a Shot -JRA
	native void fireMissile(); //Fires a Missile -JRA
	native void fireTorpedo(); //Fires a Torpedo //Commented out no idea why, I didn't do it -JRA
	native void fireHeat(); //Fires a Heat Seeking Missile -JRA
	native void dropMine(); //Drops a Stationary Mine from the ship -JRA
	native void detachMine(); //Releases a Mine from the ship -JRA
	native void detonateMines(); //Detonates released Mines -JRA
	native void fireLaser(); //Fires a Laser -JRA
//Item usage methods -JNE
	native void tankDetach(); //Detaches a fuel tank from the ship -JRA
	native void cloak(); //Cloaks the ship -JRA
	native void ecm(); //Launches an ECM to temporarily blind opponents -JRA
	native void transporter(); //Uses the transporter item to steal an opponent's item or fuel -JRA
	native void tractorBeam(int flag); //Uses the ship's Tractor Beam to pull in enemy ships -JRA
	native void pressorBeam(int flag); //Uses the ship's Pressor Beam to push away enemy ships -JRA
	native void phasing(); //Uses the Phasing item to allow the ship to pass through walls -JRA
	native void shield(); //Turns on or off the ship's Shield -JRA
	native void emergencyShield(); //Uses the Emergency Shield item to protect your ship from damage for a period of time -JRA
	native void hyperjump(); //Uses the Hyper Jump item to warp the ship to a random location -JRA
	native void nextTank(); //Switches to the ship's next fuel tank -JRA
	native void prevTank(); //Switches to the ship's previous fuel tank -JRA
	native void toggleAutopilot(); //Uses the Autopilot item to stop the ship's movement -JRA
	native void emergencyThrust(); //Uses the Emergency Thrust item to increase the ship's movement speed for a period of time -JRA
	native void deflector(); //Uses the deflector item to push everything away from the ship -JRA
	native void selectItem(); //Selects the ships item to be dropped -JRA
	native void loseItem(); //Drops the ships selected item //Commented out no idea why, I didn't do it -JRA
//Lock methods -JNE
	native void lockNext(); //Locks onto the next ship in the ship buffer -JRA
	native void lockPrev(); //Locks onto the prev ship in the ship buffer -JRA
	native void lockClose(); //Locks onto the closest ship -JRA
	native void lockNextClose(); //Locks-on to the next closest ship -JRA
	native void loadLock1(); //Load a saved lock-on enemy ship -JRA
	native void loadLock2(); //Load a saved lock-on enemy ship -JRA
	native void loadLock3(); //Load a saved lock-on enemy ship -JRA
	native void loadLock4(); //Load a saved lock-on enemy ship -JRA
//Modifier methods -JNE
	native void toggleNuclear(); //Toggles the option to have the ship fire Nuclear weapons instead of regualar weapons, takes up five mines or seven missile -JRA
	native void togglePower(); //Toggles the Power of the weapon -JRA
	native void toggleVelocity(); //Modifies explosion velocity of mines and missiles -JRA
	native void toggleCluster(); //Toggles the option to have the ship fire Cluster weapons instead of regular weapons -JRA
	native void toggleMini(); //Toggles the option to have the ship fire Mini weapons instead of regular weapons -JRA
	native void toggleSpread(); //Toggles the option to have the ship fire Spread weapons instead of regular weapons -JRA
	native void toggleLaser(); //Toggles between the LS stun laser and the LB blinding laser -JRA
	native void toggleImplosion(); //Toggle the option to have mines and missiles implode instead of exlode, the explosion will draw in players instead of blowing them away -JRA
	native void toggleUserName(); //Toggles the displayed information on the HUD on the left of the screen -JRA
	native void loadModifiers1(); //Loads Modifiers -JRA
	native void loadModifiers2(); //Loads Modifiers -JRA
	native void loadModifiers3(); //Loads Modifiers -JRA
	native void loadModifiers4(); //Loads Modifiers -JRA
	native void clearModifiers(); //Clears Modifiers -JRA
//map features -JNE
	native void connector(int flag); //Connects the ship to the ball in Capture the Flag Mode -JRA
	native void dropBall(); //Drops the ball in Capture the Flag Mode -JRA
	native void refuel(int flag); //Refuels the ship -JRA
//other options -JNE
	native void keyHome(); //Changes the ship's Home Base or respawn location -JRA
	native void selfDestruct(); //Triggers the ship's Self Destruct mechanism //Do not repeatedly press or the ship will not self destruct, it works as a toggle and has a timer -JRA
	native void pauseAI(); //Pauses the game for the ship, does not affect other ships -JRA
	native void swapSettings(); //Swaps between ship Settings for turn rate and thrusting power -JRA 
	native void quitAI(); //Quits the game //Do not have toggleNuclear in the same code segment or else it will not quit -JRA
	native void talkKey(); //Opens up the chat window -JRA
	native void toggleCompass(); //Toggles the ship's Compass -JRA
	native void toggleShowMessage(); //Toggles Messages on the HUD on the left side of the screen -JRA 
	native void toggleShowItems(); //Toggles Items on the HUD on the left side of the screen -JRA 
	native void repair(); //Repairs a target -JRA
	native void reprogram(); //Reprogram a modifier or lock bank -JRA
	native void talk(String talk_str); //Sends a message -JRA
	native String scanMsg(int id); // Returns the specified message -EGG
	native String scanGameMsg(int id); // Returns the specified game message -EGG
//self properties -JNE
	native int selfX(); //Returns the ship's X Position -JRA
	native int selfY(); //Returns the ship's Y Position -JRA
	native int selfRadarX(); //Returns the ship's X Radar Coordinate -JRA
	native int selfRadarY(); //Returns the ship's Y Radar Coordinate -JRA
	native int selfVelX(); //Returns the ship's X Velocity -JRA
	native int selfVelY(); //Returns the ship's Y Velocity -JRA
	native int selfSpeed(); //Returns the ship's Speed -JRA
	native double lockHeadingDeg(); //Returns in Degrees the direction of the ship's Lock-on of an enemy -JRA
	native double lockHeadingRad(); //Returns in Radians the direction of the ship's Lock-on of an enemy -JRA
	native short selfLockDist(); //Returns the Distance of the enemy that the ship has Locked-on to -JRA
	native int selfReload(); //Returns the player's Reload time remaining, based on a call to fireShot() -JRA 
	native int selfID(); //Returns the ID of the ship -JRA
	native int selfAlive(); //Returns if the ship is Dead or Alive -JRA
	native int selfTeam(); //Returns the ship's Team -JRA
	native int selfLives(); //Returns how many Lives are left for the ship -JRA
	native double selfTrackingRad(); //Returns the ship's Tracking in Radians -JRA
	native double selfTrackingDeg(); //Returns the ship's Tracking in Degrees -JRA
	native double selfHeadingDeg(); //Returns the Direction of the ship's Lock-on from the ship in Degrees -JRA
	native double selfHeadingRad(); //Returns the Direction of the ship's Lock-on from the ship in Radians -JRA
	native String hud(int i); //Returns the Name on the HUD -JRA
	native String hudScore(int i); //Returns the Score on the HUD -JRA
	native double hudTimeLeft(int i); //Returns the Time Left on the HUD -JRA
	native double getTurnSpeed(); //Returns the ship's Turn Speed -JRA
	native double getPower(); //Returns the ship's Power Level -JRA
	native int selfShield(); //Returns the ship's Shield status -JRA
	native String selfName(); //Returns the ship's Name -JRA
	native double selfScore(); //Returns the ship's Score -JRA
//Closest functions -JNE
	native int closestRadarX(); //Returns the Closest ship's X Radar Coordinate -JRA
	native int closestRadarY(); //Returns the Closest ship's Y Radar Coordinate -JRA
	native int closestItemX(); //Returns the Closest Item's X Radar Coordinate -JRA
	native int closestItemY(); //Returns the Closest Item's Y Radar Coordinate -JRA
	native int closestShipId(); //Returns the Closest ship's ID -JRA
//ID functions -JNE
	native double enemySpeedId(int id); //Returns the Speed of the Specified Enemy -JRA
	native double enemyTrackingRadId(int id); //Returns the Specified Enemy's Tracking in Radians -JRA
	native double enemyTrackingDegId(int id); //Returns the Specified Enemy's Tracking in Degrees -JRA
	native int enemyReloadId(int id); //Returns the Specified Enemy's Reload time remaining -JRA
	native int screenEnemyXId(int id); //Returns the Specified Enemy's X Coordinate -JRA
	native int screenEnemyYId(int id); //Returns the Specified Enemy's Y Coordinate -JRA
	native double enemyHeadingDegId(int id); //Returns the Heading of the Specified Enemy from the ship in Degrees -JRA
	native double enemyHeadingRadId(int id); //Returns the Heading of the Specified Enemy from the ship in Radians -JRA
	native int enemyShieldId(int id); //Returns the Specified Enemy's Shield Status -JRA
	native int enemyLivesId(int id); //Returns the Specified Enemy's Remaining Lives -JRA
	native String enemyNameId(int id); //Returns the Specified Enemy's Name -JRA
	native double enemyScoreId(int id); //Returns the Specified Enemy's Score -JRA
	native int enemyTeamId(int id); //Returns the Specified Enemy's Team ID -JRA
	native double enemyDistanceId(int id); //Returns the Distance between the ship and the Specified Enemy -JRA
//idx functions, idx is the index in the sorted ship buffer-JNE
	native double enemyDistance(int idx); //Returns the Distance between the ship and the Specified Enemy -JRA
	native double enemySpeed(int idx); //Returns the Speed of the Specified Enemy -JRA
	native int enemyReload(int idx); //Returns the Specified Enemy's Reload time remaining -JRA
	native double enemyTrackingRad(int idx); //Returns the Specified Enemy's Tracking in Radians -JRA
	native double enemyTrackingDeg(int idx); //Returns the Specified Enemy's Tracking in Degrees -JRA
	native int screenEnemyX(int idx); //Returns the Specified Enemy's X Coordinate -JRA
	native int screenEnemyY(int idx); //Returns the Specified Enemy's Y Coordinate -JRA
	native double enemyHeadingDeg(int idx); //Returns the Heading of the Specified Enemy from the ship in Degrees -JRA
	native double enemyHeadingRad(int idx); //Returns the Heading of the Specified Enemy from the ship in Radians -JRA
	native int enemyShield(int idx); //Returns the Specified Enemy's Shield Status -JRA
	native int enemyLives(int idx); //Returns the Specified Enemy's Remaining Lives -JRA
	native int enemyTeam(int idx); //Returns the Specified Enemy's Team -JRA
	native String enemyName(int idx); //Returns the Specified Enemy's Name -JRA
	native double enemyScore(int idx); //Returns the Specified Enemy's Score -JRA
	native double degToRad(int deg); //Converts Degrees to Radians -JRA
	native int radToDeg(double rad); //Converts Radians to Degrees -JRA
	native int angleDiff(int angle1, int angle2); //Calculates Difference between Two Angles -JRA
	native int angleAdd(int angle1, int angle2); //Calculates the Addition of Two Angles -JRA
	native int wallFeeler(int dist, int angle); //Returns if there is a wall or not at the Specified Angle within the Specified Distance of the ship -JRA
	native int wallFeelerRad(int dist, double a); //Returns if there is a wall or not at the Specified Angle within the Specified Distance of the ship -JRA
	native int wallBetween(int x1, int y1, int x2, int y2, int flag_wf, int flag_wd); //Returns if there is a wall or not between two Specified Points -JRA
//Shot functions -JNE
	native int shotAlert(int idx); //Returns a Danger Rating of a shot -JRA
	native int shotX(int idx); //Returns the X coordinate of a shot -JRA
	native int shotY(int idx); //Returns the Y coordinate of a shot -JRA
	native int shotDist(int idx); //Returns the Distance of a shot from the ship -JRA
	native int shotVel(int idx); //Returns the Velocity of a shot -JRA
	native int shotVelDir(int idx); //Returns the Direction of the Velocity of a shot -JRA
	native int aimdir(int idx); //Returns the Direction that the ship needs to turn to shot the Enemy -JRA
//Capture the flag functions - Sarah Penrose
	native int ballX(); //Returns the ball's X Position -JRA
	native int ballY(); //Returns the ball's Y Position -JRA
	native int connectorX0(); //Returns the connector's X Position -JRA
	native int connectorX1(); //Returns the connector's X Position -JRA
	native int connectorY0(); //Returns the connector's Y Position -JRA
	native int connectorY1(); //Returns the connector's Y Position -JRA
	//Default declaration for loop to be overridden -EGG
	public void AI_loop() {}
	//Load the library -JTO
	static {System.loadLibrary("javaAI");}
	public javaAI() {}
	//Constructor
	public javaAI(String args[]) {
		//C-ify our arguments -EGG
		String[] das_args= new String[args.length+1];
		das_args[0] = "xpilot-ng-x11"; //A string is prepended to represent the argv[0]
		System.arraycopy(args, 0, das_args, 1, args.length);
		//Call native method -JTO
		start(das_args.length, das_args);
	}
	public static void main(String args[]) {
		javaAI xp = new javaAI(args);
	}
}
