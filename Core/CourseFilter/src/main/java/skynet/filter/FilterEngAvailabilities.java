package skynet.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import skynet.scheduler.common.Course;
import skynet.scheduler.common.SemesterSeasons;

public class FilterEngAvailabilities 
{
	
	/**
	 * Method that returns a list of string representing the contents of EngAvailabilitiesSOEN.txt.
	 * @param program
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<String> getEngFilter(String program) throws FileNotFoundException
	{
		ArrayList<String> filter = new ArrayList<String>();

		//Scanner fileReader = new Scanner(new File("../CourseFilter/src/main/java/skynet/filter/EngAvailabilitiesSOEN.txt"));
		Scanner fileReader = new Scanner(new File("../../Core/CourseFilter/src/main/java/skynet/filter/EngAvailabilitiesSOEN.txt"));

		while(fileReader.hasNextLine()) filter.add(fileReader.nextLine());
		
		fileReader.close();
		
		return filter;
	}
	
	
	/**
	 * This method updates each course object's availabilities to match the ones specified in
	 * EngAvailabilitiesSOEN.txt. Only applies to a small subset of SOEN program courses.
	 * Does not do anything if list of courses pertains to a different program.
	 * 
	 * @param courses List of courses to update availabilities
	 * @return updated List.
	 * @throws FileNotFoundException
	 */
	public static List<Course> filterAvailabilitiesForEng(List<Course> courses) throws FileNotFoundException
	{
		ArrayList<String> filter = new ArrayList<String>();

		//Scanner fileReader = new Scanner(new File("../CourseFilter/src/main/java/skynet/filter/EngAvailabilitiesSOEN.txt"));
		Scanner fileReader = new Scanner(new File("../../Core/CourseFilter/src/main/java/skynet/filter/EngAvailabilitiesSOEN.txt"));

		while(fileReader.hasNextLine()) filter.add(fileReader.nextLine());
		
		fileReader.close();
		
		for(int i = 0 ; i < courses.size(); ++i)
		{
			for(String j : filter)
			{
				String courseCode = j.substring(0,7);
				j = j.replace(j.substring(0,8), "");
				if(courseCode.equals(courses.get(i).getCourseCode()))
				{
					List<SemesterSeasons> EngAvail = new ArrayList<SemesterSeasons>();
					List<String> tokens = new ArrayList<String>();
					while(j.indexOf(" ") != -1)
					{
						tokens.add(j.substring(0,j.indexOf(" ")));
						j = j.replace(j.substring(0,j.indexOf(" ")+1), "");
					}
					tokens.add(j);
					
					for(String k : tokens)
					{
						if(k.equals("Fall"))
							EngAvail.add(SemesterSeasons.Fall);
						else if(k.equals("Summer"))
							EngAvail.add(SemesterSeasons.Summer);
						else if(k.equals("Winter"))
							EngAvail.add(SemesterSeasons.Winter);
					}
					
					courses.get(i).setCourseAvailability(EngAvail);
				}
			}
		}
		
		return courses;
	}
	
}
