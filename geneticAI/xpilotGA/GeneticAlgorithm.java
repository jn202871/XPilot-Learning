import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
    private int populationSize;
    private List<Chromosome> population;
    private int generation;
    private double mutationRate;
    private int chromosomeSize;
    
    public GeneticAlgorithm(int populationSize, int chromosomeSize, double mutationRate, boolean newPopulation) { //Constructor class
      this.populationSize = populationSize;
      this.chromosomeSize = chromosomeSize;
      this.mutationRate = mutationRate;
      this.population = initializePopulation(newPopulation);
      this.generation = 0;
    }

    private List<Chromosome> initializePopulation(boolean newPopulation) { //Initializes population
      List<Chromosome> tempPopulation = new ArrayList<>();
      if (newPopulation) { //If making brand new population, generate random chromosomes
        for (int i = 0; i < populationSize; i++) {
          StringBuilder chromosome = new StringBuilder();
          for (int j = 0; j < chromosomeSize; j++) {
            chromosome.append(Math.random() > 0.5 ? "1" : "0");
          }
          tempPopulation.add(new Chromosome(chromosome.toString()));
        }
      } else { //If reading population from file... well it reads them from the file
        File populationFile = new File("population.txt");
        try {
          Scanner scan = new Scanner(populationFile);
          for (int i = 0; i < populationSize; i++) {
            if (scan.hasNextLine()) {
              String genes = scan.nextLine();
              tempPopulation.add(new Chromosome(genes));
            } else {
              System.out.println("Error: Chromosome Population Is Too Small");
            }
          }
          scan.close();
        } catch (FileNotFoundException e) {
          System.out.println("Error: File Not Found");
        }
      }
      return tempPopulation;
    }
    
    private Chromosome selectParent() {
    	double fitnessSum = population.stream().mapToDouble(chr -> chr.fitness).sum();
    	double sum = 0;
    	double target = ThreadLocalRandom.current().nextDouble(0, fitnessSum);
    	for (int i = 0; i < populationSize; i++) {
    	    if (population.get(i).fitness + sum >= target) {
    			return population.get(i);
    	    } else {
    	    	sum += population.get(i).fitness;
    	    }
    	}
    	return null;
    }

    private void crossover() { //Standard crossover using a random crossover index and substrings, also handles mutation
                               //Seems like bit-wise crossover works a little better? I believe russell used that and it was able to get to max fitness in 10 or less generations while this takes it roughly 15
      List<Chromosome> newGeneration = new ArrayList<>(); //Temporary new ArrayList to hold the new generation
      for (int i = 0; i < populationSize; i++) {
        Chromosome parent1 = selectParent();
        Chromosome parent2 = selectParent();

        StringBuilder child = new StringBuilder(); //I love StringBuilder, if only I knew it worked like this over the summer...
        int crossover = new Random().nextInt(chromosomeSize);
        child.append(parent1.genes.substring(0,crossover));
        child.append(parent2.genes.substring(crossover));

        for (int j = 0; j < chromosomeSize; j++) { //Mutation
          if (Math.random() < mutationRate) {
            child.setCharAt(j, child.charAt(j) == '0' ? '1' : '0'); //The fact that this exists and works feels like cheating, I put so much more effort in with substrings and char arrays for mutation over the summer >:(
          }
        }

        newGeneration.add(new Chromosome(child.toString()));
      }
      this.population = newGeneration;
    }

    public void run(int maxGenerations) { //Primary function that handles running the GA through generations and stops when a certain fitness is reached
      while (generation < maxGenerations) {
        crossover();
        generation++;

        Chromosome best = getBest();
        System.out.println("Generation: " + generation + " | Best Fitness: " + best.fitness + " | Average Fitness: " + averageFitness());
        if (best.fitness == chromosomeSize) { //This is kind of a hack since we know the maximum possible fitness and should be changed for the xpilot bot
          System.out.println("Best Chromosome Found: " + best.genes);
          break;
        }
        if (generation%10 == 0) { //Saving every 10 generations best chromosome and avg fitness
          midSave();
        }
        //rawSave(); //Saves the raw chromosome data to file each generation, theres no real point to this lol I thought it was cool
      }
      save(); //Saves final population and fitnesses to file
    }

    private Chromosome getBest() { //Finds the best individual from the population, I really wish I knew about the Collections import before now
      return Collections.max(population, Comparator.comparing(chr -> chr.fitness));
    }
    
    private double averageFitness() { //Finds the average fitness of all individuals
                                      //Apparently you can stream an ArrayList into a function that will apply a math function to everything in the array list, kinda cool
                                      //Also I have literally no idea what .orElseThrow() does but I get an error without it lol
      return population.stream().mapToDouble(chr -> chr.fitness).average().orElseThrow();
    }

    private void midSave() { //Saves every x generations the best chromosome and the avg fitness of the population
      try {
        FileWriter writer = new FileWriter("./checkpoints/" + Integer.toString(generation) + "_generation_checkpoint.txt", false);
        Chromosome best = getBest();
        double avg = averageFitness();
        writer.write("Best Chromosome: " + best.genes + "\n" + "Best Fitness: " + best.fitness + "\n" + "Average Fitness: " + Double.toString(avg) + "\n");
        writer.close();
      } catch (IOException e) {
        System.out.println("Error: Could Not Save Checkpoint, Is ./checkpoints Present?");
      }
    }

    private void rawSave() { //Saves all chromosomes to a file every generation
      try {
        FileWriter writer = new FileWriter("./rawData/" + Integer.toString(generation) + "_raw.txt", false);
        for (Chromosome x : population) {
          writer.write(x.genes + "\n");
        }
        writer.close();
      } catch (IOException e) {
        System.out.println("Error: Could Not Save Raw Data, Is ./rawData Present?");
      }
    }

    private void save() { //Saves the final population and fitnesses once GA completes
      try {
        FileWriter writer = new FileWriter("final_population.txt", false);
        for (Chromosome x : population) {
          writer.write("Chromosome: " + x.genes + "\n" + "Fitness: " + x.fitness + "\n");
        }
        writer.close();
      } catch (IOException e){
        System.out.println("Error: Could Not Save Final Population");
      }
    }

    public static void main(String[] args) { //Main function
      GeneticAlgorithm ga = new GeneticAlgorithm(200, 90, 0.001, true);
      ga.run(1000);
    }
}

class Chromosome { //Chromosome helper class
    public String genes;
    public double fitness;

    public Chromosome(String genes) {
      this.genes = genes;
      this.fitness = calculateFitness();
    }

    public double calculateFitness() { // Calculates fitness of chromosome
      double fit = 0;
      String[] new_args = {"-name","Petko & Co.","-join","localhost"};
      try {
      PrintWriter out = new PrintWriter("chromosome.txt");
      out.println(genes);
	  out.close();
	  } catch (IOException e) {
	  System.out.println("uhoh");
	  }
      System.out.println("New Agent Chromosome: " + genes);
      Production prod = new Production(new_args);
      System.out.println(prod.fitness);
      System.out.println(prod.chromosome);
      fit = prod.fitness;
      prod = null;
      return fit;
    }
}
