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

    @Override
    public String[] getSemesterAvailability() { return semesterAvailability; }
    
    public int getCredits() { return credits; }
    
    public void incrementPriority() { priority++; }
    
    public int getPriority() { return priority; }
    
}
