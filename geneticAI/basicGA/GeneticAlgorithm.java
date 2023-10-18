import java.util.*;
import java.io.*;
import java.lang.*;

public class GeneticAlgorithm {
    private int populationSize;
    private List<Chromosome> population;
    private int generation;
    private double mutationRate;
    private int chromosomeSize;
    
    public GeneticAlgorithm(int populationSize, int chromosomeSize, double mutationRate, boolean newPopulation) {
      this.populationSize = populationSize;
      this.chromosomeSize = chromosomeSize;
      this.mutationRate = mutationRate;
      this.population = initializePopulation(newPopulation);
      this.generation = 0;
    }

    private List<Chromosome> initializePopulation(boolean newPopulation) {
      List<Chromosome> tempPopulation = new ArrayList<>();
      if (newPopulation) {
        for (int i = 0; i < populationSize; i++) {
          StringBuilder chromosome = new StringBuilder();
          for (int j = 0; j < chromosomeSize; j++) {
            chromosome.append(Math.random() > 0.5 ? "1" : "0");
          }
          tempPopulation.add(new Chromosome(chromosome.toString()));
        }
      } else {
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
      Random rand = new Random();
      Chromosome parent = population.get(rand.nextInt(populationSize));
      for (int i = 0; i < 3; i++) {
        Chromosome temp = population.get(rand.nextInt(populationSize));
        if (temp.fitness > parent.fitness) {
          parent = temp;
        }
      }
      return parent;
    }

    private void crossover() {
      List<Chromosome> newGeneration = new ArrayList<>();
      for (int i = 0; i < populationSize; i++) {
        Chromosome parent1 = selectParent();
        Chromosome parent2 = selectParent();

        StringBuilder child = new StringBuilder();
        int crossover = new Random().nextInt(chromosomeSize);
        child.append(parent1.genes.substring(0,crossover));
        child.append(parent2.genes.substring(crossover));

        newGeneration.add(new Chromosome(child.toString()));
      }
      this.population = newGeneration;
    }

    private void mutate() {
      for (Chromosome x : population) {
        StringBuilder genes = new StringBuilder(x.genes);
        for (int i = 0; i < chromosomeSize; i++) {
          if (Math.random() < mutationRate) {
            genes.setCharAt(i, genes.charAt(i) == '0' ? '1' : '0');
          }
        }
        x.genes = genes.toString();
        x.fitness = x.calculateFitness();
      }
    }

    public void run(int maxGenerations) {
      while (generation < maxGenerations) {
        crossover();
        mutate();
        generation++;

        Chromosome best = getBest();
        System.out.println("Generation: " + generation + " | Best Fitness: " + best.fitness + " | Average Fitness: " + averageFitness());
        if (best.fitness == chromosomeSize) {
          System.out.println("Best Chromosome Found: " + best.genes);
          break;
        }
        if (generation%10 == 0) {
          midSave();
        }
        rawSave();
      }
      save();
    }

    private Chromosome getBest() {
      return Collections.max(population, Comparator.comparing(chr -> chr.fitness));
    }

    private double averageFitness() {
      return population.stream().mapToInt(chr -> chr.fitness).average().orElseThrow();
    }

    private void midSave() {
      try {
        FileWriter writer = new FileWriter("./checkpoints/" + Integer.toString(generation) + "_generation_checkpoint.txt", false);
        Chromosome best = getBest();
        double avg = averageFitness();
        writer.write("Best Chromosome: " + best.genes + "\n" + "Best Fitness: " + best.fitness + "\n" + "Average Fitness: " + Double.toString(avg) + "\n");
        writer.close();
      } catch (IOException e) {
        System.out.println("Error: Could Not Save Checkpoint");
      }
    }

    private void rawSave() {
      try {
        FileWriter writer = new FileWriter("./rawData/" + Integer.toString(generation) + "_raw.txt", false);
        for (Chromosome x : population) {
          writer.write(x.genes + "\n");
        }
        writer.close();
      } catch (IOException e) {
        System.out.println("Error: Could Not Save Raw Data");
      }
    }

    private void save() {
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

    public static void main(String[] args) {
      GeneticAlgorithm ga = new GeneticAlgorithm(200,64,0.01,false);
      ga.run(1000);
    }
}

class Chromosome {
    public String genes;
    public int fitness;

    public Chromosome(String genes) {
      this.genes = genes;
      this.fitness = calculateFitness();
    }

    public int calculateFitness() {
      int fit = 0;
      for (int i = 0; i < genes.length(); i++) {
        if (genes.charAt(i) == '1') {
          fit++;
        }
      }
      return fit;
    }
}

class SortByFit implements Comparator<Chromosome> {
  public int compare(Chromosome a, Chromosome b) {
    return a.fitness - b.fitness;
  }
} 
