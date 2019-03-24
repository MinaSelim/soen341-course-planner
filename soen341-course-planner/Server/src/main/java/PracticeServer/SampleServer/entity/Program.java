package PracticeServer.SampleServer.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import java.util.*;
import java.io.IOException;
import PracticeServer.SampleServer.entity.Course;

public class Program {

    //Base Course Object properties
    @Id
    private String programId;

    private List<Course> courses;

    //Default Constructor
    public Program() {
    }

    //Contrsuctor
    public Program(String programId, List<Course> courses) {
        this.programId = programId;
        this.courses = courses;
    }

    //Getter that returns the course name
    public String getProgramId() {
        return this.programId;
    }

    //Setter that sets the course name
    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String toString() {
        return "Program [programId=" + this.programId + ", " +
                "courses=" + this.courses + "]";
    }


}