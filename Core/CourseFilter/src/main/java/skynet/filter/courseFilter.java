package skynet.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import skynet.scheduler.common.ICourse;

public class courseFilter
{
	public static List<ICourse> FilterListForProgram(String Program, List<ICourse> unfilteredCourses) throws IOException
	{
		//Safety check to avoid iterating through a null list
		if(unfilteredCourses == null)
			return new ArrayList<ICourse>();
		
		//The output list of the method
		List<ICourse> filteredCourses = new ArrayList<ICourse>();
		
		//The local String buffer to avoid reading from a file for every line check
		List<String> filter = new ArrayList<String>();
		
		//Open the filter.txt file
		Scanner fileReader = new Scanner(new File("../CourseFilter/src/main/java/skynet/filter/"+Program+"filter.txt"));
		
		//Populate the local String filter buffer
		while(fileReader.hasNextLine()) filter.add(fileReader.nextLine());
		
		//Once done, close the scanner
		fileReader.close();
		
		//Start Filtering the List
		for(int i = 0;i < unfilteredCourses.size(); ++i)
		{
			if(filter.contains(unfilteredCourses.get(i).getCourseCode()))
				filteredCourses.add(unfilteredCourses.get(i));
		}
	
		return filteredCourses;
	}
}
