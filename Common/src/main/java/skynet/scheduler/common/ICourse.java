package skynet.scheduler.common;

public interface ICourse 
{
	public ICourse[] getPrerequisites(); // Comp-248
	public String getCourseName(); // Object-Oriented programmming II
	public String getCourseCode(); // Comp-249
	public int getCreditUnits(); //3.5
	public SemesterSeasons[] getCourseAvailability();
	public SemesterSeasons[] getEngineerAvailability();
}
