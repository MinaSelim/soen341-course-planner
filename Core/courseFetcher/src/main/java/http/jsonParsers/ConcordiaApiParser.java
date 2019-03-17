package http.jsonParsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import skynet.scheduler.common.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
public class ConcordiaApiParser 
{
    public static List<ICourse> getCourses(String jsonResponse)
    {
        List<ICourse> courses = new ArrayList<ICourse>();
        HashMap<String, ICourse> lookup = new HashMap<String, ICourse>();

        //https://stackoverflow.com/questions/5490789/json-parsing-using-gson-for-java
        JsonElement httpContent = new JsonParser().parse(jsonResponse);
        JsonArray coursesJson = httpContent.getAsJsonArray();

        for(int i =0; i < coursesJson.size(); i++)
        {
            Course course = getCourseFromJson(coursesJson.get(i).getAsJsonObject(), lookup);
            if(course != null)
                courses.add(course);
        }
        return courses;
    }

    public static Course getCourse(String jsonResponse)
    {
        JsonElement httpContent = new JsonParser().parse(jsonResponse);
        JsonArray coursesJson = httpContent.getAsJsonArray();

        return getCourseFromJson(coursesJson.get(0).getAsJsonObject(), null);
    }

    private static Course getCourseFromJson(JsonObject element, HashMap<String, ICourse> map)
    {
        JsonElement prereq = element.get("prerequisites");
        String id = element.get("ID").getAsString();

        if(map != null && map.containsKey(id))
            return null;

        List<String> pre = new ArrayList<String>();
        
        if(element.get("subject").getAsString().equals("ENGR")
        		&& element.get("catalog").getAsString().equals("391"))
        	System.out.println("");

        if(prereq != null)
            getPrereq(prereq.getAsString(), pre);

        String[] preArray = new String[pre.size()];
        pre.toArray(preArray);

        Course course =  new Course(
                element.get("title").getAsString(),
                element.get("title").getAsString(),
                id,
                preArray,
                element.get("subject").getAsString(),
                element.get("catalog").getAsString(),
                (int)element.get("classUnit").getAsDouble(),
                element.get("career").getAsString()
        );

        if(map != null)
            map.put(id, course);

        return course;
    }

    public static void getPrereq(String content, List<String> course) 
    {
    	//Depending on if the course has prereqs or coreqs, or both, key is assigned a specific string
    	String key = null;
        if(content.indexOf("Prerequisite") != -1)
        {
        	key = "Prerequisite:";
        }
        else if(content.indexOf("Corequisite") != -1)
        {
        	key = "Corequisite:";
        }
        else
        	return;

        //Remove all spaces
        content = content.replaceAll(" ", "");
        int start = content.indexOf(key);

        //handle other case
        if(start == -1) {
            start = content.indexOf("Prerequisite ");
        }

        if(start != -1)
            start += key.length();
        else
            return; //nothing to read

        //Added Logic here - Derek
        content = content.replace(';', ',');
        content = content.replace('.', ',');
        content = content.replaceAll("and", ",");
        content = content.replaceAll("previouslyorconcurrently", "");
        content = content.replaceAll("orequivalent", "");
        if(content.contains("Youmustcomplete1ofthefollowingcourses"))
        {
        	int count = 1;
        	while(content.indexOf(",", content.indexOf("Youmustcomplete1ofthefollowingcourses")) != -1)
        	{
        		String ModifiedEnd = content.substring(content.indexOf("Youmustcomplete1ofthefollowingcourses"), content.length()).replaceFirst(",", "or"+count++);
        		content = content.replaceAll(content.substring(content.indexOf("Youmustcomplete1ofthefollowingcourses"), content.length()), ModifiedEnd);
        	}
        }
        content = content.replaceAll("Youmustcomplete1ofthefollowingcourses", "");       

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

    private static int getEnd(String content, int start)
    {
        int index = content.indexOf("Course", start + 1);

        if(index == -1)
            index = content.indexOf("Co-requisite", start + 1);
        if(index == -1)
            index = content.indexOf("requisite", start + 1);

        return index;
    }
}
