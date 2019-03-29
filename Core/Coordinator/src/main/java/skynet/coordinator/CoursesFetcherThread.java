package skynet.coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skynet.scheduler.common.ICourse;

public class CoursesFetcherThread extends Thread{
	
	private String courseCode;
	private ArrayList<String> filter;
	
	public CoursesFetcherThread(String courseCode,  ArrayList<String> filter) {
		super();
		this.courseCode = courseCode;
		this.filter = filter;
	}

	@Override
	public void run() 
	{
		try {
			List<ICourse> courses = Coordinator.getCourseService().getCoursesForProgram(courseCode, filter);
			Coordinator.addToFetchedCourses(courses);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
