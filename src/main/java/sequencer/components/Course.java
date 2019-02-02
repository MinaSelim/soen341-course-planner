package sequencer.components;

import java.util.Arrays;
import java.util.List;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

public class Course implements ICourse {

    private String courseName;
    private String courseTitle;
    private String courseID;

    private List<String> stringPrerequisites;
    private ICourse[] prerequisites;
    private String courseSubject;
    private String courseCatalog;

    private int creditUnits;
    private String academicCareer;
    private SemesterSeasons[] seasonalAvailability;
    private SemesterSeasons[] taughtByEngineerAvailability;
    
    private int priority;

    public Course(){}

    public Course(String courseName, String courseTitle, String courseID, List<String> prerequisites, String courseSubject, String courseCatalog, int creditUnits, String academicCareer) {
        this.courseName = courseName;
        this.courseTitle = courseTitle;
        this.courseID = courseID;
        this.stringPrerequisites = prerequisites;
        this.courseSubject = courseSubject;
        this.courseCatalog = courseCatalog;
        this.creditUnits = creditUnits;
        this.academicCareer = academicCareer;
        this.priority = 0;
    }

    public List<String> getStringPrerequisites() {
        return stringPrerequisites;
    }

    public String getCourseName() {
        return null;
    }

    public String getCourseTitle() {
        return null;
    }

    public String getCourseID() {
        return "";
    }

    public String getCourseSubject() {
        return null;
    }

    public int getCourseCatalog() {
        return 0;
    }

    public int getCreditUnits() {
        return 0;
    }

    public String getAcademicCareer() {
        return null;
    }

    @Override
    public String toString(){

        String[] info = new String[prerequisites.length];
      //  info = prerequisites.toArray(info);

        return courseSubject + "" + courseCatalog + "; " + Arrays.toString(info);
    }

	@Override
	public ICourse[] getPrerequisites() {
		return prerequisites;
	}

	@Override
	public String getCourseCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SemesterSeasons[] getCourseAvailability() {
		return taughtByEngineerAvailability;
	}

	@Override
	public SemesterSeasons[] getEngineerAvailability() {
		return seasonalAvailability;
	}

	@Override
	public void incrementPriority() {
		++priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}
}