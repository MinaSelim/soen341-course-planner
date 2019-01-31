/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynet_sequencer;

/**
 *
 * @author menac
 */

import java.util.ArrayList;

public class Skynet_Sequencer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Course(String n, Course[] p, String[] sa, int c)
        
        Course[] prereqs_248 = {};
        Course comp248 = new Course("comp 248", prereqs_248);
        
        Course[] prereqs_232 = {};
        Course comp232 = new Course("comp 232", prereqs_232);
        
        Course[] prereqs_249 = {comp248};
        Course comp249 = new Course("comp 249", prereqs_249);
        
        Course[] prereqs_352 = {comp232, comp249};
        Course comp352 = new Course("comp 352", prereqs_352);
        
        Course[] prereqs_348 = {comp249};
        Course comp348 = new Course("comp 348", prereqs_348);
        
        Course[] prereqs_335 = {comp232};
        Course comp335 = new Course("comp 335", prereqs_335);
        
        Course[] prereqs_346 = {comp352};
        Course comp346 = new Course("comp 346", prereqs_346);
        
        Course[] prereqs_287 = {comp248};
        Course soen287 = new Course("soen 287", prereqs_287);
        
        Course[] prereqs_331 = {comp249};
        Course soen331 = new Course("soen 331", prereqs_331);
        
        Course[] prereqs_341 = {comp352};
        Course soen341 = new Course("soen 341", prereqs_341);

        Course[] prereqs_321 = {comp346};
        Course soen321 = new Course("soen 321", prereqs_321);
        
        Course[] prereqs_213 = {};
        Course engr213 = new Course("engr 213", prereqs_213);
        
        Course[] prereqs_233 = {};
        Course engr233 = new Course("engr 233", prereqs_233);
        
        Course[] prereqs_371 = {engr213, engr233};
        Course engr371 = new Course("engr 371", prereqs_371);
        
        Course[] prereqs_391 = {engr213, engr233, comp248};
        Course engr391 = new Course("engr 391", prereqs_391);
        
        Course[] prereqs_301 = {};
        Course engr301 = new Course("engr 301", prereqs_301);
        
        Course[] prereqs_282 = {};
        Course encs282 = new Course("encs 282", prereqs_282);
        
        ArrayList<Course> taken = new ArrayList();
        taken.add(comp232);
        taken.add(comp248);
        taken.add(engr213);
        taken.add(engr233);
        
        Course[] all = {comp232, comp248, comp249, comp352, comp348, comp335, comp346, 
                        soen287, soen331, soen341, soen321, 
                        encs282, engr213, engr233, engr371, engr391, engr301};
        
        System.out.println("Starting Sequencer");
        System.out.println("----------------------------");
        System.out.println();
        
        ArrayList<Semester> sequence = Sequencer.generateSequence("SOEN", taken, all); 
        
        System.out.println("Generated Sequence. No Errors\n");
        
        System.out.print("Displaying Required Courses\n[ ");
        for (int i = 0; i < all.length; i++)
            System.out.print(all[i].getCourseName() + " ");
        System.out.println("]\n");
        
        System.out.println("Displaying Sequence");
        for (int i = 0 ; i < sequence.size(); i++)
            sequence.get(i).display();
        
        
        System.out.println("\n----------------------------\nEOP.");
    }
    
}
