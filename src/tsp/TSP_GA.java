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
    
    public static Tour TwoOptSwap(int i, int k , Tour best) 
{
    int size = best.tourSize();
    Tour newTour = new Tour();
 
    // 1. take route[0] to route[i-1] and add them in order to new_route
    for ( int c = 0; c <= i - 1; ++c )
    {
        newTour.setCity(c, best.getCity(c));
    }
     
    // 2. take route[i] to route[k] and add them in reverse order to new_route
    int dec = 0;
    for ( int c = i; c <= k; ++c )
    {
        newTour.setCity(c, best.getCity(k - dec));
        dec++;
    }
 
    // 3. take route[k+1] to end and add them in order to new_route
    for ( int c = k + 1; c < size; ++c )
    {
        newTour.setCity(c, best.getCity(c));
    }
    //System.out.println(newTour.getDistance());
    return newTour;
}
    public static void TwoOpt(Tour tour){
    // Get tour size
    int size = TourManager.numberOfCities();
 
    // repeat until no improvement is made 
    int improve = 0;
 
    while ( improve < 20 ){
        double best_distance = tour.getDistance();
 
        for ( int i = 0; i < size - 1; i++ ) 
        {
            for ( int k = i + 1; k < size; k++) 
            {
                Tour newestTour = TwoOptSwap(i, k, tour);
                
                double new_distance = newestTour.getDistance();
                if ( new_distance < best_distance ) 
                {
                    // Improvement found so reset
                    improve = 0;
                    tour = newestTour;
                    best_distance = new_distance;
                }
            }
        }
 
            improve ++;
        }
        draw(tour);
    }

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
        Population pop = new Population(150, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        //Set mutationrate
        GA.setMutationRate(0.005);
        
        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 300; i++) {
            pop = GA.evolvePopulation(pop);
        }
        System.out.println(pop.getFittest().getDistance());
        drawBest(pop.getFittest());
        TwoOpt(pop.getFittest());
        

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