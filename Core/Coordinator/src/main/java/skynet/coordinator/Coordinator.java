package skynet.coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import services.AttachSeason;
import services.CourseService;
import skynet.filter.CourseFilter;
import skynet.filter.FilterEngAvailabilities;
import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.sequencer.Semester;
import skynet.sequencer.Sequencer;

public class Coordinator 
{	
	private static List<ICourse> fetchedCourses;
	private static List<Course> fetchedTaken;
	private static CourseService service;
	private static List<Semester> sequence;
	
	/* Entry point of the core logic */
	/* Main will return the completed list of Semester objects to the server */
	/* NOTE: I'm using interfaces for now
	/* to simulate the Fetcher API and the prerequisite filler class.*/
	public static void main(String[] args) throws IOException 
	{
		ArrayList<String> taken = new ArrayList<String>();
		for(int i = 1; i < args.length; ++i)
			taken.add(args[i]);
		getSequence(args[0], taken);
	}
	
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
	
	private static void filterPrereqsOutsideOfProgram(List<ICourse> fetchedCourses, List<String> filter)
	{
		for(ICourse course : fetchedCourses)
		{
			List<ICourse> prereqs = Arrays.asList(course.getPrerequisites());
			try {
				prereqs = CourseFilter.FilterListForProgram(prereqs, filter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ICourse[] filteredPrereqs = new ICourse[prereqs.size()];
			prereqs.toArray(filteredPrereqs);
			((Course)course).setPrerequisites(filteredPrereqs);
			
		}
	}
	
	private static void filterCoreqsOutsideOfProgram(List<ICourse> fetchedCourses, List<String> filter)
	{
		for(ICourse course : fetchedCourses)
		{
			List<ICourse> coreqs = Arrays.asList(course.getCorequisites());
			try {
				coreqs = CourseFilter.FilterListForProgram(coreqs, filter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ICourse[] filteredCoreqs = new ICourse[coreqs.size()];
			coreqs.toArray(filteredCoreqs);
			((Course)course).setCorequisites(filteredCoreqs);
			
		}
	}

	static synchronized void addToFetchedCourses(List<ICourse> courses)
	{
		fetchedCourses.addAll(courses);
	}
	
	static synchronized void addToFetchedTaken(ICourse course)
	{
		fetchedTaken.add((Course)course);
	}
	
	static CourseService getCourseService()
	{
		return service;
	}
	
	/*
	 * This is simply a copy paste of the main method of the coordinator.
	 * Can be used by the server to retrieve the sequence directly.
	 * Otherwise, if the user wants to use the sequencer as an application,
	 * he can simply run the program from the coordinator directly.
	 */
	public static List<Semester> getSequence(String program, List<String> takenAsString) throws IOException
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
		
		ArrayList<String> requiredCourses = CourseFilter.getFilterForProgram(program);
		
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
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

		if(takenAsString.size() != 0)
		{
			for(String i : takenAsString)
			{
				addCourse(i.substring(0,4), i.substring(4), fetchedTaken);
			}
		}

		/* The next step is to filter out all the courses that are not required for
		 * specified degree */
		fetchedCourses = CourseFilter.FilterListForProgram(fetchedCourses, requiredCourses);
		
		/* This is a debug tool output.
		 * Outputs every course object in the filteredList as well as its
		 * prerequisites.
		 * */
		for(int i = 0; i < fetchedCourses.size(); ++i)
		{
			System.out.println("Course: "+fetchedCourses.get(i).getCourseCode());
			for(int j = 0; j < fetchedCourses.get(i).getPrerequisites().length; ++j)
				System.out.println(fetchedCourses.get(i).getPrerequisites()[j].getCourseCode());
			System.out.println();
		}
		
		/* TEMPORARY FIX: A list of Taken courses is passed to the sequencer
		 * These courses are pre-UnderGrad requirements */
		List<Course> taken = new ArrayList<Course>();
	
		
		/* Filter out prerequisites that are not part of the program */
		filterPrereqsOutsideOfProgram(fetchedCourses, requiredCourses);
		
		/* Filter out prerequisites that are not part of the program */
		filterCoreqsOutsideOfProgram(fetchedCourses, requiredCourses);
		
		/* Next, Attach the available seasons to each course object in the filtered List
		 */
		AttachSeason.attachSeasons(fetchedCourses, service);
		
		/* Convert List of ICourse to a Compatible List of Course objects */
		List<Course> ConvertedList = new ArrayList<Course>();
		for(ICourse i : fetchedCourses)
			ConvertedList.add((Course)i);
		
 		/* Filter current availabilities to match Engineer Availabilities
 		 * Only applies if requested program sequence is SOEN */
 		if(program.equals("SOEN"))
 			ConvertedList = FilterEngAvailabilities.filterAvailabilitiesForEng(ConvertedList);
		
		// add special courses
		SpecialCoursesHandler.addSpecialCoursesToTheList(ConvertedList, requiredCourses);
		
		SpecialCoursesHandler.handleEncs282Case(ConvertedList);

		/* Finally, Generate a sequence */
		sequence = Sequencer.generateSequence(taken, ConvertedList);
		
		/* Display the Sequence */
        System.out.println("Displaying Sequence");
        for (Semester i : sequence)
        {
        	System.out.println(" Semester : " + i.getSeason());
        	for(ICourse c : i.getCoursesScheduled())
        		System.out.println("\t" + c.getCourseCode() + " (" + c.getCreditUnits() + ")");
        }
		return sequence;
	}
}
