package skynet.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		//Open the filter.txt file
		BufferedReader fileReader = new BufferedReader(new FileReader("../CourseFilter/src/main/java/skynet/filter/filter.txt"));
		
		//Set BufferedReader pointer to proper line in the filter.txt file
		while(!fileReader.readLine().equals(Program));
		
		//Mark the BufferedReader pointer index while allowing at least 200 chars to be read and keeping the mark
		fileReader.mark(200);
		
		//Start Filtering the List
		for(int i = 0;i < unfilteredCourses.size(); ++i)
		{
			String currentLine = fileReader.readLine();
			boolean eof = false;
			while(!eof)
			{
				if(currentLine.equals(unfilteredCourses.get(i).getCourseCode())
						|| currentLine.equals("-"))
					eof = true;
				else
					currentLine = fileReader.readLine();
			}
			
			if(!currentLine.equals("-"))
				filteredCourses.add(unfilteredCourses.get(i));
			
			fileReader.reset();	
		}
		
		fileReader.close();
		return filteredCourses;
	}
}
