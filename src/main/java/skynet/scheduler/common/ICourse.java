package skynet.scheduler.common;

import java.util.List;

public interface ICourse 
{
	public ICourse[] getPrerequisites(); // Comp-248
	public List<String> getStringPrerequisites();
	public String getCourseName(); // Object-Oriented programmming II
	public String getCourseCode(); // Comp-249
	public int getCreditUnits(); //3.5
	public SemesterSeasons[] getCourseAvailability();
	public SemesterSeasons[] getEngineerAvailability();
	public void incrementPriority();
	public int getPriority();
}
