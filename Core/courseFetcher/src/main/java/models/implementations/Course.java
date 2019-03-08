package models.implementations;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

public class Course implements ICourse {

    private String courseName;
    private String courseTitle;
    private String courseID;


    private String[] StringPrerequisites;
    private String courseSubject;
    private String courseCatalog;
    private String academicCareer;

    private double creditUnits;

    private ICourse[] prereq;


    public Course(){}

    public Course(String courseName, String courseTitle, String courseID, String[] prerequisites, String courseSubject, String courseCatalog, int creditUnits, String academicCareer) {
        this.courseName = courseName;
        this.courseTitle = courseTitle;
        this.courseID = courseID;
        this.StringPrerequisites = prerequisites;
        this.courseSubject = courseSubject;
        this.courseCatalog = courseCatalog;
        this.creditUnits = creditUnits;
        this.academicCareer = academicCareer;

        this.prereq = new ICourse[StringPrerequisites.length];
    }

    public ICourse[] getPrereq() {
        return prereq;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTitle() {
        return null;
    }

    public String getCourseID() {
        return "";
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public String getCourseCatalog() {
        return courseCatalog;
    }

    public String getAcademicCareer() {
        return null;
    }

    public SemesterSeasons[] getCourseAvailability() {
        return null;
    }

    public SemesterSeasons[] getEngineerAvailability() {
        return null;
    }

    @Override
    public String toString(){

        String[] info = new String[StringPrerequisites.length];
        //info = StringPrerequisites.toArray(info);

        return courseSubject + "" + courseCatalog + "; ";// + Arrays.toString(info);
    }

	@Override
	public ICourse[] getPrerequisites() {
		// TODO Auto-generated method stub
		return prereq;
	}

	@Override
	public String[] getPrerequisitesAsCourseCodes() {
		// TODO Auto-generated method stub
		return StringPrerequisites;
	}

	@Override
	public String getCourseCode() {
		// TODO Auto-generated method stub
		return courseSubject+courseCatalog;
	}

	@Override
	public double getCreditUnits() {
		// TODO Auto-generated method stub
		return creditUnits;
	}

	@Override
	public void setPrerequisites(ICourse[] prereq){
        this.prereq = prereq;
    }
}
