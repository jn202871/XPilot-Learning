
public class neuralNet {
    private neuron[][] hiddenLayers;
    private neuron outputNeuron;
    private int hiddenLayerCount = 1;
    private int hiddenLayerWidth = 3;
    private int inputs = 5;
    private double[] hiddenOutput;

    public neuralNet() {
        hiddenLayers = new neuron[hiddenLayerCount][hiddenLayerWidth];
        for (int i = 0; i < hiddenLayerCount; i++) {
            for (int j = 0; j < hiddenLayerWidth; j++) {
                int inputCount = (i == 0) ? inputs : hiddenLayerWidth;
                hiddenLayers[i][j] = new neuron(inputCount);
            }
        }
        outputNeuron = new neuron(hiddenLayerWidth);
    }

    public double think(double[] input) {
        for (int i = 0; i < hiddenLayerCount; i++) {
            neuron[] currentLayer = hiddenLayers[i];
            hiddenOutput = new double[currentLayer.length];
            for (int j = 0; j < hiddenLayerWidth; j++) {
                hiddenOutput[j] = currentLayer[j].think(input);
            }
            input = hiddenOutput;
        }
        return outputNeuron.think(hiddenOutput);
    }

    public void train(double[] input, int expected) {
        if (input.length != inputs) {
            throw new IllegalArgumentException("Input length must match the number of input neurons.");
        }
        double[] originalInput = input.clone();
        double prediction = think(input);
        int prediction2 = (prediction < 0.5) ? 0 : 1;
        double error = expected - prediction2;

        backPropagation(originalInput, hiddenOutput, error);
    }

    private void backPropagation(double[] input, double[] hiddenOutput, double error) {
        double output = outputNeuron.think(hiddenOutput);
        double outputDelta = error * outputNeuron.sigmoidDerivative(output);
        outputNeuron.trainError(hiddenOutput, outputDelta);

        double[][] hiddenErrors = new double[hiddenLayerCount][hiddenLayerWidth];
        for (int j = 0; j < hiddenLayerWidth; j++) {
            hiddenErrors[hiddenLayerCount - 1][j] = outputDelta * outputNeuron.getWeights()[j];
        }
        for (int i = hiddenLayerCount - 2; i >= 0; i--) {
            for (int j = 0; j < hiddenLayerWidth; j++) {
                double sum = 0;
                for (int k = 0; k < hiddenLayerWidth; k++) {
                    sum += hiddenErrors[i + 1][k] * hiddenLayers[i + 1][k].getWeights()[j];
                }
                double activation = hiddenLayers[i][j].think(input);
                hiddenErrors[i][j] = sum * hiddenLayers[i][j].sigmoidDerivative(activation);
            }
        }
        double[] currentInput = input;
        for (int i = 0; i < hiddenLayerCount; i++) {
            double[] hiddenOutputs = new double[hiddenLayerWidth];
            for (int j = 0; j < hiddenLayerWidth; j++) {
                hiddenLayers[i][j].trainError(currentInput, hiddenErrors[i][j]);
            }
            for (int j = 0; j < hiddenLayerWidth; j++) {
                hiddenOutputs[j] = hiddenLayers[i][j].think(currentInput);
            }
            currentInput = hiddenOutputs;
        }
    }
}
