package skynet.scheduler.common;

public interface ICourse 
{
	public ICourse[] getPrerequisites(); 				// Returns an Array of Prereq objects
	public String[] getPrerequisitesAsCourseCodes(); 	// Returns an Array of Strings representing Prereqs.
	public String getCourseName(); 						// E.g. Object-Oriented Programming II
	public String getCourseCode(); 						// E.g. COMP248
	public double getCreditUnits(); 					// E.g. 3.5
	public SemesterSeasons[] getCourseAvailability();	// E.g. { Fall, Winter, Summer };
	public SemesterSeasons[] getEngineerAvailability();	// E.g. { Fall, Winter, Summer };
	public int getPriority();
	public ICourse[] getCorequisites();
	public String[] getCorequisitesAsCourseCodes();
	public boolean hasPrereqs(); 						// Returns true if a course has prerequisites
	public boolean hasCoreqs(); 
}
