package skynet.coordinator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skynet.scheduler.common.ICourse;

public class CoursesFetcherThread extends Thread{
	
	private String courseCode;
	private ArrayList<String> filter;
	private boolean forTaken;
	
	public CoursesFetcherThread(String courseCode,  ArrayList<String> filter, boolean forTaken) {
		super();
		this.courseCode = courseCode;
		this.filter = filter;
		this.forTaken = forTaken;
	}

	@Override
	public void run() 
	{
		try {
			List<ICourse> courses = Coordinator.getCourseService().getCoursesForProgram(courseCode, filter);
			if(!this.forTaken)
				Coordinator.addToFetchedCourses(courses);
			else
				Coordinator.addToFetchedTaken(courses);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
