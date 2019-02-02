package fetcher.jsonParsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sequencer.components.Course;
import skynet.scheduler.common.ICourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConcordiaApiParser {

    public static List<ICourse> getCourses(String jsonResponse){

        List<ICourse> courses = new ArrayList<ICourse>();
        HashMap<String, ICourse> lookup = new HashMap<String, ICourse>();

        //https://stackoverflow.com/questions/5490789/json-parsing-using-gson-for-java
        JsonElement httpContent = new JsonParser().parse(jsonResponse);
        JsonArray coursesJson = httpContent.getAsJsonArray();

        for(int i =0; i < coursesJson.size(); i++){

            Course course = getCourseFromJson(coursesJson.get(i).getAsJsonObject(), lookup);
            if(course != null)
                courses.add(course);
        }
        return courses;
    }

    private static Course getCourseFromJson(JsonObject element, HashMap<String, ICourse> map){

        JsonElement prereq = element.get("prerequisites");
        String id = element.get("ID").getAsString();

        if(map.containsKey(id))
            return null;

        List<String> pre = new ArrayList<String>();
        if(prereq != null)
            getPrereq(prereq.getAsString(), pre);

        Course course =  new Course(
                element.get("title").getAsString(),
                element.get("title").getAsString(),
                id,
                pre,
                element.get("subject").getAsString(),
                element.get("catalog").getAsString(),
                (int)element.get("classUnit").getAsDouble(),
                element.get("career").getAsString()
        );

        map.put(id, course);

        return course;
    }

    public static void getPrereq(String content, List<String> course) {

        if(content.indexOf("Prerequisite") == -1)
            return;

        String key = "Prerequisite:";
        int start = content.indexOf(key);

        //handle other case
        if(start == -1) {
            start = content.indexOf("Prerequisite ");
        }

        if(start != -1)
            start += key.length();
        else
            return; //nothing to read

        content = content.replace(';', ',');
        content = content.replace('.', ',');
        content = content.replaceAll("and", ",");

        //Never Taken
        int end1 = getEnd(content, start);
        int end2 = content.indexOf("Never", start + 1);

        int end  = 0;

        //Ensure to grab content for Prereq only. (Done because Http response not structured properly)
        if(end1 == -1) {
            if(end2 != -1)
                end = end2;
        }else{
            if(end2 == -1)
                end = end1;
            else
            {
                if(end2 < end1)
                    end = end2;
                else
                    end = end1;
            }
        }

        if(start > end)
            end = content.length();

        String courseStr = content.substring(start, end);
        //courseStr = courseStr.replaceAll(" ", "");
        String[] pre = courseStr.split(",");

        for(String c : pre) {
            String code = c.trim();
            if(!code.equals(""))
                course.add(c.trim());
        }
    }


    private static int getEnd(String content, int start){

        int index = content.indexOf("Course", start + 1);

        if(index == -1)
            index = content.indexOf("Co-requisite", start + 1);
        if(index == -1)
            index = content.indexOf("requisite", start + 1);

        return index;
    }
}