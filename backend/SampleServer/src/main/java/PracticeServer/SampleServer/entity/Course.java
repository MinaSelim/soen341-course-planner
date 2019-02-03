package PracticeServer.SampleServer.entity;

public class Course {

    private String courseName;
    private String courseCode;
    private double credits;


    public Course() {}

    public Course(String courseName, String courseCode, double credits)
    {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getCourseCode()
    {
        return courseCode;
    }

    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public double getCredits()
    {
        return credits;
    }

    public void setCredits(double credits)
    {
        this.credits = credits;
    }
}
