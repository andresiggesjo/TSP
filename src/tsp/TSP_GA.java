/*
* TSP_GA.java
* Create a tour and evolve a solution
*/

package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSP_GA {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // Create and add our cities
        /*
        for (int i = 0; i < 100; i++) {
            City city = new City();
            TourManager.addCity(city);
        }*/
        BufferedReader br = new BufferedReader(new FileReader("berlin52.txt"));
        
   
        String line;
        while ((line = br.readLine()) != null) {

            String[] tempArray = line.split("\\s+");
            City tempcity = new City(Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]));
            TourManager.addCity(tempcity);
            
         }


        

       
        // Initialize population
        Population pop = new Population(250, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());


        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 400; i++) {
            pop = GA.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
    }
}