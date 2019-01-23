package http.jsonParsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.ICourse;
import models.implementations.Course;

import java.util.ArrayList;
import java.util.List;

public class ConcordiaApiParser {

    public static List<ICourse> getCourses(String jsonResponse){

        List<ICourse> courses = new ArrayList<ICourse>();

        //https://stackoverflow.com/questions/5490789/json-parsing-using-gson-for-java
        JsonElement httpContent = new JsonParser().parse(jsonResponse);
        JsonArray coursesJson = httpContent.getAsJsonArray();

        for(int i =0; i < coursesJson.size(); i++){
            courses.add(getCourseFromJson(coursesJson.get(i).getAsJsonObject()));
        }
        return courses;
    }

    private static Course getCourseFromJson(JsonObject element){

        return new Course(
                element.get("title").getAsString(),
                element.get("title").getAsString(),
                element.get("ID").getAsString(),
                null,
                element.get("subject").getAsString(),
                element.get("catalog").getAsString(),
                (int)element.get("classUnit").getAsDouble(),
                element.get("career").getAsString()
        );
    }
}
