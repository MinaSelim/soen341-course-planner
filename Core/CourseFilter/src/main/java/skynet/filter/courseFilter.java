package skynet.filter;

import java.util.ArrayList;
import java.util.List;
import skynet.scheduler.common.*;

public class courseFilter
{
	public static List<ICourse> FilterListForProgram(String Program, List<ICourse> unfilteredCourses)
	{
		if(unfilteredCourses == null)
			return null;
		
		//Output Filtered List
		List<ICourse> filteredCourses = new ArrayList<ICourse>();
		
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
			//Not implemented yet.
			break;
		default:
			System.out.println("Invalid Program String. Returning null");
			return null;
		}
		
		//Print out filtered course list
		for(int i = 0;i < filteredCourses.size();++i)
		{
			System.out.println(filteredCourses.get(i).getCourseCode());
		}
		
		return filteredCourses;
	}
}
