import java.io.Serializable;
import java.util.*;

public class HiddenLayer implements Serializable{
    
    double[] inputs;
    double[] outputs;
    Neuron[] neurons;
    double[] errorGrad;
    HiddenLayer next;
    HiddenLayer last;
    double alpha = 0.1;

    public HiddenLayer(int n_inputs, int n_neurons, HiddenLayer next, HiddenLayer last) {
        this.next = next;
        this.last = last;
        inputs = new double[n_inputs];
        neurons = new Neuron[n_neurons];
        outputs = new double[n_neurons];

        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(n_inputs);
        }
    }

    public double[] think(double[] input) {
        inputs = input;
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].think(inputs);
        }
        return outputs;
    }

    public double[] train(double[] prevErrorGrad) {
        errorGrad = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            double errorGradSum = 0;
            for (int j = 0; j < next.neurons.length; j++) {
                errorGradSum += prevErrorGrad[j] * next.neurons[j].getWeights()[i];
            }
            errorGrad[i] = outputs[i] * (1-outputs[i]) * errorGradSum;
        }
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].trainError(inputs,errorGrad[i]);
        }

        if (last != null) {
            last.train(errorGrad);
        }
        return errorGrad;
    }
}
