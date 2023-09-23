/*Create a perceptron with 5 inputs and one output.
 Set the weights yourself to make it output 1 if the input is 01111, 10111, 11011, 11101, 11110, or 11111, and the output is 0 otherwise. 
 Now add a method for doing weight training and have it learn the proper weights. 
 Including a weight for the threshold with a constant activation of -1, there will be 6 weights to learn. 
 Your input for training should be all 32 possible inputs with the corresponding desired output. 
 Run the entire set of 32 inputs/output pairs through several times (try 100, 1000, etc) to do the training.
*/
package neuralAI.perceptron;

public class neuron {
    //Set Trained Weights Here, Current Accuracy: 100%
    public double bias = -1.6000000000000005;
    private double alpha = 0.01;
    public double[] weights = {
        0.3212283744549727,0.4948224226983656,
        0.43220344685191936,0.3964578023508273,
        0.4587907149216467};
    
    //Random Weights To Start Training
    public neuron(boolean trainingMode) {
        if (trainingMode == true) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] = Math.random();
            }
            bias = -1;
        }
    }

    public neuron() {
        for (int i = 0; i < weights.length; i++) {
                weights[i] = Math.random();
            }
        bias = -1;
    }

    public int think(double input[]) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i]*weights[i];
        }

        sum += bias;

        int result = sum > 0 ? 1 : 0;
        return result;
    }

    public void train(double[] input, int expected) {
        int prediction = think(input);

        int error = expected - prediction;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += alpha * error * input[i];
        }

        bias += alpha * error;
    }

    public void printWeights(){
        System.out.println("Trained Weights:");
        for (int i = 0; i < weights.length; i++){
            System.out.print("Weight " + i + ": ");
            System.out.print(weights[i] + " ");
            System.out.println("");
        }
        System.out.println("Bias: " + bias);
        System.out.println("");
    }
}