import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import skynet.coordinator.Coordinator;
import skynet.scheduler.common.ICourse;
import skynet.sequencer.Semester;

public class Driver 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args.length == 0)
		{
			System.out.println("You must pass one file path");
			System.exit(1);
		}
		
		Scanner scan = new Scanner(new File(args[0]));;
		
		
		ArrayList<String> coursesInProgram = new ArrayList<String>();
		
		ArrayList<String> takenCourses = new ArrayList<String>();
		
		while(scan.hasNext()) coursesInProgram.add(scan.nextLine());
		
		if(args.length > 1)
		{
			scan.close();
			scan = new Scanner(new File(args[1]));
			while(scan.hasNext()) takenCourses.add(scan.nextLine());
		}
		
		scan.close();
		List<Semester> sequence = Coordinator.getSequence(coursesInProgram, takenCourses);
		
		System.out.println("Displaying Sequence");
        for (Semester i : sequence)
        {
        	System.out.println(" Semester : " + i.getSeason());
        	for(ICourse c : i.getCoursesScheduled())
        		System.out.println("\t" + c.getCourseCode() + " (" + c.getCreditUnits() + ")");
        }
		
	}

}
