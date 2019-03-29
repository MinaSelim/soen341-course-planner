package skynet.test;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

/* Course implementation for test cases */
public class Course implements ICourse
{
	private String courseName;
	private String courseCode;
	private Course[] prerequisites;
	private double creditUnits;
	private SemesterSeasons[] availability;
	private SemesterSeasons[] taughtByEngineer;
	
	
	public Course(String name, String code, double credits)
	{
		this.courseName = name;
		this.courseCode = code;
		this.creditUnits = credits;
		this.prerequisites = null;
		this.availability = null;
		this.taughtByEngineer = null;	
	}
	
	
	@Override
	public ICourse[] getPrerequisites() {
		// TODO Auto-generated method stub
		return prerequisites;
	}

	@Override
	public String[] getPrerequisitesAsCourseCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCourseName() {
		// TODO Auto-generated method stub
		return courseName;
	}

	@Override
	public String getCourseCode() {
		// TODO Auto-generated method stub
		return courseCode;
	}

	@Override
	public double getCreditUnits() {
		// TODO Auto-generated method stub
		return creditUnits;
	}

	@Override
	public SemesterSeasons[] getCourseAvailability() {
		// TODO Auto-generated method stub
		return availability;
	}

	@Override
	public SemesterSeasons[] getEngineerAvailability() {
		// TODO Auto-generated method stub
		return taughtByEngineer;
	}

}
