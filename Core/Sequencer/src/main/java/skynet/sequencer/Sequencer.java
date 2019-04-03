package skynet.sequencer;

import java.util.ArrayList;
import java.util.List;

import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

// TODO add credit requirement for certain courses like (278)
// TODO remove summer feature 
public class Sequencer 	{

    private static void setRequisitePriority(Course course, List<Course> required) 
    {
    	// Get the combined list of prerequisites and corequisites 
    	ArrayList<Course> requisites = new ArrayList<>();
    	
    	for (ICourse ic : course.getPrerequisites())
    		requisites.add((Course) ic);
    	for (ICourse ic : course.getCorequisites())
    		requisites.add((Course) ic);
    		
    	
    	// Set the priority 
    	// Increment the priority of each requisite course which is required (not taken) 
    	// and then recursively call setPriority on the requisite course. 
    	for (Course requisiteCourse : requisites)
    	{
    		for (Course requiredCourse : required)
    			if (requisiteCourse.getCourseCode().equals(requiredCourse.getCourseCode())) 
    				requiredCourse.incrementPriority(); // WHY?!? why requisite.inc	
    			
    		setRequisitePriority(requisiteCourse, required);
    		
    	}
    }
    
    private static void adjustPriorityForAvailability(List<Course> required)
    {
    	for(Course c : required)
    	{
    		c.setPriority(c.getPriority()*2);
    		if(c.getCourseAvailability().length < 2) //2 is number of seasons currently allowed
    		{
    			c.incrementPriority();
    		}
    	}
    }
    
    
    private static void updateAvailability_refactored(List<Course> taken, List<Course> required, List<Course> available) 
    {
    	// Two wave approach 
    	
    	// First wave: All courses whose prereqs and coreqs are met 
    	ArrayList<Course> toRemove = new ArrayList<>();
    	ArrayList<Course> toAdd = new ArrayList<>();
    	
    	ArrayList<Course> hasUnmetCoreqs = new ArrayList<>();

    	for (Course course : required) 
    	{
    		boolean prerequisitesMet = checkRequisites(course.getPrerequisites(), taken);
    		boolean corequisitesMet = checkRequisites(course.getCorequisites(), taken);
    		
    		
    		if (prerequisitesMet && corequisitesMet)
    		{
    			toRemove.add(course);
    			toAdd.add(course);
    		}
    		else if (prerequisitesMet && !corequisitesMet) 
    		{
    			hasUnmetCoreqs.add(course); 
    		}
    	}
    	
    	// Avoids concurrent modification error of adding in the if
		for (Course c : toRemove)
			required.remove(c);
    	
		for (Course c : toAdd)
			available.add(c);
		
		toRemove.clear();
		toAdd.clear();
		
		// Second wave All courses with coreqs which might have been added in the last wave 
		for (Course coreq : hasUnmetCoreqs)
		{
			boolean corequisitesMet = checkRequisites(coreq.getCorequisites(), available);
			
			if (corequisitesMet)
			{
				toRemove.add(coreq);
				toAdd.add(coreq);
			}
		}
		
		for (Course c : toRemove)
			required.remove(c);
    	
		for (Course c : toAdd)
			available.add(c);
		
    	if (available.size() > 0)
    		sortAvailable(available);
    }
    
	private static boolean checkRequisites(ICourse[] requisites, List<Course> target)
    {
    	for (ICourse requisite : requisites)
    		if (!contains((Course) requisite, target))
    			return false;
    	
    	return true;
    }
	
	private static boolean contains(Course course, List<Course> target)
    {
    	for(Course c: target)
			if(course.getCourseCode().equals(c.getCourseCode()))
				return true;
    	
    	return false;
    }

    
//    private static boolean checkRequisites(ICourse[] requisites, List<Course> target)
//    {
//    	ArrayList<String> reqStrings = new ArrayList<String>();
//    	ArrayList<String> takenStrings = new ArrayList<String>();
//    	
//    	for(ICourse c : requisites)
//    		reqStrings.add(c.getCourseCode());
//    	
//    	for(Course c: target)
//    		takenStrings.add(c.getCourseCode());
//    	
//    	for(String req : reqStrings)
//    		if(!takenStrings.contains(req))
//    			return false;
//    	
//    	return true;
//    }
    
    private static void sortAvailable(List<Course> available) 
    {
        int highestIndex; 

        for (int i = 0; i < available.size()-1; i++) 
        {
            highestIndex = i;
            for (int j = i+1; j < available.size(); j++) 
            {
                if (available.get(j).getPriority() > available.get(highestIndex).getPriority())
                    highestIndex = j;
            }
            
            Course temp = available.get(i);
            available.set(i, available.get(highestIndex));
            available.set(highestIndex, temp);
        }
    }
    
    public static List<Semester> generateSequence(List<Course> taken, List<Course> required) 
    {
    	/* Step 1: Verify if any courses in taken list correspond to required.
    	 * If True, remove the corresponding course from required list.
    	 */
        if (taken.size() > 0) 
        {
        	for (int i = 0; i < required.size(); i++) 
        	{
        		for (int j = 0; j < taken.size(); j++) 
        		{
        			if (taken.get(j).getCourseCode().equals(required.get(i).getCourseCode()))
        				required.remove(i--);
        		}
        	}
        }
    	
    	// Refactored Step 1 - untested 
//    	 if (taken.size() > 0)
//    		 for (Course requiredCourse : required)
//    			 for (Course takenCourse : taken)
//    				 if (takenCourse.getCourseCode().equals(requiredCourse.getCourseCode()))
//    					 required.remove(requiredCourse);
    	 
        /* Step 2: Set the level of priority to each course within the required list.
         * See setPriority() method for more details.
         */
  
        
        // Refactored Step 2 - untested
         for (Course requiredCourse : required)
        	setRequisitePriority(requiredCourse, required);
         
         adjustPriorityForAvailability(required);
         
 
         System.out.println("Displaying priorities");
         
         for (Course requiredCourse : required)
        	 System.out.println(requiredCourse.getCourseCode() + ": " +requiredCourse.getPriority());
         
         
        /* Step 3: Generate the sequence.
         * This is achieved by iterating over the required list of courses until it is depleted.
         * The available list contains courses from the required list that currently have all
         * of their prerequisites satisfied and can be added to a semester's courses[]. 
         */ 
        int year = 2019;
        int season = 0; 
        double creditCap = 14.9; 
        
        List<Semester> sequence = new ArrayList<Semester>();
        List<Course> available = new ArrayList<Course>();
        
        //updateAvailability(taken, required, available);
        
     
        
        while (!required.isEmpty() || !available.isEmpty()) 
        {   
            //updateAvailability(taken, required, available);
         	updateAvailability_refactored(taken, required, available);
            
            double totalCredits = 0.0;
            List<Course> registeredCourses = new ArrayList<Course>(); 
            
			// Using season enum
            SemesterSeasons currentSeason = getCurrentSeason(season++);
            
            if (season % 2 == 0)
            	year++; 
			
			for (Course course : available)
			{
				// Check Semester Availablility 
				boolean courseOffered = checkAvailablity(course, currentSeason);
				
				if (courseOffered)
				{
					if (course.hasCoreqs()) // the course is offered and has coreqs
					{
					
						// Check the status of the coreqs 
						boolean coreqsMet = true;

						ICourse[] corequisites = course.getCorequisites();
						ArrayList<ICourse> unmetCorequisites = new ArrayList<>(); 
						
						for (int index = 0; index < corequisites.length; index++) 
						{
							ICourse corequisite = corequisites[index];
							
							//if (!taken.contains(corequisite) && !registeredCourses.contains(corequisite)) 
							if (!contains((Course) corequisite, taken) && !contains((Course) corequisite, registeredCourses)) 
							{
								coreqsMet = false;
								unmetCorequisites.add(corequisite);
							}
						}
						
						if (coreqsMet) // the coreqs are met 
						{
//							if (course.getCreditUnits() + totalCredits <= creditCap)
//							{
								registeredCourses.add(course);
								totalCredits += course.getCreditUnits();
							//}
						}
						else // some coreqs are not met (still in available) 
						{
							// See if we can add all untaken coreqs at the same time
							double sumOfCredits = totalCredits + course.getCreditUnits();
							boolean coreqsAvailable = true; 
							for (ICourse corequisite : unmetCorequisites) 
							{
								sumOfCredits += corequisite.getCreditUnits();
								coreqsAvailable &= checkAvailablity((Course)corequisite, currentSeason);
							}
							
							// All coreqs can be added 
							if (coreqsAvailable && sumOfCredits <= creditCap)
							{
								for (ICourse corequisite : unmetCorequisites) // changed for coreqs HOPE!!!
									registeredCourses.add((Course) corequisite); 
								
								totalCredits = sumOfCredits;
							}	
						}
					}
					else // the course is offered and there are no coreqs 
					{
//						if (course.getCreditUnits() + totalCredits <= creditCap)
//						{
							registeredCourses.add(course);
							totalCredits += course.getCreditUnits();
						//}
					}
				}
				
				if (totalCredits >= creditCap)
					break;
			}
			
			// Create current semester 
			Semester currentSemester = new Semester(currentSeason, year, registeredCourses);
            sequence.add(currentSemester); 
            
			// remove registered courses from required and add them to taken  
			for (Course rc : registeredCourses) 
			{
				taken.add(rc);
				available.remove(rc);
			}
			
			registeredCourses = new ArrayList<Course>();
			Course bcee = getCourseFromListByID(available, "BCEE371");
			
			if(bcee != null && currentSeason == SemesterSeasons.Winter)
			{
				List<Course> register = new ArrayList<Course>();
				register.add(bcee);
				taken.add(bcee);
				available.remove(bcee);
				SemesterSeasons summer = SemesterSeasons.Summer;
				Semester sem = new Semester(summer, year, register);
				sequence.add(sem);
			}
			
			//registeredCourses.clear(); 
        }
        
        return sequence;
    } 
    


	private static boolean checkAvailablity(Course course, SemesterSeasons currentSeason)
    {
    	SemesterSeasons[] availableSeasons = course.getCourseAvailability(); 
    	
		for (SemesterSeasons season : availableSeasons) 
			if (currentSeason == season)
				return true;
		
		return false;
    }
    
    private static SemesterSeasons getCurrentSeason(int semesterCount)
    {
    	switch ((semesterCount) % 2) {
		case (0):
			return SemesterSeasons.Fall;
		case (1):
			return SemesterSeasons.Winter;
//		case (2):
//			return SemesterSeasons.Summer;
		default:
			System.out.println("Error in switch");
			return null;
		}
    }
    
    private static Course getCourseFromListByID(List<Course> courses, String id)
    {
    	for(Course c : courses)
    	{
    		if(c.getCourseCode().equals(id))
    		{
    			return c;
    		}
    	}
    	return null;
    }
    
}

