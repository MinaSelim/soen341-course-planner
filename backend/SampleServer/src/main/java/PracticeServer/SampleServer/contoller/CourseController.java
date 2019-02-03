package PracticeServer.SampleServer.contoller;

import PracticeServer.SampleServer.entity.Course;
import PracticeServer.SampleServer.service.CourseService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Collection;


@RestController
@RequestMapping("/courses")
public class CourseController
{
    @Autowired
    private CourseService courseservice;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Course> getAllCourses()
    {
        return courseservice.getAllCourses();
    }

    @RequestMapping(value = "/{courseCode}", method = RequestMethod.GET)
    public Course getCourseByCode(@PathVariable("courseCode") String courseCode)
    {
        return courseservice.getCourseByCode(courseCode);
    }

    @RequestMapping(value = "/{courseCode}", method = RequestMethod.DELETE)
    public void removeCourseByCode(@PathVariable("courseCode") String courseCode)
    {
        courseservice.removeCourseByCode(courseCode);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCourse(@RequestBody Course course)
    {
        courseservice.updateCourse(course);
    }

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertCourse(@RequestBody Course course)
	{
	    courseservice.insertCourse(course);
	}
}
