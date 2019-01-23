package models.implementations;

import models.ICourse;
import models.SSObject;

import java.util.List;

public class Course implements ICourse {

    private String courseName;
    private String courseTitle;
    private String courseID;


    private List<ICourse> prerequisites;
    private String courseSubject;
    private String courseCatalog;

    private int creditUnits;
    private String academicCareer;

    public Course(){}

    public Course(String courseName, String courseTitle, String courseID, List<ICourse> prerequisites, String courseSubject, String courseCatalog, int creditUnits, String academicCareer) {
        this.courseName = courseName;
        this.courseTitle = courseTitle;
        this.courseID = courseID;
        this.prerequisites = prerequisites;
        this.courseSubject = courseSubject;
        this.courseCatalog = courseCatalog;
        this.creditUnits = creditUnits;
        this.academicCareer = academicCareer;
    }

    public List<ICourse> getPrerequisites() {
        return null;
    }

    public String getCourseName() {
        return null;
    }

    public String getCourseTitle() {
        return null;
    }

    public int getCourseID() {
        return 0;
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

    public SSObject.Seasons[] getCourseAvailability() {
        return new SSObject.Seasons[0];
    }

    public SSObject.Seasons[] getEngineerAvailability() {
        return new SSObject.Seasons[0];
    }
}
