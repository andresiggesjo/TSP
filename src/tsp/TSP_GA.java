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
        BufferedReader br = new BufferedReader(new FileReader("danzig42.txt"));
        //ArrayList<City> OptimumList = new ArrayList<City>();
        String line;
        while ((line = br.readLine()) != null) {
            
            String[] tempArray = line.split("\\s+");

            //create city and add it to the tourmanager
            City tempcity = new City(Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]));
           // OptimumList.add(tempcity);
            TourManager.addCity(tempcity);
            
            
         }

        Population pop = new Population(25, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());


        
        
        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        double xy = 0.5;
        for (int i = 0; i < 100; i++) {
            GA.setMutationRate(xy);
            xy -= 0.00499;
            System.out.println(xy);
            pop = GA.evolvePopulation(pop);
            System.out.println(pop.getFittest().getDistance());
        }
        System.out.println(pop.getFittest().getDistance());
        drawBest(pop.getFittest());

        

        //System.out.println("Final distance: " + pop.getFittest().getDistance());
        //System.out.println("Solution:");
        //System.out.println(pop.getFittest());
        //System.out.println("Berlin52 optimal solution 7500ish");
    }

    private static void drawBest(Tour tour) {
       GPoints g = new GPoints(); 
       Graph g2 = new Graph(tour.getDistance());

       for (int i = 0; i < tour.tourSize(); i++) {
        	
        	City c = tour.getCity(i);
        	g2.addPoint(c.getX(), c.getY());
        	
		}
        g.initUI(g2);
        g.setVisible(true);

        // Print final results
        System.out.println(tour.getDistance());
        System.out.println("Finished");
    }
        private static void draw(Tour tour) {
       GPoints g = new GPoints(); 
       Graph g2 = new Graph(tour.getDistance());

       for (int i = 0; i < tour.tourSize(); i++) {
        	
        	City c = tour.getCity(i);
        	g2.addPoint(c.getX(), c.getY());
        	
		}
        g.initUI(g2);
        g.setVisible(true);

        // Print final results
        System.out.println(tour.getDistance());
        System.out.println("Finished");
    }
}