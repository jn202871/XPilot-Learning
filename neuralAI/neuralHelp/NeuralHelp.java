// Xpilot agent controlled by a neural network
// Jay Nash & Russell Kosovsky

import java.io.*;
import java.util.*;

class NeuralHelp extends javaAI {
        static Network net = null;
        @Override
        public void AI_loop() {
            thrust(0);
            turnRight(0);
            turnLeft(0);
            int heading = (int)selfHeadingDeg();
            int tracking = (int)selfTrackingDeg();
            double rawDist = enemyDistance(0);
            double aimDiff = ((aimdir(0)-heading+540)%360)-180;
            double trackDiff = ((tracking-heading+540)%360)-180;
            
            double modDist;
            if (rawDist > 1000) {
            	modDist = 1000;
            } else {
            	modDist = rawDist;
            }

            double[] inputs = {
                selfHeadingDeg()/360,
                selfTrackingDeg()/360,
  
                wallFeeler(1000,heading)/1000,
                wallFeeler(1000,heading+10)/1000,
                wallFeeler(1000,heading-10)/1000,
  
                wallFeeler(1000,heading+90)/1000,
                wallFeeler(1000,heading+100)/1000,
                wallFeeler(1000,heading+80)/1000,
  
                wallFeeler(1000,heading+270)/1000,
                wallFeeler(1000,heading+280)/1000,
                wallFeeler(1000,heading+260)/1000,
  
                wallFeeler (1000,heading+180)/1000,
                wallFeeler(1000,heading+190)/1000,
                wallFeeler(1000,heading+170)/1000,
  
                wallFeeler(1000,tracking)/1000,
                wallFeeler(1000,tracking+10)/1000,
                wallFeeler(1000,tracking-10)/1000,
                
                modDist/1000,
                aimDiff/180,
                trackDiff/180,
                selfSpeed()/10
            };
            double[] actions = net.think(inputs);
            System.out.println(Arrays.toString(actions));
    
            if (actions[0] > 0.5) {
                thrust(1);
            }
            if (actions[1] > 0.5) {
                turnLeft(1);
            }
            if (actions[2] > 0.5) {
                turnRight(1);
            }
            if (actions[3] > 0.5) {
                fireShot();
            }
        }

        public NeuralHelp(String args[]) {
                super(args);
        }
        
        public static void main(String args[]) {
            String[] new_args = {"-name","Spinner"};
            try {
                FileInputStream file = new FileInputStream("net.ser");
                ObjectInputStream in = new ObjectInputStream(file);

                net = (Network)in.readObject();

                in.close();
                file.close();
                System.out.println("Nerual Net Loaded");
            } catch(IOException ex) {
                System.out.println("IOException");
            } catch(ClassNotFoundException ex) {
                System.out.println("Class Not Found");
            }
                NeuralHelp help = new NeuralHelp(new_args);
        }
}

