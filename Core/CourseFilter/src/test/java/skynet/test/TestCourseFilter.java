package skynet.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import skynet.filter.CourseFilter;
import skynet.scheduler.common.*;

public class TestCourseFilter {

	private List<ICourse> testInputList;
	private int checkValue;

	
	
	@Before
	public void setup()
	{
		this.testInputList = new ArrayList<ICourse>();
	}
	
	@Test
	public void testSOEN() throws IOException 
	{
		this.checkValue = 5;
		/* This test constructs a list of 7 arbitrary courses
		 * and verifies if the expected number of courses in the output list
		 * equals to what would be expected if program specified is SOEN */
		testInputList.add(new Course("","ENGR233",0));
		testInputList.add(new Course("","ENGR202",0));
		testInputList.add(new Course("","SOEN341",0));
		testInputList.add(new Course("","COMP232",0));
		testInputList.add(new Course("","ENGR201",0));
		testInputList.add(new Course("","ELEC311",0));
		/* The expected number of courses in the output list in this case is 5
		 * since ELEC and COMP courses are not part of SOEN core */
		assertEquals(checkValue,
				CourseFilter.FilterListForProgram("SOEN", testInputList, null).size());
	}
	
	@Test
	public void testCOMP() throws IOException
	{
		this.checkValue = 1;
		/* This test constructs a list of 7 arbitrary courses
		 * and verifies if the expected number of courses in the output list
		 * equals to what would be expected if program specified is COMP */
		testInputList.add(new Course("","ENGR233",0));
		testInputList.add(new Course("","ENGR202",0));
		testInputList.add(new Course("","SOEN341",0));
		testInputList.add(new Course("","COMP232",0));
		testInputList.add(new Course("","ENGR201",0));
		testInputList.add(new Course("","ELEC311",0));
		/* The expected number of courses in the output list in this case is 1
		 * since ELEC and ENGR courses are not part of COMP core */
		assertEquals(checkValue,
				CourseFilter.FilterListForProgram("COMP", testInputList, null).size());
	}

	@Test
	public void testSOENNull() throws IOException
	{
		/* This test performs a check to verify that in no circumstances
		 * the output of the filterListForProgram() method never returns null
		 * even if the input list itself is null */
		assertNotNull(CourseFilter.FilterListForProgram("SOEN", null, null));
		/* The expected output in this case is simply an empty list */
		System.out.println(System.getProperty("user.dir"));
	}
	
	@Test
	public void testCOMPNull() throws IOException
	{
		/* This test performs a check to verify that in no circumstances
		 * the output of the filterListForProgram() method never returns null
		 * even if the input list itself is null */
		assertNotNull(CourseFilter.FilterListForProgram("COMP", null, null));
		/* The expected output in this case is simply an empty list */
	}
}
