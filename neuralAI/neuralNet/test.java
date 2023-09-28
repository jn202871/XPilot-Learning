import java.util.*;

public class Test {

	public static void main(String[] args) {
		double[][] tests = new double[][]{{0,0,0,0,0},{0,0,0,0,1},{0,0,0,1,0},{0,0,0,1,1},{0,0,1,0,0},{0,0,1,0,1},{0,0,1,1,0},
        {0,0,1,1,1},{0,1,0,0,0},{0,1,0,0,1},{0,1,0,1,0},{0,1,0,1,1},{0,1,1,0,0},{0,1,1,0,1},{0,1,1,1,0},{0,1,1,1,1},{1,0,0,0,0},
        {1,0,0,0,1},{1,0,0,1,0},{1,0,0,1,1},{1,0,1,0,0},{1,0,1,0,1},{1,0,1,1,0},{1,0,1,1,1},{1,1,0,0,0},{1,1,0,0,1},{1,1,0,1,0},
        {1,1,0,1,1},{1,1,1,0,0},{1,1,1,0,1},{1,1,1,1,0},{1,1,1,1,1}};
        Network net = new Network(1, 5, 1, 3);
        for (int i = 0; i < 1000000; i++) {
            for (int j = 0; j < 32; j++) {
                double[] answer = new double[1];
                answer[0] = (j == 0 || j == 16 || j == 8 || j == 4 || j == 2 || j == 1 || j == 15 || j == 23 || j == 27 || j == 29 || j == 30 || j == 31) ? 1 : 0;
                net.train(tests[j],answer);
            }
        }
        int correct = 0;
        int incorrect = 0;
        for (int j = 0; j < 32; j++) {
            int answer = (j == 0 || j == 16 || j == 8 || j == 4 || j == 2 || j == 1 || j == 15 || j == 23 || j == 27 || j == 29 || j == 30 || j == 31) ? 1 : 0;
            double[] input = tests[j];
            String inString = Arrays.toString(input);
            double[] guess = net.think(input);
            int guess2 = (guess[0] < 0.5) ? 0 : 1;
            System.out.println("Testing case: " + inString);
            System.out.println("Neural Net Guess: " + guess[0]);
            System.out.println("Activation: " + guess2);
            System.out.println("Correct Answer: " + answer);
            if (answer == guess2) {
                correct++;
            } else {
                incorrect++;
            }
        }
        System.out.println("Total Correct: " + correct);
        System.out.println("Total Incorrect: " + incorrect);
        System.out.println("Overall Accuracy: " + correct + "/32");
	}
}
