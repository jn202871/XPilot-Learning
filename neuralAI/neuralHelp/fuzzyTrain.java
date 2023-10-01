import java.io.*;
import java.util.Arrays;

class fuzzyTrain {
	public static void main(String[] args) throws Exception {
		Network net = new Network(2,15,4,12);
		double correct = 0;
		double incorrect = 0;
		int epoch = 1000;
		double elapsedTotal = 0;
		double totalTime = 0;
		for (int j = 0; j < epoch; j++) {
		double startTime = System.nanoTime();
		totalTime += startTime;
		System.out.println("Starting Epoch #: " + j + " | Epoch Time: " + elapsedTotal);
		elapsedTotal = 0;
		correct = 0;
		incorrect = 0;
		File input = new File("./inputs_15.txt");
		File output = new File("./outputs_15.txt");
		BufferedReader bri = new BufferedReader(new FileReader(input));
		BufferedReader bro = new BufferedReader(new FileReader(output));
		String in;
		String out;
		while ((in = bri.readLine()) != null) {
		if (Math.random() > 0.9 || j == epoch-1 || true) {
			out = bro.readLine();
			String[] inputArr = in.split(",");
			double[] inputDouble = new double[inputArr.length];
			for (int i = 0; i < inputArr.length; i++) {
				inputDouble[i] = Double.valueOf(inputArr[i]);
			}
			String[] outputArr = out.split(",");
			double[] outputDouble = new double[outputArr.length];
			for (int i = 0; i < outputArr.length; i++) {
				outputDouble[i] = Integer.valueOf(outputArr[i]);
			}
			double[] thunk = net.think(inputDouble);
			double[] thunkInt = new double[4];
			for (double x : thunk) {
				for (int i = 0; i < thunk.length; i++) {
					thunkInt[i] = (thunk[i] > 0.5) ? 1.0 : 0.0;
				}
			}	
			//System.out.println(Arrays.toString(outputDouble));
			//System.out.println(Arrays.toString(thunkInt));
			if (outputDouble[0] == thunkInt[0] && outputDouble[1] == thunkInt[1] && outputDouble[2] == thunkInt[2] && outputDouble[3] == thunkInt[3]) {
				correct++;
			} else {incorrect++;}
				net.train(inputDouble,outputDouble);
			}
			}
			elapsedTotal = System.nanoTime() - startTime;
			elapsedTotal = elapsedTotal/1000000000;
			totalTime += elapsedTotal;
			
			System.out.println("Correct: " + correct);
			System.out.println("Incorrect: " + incorrect);
			System.out.println("Accuracy: " + (correct/(correct+incorrect))*100);
		}
		System.out.println("Total Training Time: " + totalTime);
		try {
			FileOutputStream file = new FileOutputStream("net_15.ser", false);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(net);
			file.close();

			System.out.println("Network Serialized");
		} catch (IOException ex) {
			System.out.println("IOExeption");
		}
	}
}
