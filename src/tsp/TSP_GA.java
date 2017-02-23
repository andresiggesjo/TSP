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

        
        //Read berlin52 
        //BufferedReader br = new BufferedReader(new FileReader("optimalsuperoptimal.txt"));
        BufferedReader br = new BufferedReader(new FileReader("berlin52.txt"));
        //ArrayList<City> OptimumList = new ArrayList<City>();
        String line;
        while ((line = br.readLine()) != null) {

            String[] tempArray = line.split("\\s+");
            
            //create city and add it to the tourmanager
            City tempcity = new City(Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]));
           // OptimumList.add(tempcity);
            TourManager.addCity(tempcity);
            
            
         }
       // Tour optimum = new Tour(OptimumList);
       // System.out.println(optimum.getDistance());


        // Initialize population
        Population pop = new Population(100, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        //Set mutationrate
        GA.setMutationRate(0.005);
        
        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }
        
       GPoints g = new GPoints(); 
       Graph g2 = new Graph();

       for (int i = 0; i < TourManager.getCityLength(); i++) {
        	;
        	City c = TourManager.getCity(i);
        	g2.addPoint(c.getX(), c.getY());
        	
		}
        g.initUI(g2);

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        System.out.println("Berlin52 optimal solution 7500ish");
    }
}