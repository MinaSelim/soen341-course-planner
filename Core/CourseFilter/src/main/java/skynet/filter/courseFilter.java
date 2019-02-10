package skynet.filter;

import java.util.ArrayList;
import java.util.List;
import skynet.scheduler.common.*;

public class courseFilter
{
	public static List<ICourse> FilterListForProgram(String Program, List<ICourse> unfilteredCourses)
	{
		//Safety check to avoid iterating through a null list
		if(unfilteredCourses == null)
			return new ArrayList<ICourse>();
		
		//The output list of the method
		List<ICourse> filteredCourses = new ArrayList<ICourse>();
		
		
		//Switch statement that filters courses based on program
		//Note that these filters only apply for the General Program options for now
		//Could modify for other options later if time allows
		switch(Program)
		{
		case "SOEN":
			for(int i = 0;i < unfilteredCourses.size(); ++i)
			{
				String str = unfilteredCourses.get(i).getCourseCode();
				
				if(str.equals("ENGR201")
						|| str.equals("ENGR213")
						|| str.equals("ENGR233")
						|| str.equals("ENGR202")
						|| str.equals("ENGR317")
						|| str.equals("ENGR391")
						|| str.equals("ENGR301")
						|| str.equals("ENGR392")
						|| str.equals("SOEN228")
						|| str.equals("SOEN287")
						|| str.equals("SOEN331")
						|| str.equals("SOEN341")
						|| str.equals("SOEN342")
						|| str.equals("SOEN343")
						|| str.equals("SOEN384")
						|| str.equals("SOEN344")
						|| str.equals("SOEN345")
						|| str.equals("SOEN357")
						|| str.equals("SOEN390")
						|| str.equals("SOEN321")
						|| str.equals("SOEN490")
						|| str.equals("SOEN385")
						|| str.equals("COMP232")
						|| str.equals("COMP348")
						|| str.equals("COMP249")
						|| str.equals("COMP348")
						|| str.equals("COMP352")
						|| str.equals("COMP346")
						|| str.equals("COMP335")
						|| str.equals("ENCS282"))
				{
					filteredCourses.add(unfilteredCourses.get(i));
				}
			}
			break;
		case "COMP":
			for(int i = 0;i < unfilteredCourses.size(); ++i)
			{
				String str = unfilteredCourses.get(i).getCourseCode();
			
				if(str.equals("COMP232")
						|| str.equals("COMP248")
						|| str.equals("COMP228")
						|| str.equals("COMP228")
						|| str.equals("COMP233")
						|| str.equals("COMP249")
						|| str.equals("ENCS282")
						|| str.equals("COMP348")
						|| str.equals("COMP352")
						|| str.equals("COMP346")
						|| str.equals("COMP335")
						|| str.equals("COMP354")
						|| str.equals("ENCS393"))
				{
					filteredCourses.add(unfilteredCourses.get(i));
				}
			}
			break;
		default:
			System.out.println("Invalid Program String. Returning original list");
			return unfilteredCourses;
		}
		
		return filteredCourses;
	}
}
