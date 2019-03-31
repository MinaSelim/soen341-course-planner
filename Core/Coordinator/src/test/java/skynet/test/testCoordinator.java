package skynet.test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import skynet.coordinator.Coordinator;
import skynet.filter.CourseFilter;
import skynet.scheduler.common.ICourse;
import skynet.sequencer.Semester;

public class testCoordinator 
{
	private static List<String> required;
	private static List<String> taken;
	
	@Test
	public void testGetSequenceSOEN() throws FileNotFoundException
	{
		/*
		 * This test simply verifies if the output sequence contains the expected
		 * number of scheduled courses.
		 */
		required = CourseFilter.getFilterForProgram("SOEN");
		
		taken = new ArrayList<String>();
		
		List<Semester> generatedSequence = Coordinator.getSequence("SOEN", taken);
		
		int count = 0;
		
		for(Semester i : generatedSequence)
		{
			for(@SuppressWarnings("unused") ICourse j : i.getCoursesScheduled())
			{
				++count;
			}
		}
		assertEquals(required.size(), count);
	}
	
	@Test
	public void testGetSequenceSOENwithTaken() throws FileNotFoundException
	{
		/*
		 * This test verifies if the taken functionality works as expected.
		 * COMP248 and COMP249 are added to taken as arbitrary taken courses.
		 *  The expected number of scheduled courses is required.size() - 2.
		 */
		required = CourseFilter.getFilterForProgram("SOEN");
		
		taken = new ArrayList<String>();
		taken.add("COMP248");
		taken.add("COMP249");
		
		List<Semester> generatedSequence = Coordinator.getSequence("SOEN", taken);
		
		int count = 0;
		
		for(Semester i : generatedSequence)
		{
			for(@SuppressWarnings("unused") ICourse j : i.getCoursesScheduled())
			{
				++count;
			}
		}
		assertEquals(required.size()-2, count);
	}

}
