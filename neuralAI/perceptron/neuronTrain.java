/*Create a perceptron with 5 inputs and one output.
 Set the weights yourself to make it output 1 if the input is 01111, 10111, 11011, 11101, 11110, or 11111, and the output is 0 otherwise. 
 Now add a method for doing weight training and have it learn the proper weights. 
 Including a weight for the threshold with a constant activation of -1, there will be 6 weights to learn. 
 Your input for training should be all 32 possible inputs with the corresponding desired output. 
 Run the entire set of 32 inputs/output pairs through several times (try 100, 1000, etc) to do the training.
*/


public class neuronTrain {
    public static void main(String args[]){
        double[][] tests = new double[][]{{0,0,0,0,0},{0,0,0,0,1},{0,0,0,1,0},{0,0,0,1,1},{0,0,1,0,0},{0,0,1,0,1},{0,0,1,1,0},
        {0,0,1,1,1},{0,1,0,0,0},{0,1,0,0,1},{0,1,0,1,0},{0,1,0,1,1},{0,1,1,0,0},{0,1,1,0,1},{0,1,1,1,0},{0,1,1,1,1},{1,0,0,0,0},
        {1,0,0,0,1},{1,0,0,1,0},{1,0,0,1,1},{1,0,1,0,0},{1,0,1,0,1},{1,0,1,1,0},{1,0,1,1,1},{1,1,0,0,0},{1,1,0,0,1},{1,1,0,1,0},
        {1,1,0,1,1},{1,1,1,0,0},{1,1,1,0,1},{1,1,1,1,0},{1,1,1,1,1}};
        neuron n1 = new neuron(true);
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 32; j++) {
                int answer = (j == 15 || j == 23 || j == 27 || j == 29 || j == 30 || j == 31) ? 1 : 0;
                n1.train(tests[j],answer);
            }
        }
        n1.printWeights();
    }
}
