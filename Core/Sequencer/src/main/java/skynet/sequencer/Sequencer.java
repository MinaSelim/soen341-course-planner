package skynet.sequencer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import skynet.scheduler.common.Course;

public class Sequencer 
{

    public static void setPriority(Course course, List<Course> required) 
    {
    	Course[] prereqs = new Course[course.getPrerequisites().length];
    	for(int i = 0; i < prereqs.length ; ++i)
    		prereqs[i] = (Course) course.getPrerequisites()[i];
       
        for (int i = 0; i < prereqs.length; i++) 
        {
            Course currentPrereq = prereqs[i]; 
   
            for (int j = 0; j < required.size(); j++) 
            {
                if (currentPrereq.getCourseCode().equals(required.get(j).getCourseCode()))
                    required.get(j).incrementPriority();
            }
            setPriority(currentPrereq, required); 
        }   
    }
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateAvailability(List<Course> taken, List<Course> required, List<Course> available) 
    {
        for (int i = 0; i < required.size(); i++) 
        {
            Course currentCourse = required.get(i);
            List<Course> currentPrereqs = new ArrayList(Arrays.asList(currentCourse.getPrerequisites()));
            
            if (currentCourse.getPrerequisites().length == 0) 
            {
                available.add(currentCourse);
                required.remove(i--); 
            } 
            else 
            {
            	int preReqsDone = 0;
            	for(int j = 0; j < currentPrereqs.size(); ++j)
            	{
            		for(int k = 0; k < taken.size(); ++k)
            		{
            			if(currentPrereqs.get(j).getCourseCode().equals(taken.get(k).getCourseCode()))
            			{
            				++preReqsDone;
            			}
            		}
            	}
                if (preReqsDone == currentPrereqs.size()) 
                {
                    available.add(currentCourse);
                    required.remove(i--); 
                }
            }
        }
        
        if (available.size() > 0)
            sortAvailable(available); 
    }
    
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
        				required.remove(i);
        		}
        	}
        }

        /* Step 2: Set the level of priority to each course within the required list.
         * See setPriority() method for more details.
         */
        for (int i = 0; i < required.size(); i++)
            setPriority(required.get(i), required); 
      
        /* Step 3: Generate the sequence.
         * This is achieved by iterating over the required list of courses until it is depleted.
         * The available list contains courses from the required list that currently have all
         * of their prerequisites satisfied and can be added to a semester's courses[]. 
         */ 
        int year = 2019;
        int season = 0; 
        int creditCap = 15; 
        
        List<Semester> sequence = new ArrayList<Semester>();
        List<Course> available = new ArrayList<Course>();
        
        while (!required.isEmpty() || !available.isEmpty()) 
        {   

        	//See updateAvailability() method for more details.
            updateAvailability(taken, required, available);
            
            int totalCredits = 0;
            
            List<Course> registeredCourses = new ArrayList<Course>(); 
            
            for (int i = 0; i < available.size(); i++) 
            {
                Course highestAvailable = available.get(i);
                
                registeredCourses.add(highestAvailable);
                taken.add(highestAvailable);
                totalCredits += highestAvailable.getCreditUnits();
                available.remove(i--);

                if (totalCredits >= creditCap)
                    break;
            }
            
            String currentSeason = ""; 
            
            switch ((season++) % 3) 
            {
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
            sequence.add(currentSemester); 
        }
        
        return sequence;
    } 
    
}
