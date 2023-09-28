import java.io.Serializable;

public class Neuron implements Serializable{
    private double bias;
    private double alpha;
    private double[] weights;
    
    //Random Weights To Start Training
    public Neuron(int inputs) {
        weights = new double[inputs];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        bias = Math.random() -0.5;
        alpha = 0.1;
    }

    public double think(double input[]) {
        if (input.length != weights.length) {
            throw new IllegalArgumentException("Inputs must match weights: " + input.length + "/" + weights.length);
        }

        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }

        sum -= bias;

        return sigmoid(sum);
    }

    public void train(double[] input, int expected) {
        double prediction = think(input);
        double error = expected - prediction;
        trainError(input, error);
    }

    public void trainError(double[] input, double error) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += alpha * error * input[i];
        }
        bias += alpha * error * -1;
    }

    public void printWeights(){
        System.out.println("Trained Weights:");
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Weight " + i + ": " + weights[i]);
        }
        System.out.println("Bias: " + bias);
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double sigmoid(double x) {
        return 1 / (1+Math.exp(-x));
    }

    public double sigmoidDerivative(double x) {
        double y = sigmoid(x);
        return y * (1 - y);
    }
}
