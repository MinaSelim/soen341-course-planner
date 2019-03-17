package skynet.coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import services.AttachSeason;
import services.CourseService;
import skynet.filter.CourseFilter;
import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.sequencer.Semester;
import skynet.sequencer.Sequencer;

public class Coordinator 
{	
	private static List<ICourse> fetchedCourses;
	private static CourseService service;
	private static List<Semester> sequence;
	
	/* Entry point of the core logic */
	/* Main will return the completed list of Semester objects to the server */
	/* NOTE: I'm using interfaces for now
	/* to simulate the Fetcher API and the prerequisite filler class.*/
	public static void main(String[] args) throws IOException 
	{
		/* Instantiate the class objects 
		 * Obviously those will not be null in the final,
		 * simply could not figure out a better way to design the code
		 * without having access to the other modules */
		fetchedCourses = new ArrayList<ICourse>();

		service = new CourseService("132","6a388ea97bb3d994c699760a7ee01472");
		
		/* When the server calls our application, it passes with it an argument 
		 * which indicates for which program we want a sequence 
		 * E.g. : >sequencer SOEN 
		 * Obviously, this is very bare bones for now, later on we can add more arguments
		 * for information on already completed courses which the user has specified
		 * on the front end.*/

		ArrayList<String> requiredCourses = CourseFilter.getFilterForProgram(args[0]);

		ArrayList<String> requiredCourseCodesForQuery = getQueryCourseCodes(requiredCourses);

		ArrayList<CoursesFetcherThread> fetchers = new ArrayList<CoursesFetcherThread>();

		for(String courseCode : requiredCourseCodesForQuery)
		{
			CoursesFetcherThread fetcher = new CoursesFetcherThread(courseCode, requiredCourses);
			fetcher.start();
			fetchers.add(fetcher);
		}

		for(CoursesFetcherThread t1 : fetchers)
		{
			try {
				t1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		/* The next step is to filter out all the courses that are not required for
		 * specified degree */
		fetchedCourses = CourseFilter.FilterListForProgram(fetchedCourses, requiredCourses);
		
		/* Next, Attach the available seasons to each course object in the filtered List
		 */
		AttachSeason.attachSeasons(fetchedCourses, service);
		
		/* This is a debug tool output.
		 * Outputs every course object in the filteredList as well as its
		 * prerequisites.
		 * */
		for(int i = 0; i < fetchedCourses.size(); ++i)
		{
			System.out.println("Course: "+fetchedCourses.get(i).getCourseCode());
			for(int j = 0; j < fetchedCourses.get(i).getPrerequisites().length; ++j)
				System.out.println(fetchedCourses.get(i).getPrerequisitesAsCourseCodes()[j]);
			System.out.println();
		}
		
		/* TEMPORARY FIX: A list of Taken courses is passed to the sequencer
		 * These courses are pre-UnderGrad requirements */
		List<Course> taken = new ArrayList<Course>();
		addCourse("MATH", "202", taken); 
		addCourse("MATH", "203", taken);
		addCourse("MATH", "205", taken);
		addCourse("MATH", "204", taken);
		addCourse("PHYS", "205", taken);
		
		/* TEMPORARY FIX: These added course are specifically problematic.
		 * Without them, the program loops in the sequencer loop forever.
		 * ENCS272 is needed since ENCS 282 requires it.
		 * Need to find a way to also implement the EWT requirement */
		addCourse("ENCS", "272", taken);
		
		/* Convert List of ICourse to a Compatible List of Course objects */
		List<Course> ConvertedList = new ArrayList<Course>();
		for(ICourse i : fetchedCourses)
			ConvertedList.add((Course)i);
		
		// add special courses
		SpecialCoursesHandler.addSpecialCoursesToTheList(ConvertedList, requiredCourses);

		/* Finally, Generate a sequence */
		sequence = Sequencer.generateSequence(taken, ConvertedList);
		
		/* Display the Sequence */
        System.out.println("Displaying Sequence");
        for (Semester i : sequence)
        {
        	System.out.println(" Semester : " + i.getSeason());
        	for(ICourse c : i.getCoursesScheduled())
        		System.out.println("\t" + c.getCourseCode());
        }
		return;
	}
	
	/* Temporary debug method */
	private static void addCourse(String program, String code, List<Course> course)
	{
		Course c = new Course(); 
		c.setCourseCode(program + " " + code);		
		course.add(c);
	}

	private static ArrayList<String> getQueryCourseCodes(List<String> requiredCourses)
	{
		ArrayList<String> courseCodes = new ArrayList<String>();
		for(String courseCode : requiredCourses)
		{
			if(!(courseCodes.contains(courseCode.substring(0, 4))) &&
			   !(courseCode.substring(0,4).equals("SPEC")))
			{
				courseCodes.add(courseCode.substring(0, 4));
			}
		}

		return courseCodes;
	}

	static synchronized void addToFetchedCourses(List<ICourse> courses)
	{
		fetchedCourses.addAll(courses);
	}

	static CourseService getCourseService()
	{
		return service;
	}
}
