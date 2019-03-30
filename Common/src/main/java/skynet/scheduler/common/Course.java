package skynet.scheduler.common;

import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

import java.util.List;

public class Course implements ICourse 
{
    private String courseName;
    private String courseTitle;
    private String courseID;
    private String[] StringPrerequisites;
    private String courseSubject;
    private String courseCatalog;
    private String academicCareer;
    private double creditUnits;
    private ICourse[] prereq;
    private int priority;

    private List<SemesterSeasons> seasons;

    public Course(){}

    public Course(String courseName,
    		String courseTitle,
    		String courseID,
    		String[] prerequisites,
    		String courseSubject,
    		String courseCatalog,
    		double creditUnits,
    		String academicCareer) 
    {
        this.courseName = courseName;
        this.courseTitle = courseTitle;
        this.courseID = courseID;
        this.StringPrerequisites = prerequisites;
        this.courseSubject = courseSubject;
        this.courseCatalog = courseCatalog;
        this.creditUnits = creditUnits;
        this.academicCareer = academicCareer;
        this.prereq = new ICourse[StringPrerequisites.length];
        this.priority = 0;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public String getCourseCatalog() {
        return courseCatalog;
    }

    public String getAcademicCareer() {
        return academicCareer;
    }
    
	public void setCourseCode(String code){
		String[] codes = code.split(" "); 
		
		courseSubject = codes[0];
		courseCatalog = codes[1];	
	}
	
	public void setPrerequisites(ICourse[] prereq){
        this.prereq = prereq;
    }
	
	public void incrementPriority(){
		priority++; 
	}

    public int getPriority(){ 
    	return priority; 
    }

    /*
     * Interface method Implementations
     * --------------------------------
     */
    
    @Override
    public String getCourseName() {
        return courseName;
    }
    
    @Override
    public SemesterSeasons[] getCourseAvailability() {

        SemesterSeasons[] s = new SemesterSeasons[this.seasons.size()];
        this.seasons.toArray(s);
        return s;
    }

    public void setCourseAvailability(List<SemesterSeasons> seasons){
        this.seasons = seasons;
    }

    @Override
    public SemesterSeasons[] getEngineerAvailability() {

        SemesterSeasons[] s = new SemesterSeasons[this.seasons.size()];
        this.seasons.toArray(s);
        return s;
    }

    @Override
    public String toString(){
        return courseSubject + "" + courseCatalog + "; ";
    }

	@Override
	public ICourse[] getPrerequisites() {
		return prereq;
	}

	@Override
	public String[] getPrerequisitesAsCourseCodes() {
		return StringPrerequisites;
	}

	@Override
	public String getCourseCode() {
		return courseSubject+courseCatalog;
	}
	
	@Override
	public double getCreditUnits() {
		return creditUnits;
	}
}
