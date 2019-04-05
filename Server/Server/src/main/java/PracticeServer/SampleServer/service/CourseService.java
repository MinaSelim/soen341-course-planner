package PracticeServer.SampleServer.service;



import java.util.Collection;

import PracticeServer.SampleServer.entity.Course;
import PracticeServer.SampleServer.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
    CourseService class communicates with the CourseDao class in order to access the data.
    So the methods in this class are simply a call to the appropriate Dao method via a CourseDao object
 */

@Service
public class CourseService {

    //instantiate an object of class CourseDao
    @Autowired
    private CourseDao courseDao;

    //Get method that returns all existing object
    public Collection<Course> getAllCourses()
    {
        return this.courseDao.getAllCourses();
    }

    //Get method that returns a course object based on the objects course code
    public Course getCourseByCode(String courseCode)
    {
        return this.courseDao.getCourseByCode(courseCode);
    }

    //Delete method that deletes a course object based off its course code
    public void removeCourseByCode(String courseCode)
    {
        this.courseDao.removeCourseByCode(courseCode);
    }

    //Update method which, given a course objects course code, you can change all of its other properties
    public void updateCourse(Course course)
    {
        this.courseDao.updateCourse(course);
    }

    //Insert method, that allows to add a new course object to the server
	public void insertCourse(Course course)
	{
		this.courseDao.insertCourse(course);
	}

}
