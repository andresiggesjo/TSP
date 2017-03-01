/*
* TSP_GA.java
* Create a tour and evolve a solution
*/

package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class TSP_GA {
    
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<Integer> resultlist = new ArrayList<>();
        
        
        loadFile();
        for (int i = 0; i < 100; i++) {
            resultlist.add(result());
            
        }
        writeFile((int) calculateAverage(resultlist));     
    }
    
    
    
   private static double calculateAverage(ArrayList<Integer> marks) {
    Integer sum = 0;
    if(!marks.isEmpty()) {
        for (Integer mark : marks) {
            sum += mark;
        }
        return sum.doubleValue() / marks.size();
        }
        return sum;
    }
    
    private static void writeFile(int subjectToWrite) throws IOException{
           try(PrintWriter out = new PrintWriter(new FileWriter("score5.txt", true))){
            out.println(subjectToWrite);
            }
    }
    private static void loadFile() throws IOException{
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
    }
    private static int result() throws IOException{

        Population pop = new Population(100, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        double xy = 0.5;
        GA.setMutationRate(0.01);
        for (int i = 0; i < 1000; i++) {
            GA.setMutationRate(xy);
            xy -= xy/200;
            pop = GA.evolvePopulation(pop);
            //System.out.println(pop.getFittest().getDistance());
        }
        System.out.println(pop.getFittest().getDistance());
        //drawBest(pop.getFittest());


        return pop.getFittest().getDistance();
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