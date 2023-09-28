import java.util.*;

public class Network {
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
        if (first.next == null) {
            first.next = last;
        }
    }

    public double[] think(double[] input) {
        double[] thinking = first.think(input);
        HiddenLayer curr = first.next;
        while (curr != null) {
            thinking = curr.think(thinking);
            curr = curr.next;
        }
        double[] output = new double[outputs];
        for (int i = 0; i < thinking.length; i++) {
            if (thinking[i] > 0.5) {output[i] = 1;} else {output[i] = 0;}
        }
        return thinking;
    }

    public void train(double[] input, double[] expout){
        think(input);
        last.train(expout);
    }
}
