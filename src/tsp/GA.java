/*
* GA.java
* Manages algorithms for evolving population
*/

package tsp;

import java.util.ArrayList;

public class GA {

    /* GA parameters */
    public static double mutationRate = 0.5;
    private static final int tournamentSize = 5; //MÄT
    private static final boolean elitism = true;
    private static boolean judge;
    private static int breed;
    private static boolean twoOpt = false;
    private static boolean origin = true;
    private static ArrayList<Tour> breedlist = new ArrayList<>();
    private static boolean bredGoneThrough;
    
    public static void setTwoOpt(boolean twoOpt) {
        GA.twoOpt = twoOpt;
    }


    public static void setBreed(int breed) {
        GA.breed = breed;
    }


    

    // Evolves a population over one generation
public static Population evolvePopulation(Population pop) {
        Population newPopulation  = new Population(pop.populationSize(), false);
        Population judgePopulation = null;
    
        if(judge){
            if(checkInbreeding(pop) > (breed)){
                //double bfjudge = 0;
                //double pjudge = 0;
                /*System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
                 for (int i = 0; i < pop.populationSize(); i++) {
                    bfjudge += pop.getTour(i).getDistance();
                     System.out.println(pop.getTour(i));
                }
                // System.out.println("ELITEN I POP: " + pop.getFittest());
                // System.out.println("DISTANCEPREJUDGE: " + bfjudge/1134); */
                 judgePopulation = judgementDay(breedlist, pop);      
                 bredGoneThrough = true;
                 
                /* System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
                 for (int i = 0; i < judgePopulation.populationSize(); i++) {
                    pjudge += judgePopulation.getTour(i).getDistance();
                     System.out.println(judgePopulation.getTour(i));
                }
                 System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
                // System.out.println("ELITEN I nPOP: " + newPopulation.getFittest());
                 //System.out.println("DISTANCEPOSTJUDGE: " + pjudge/1134);*/
            }
        }
        
        //Population newPopulation = new Population(pop.populationSize(), false);
        


        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }
        if(!bredGoneThrough){
            judgePopulation = pop;
        }
        else
        {
            bredGoneThrough = false;
            
        }

        //Orginal crossover
        if(origin){

                for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
                // Select parents
                Tour parent1 = tournamentSelection(judgePopulation);
                Tour parent2 = tournamentSelection(judgePopulation);
                // Crossover parents
                Tour child = crossover(parent1, parent2);
                // Add child to new population
                newPopulation.saveTour(i, child);
            }      
             
        }
        else{
             //Crossover with whilecheck
            for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
                // Select parents
                Tour parent1 = tournamentSelection(judgePopulation);
                Tour parent2 = tournamentSelection(judgePopulation);
                // Crossover parents
                while(parent1.equals(parent2)){
                    parent2 = tournamentSelection(judgePopulation);                
                }
                if(oneRog(parent1, parent2)){
                    //mutateSwap(parent2);
                    parent2.shuffleStuff();
                    Tour child = crossover(parent1, parent2);
                    newPopulation.saveTour(i, child);
                }
                else {
                    Tour child = crossover(parent1, parent2);
                    newPopulation.saveTour(i, child); 
                }

            }
            
        }

 
        //System.out.println("After cross: " + newPopulation.getFittest().getDistance());

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
           
            if(twoOpt){
             newPopulation.saveTour(i, mutate(newPopulation.getTour(i)));
            }
             else{
                mutateSwap(newPopulation.getTour(i));
            }
             
        }
        
        //System.out.println("After mutat:  " + newPopulation.getFittest().getDistance());
        return newPopulation;
    }

    // Applies crossover to a set of parents and creates offspring
    public static Tour crossover(Tour parent1, Tour parent2) {
        // Create new child tour
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    // Mutate a tour using swap mutation
    private static Tour mutate(Tour tour) {
            if(Math.random() < mutationRate){              
                 Tour newestTour = TwoOpt(tour);
                 return newestTour;
                
            }
        
            return tour;
    }
    
    private static void mutateSwap(Tour tour){
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            // Apply mutation rate
            
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());
                
                //Cant mutate itself
                while(tourPos1 == tourPos2){
                    tourPos2 = (int) (tour.tourSize() * Math.random());
                }
                // Get the cities at target position in tour
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
                
            }
                
        }
        
    }
    public static Tour TwoOptSwap(int i, int k , Tour best) 
{
    int size = best.tourSize();
    Tour newTour = new Tour();
 
    // 1. take route[0] to route[i-1] and add them in order to new_route
    for ( int c = 0; c <= i - 1; c++)
    {
        newTour.setCity(c, best.getCity(c));
    }
     
    // 2. take route[i] to route[k] and add them in reverse order to new_route
    int dec = 0;
    for ( int c = i; c <= k; c++ )
    {
        newTour.setCity(c, best.getCity(k - dec));
        dec++;
    }
 
    // 3. take route[k+1] to end and add them in order to new_route
    for ( int c = k + 1; c < size; c++ )
    {
        newTour.setCity(c, best.getCity(c));
    }
    return newTour;
}
    public static Tour TwoOpt(Tour tour){

 
    // repeat until no improvement is made 
        int improvement = 0;
        while(improvement < 20){


        double best_distance = tour.getDistance();
 
        //for ( int i = 0; i < size - 1; i++ ) 
        //{
            
            int k = (int)Math.ceil(Math.random()*(tour.tourSize()-1));
            int i = (int)Math.ceil(Math.random()*(tour.tourSize()-1));


                Tour newestTour = TwoOptSwap(i, k, tour);

                
                double new_distance = newestTour.getDistance();
                
               
                if ( new_distance < best_distance ) {
                    tour = newestTour;
                    return tour;
                }
            improvement++;
        }
        
            
        return tour;

        //draw(tour);
    }

    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            //spara en gammal individ ifrån förra populationen 
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }

    private static boolean oneRog(Tour parent1, Tour parent2) {
            if(parent1.getFitness() == parent2.getFitness()){
                return true;
            }
        return false;
    }
    public static double checkInbreeding(Population pop){
        int inbreeding = 0;       
        for (int i = 1; i < pop.populationSize(); i++) {
            //System.out.println("ELITE DISTANCE: " + pop.getTour(0).getDistance());
            //System.out.println("OTHER COMPARE DISTANCE: " +pop.getTour(i).getDistance());
            //System.out.println("FIRSTTOUR: " + pop.getTour(0));
            //System.out.println("COMPETOUR: " + pop.getTour(i));
            if(pop.getTour(0).getTour().equals(pop.getTour(i).getTour())){
                
                //breedlist.add(pop.getTour(i));
                
                inbreeding += 1;

            }
        }
        //System.out.println(inbreeding);
        return inbreeding;
    }
    //shuffla om endast dom som är homogena, tryck sedan in dom i populationen igen
    public static Population judgementDay(ArrayList<Tour> breedList, Population pop){
        //System.out.println("judge");
        Population judgePopulation = new Population(pop.populationSize(), false);
        


        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;

            judgePopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;

        
            //System.out.println("judge");
            for (int i = elitismOffset; i < judgePopulation.populationSize(); i++) {
                Tour randTour = new Tour();
                randTour.generateIndividual();
                /*System.out.println("PREJUDGE");
                System.out.println(pop.getTour(i));
                System.out.println(pop.getTour(i).getDistance());*/
                judgePopulation.saveTour(i, randTour);
                //pop.tours[i].shuffleStuff();
                //pop.updateTour(pop.tours[i], i);
                //pop.getTour(i).setDistance();
                /*System.out.println("POSTJUDGE");
                System.out.println(pop.getTour(i));
                
                System.out.println(pop.getTour(i).getDistance());*/
                //pop.tours[i].setDistance();
                //pop.saveTour(i, pop.tours[i]);
                //pop.tours[i].setDistance();


            }
            return judgePopulation;
        
    }

    public static void setJudge(boolean judge) {
        GA.judge = judge;
    }

    public static void setMutationRate(double mutationRate) {
        GA.mutationRate = mutationRate;
    }
    public static void setCrossover(boolean origin){
        GA.origin = origin;
    }
}