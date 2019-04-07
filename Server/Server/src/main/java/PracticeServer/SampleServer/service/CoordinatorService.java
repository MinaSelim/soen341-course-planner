package PracticeServer.SampleServer.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Service;
import skynet.scheduler.common.Course;
import skynet.sequencer.Semester;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class CoordinatorService {

    //public CoordinatorService(){}

    public String getJson(List list) {

        String json = new Gson().toJson(list);
        return json;

    }

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
            //Course course = gson.fromJson(coursesJson.get(i),Course.class);
            //courses.add(course);
        }
        return courses;
    }

    public static HashMap getJsonSequence(List<Semester> sequence) {

        Gson parser = new Gson();
        parser = new GsonBuilder().setPrettyPrinting().create();

        HashMap<String, ArrayList<String>> jsonSequence = new HashMap<>();
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


