import java.io.*;

class NeuralHelp extends javaAI {
        static Network net = null;
        @Override
        public void AI_loop() {
            thrust(0);
            turnRight(0);
            turnLeft(0);
            double heading = selfHeadingDeg();
            double tracking = selfTrackingDeg();
            double rawDist = enemyDistance(0);
            double aimDiff = ((aimdir(0)-heading+540)%360)-180;
            double trackDiff = ((tracking-heading+540)%360)-180;

            double[] inputs = {
                selfHeadingDeg(),
                selfTrackingDeg(),
  
                wallFeeler(1000,heading),
                wallFeeler(1000,heading+10),
                wallFeeler(1000,heading-10),
  
                wallFeeler(1000,heading+90),
                wallFeeler(1000,heading+100),
                wallFeeler(1000,heading+80),
  
                wallFeeler(1000,heading+270),
                wallFeeler(1000,heading+280),
                wallFeeler(1000,heading+260),
  
                wallFeeler (1000,heading+180),
                wallFeeler(1000,heading+190),
                wallFeeler(1000,heading+170),
  
                wallFeeler(1000,tracking),
                wallFeeler(1000,tracking+10),
                wallFeeler(1000,tracking-10),
                
                rawDist,
                aimDiff,
                trackDiff
            };
            double[] actions = net.think(inputs);
            if (actions[0] == 1) {
                thrust(1);
            }
            if (actions[1] == 1) {
                turnLeft(1);
            }
            if (actions[2] == 1) {
                turnRight(1);
            }
            if (actions[3] == 1) {
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

