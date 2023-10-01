// Purpose: Network class for neural network

import java.io.Serializable;
import java.util.*;

public class Network implements Serializable {
    HiddenLayer first;
    OutputLayer last;
    int outputs;

    public Network(int layers, int inputs, int outputs, int hiddenWidth) {
        this.outputs = outputs;
        first = new HiddenLayer(inputs, hiddenWidth, null, null);
        HiddenLayer curr = first;
        --layers;
        for (int i = 0; i < layers; i++) {
            HiddenLayer temp = new HiddenLayer(hiddenWidth, hiddenWidth, null, curr);
            curr.next = temp;
            curr = temp;
        }
        last = new OutputLayer(hiddenWidth, outputs, curr);
        curr.next = last;
        if (first.next == null) {
            first.next = last;
        }
    }

    public double[] think(double[] input) {
        double[] thinking = input;
        HiddenLayer curr = first;
        while (curr != null) {
            thinking = curr.think(thinking);
            curr = curr.next;
        }
        return thinking;
    }

    public void train(double[] input, double[] expout){
        think(input);
        last.train(expout);
    }
}
