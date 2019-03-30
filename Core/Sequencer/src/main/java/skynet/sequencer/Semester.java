package skynet.sequencer;

import java.util.ArrayList;
import java.util.List;
import skynet.scheduler.common.*;

public class Semester implements ISemester 
{
    private String season; 
    private SemesterSeasons seasonEnum;
    private int year; 
    private List<Course> courses; 
    
    public Semester() 
    {
        season = "unset";
        seasonEnum = null;
        year = 0;
        courses = null;
    }
    
    public Semester(String s, int y, List<Course> c) 
    {
        season = s;
        switch(season)
        {
        case "Fall":
        	seasonEnum = SemesterSeasons.Fall;
        	break;
        case "Winter":
        	seasonEnum = SemesterSeasons.Winter;
        	break;
        case "Summer":
        	seasonEnum = SemesterSeasons.Summer;
        	break;
        default:
        	seasonEnum = SemesterSeasons.Fall;
        	break;
        }
        year = y;
        courses = c; 
    }
    
    public String getSeason() { return season; }
    public List<Course> getCourses() { return courses; } 
    public void setSeason(String s) { season = s; }
    public void setYear(int y) { year = y; }
    public void setCourses(ArrayList<Course> c) { courses = c; }
    
    public void display() 
    {
        System.out.print(season + " " + year);
        System.out.print("\tCourses = [ ");
        for (int i = 0; i < courses.size(); i++) 
        {
            System.out.print(courses.get(i).getCourseName() + " ");
        }
        System.out.println("]");
    }

    /*	Interface implementation methods
     * 	---------------------------------
     */
    
	@Override
	public ICourse[] getCoursesScheduled() {
		
		ICourse[] iCourses = new ICourse[courses.size()];
		courses.toArray(iCourses);
	
		return iCourses;
	}

	@Override
	public SemesterSeasons getSemester() {
		return seasonEnum;
	}
	
    @Override
    public int getYear()
    { 
    	return year;
    }
}