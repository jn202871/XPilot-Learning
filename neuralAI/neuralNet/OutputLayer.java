package neuralAI.neuralNet;


public class OutputLayer extends HiddenLayer{

    public OutputLayer(int n_inputs, int n_neurons, HiddenLayer last) {
        super(n_inputs,n_neurons,null,last);
    }

    public double[] think(double[] input) {
        inputs = input;
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].think(inputs);
        }
        return outputs;
    }

    public double[] train(double[] expected) {
        double error[] = new double[expected.length];
        for (int i = 0; i < expected.length; i++) {
            error[i] = expected[i] - outputs[i];
        }
        errorGrad = new double[expected.length];
        for (int i = 0; i < expected.length; i++) {
            errorGrad[i] = outputs[i] * (1-outputs[i]) * error[i];
        }
        for (int i = 0; i < expected.length; i++) {
            neurons[i].trainError(inputs,errorGrad[i]);
        }

        if (last != null) {
            last.train(errorGrad);
        }
        return errorGrad;
    }

}
