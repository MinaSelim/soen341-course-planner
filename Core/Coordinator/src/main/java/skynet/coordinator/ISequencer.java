package skynet.coordinator;

import java.util.List;

import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.ISemester;

public interface ISequencer 
{
	public List<ISemester> generateSequence(List<ICourse> courses);
}
