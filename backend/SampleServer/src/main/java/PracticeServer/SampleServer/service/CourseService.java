package PracticeServer.SampleServer.service;



import java.util.Collection;

import PracticeServer.SampleServer.entity.Course;
import PracticeServer.SampleServer.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public Collection<Course> getAllCourses()
    {
        return this.courseDao.getAllCourses();
    }

    public Course getCourseByCode(String courseCode)
    {
        return this.courseDao.getCourseByCode(courseCode);
    }

    public void removeCourseByCode(String courseCode)
    {
        this.courseDao.removeCourseByCode(courseCode);
    }

    public void updateCourse(Course course)
    {
        this.courseDao.updateCourse(course);
    }

	public void insertCourse(Course course)
	{
		this.courseDao.insertCourse(course);
	}

}
