package skynet.scheduler.common;

public interface ICourse 
{
	public ICourse[] getPrerequisites();
	public String getCourseName();
	public int getCreditUnits();
	public SemesterSeasons[] getCourseAvailability();
	public SemesterSeasons[] getEngineerAvailability();
}
