package http.jsonParsers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import availability.Availability;
import availability.AvailabilityProvider;
import services.CourseService;
import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;
 
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
        List<String> coreqs = new ArrayList<>();

        if(element.get("subject").getAsString().equals("COEN")
        		&& element.get("catalog").getAsString().equals("346"))
        	System.out.println("");

        if(prereq != null) {
            pre = RequirementsParser.getRequirements(prereq.getAsString()).get("prereqs");
            coreqs = RequirementsParser.getRequirements(prereq.getAsString()).get("coreqs");
        }
        if(pre == null)
            pre = new ArrayList<String>();
        if(coreqs == null)
            coreqs = new ArrayList<>();

        String[] preArray = new String[pre.size()];
        pre.toArray(preArray);

        String[] coreqArray = new String[coreqs.size()];
        coreqs.toArray(coreqArray);

        Course course =  new Course(
                element.get("title").getAsString(),
                element.get("title").getAsString(),
                id,
                preArray,
                coreqArray,
                element.get("subject").getAsString(),
                element.get("catalog").getAsString(),
                element.get("classUnit").getAsDouble(),
                element.get("career").getAsString()
        );

        if(map != null)
            map.put(id, course);

        return course;
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

    public static List<Availability> getAvailability(int yearToAdd, String response){

        List<Availability> availabilities = new ArrayList<>();

        JsonElement httpContent = new JsonParser().parse(response);
        JsonArray elements = httpContent.getAsJsonArray();

        for(int i = 0; i < elements.size(); i++) {
            JsonObject obj = elements.get(i).getAsJsonObject();

            String des = obj.get("termDescription").getAsString();

            boolean skip = true;
            
            
            if(des.indexOf(Integer.toString(yearToAdd)) != -1
            		|| des.indexOf(Integer.toString(yearToAdd+1)) != -1
            		|| des.indexOf(Integer.toString(yearToAdd-1)) != -1) 
            {
                    skip = false;
            }
            if(!skip){
                String termCode = obj.get("termCode").getAsString();
                String ses = obj.get("sessionCode").getAsString();
                String sesDes = obj.get("sessionDescription").getAsString();

                availabilities.add(new Availability(termCode, des, ses, sesDes));
            }
        }
        return availabilities;

    }

    public static List<SemesterSeasons> getSeasons(String json, CourseService service){

        HashMap<String, SemesterSeasons> lookup = AvailabilityProvider.getLookup(service);

        JsonElement httpContent = new JsonParser().parse(json);
        JsonArray elements = httpContent.getAsJsonArray();

        
        List<SemesterSeasons> seasons = new ArrayList<>();

        for(int i =0; i < elements.size(); i++) {
        	
            JsonObject obj = elements.get(i).getAsJsonObject();
            String termCode = obj.get("termCode").getAsString();
            SemesterSeasons s = lookup.get(termCode);

            if(s != null && !seasons.contains(s)) {
                seasons.add(lookup.get(termCode));
            }
        }

        return seasons;
    }
}
