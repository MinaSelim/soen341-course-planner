package skynet.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import skynet.filter.courseFilter;
import skynet.scheduler.common.*;

public class TestCourseFilter {

	private List<ICourse> testInputList;
	
	@Before
	public void setup()
	{
		this.testInputList = new ArrayList<ICourse>();
	}
	
	@Test
	public void testEquals() 
	{
		testInputList.add(new Course("","","",null,"ENCS","282",0,""));
		assertEquals(testInputList.get(0),
				courseFilter.FilterListForProgram("SOEN", testInputList).get(0));
	}

}
