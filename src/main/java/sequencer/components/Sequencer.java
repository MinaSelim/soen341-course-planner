package sequencer.components;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author menac
 */

import java.util.ArrayList;
import java.util.Arrays;

import skynet.scheduler.common.ICourse;

public class Sequencer {
    
    // Assigns each course in required a priority rating based on the number of
    // courses it is a prerequisite for (recursively). 
    public static void setPriority(ICourse course, ArrayList<Course> required) {
        ICourse[] prereqs = course.getPrerequisites(); 
        
        // Scan through list of prereqs
        for (int i = 0; i < prereqs.length; i++) {
            ICourse currentPrereq = prereqs[i]; 
            
            // Update the priority of the direct prereq
            for (int j = 0; j < required.size(); j++) {
                if (currentPrereq == required.get(j))
                    required.get(j).incrementPriority();
            }
            
            // Recursively update lower prereqs
            setPriority(currentPrereq, required); 
        }
        
    }
    
    // Moves courses from required to available if all prerequisites are in taken
    public static void updateAvailability(ArrayList<Course> taken, ArrayList<Course> required, ArrayList<Course> available) {

        for (int i = 0; i < required.size(); i++) {

            Course currentCourse = required.get(i);
            ArrayList<Course> currentPrereqs = new ArrayList(Arrays.asList(currentCourse.getPrerequisites()));
            
            if (currentCourse.getPrerequisites().length == 0) {
                available.add(currentCourse);
                required.remove(i--); 
            } 
            else {
                ArrayList<Course> copy = currentPrereqs;

                for (int j = 0; j < copy.size(); j++) {
                    for (int k = 0; k < taken.size(); k++) {
                        if (copy.get(j) == taken.get(k)) {
                            copy.remove(j--);
                            break; // why?
                        }  
                    }
                }

                if (copy.isEmpty()) {
                    available.add(currentCourse);
                    required.remove(i--); 
                } 
            }
        }
        
        if (available.size() > 0)
            sortAvailable(available); 
    }
    
    
    // Simple selection sort to sort available based on course priority
    // Highest to lowest
    private static void sortAvailable(ArrayList<Course> available) {

//        Course highestPriority = available.get(0);
//        int highestPriorityIndex = 0; 
//        Course highestPriority;

        int highestIndex; 
        
        // Do properly
        for (int i = 0; i < available.size()-1; i++) {
            
            highestIndex = i;
            for (int j = i+1; j < available.size(); j++) {
                
                if (available.get(j).getPriority() > available.get(highestIndex).getPriority())
                    highestIndex = j;
                
//                if (available.get(j).getPriority() > highestPriority.getPriority()) {
//                    highestPriority = available.get(j);
//                    highestPriorityIndex = j;
//                }
            }
            
            // Swap
            Course temp = available.get(i);
            available.set(i, available.get(highestIndex));
            available.set(highestIndex, temp);
            
//            if (available.get(i) != highestPriority) {
//                // Swap 
//                Course temp = available.get(i);
//                available.set(i, highestPriority);
//                available.set(highestPriorityIndex, temp);
//            }
        }
        
        //return available;
    }
    
    public static ArrayList<Semester> generateSequence(String program, ArrayList<Course> taken, Course[] all) {

        // Step 1: Create ArrayList of required courses
        //System.out.println("Step 1: Required\n");
        ArrayList<Course> required = new ArrayList();
        for (int i = 0; i < all.length; i++) {
            if (taken.size() > 0) {
                for (int j = 0; j < taken.size(); j++) {
                    if (taken.get(j) == all[i]) // Current course being evaluated has been taken
                    {
                        break;
                    }
                    if (j == taken.size() - 1) // Current course has not been taken and needs to be added
                    {
                        required.add(all[i]);
                    }
                }
            }
            else {
                required.add(all[i]);
            }
        }

        
        // Step 2: Set Priority
        //System.out.println("Step 2: Priority\n");
        for (int i = 0; i < required.size(); i++)
            setPriority(required.get(i), required); 
        
        System.out.println("Showing Priorities");
        for (int i = 0; i < required.size(); i++) {
            System.out.println(required.get(i).getCourseName() + " " + required.get(i).getPriority());
        }
        System.out.println();

        
        // Step 3: Generate Sequence
        //System.out.println("Step 3: Sequence\n");
        ArrayList<Semester> sequence = new ArrayList(); 
        
        int year = 2019;
        int season = 0; 
        int creditCap = 15; 
        
        ArrayList<Course> available = new ArrayList(); // List of all available courses
        
        int counter = 0;
        while (!required.isEmpty() || !available.isEmpty()) {

            
            updateAvailability(taken, required, available);
            
//            System.out.println("\nIteration " + ++counter);
//            System.out.println("Showing sorted available");
//            System.out.print("[ ");
//            for (int i = 0; i < available.size(); i++)
//                System.out.print(available.get(i).getCourseName() + " "); 
//            System.out.println("]");
            
            int totalCredits = 0;
            
            ArrayList<Course> registeredCourses = new ArrayList(); 
            
            for (int i = 0; i < available.size(); i++) {
                Course highestAvailable = available.get(i);
                
                registeredCourses.add(highestAvailable);
                taken.add(highestAvailable);
                totalCredits += highestAvailable.getCreditUnits();
                available.remove(i--);

                if (totalCredits >= creditCap)
                    break;
            }
            
            String currentSeason = ""; 
            
            switch ((season++) % 3) {
                case(0):
                    currentSeason = "Fall";
                    break;
                case(1):
                    year++; 
                    currentSeason = "Winter";
                    break;
                case(2):
                    currentSeason = "Summer";
                    break;
                default:
                    System.out.println("Error in switch");     
            }
            
            Semester currentSemester = new Semester(currentSeason, year, registeredCourses);
  
//            System.out.println("Semester " + counter);
//            System.out.print("[ ");
//            for (int i = 0; i < currentSemester.getCourses().size(); i++)
//                System.out.print(currentSemester.getCourses().get(i).getCourseName() + " ");
//            System.out.println("]\n");
            
            sequence.add(currentSemester); 
        }
        
        return sequence;
    } 
    
}