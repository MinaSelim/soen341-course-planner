package skynet.coordinator;

import java.util.List;
import skynet.scheduler.common.ICourse;

public interface ICourseService 
{
	public List<ICourse> getCourseForProgram(String program);
	public ICourse getCourse(String courseCode);
}
