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
public interface ICourse {
    public Course[] getPrerequisites();
    public String getCourseName(); 
    public String[] getSemesterAvailability(); 
}
