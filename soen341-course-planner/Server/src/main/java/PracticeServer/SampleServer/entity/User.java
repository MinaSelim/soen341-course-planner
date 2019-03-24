package PracticeServer.SampleServer.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import java.util.*;
import java.io.IOException;

public class User {

    //Base Course Object properties
    @Id
    private String userID;

    private String password;
    private List<Course> courses;
    public Program program;

    //Default Constructor
    public User() {}

    //Contrsuctor
    public User(String userID, String password, List<Course> courses, Program program)
    {
        this.userID = userID;
        this.password = password;
        this.courses = courses;
        this.program = program;
    }

    //Getter that returns the course name
    public String getUserID()
    {
        return this.userID;
    }

    //Setter that sets the course name
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    //Getter that returns the course code
    public String getPassword()
    {
        return this.password;
    }

    //Setter that sets the course code
    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Program getProgram()
    {
        return this.program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }


    public String toString() {
        return "User [userID=" + this.userID + ", " +
                "password=" + this.password + ", " +
                "courses=" + this.courses + ", " +
                "program=" + this.program + "]";
    }

    public String getJson() {
        ObjectMapper Obj = new ObjectMapper();

        String jsonStr = null;

        try {
            jsonStr = Obj.writeValueAsString(this);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            return jsonStr;
        }
    }
}
