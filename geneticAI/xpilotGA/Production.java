class Production extends javaAI {
	public static double fitness = 0;
	public static String chromosome;
	int round = 0;
	boolean alive = (selfAlive() == 1 ? true : false);
        @Override
        public void AI_loop() {
			// Round Check
			if (round == 6) {
				fitness = selfScore();
				quitAI();
			}

			// Release Keys
			thrust(0);
			turnLeft(0);
			turnRight(0);
			setPower(25);
			
			// Chromosome Interpretation - WIP

			int speedLimit = 15;
			int frontLimit = 1500;
			int trackLimit = 450;
			int angleLimitMax = 270;
			int angleLimitMin = 90;
			int backWallLimit = 10;

			int engagementRange = 300;
			int engagementWallDist = 100;
			int trackWallTurningDist = 900;
			int lowSpeed = 5;
			int mediumSpeed = 10;

			int shotAngleMax = 5;
			int trackWallDanger = 900;
			int trackWallClose = 500;
			int aimCatchDist = 100;

			// Production System

			// Variables
			int heading = (int) selfHeadingDeg();
			int tracking = (int) selfTrackingDeg();
			int speed = (int) selfSpeed();
			int enemyDistance = (int) enemyDistance(0);
			int aimDirection = Math.abs((((int) aimrdir(0) - heading + 540)%360)-180);

			int frontWall = wallFeeler(1000, heading);
			int leftWall = wallFeeler(1000, heading + 90);
			int rightWall = wallFeeler(1000, heading - 90);
			int backWall = wallFeeler(1000, heading + 180);
			int trackWall = wallFeeler(1000, tracking);

			int headingTrackingDiff = Math.abs(((tracking - heading + 540)%360)+180);

			// Thrusting
			if (frontWall > frontLimit && speed < speedLimit) {
				thrust(1);
			} else if (frontWall > leftWall && frontWall > rightWall && frontWall > backWall && frontWall > trackWall && speed < speedLimit) {
				thrust(1);
			} else if (trackWall < trackLimit && (angleLimitMax >= Math.abs(tracking-heading) || Math.abs(tracking-heading) >= angleLimitMin)) {
				thrust(1);
			} else if (backWall < backWallLimit) {
				thrust(1);
			}

			// Turning
			if (heading > aimDirection && enemyDistance < engagementRange && trackWall > engagementWallDist) {
				turnRight(1);
			} else if (heading < aimDirection && enemyDistance < engagementRange && trackWall > engagementWallDist) {
				turnLeft(1);
			} else if (leftWall < rightWall && trackWall > trackWallTurningDist && speed > lowSpeed) {
				turnRight(1);
			} else if (leftWall > rightWall && trackWall > trackWallTurningDist && speed > lowSpeed) {
				turnLeft(1);
			} else if (heading > headingTrackingDiff && trackWall < trackWallDanger && speed > mediumSpeed) {
				turnRight(1);
			} else if (heading < headingTrackingDiff && trackWall < trackWallDanger && speed > mediumSpeed) {
				turnLeft(1);
			} else if (leftWall < rightWall && trackWall < trackWallClose) {
				turnRight(1);
			} else if (leftWall > rightWall && trackWall < trackWallClose) {
				turnLeft(1);
			} else if (heading > aimDirection && trackWall > aimCatchDist) {
				turnRight(1);
			} else if (heading < aimDirection && trackWall > aimCatchDist) {
				turnLeft(1);
			} else if (leftWall < rightWall) {
				turnRight(1);
			} else if (leftWall > rightWall) {
				turnLeft(1);
			}

			// Shooting
			if (aimDirection < shotAngleMax) {
				fireShot();
			}

			// Round Tracking
			if ((selfAlive() == 1 ? true : false) != alive) {
				round++;
				alive = (selfAlive() == 1 ? true : false);
			} 
        }
		
		public Production(String args[], String chromosome) {
			super(args);
			this.chromosome = chromosome;
		}

//      public static void main(String args[]) {
//          String[] new_args = {"-name","Petko & Co.","-join","localhost"};
//          Production production = new Production(new_args);
//      }
}
