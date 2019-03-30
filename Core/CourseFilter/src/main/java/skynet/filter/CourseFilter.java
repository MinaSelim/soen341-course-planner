package skynet.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import skynet.scheduler.common.ICourse;

public class CourseFilter
{
	/**
	 * 
	 * Returns a filtered list of ICourse objects that correspond to specified program from an unfiltered list.
	 * @param Program String representing the program for which to filter the list
	 * @param unfilteredCourses List containing all generic ICourse objects to be filtered
	 * @return List<ICourse>
	 */
	public static List<ICourse> FilterListForProgram(List<ICourse> unfilteredCourses, List<String> filter) throws IOException
	{
		//If unfilteredCourses is null, return empty list.
		if(unfilteredCourses == null)
			return new ArrayList<ICourse>();

		
		//The output list of the method
		List<ICourse> filteredCourses = new ArrayList<ICourse>();
		
		//Start Filtering the List
		for(int i = 0;i < unfilteredCourses.size(); ++i)
		{
			if(filter.contains(unfilteredCourses.get(i).getCourseCode()))
				filteredCourses.add(unfilteredCourses.get(i));
		}
	
		return filteredCourses;
	}
	
	/**
	 * 
	 * Returns a List of Strings that reflect the corresponding txt filter.
	 * Mostly used for OR case filtering during API fetching
	 * @param program String representing the appropriate program filter needed
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getFilterForProgram(String program) throws FileNotFoundException
	{
		ArrayList<String> filter = new ArrayList<String>();

		Scanner fileReader = new Scanner(new File("Core/CourseFilter/src/main/java/skynet/filter/"+program+"filter.txt"));

		while(fileReader.hasNextLine()) filter.add(fileReader.nextLine());
		
		fileReader.close();
		
		return filter;
	}
}
