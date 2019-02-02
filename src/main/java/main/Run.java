package main;

import java.io.IOException;
import java.util.List;

import fetcher.services.CourseService;
import skynet.scheduler.common.ICourse;

public class Run 
{
	public static void main(String args[]) throws IOException
	{
		CourseService service = new CourseService("157","ffdb710b1f5157c2bf406c42bfbb8bec");
		List<ICourse> courses = service.getCourseForProgram("SOEN");
		
		for(int i = 0;i<courses.size();++i)
		{
			System.out.println(courses.get(i).getCourseName());
		}
	}
}
