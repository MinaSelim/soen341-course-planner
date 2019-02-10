package skynet.coordinator;

import java.util.ArrayList;
import java.util.List;

import skynet.filter.courseFilter;
import skynet.scheduler.common.*;

public class Coordinator 
{	
	private static List<ICourse> fetchedCourses;
	
	/* Entry point of the core logic */
	/* Main will return the completed list of Semester objects to the server */
	/* NOTE: the extra parameters to the main are simply place holders so i can
	/* implement a logical sequence of statements. I'm using interfaces for now
	/* to simulate the Fetcher API and the prerequisite filler class.*/
	public static List<ISemester> main(String[] args,
			ICourseService service,
			IPrereqFiller filler,
			ISequencer sequencer) 
	{
		/* Instantiate the fetchedCourses list */
		fetchedCourses = new ArrayList<ICourse>();
		
		/* When the server calls our application, it passes with it an argument 
		 * which indicates for which program we want a sequence 
		 * E.g. : >sequencer SOEN 
		 * Obviously, this is very bare bones for now, later on we can add more arguments
		 * for information on already completed courses which the user has specified
		 * on the front end.*/
		switch(args[0])
		{
		case "SOEN":
			fetchedCourses = service.getCourseForProgram("SOEN");
			fetchedCourses.addAll(fetchedCourses.size(), service.getCourseForProgram("ENGR"));
			fetchedCourses.addAll(fetchedCourses.size(), service.getCourseForProgram("ENCS"));
			break;
		case "COMP":
			fetchedCourses = service.getCourseForProgram("COMP");
			fetchedCourses.addAll(fetchedCourses.size(), service.getCourseForProgram("ENCS"));
			break;
		default:
			return new ArrayList<ISemester>();
		}
		
		/* Once the courses fetched, call the populatePrereq() method to
		 * fill the prereq course arrays within each ICourse object. */
		fetchedCourses = filler.populatePrereq(fetchedCourses);
		
		/* The next step is to filter out all the courses that are not required for
		 * specified degree */
		fetchedCourses = courseFilter.FilterListForProgram(args[0], fetchedCourses);
		
		/* Finally, the list of courses is passed to the sequencer which returns 
		 * a compatible list of semester objects */	
		return sequencer.generateSequence(fetchedCourses);
	}
}
