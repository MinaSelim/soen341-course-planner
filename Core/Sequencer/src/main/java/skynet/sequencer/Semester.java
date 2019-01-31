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

import java.util.ArrayList;

public class Semester {
    private String season; 
    private int year; 
    private ArrayList<Course> courses; 
    
    public Semester() {
        season = "unset";
        year = 0;
        courses = null;
    }
    
    public Semester(String s, int y, ArrayList<Course> c) {
        season = s;
        year = y;
        courses = c; 
    }
    
    public String getSeason() { return season; }
    public int getYear() { return year; }
    public ArrayList<Course> getCourses() { return courses; } 
    
    public void setSeason(String s) { season = s; }
    public void setYear(int y) { year = y; }
    public void setCourses(ArrayList<Course> c) { courses = c; }
    
    public void display() {
        System.out.print(season + " " + year);
        System.out.print("\tCourses = [ ");
        for (int i = 0; i < courses.size(); i++) {
            System.out.print(courses.get(i).getCourseName() + " ");
        }
        System.out.println("]");
    }
}
