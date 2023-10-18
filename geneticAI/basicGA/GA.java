import java.util.*;
import java.io.*;

public class GA {

    private int populationSize;
    private List<Chromosome> population;
    private int generationCount;
    public static int chromosomeSize;
    private double mutationRate;

    public GA(int populationSize, int chromosomeSize, double mutationRate) {
        this.populationSize = populationSize;
        this.chromosomeSize = chromosomeSize;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();
        this.generationCount = 0;
        initializePopulation();
    }

    void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            StringBuilder genes = new StringBuilder();
            for (int j = 0; j < chromosomeSize; j++) {
                genes.append(Math.random() > 0.5 ? "1" : "0");
            }
            population.add(new Chromosome(genes.toString()));
        }
    }

    Chromosome selectParent() {
        // Tournament selection
        Random rand = new Random();
        Chromosome best = population.get(rand.nextInt(populationSize));
        for (int i = 0; i < 3; i++) { // Tournament size
            Chromosome contender = population.get(rand.nextInt(populationSize));
            if (contender.fitness > best.fitness) {
                best = contender;
            }
        }
        return best;
    }

    void crossover() {
        List<Chromosome> newGeneration = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome parent1 = selectParent();
            Chromosome parent2 = selectParent();

            // Perform crossover at a random position
            StringBuilder childGenes = new StringBuilder();
            int crossOverPoint = new Random().nextInt(chromosomeSize);
            childGenes.append(parent1.genes.substring(0, crossOverPoint))
                      .append(parent2.genes.substring(crossOverPoint));
            
            // Add child to new generation
            newGeneration.add(new Chromosome(childGenes.toString()));
        }
        this.population = newGeneration;
    }

    void mutate() {
        for (Chromosome individual : population) {
            StringBuilder genes = new StringBuilder(individual.genes);
            for (int i = 0; i < chromosomeSize; i++) {
                if (Math.random() < mutationRate) {
                    // Flip the bit
                    genes.setCharAt(i, genes.charAt(i) == '0' ? '1' : '0');
                }
            }
            individual.genes = genes.toString();
            individual.fitness = individual.calculateFitness();
        }
    }

    void run(int maxGenerations) {
        while (generationCount < maxGenerations) {
            crossover();
            mutate();
            generationCount++;

            Chromosome best = getFittest();
            System.out.println("Generation: " + generationCount +
                               " Fittest: " + best.fitness +
                               " Average fitness: " + averageFitness());

            if (best.fitness == chromosomeSize) {
                System.out.println("Best Chromosome Found: " + best.genes);
                break; // Stop if we found a solution
            }
        }
        savePopulationToFile();
    }

    Chromosome getFittest() {
        return Collections.max(population, Comparator.comparing(c -> c.fitness));
    }

    double averageFitness() {
        return population.stream().mapToInt(c -> c.fitness).average().orElseThrow();
    }

    void savePopulationToFile() {
        try (PrintWriter writer = new PrintWriter(new File("final_population.txt"))) {
            for (Chromosome individual : population) {
                writer.println(individual.genes + " " + individual.fitness);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to save to file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GA ga = new GA(200, 64, 0.01);
        ga.run(1000);
    }
}

class Chromosome {
    String genes;
    int fitness;

    public Chromosome(String genes) {
        this.genes = genes;
        this.fitness = calculateFitness();
    }

    int calculateFitness() {
        int fit = 0;
        for (int i = 0; i < genes.length(); i++) {
            if (genes.charAt(i) == '1') {
                fit++;
            }
        }
        return fit;
    }
}


