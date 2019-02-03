package PracticeServer.SampleServer.dao;

import PracticeServer.SampleServer.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseDao {

    private static Map<String, Course> courses;

    static
    {
        courses = new HashMap<String, Course>()
        {
            {
                put("COMP-248",new Course("Object-Oriented Programming","COMP-248", 3.5));
                put("COMP-249",new Course("Object-Oriented Programming II","COMP-249", 3.5));
                put("SOEN-228",new Course("System Hardware","SOEN-228", 4));
            }
        };
    }

    public Collection<Course> getAllCourses()
    {
        return this.courses.values();
    }

    public Course getCourseByCode(String courseCode)
    {
        return this.courses.get(courseCode);
    }

    public void removeCourseByCode(String courseCode)
    {
        this.courses.remove(courseCode);
    }

    public void updateCourse (Course course)
    {
        Course c = courses.get(course.getCourseCode());
        c.setCourseName(course.getCourseName());
        c.setCredits(course.getCredits());
        courses.put(course.getCourseCode(), course);
    }

	public void insertCourse(Course course)
	{
		this.courses.put(course.getCourseCode(), course);
	}
}
