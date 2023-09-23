/*Create a perceptron with 5 inputs and one output.
 Set the weights yourself to make it output 1 if the input is 01111, 10111, 11011, 11101, 11110, or 11111, and the output is 0 otherwise. 
 Now add a method for doing weight training and have it learn the proper weights. 
 Including a weight for the threshold with a constant activation of -1, there will be 6 weights to learn. 
 Your input for training should be all 32 possible inputs with the corresponding desired output. 
 Run the entire set of 32 inputs/output pairs through several times (try 100, 1000, etc) to do the training.
*/
package neuralAI.perceptron;

import java.util.Arrays;

public class neuronTest {
    public static void main(String args[]){
        double[][] tests = new double[][]{{0,0,0,0,0},{0,0,0,0,1},{0,0,0,1,0},{0,0,0,1,1},{0,0,1,0,0},{0,0,1,0,1},{0,0,1,1,0},
        {0,0,1,1,1},{0,1,0,0,0},{0,1,0,0,1},{0,1,0,1,0},{0,1,0,1,1},{0,1,1,0,0},{0,1,1,0,1},{0,1,1,1,0},{0,1,1,1,1},{1,0,0,0,0},
        {1,0,0,0,1},{1,0,0,1,0},{1,0,0,1,1},{1,0,1,0,0},{1,0,1,0,1},{1,0,1,1,0},{1,0,1,1,1},{1,1,0,0,0},{1,1,0,0,1},{1,1,0,1,0},
        {1,1,0,1,1},{1,1,1,0,0},{1,1,1,0,1},{1,1,1,1,0},{1,1,1,1,1}};
        neuron n1 = new neuron(false);
        int correct = 0;
        int incorrect = 0;
        for (int j = 0; j < 32; j++) {
            int answer = (j == 15 || j == 23 || j == 27 || j == 29 || j == 30 || j == 31) ? 1 : 0;
            double[] input = tests[j];
            String inString = Arrays.toString(input);
            int guess = n1.think(input);
            System.out.println("Testing case: " + inString);
            System.out.println("Perceptron Guess: " + guess);
            System.out.println("Correct Answer: " + answer);
            if (answer == guess) {
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
