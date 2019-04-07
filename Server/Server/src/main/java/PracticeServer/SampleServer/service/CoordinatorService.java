package PracticeServer.SampleServer.service;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import skynet.scheduler.common.Course;

import java.util.ArrayList;
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

    public static List getJsonSequence(List sequence)
    {
        Gson parser = new Gson();

        for (int i = 0; i < sequence.size(); i++) {
             sequence.add(i, parser.toJson(sequence.get(i)));
        }

        return sequence;
    }

}
