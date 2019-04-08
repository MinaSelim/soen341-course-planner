package PracticeServer.SampleServer.service;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import skynet.scheduler.common.Course;
import skynet.sequencer.Semester;

import java.util.*;

@Service
public class CoordinatorService {

    /*
    Converts a list of any type to a JSON object
     */
    public String getJson(List list) {

        String json = new Gson().toJson(list);
        return json;

    }

    /*
    Given a a JSON object, the following method converts it to list of Course objects
     */
    public static List getCourses(String jsonResponse)
    {
        List courses = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        //https://stackoverflow.com/questions/5490789/json-parsing-using-gson-for-java
        JsonElement httpContent = new JsonParser().parse(jsonResponse);
        JsonArray coursesJson = httpContent.getAsJsonArray();

        for(int i =0; i < coursesJson.size(); i++)
        {
            Course course = gson.fromJson(coursesJson.get(i),Course.class);
            courses.add(course);
        }
        return courses;
    }

    /*
    Given the list of Semester objects from the generate sequence method, this method iterates through the list
    and parses only the relevant information ( i.e the semester, the year, the course codes and the credit of each course)
    The information is stored in a HashMap with a key of type String containing the semester and the year, with a list of
    strings containing the course code and credits for the value of the map.
     */
    public static HashMap getJsonSequence(List<Semester> sequence) {

        LinkedHashMap<String, ArrayList<String>> jsonSequence = new LinkedHashMap<>();
        String semesterInfo;
        String courseInfo;

        for (int i = 0; i < sequence.size(); i++)
        {
            ArrayList <String> requiredCourses = new ArrayList<>();
            semesterInfo = sequence.get(i).getSeason() + " " + sequence.get(i).getYear();

            for(int j = 0; j < sequence.get(i).getCourses().size(); j++)
            {
                courseInfo = sequence.get(i).getCourses().get(j).getCourseCode() + " " + sequence.get(i).getCourses().get(j).getCreditUnits();
                requiredCourses.add(courseInfo);
            }
            jsonSequence.put(semesterInfo,requiredCourses);
        }

      return jsonSequence;
    }
}


