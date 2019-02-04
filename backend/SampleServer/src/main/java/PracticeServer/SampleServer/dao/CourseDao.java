package PracticeServer.SampleServer.dao;

import PracticeServer.SampleServer.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
    Based on the request from the controller this Dao class will execute the
    corresponding method to access the data and return whatever information is required
 */
@Repository
public class CourseDao {

    /*
        Creating a list of 3 course object with the properties provided bellow,
        each of the objects respective CourseCode acts as its key within the list
     */
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

    //Get method that returns all existing object
    public Collection<Course> getAllCourses()
    {
        return this.courses.values();
    }

    //Get method that returns a course object based on the objects course code
    public Course getCourseByCode(String courseCode)
    {
        return this.courses.get(courseCode);
    }

    //Delete method that deletes a course object based off its course code
    public void removeCourseByCode(String courseCode)
    {
        this.courses.remove(courseCode);
    }

    //Update method which, given a course objects course code, you can change all of its other properties
    public void updateCourse (Course course)
    {
        Course c = courses.get(course.getCourseCode());
        c.setCourseName(course.getCourseName());
        c.setCredits(course.getCredits());
        courses.put(course.getCourseCode(), course);
    }

    //Insert method, that allows to add a new course object to the server
	public void insertCourse(Course course)
	{
		this.courses.put(course.getCourseCode(), course);
	}
}
