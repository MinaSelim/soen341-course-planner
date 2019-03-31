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
			if(!this.forTaken)
			{
				List<ICourse> courses = Coordinator.getCourseService().getCoursesForProgram(courseCode, filter);
				Coordinator.addToFetchedCourses(courses);
			}
			else
			{
				List<String> taken = new ArrayList<String>();
				taken.add(courseCode);
				ICourse takenCourse = Coordinator.getCourseService().getCourse(courseCode.substring(0, 4), courseCode.substring(4));
				Coordinator.addToFetchedTaken(takenCourse);
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
