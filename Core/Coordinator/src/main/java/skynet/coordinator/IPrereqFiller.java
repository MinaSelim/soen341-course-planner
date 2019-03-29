package skynet.coordinator;

import java.util.List;
import skynet.scheduler.common.ICourse;

public interface IPrereqFiller 
{
	public List<ICourse> populatePrereq(List<ICourse> courses);
}
