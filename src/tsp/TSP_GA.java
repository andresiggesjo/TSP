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
            //result(populationSize, NumberOfGenerations, LogarithmicOrStaticMutationrate, JudgementTrueorFalse, Mutationmethod, Crossovermethod)
            //resultlist.add(result(1000,1000,false, false, 0, true, false));
            
            //MÄT POPSIZE, GENSIZE, 
            
            //2-OPT SKAPAR KNAS PREMATURE CONVERGENCE JUDGE FUCKAR UR MED DEN
            double d = result(1000,5000,false, true, 90, false, false);
            String d2 = String.valueOf(d);
            writeFile(d2);
            
        }
        //System.out.println(calculateAverage(resultlist));
        //writeFile("pop500|gen1000|logmutfalse|judgefalse|judgevalue|2-opt|originalCrossover|berlin52|runs100");
        //double d = calculateAverage(resultlist);
        //String d2 = String.valueOf(d);
        //writeFile(d2);
        
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
    
    private static void writeFile(String subjectToWrite) throws IOException{
           try(PrintWriter out = new PrintWriter(new FileWriter("Scores/it3whilerat195.txt", true))){
            out.println(subjectToWrite);
            }
    }
    private static void loadFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("Problems/berlin52.txt"));
        //ArrayList<City> OptimumList = new ArrayList<City>();
        String line;
        while ((line = br.readLine()) != null) {
            
            String[] tempArray = line.split("\\s+");

            //create city and add it to the tourmanager
            City tempcity = new City(Integer.parseInt(tempArray[1]), Integer.parseInt(tempArray[2]));
           // OptimumList.add(tempcity);
            TourManager.addCity(tempcity);

            
            
         }
    }
    private static int result(int popSize, int generations, boolean logMutation, boolean judge, int breed, boolean mutation, boolean origin) throws IOException{

        Population pop = new Population(popSize, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());


        pop = GA.evolvePopulation(pop);
        double xy = 0.5;
        GA.setMutationRate(0.05);
        GA.setJudge(judge);
        GA.setBreed(breed);
        GA.setTwoOpt(mutation);
        GA.setCrossover(origin);
        int temp = 0;
        for (int i = 0; i < generations; i++) {

            

            if(logMutation){
                
                GA.setMutationRate(xy);
                xy -= xy/1000;
                //System.out.println(xy);
            }
            
            pop = GA.evolvePopulation(pop);
            if(pop.getFittest().getDistance() < temp){
                System.out.println("fittest tour is " + pop.getFittest().getDistance() + " on generation: " + i);
                 

            }
            
            //System.out.println("temp" + temp);
            temp = pop.getFittest().getDistance();
            
            //System.out.println("fittest" +pop.getFittest().getDistance());
            if(i == generations){
                System.out.println("MAXGENERATIONS");
            }



            for (int j = 0; j < pop.populationSize(); j++) {
                    //System.out.println(pop.getTour(j));
                }
            /*if(GA.checkInbreeding(pop) > breed){
                System.out.println("ELITEN");
                System.out.println(pop.getFittest().getTour());
                for (int j = 0; j < pop.populationSize(); j++) {
                    System.out.println(pop.getTour(j));
                }
            }*/
            
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
        //System.out.println(tour.);
        System.out.println(tour.getDistance());
        System.out.println("Finished");
    }
}