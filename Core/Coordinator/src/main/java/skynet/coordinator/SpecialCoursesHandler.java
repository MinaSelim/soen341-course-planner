package skynet.coordinator;

import static skynet.scheduler.common.SemesterSeasons.Fall;
import static skynet.scheduler.common.SemesterSeasons.Winter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

public class SpecialCoursesHandler
{
	public static void addSpecialCoursesToTheList(List<Course> courses, List<String> programCourses)
	{
		List<String> specialCourses = getSpecialCoursesWithoutCode(programCourses);
		for(String courseName : specialCourses)
		{
			
			if(courseName.equals("GeneralElective"))
			{
				Course course = new Course("General Education Elective", "SPEC", "GeneralElective", new String[0], new String[0], "General Education Elective", "", 3, null);
				SemesterSeasons[] availability = {Fall, Winter};
				course.setCourseAvailability(Arrays.asList(availability));
				courses.add(course);
			}
			
			if(courseName.equals("BasicScience"))
			{
				Course course = new Course("Basic Science", "SPEC", "GeneralElective", new String[0],  new String[0], "Basic Science", "", 3, null);
				SemesterSeasons[] availability = {Fall, Winter};
				course.setCourseAvailability(Arrays.asList(availability));
				courses.add(course);
			}
			
			if(courseName.equals("ProgramElective"))
			{
				Course course = new Course("Program Elective", "SPEC", "ProgramElective", new String[0], new String[0], "Program Elective", "", 4, null);
				SemesterSeasons[] availability = {Fall, Winter};
			//	attach400LevelPrereqs(course, courses);
				course.setCourseAvailability(Arrays.asList(availability));
				courses.add(course);
			}
			
			if(courseName.equals("Capstone(1)"))
			{
				Course course = new Course("Capstone(1)", "SPEC", "Capstone(1)", new String[0], new String[0], "Capstone(1)", "", 4, null);
				SemesterSeasons[] availability = {Fall};
				course.setCourseAvailability(Arrays.asList(availability));
			//	attachCapstonePrereqs(course, courses);
				courses.add(course);
			}
			
			if(courseName.equals("Capstone(2)"))
			{
				Course course = new Course("Capstone(2)", "SPEC", "Capstone(2)", new String[0], new String[0], "Capstone(2)", "", 4, null);
				SemesterSeasons[] availability = {Winter};
				course.setCourseAvailability(Arrays.asList(availability));
			//	attachCapstonePrereqs(course, courses);
				courses.add(course);
			}
		}
		
		postProcessCourses(courses);
		
	}
	
	private static void postProcessCourses(List<Course> courses)
	{
		for(Course c : courses)
		{
			if(c.getCourseName().contains("Program Elective"))
			{
				attach400LevelPrereqs(c, courses);
			}
			
			if(c.getCourseName().contains("Capstone"))
			{
				attachCapstonePrereqs(c, courses);
			}
		}
	}
	
	private static List<String> getSpecialCoursesWithoutCode( List<String> programCourses)
	{
		List<String> specialCourses = new ArrayList<String>();
		for(String courseCode : programCourses)
		{
			if(courseCode.substring(0, 4).equals("SPEC"))
			{
				specialCourses.add(courseCode.substring(4));
			}
		}
		
		return specialCourses;
	}

	private static void attach400LevelPrereqs(Course course, List<Course> courses) 
	{
		ArrayList<ICourse> prereqs = get200LevelCourses(courses);
		ICourse[] prereqsAsArray = new ICourse[prereqs.size()];
		prereqs.toArray(prereqsAsArray);
		course.setPrerequisites(prereqsAsArray);
	}

	private static void attachCapstonePrereqs(Course course, List<Course> courses)
	{
		ArrayList<ICourse> prereqs =  get200LevelCourses(courses);

		for(Course x: courses)
		{
			if(x.getCourseCode().substring(4).equals("390"))
			{
				prereqs.add(x);
			}
			
			if(course.getCourseCode().equals("Capstone(2)") && x.getCourseCode().equals("Capstone(1)"))
			{
				prereqs.add(x);
			}
		}
		
		ICourse[] prereqsAsArray = new ICourse[prereqs.size()];
		prereqs.toArray(prereqsAsArray);
		course.setPrerequisites(prereqsAsArray);
		
	}
	
	private static ArrayList<ICourse> get200LevelCourses(List<Course> courses)
	{
		ArrayList<ICourse> prereqs = new ArrayList<ICourse>();
		for(Course x : courses)
		{
			if(x.getCourseCode().charAt(4) == '2' || x.getCourseName().contains("Basic") || x.getCourseName().contains("General"))
			{
				prereqs.add(x);
			}
		}
		
		
		return prereqs;
	}
	
	public static void handleEncs282Case(List<Course> courses)
    {
        Course encs282  =getCourseFromListByID(courses, "ENCS282");
        if(encs282 != null)
        {
            Course encs271  =getCourseFromListByID(courses, "ENCS271");
            if(encs271 == null)
            {
                Course engr = getCourseFromListByID(courses, "ENGR213");
                ICourse[] prereqs = new ICourse[1];
                prereqs[0] = engr;
                encs282.setPrerequisites(prereqs);
            }

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
