/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skynet.sequencer;

/**
 *
 * @author menac
 */

import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

public class Course implements ICourse {
    
    private String name;
    private Course[] prereqs; 
    private String[] semesterAvailability; 
    private int credits;
    private int priority;
    
    public Course() {
        name = "unset";
        prereqs = null;
        semesterAvailability = null;
        credits = 0; 
        priority = 0;
    }
    
    public Course(String n, Course[] p, String[] sa, int c) {
        name = n;
        prereqs = p;
        semesterAvailability = sa; 
        credits = c;
        priority = 0;
    }
    
    public Course(String n, Course[] p) {
        name = n;
        prereqs = p;
        semesterAvailability = null; 
        credits = 3;
        priority = 0;
    }
    
    @Override
    public String getCourseName() { return name; }
    
    @Override
    public Course[] getPrerequisites() { return prereqs; }
    
    public void setPrerequisites(Course[] p) { prereqs = p; } 

    public String[] getSemesterAvailability() { return semesterAvailability; }
    
    public int getCredits() { return credits; }
    
    public void incrementPriority() { priority++; }
    
    public int getPriority() { return priority; }

	@Override
	public String[] getPrerequisitesAsCourseCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCourseCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCreditUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SemesterSeasons[] getCourseAvailability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SemesterSeasons[] getEngineerAvailability() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
