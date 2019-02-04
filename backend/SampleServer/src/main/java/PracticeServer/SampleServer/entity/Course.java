package PracticeServer.SampleServer.entity;

public class Course {

    //Base Course Object properties
    private String courseName;
    private String courseCode;
    private double credits;

    //Default Constructor
    public Course() {}

    //Contrsuctor
    public Course(String courseName, String courseCode, double credits)
    {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
    }

    //Getter that returns the course name
    public String getCourseName()
    {
        return courseName;
    }

    //Setter that sets the course name
    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    //Getter that returns the course code
    public String getCourseCode()
    {
        return courseCode;
    }

    //Setter that sets the course code
    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    //Getter that returns the amounts of credits
    public double getCredits()
    {
        return credits;
    }

    //Setter that sets the amount of credits
    public void setCredits(double credits)
    {
        this.credits = credits;
    }
}
