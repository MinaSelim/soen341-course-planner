//package PracticeServer.SampleServer.contoller;
//
//import PracticeServer.SampleServer.entity.Course;
//import PracticeServer.SampleServer.service.CourseService;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.util.Collection;
//
///*
//    In order to have access to the data the CourseController class communicates with the CourseService class via a
//    CourseService object which in turns communicates to the CourseDao which will allow access to the data by
//    invoking the methods in the Dao Class.
//
//    Therefore the controller class strictly makes a call to the given method in the CourseService class.
// */
//@RestController
//@RequestMapping("/courses")
//public class CourseController
//{
//
//    //Instantiates a an object of class CourseService
//    @Autowired
//    private CourseService courseService;
//
//    //Get method that returns all existing object
//    @RequestMapping(method = RequestMethod.GET)
//    public Collection<Course> getAllCourses()
//    {
//        return courseService.getAllCourses();
//    }
//
//    //Get method that returns a course object based on the objects course code
//    @RequestMapping(value = "/{courseCode}", method = RequestMethod.GET)
//    public Course getCourseByCode(@PathVariable("courseCode") String courseCode)
//    {
//        return courseService.getCourseByCode(courseCode);
//    }
//
//    //Delete method that deletes a course object based off its course code
//    @RequestMapping(value = "/{courseCode}", method = RequestMethod.DELETE)
//    public void removeCourseByCode(@PathVariable("courseCode") String courseCode)
//    {
//        courseService.removeCourseByCode(courseCode);
//    }
//
//    //Update method which, given a course objects course code, you can change all of its other properties, ONLY takes in object of type JSON
//    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateCourse(@RequestBody Course course)
//    {
//        courseService.updateCourse(course);
//    }
//
//    //Insert method, that allows to add a new course object to the server, ONLY takes in objects of type JSON
//	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public void insertCourse(@RequestBody String[] course)
//	{
//	    courseService.insertCourse(course);
//	}
//}