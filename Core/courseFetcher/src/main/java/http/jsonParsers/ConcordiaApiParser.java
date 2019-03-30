package http.jsonParsers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import availability.Availability;
import availability.AvailabilityProvider;
import services.CourseService;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        List<String> coreqs = new ArrayList<>();

        if(element.get("subject").getAsString().equals("ENGR")
        		&& element.get("catalog").getAsString().equals("391"))
        	System.out.println("");

        if(prereq != null) {
            pre = getRequirements(prereq.getAsString()).get("prereqs");
            coreqs = getRequirements(prereq.getAsString()).get("coreqs");
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
                (int)element.get("classUnit").getAsDouble(),
                element.get("career").getAsString()
        );

        if(map != null)
            map.put(id, course);

        return course;
    }

    public static HashMap<String,ArrayList<String>> getRequirements(String content){

        HashMap<String , ArrayList<String>> requirements = new  HashMap<String , ArrayList<String>>();

        //inforcing universal formating on the requirements strings
        //Format example : 
        
        content = content.replaceAll("-","");
        content = content.replaceAll("\\s|;|:|,|/|and" , "");
        content = content.replaceAll("(?i)Prerequisite","P:");
        content = content.replaceAll("(?i)Corequisite", "C:");
        content = content.replaceAll("(?i)notregistered" , "");
        content = content.replaceAll("(?i)NeverTaken" , "NT:");
        content = content.replaceAll("(?i)Course" , "");

         // System.out.println("Content String after reformatting = " + content);


        //Create Patterns
        Pattern neverTakenPattern = Pattern.compile("NT:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)*");
        Pattern coreqPattern = Pattern.compile("C:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)*");
        Pattern prereqPattern = Pattern.compile("P:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)*");
        Pattern courseSeriePattern = Pattern.compile("(?<!or)[A-Za-z]{4}(?:[0-9]{3})+(?!or)");
        Pattern courseAlphaPattern = Pattern.compile("[A-Za-z]{4}");
        

        //Create Matchers
        Matcher neverTakenMatcher = neverTakenPattern.matcher(content);
        Matcher  coreqMatcher = coreqPattern.matcher(content);
        Matcher  prereqMatcher = prereqPattern.matcher(content);


        String courseLetterCode = new String();

        if(neverTakenMatcher.find()){

            String ntreqs = neverTakenMatcher.group(0);
            //System.out.println("Never Taken requirements = " + ntreqs);
            ArrayList<String> neverTaken = new ArrayList<String>();
            Matcher courseSerieMatcher = courseSeriePattern.matcher(ntreqs);


            while(courseSerieMatcher.find()){
                String courseSerie = courseSerieMatcher.group(0);

                Matcher letterCodeMatcher = Pattern.compile("[A-Za-z]{4}").matcher(courseSerie);
                if(letterCodeMatcher.find())
                    courseLetterCode = letterCodeMatcher.group(0);

                Matcher numericCodeMatcher =Pattern.compile("[0-9]{3}").matcher(courseSerie);

                while(numericCodeMatcher.find()){
                    neverTaken.add(courseLetterCode + numericCodeMatcher.group(0));
                }

                ntreqs = ntreqs.replaceAll(courseSerie , "");

            }


            ntreqs = ntreqs.replaceAll("NT:" , "");

            Matcher splitMatcher = Pattern.compile("(?:[A-Za-z]{4}[0-9]{3}){2}").matcher(ntreqs);

            StringBuilder ntreqsSb = new StringBuilder(ntreqs);
                
            if(splitMatcher.find()){

                ntreqsSb.insert((splitMatcher.end()+splitMatcher.start())/2,"#");
                ntreqs = ntreqsSb.toString();
            } 

           ArrayList<String> alternativeNtreqs = new ArrayList<String>(Arrays.asList(ntreqs.split("#")));

           neverTaken.addAll(alternativeNtreqs);

           requirements.put("never taken" , neverTaken);


           //System.out.println(Arrays.toString(neverTaken.toArray()));
        }



        if(prereqMatcher.find()){

            String prereqs  = prereqMatcher.group(0);
            //System.out.println("Prerequisite requirements = " + prereqs);
            ArrayList<String> prerequisites = new ArrayList<String>();
            Matcher courseSerieMatcher = courseSeriePattern.matcher(prereqs);

            while(courseSerieMatcher.find()){
                String courseSerie = courseSerieMatcher.group(0);

                Matcher letterCodeMatcher = Pattern.compile("[A-Za-z]{4}").matcher(courseSerie);

                if(letterCodeMatcher.find())
                    courseLetterCode = letterCodeMatcher.group(0);

                Matcher numericCodeMatcher =Pattern.compile("[0-9]{3}").matcher(courseSerie);

                while(numericCodeMatcher.find()){
                    prerequisites.add(courseLetterCode + numericCodeMatcher.group(0));
                }

                prereqs = prereqs.replaceAll(courseSerie , "");

            }


            prereqs = prereqs.replaceAll("P:" , "");

            Matcher splitMatcher = Pattern.compile("(?:[A-Za-z]{4}[0-9]{3}){2}").matcher(prereqs);

            StringBuilder prereqsSb = new StringBuilder(prereqs);
                
            if(splitMatcher.find()){
        
                prereqsSb.insert((splitMatcher.end()+splitMatcher.start())/2,"#");
                prereqs = prereqsSb.toString();
            } 

           ArrayList<String> alternativePrereqs = new ArrayList<String>(Arrays.asList(prereqs.split("#")));

           prerequisites.addAll(alternativePrereqs);

           requirements.put("prereqs", prerequisites);

           //System.out.println("PREREQUISITES = " + Arrays.toString(prerequisites.toArray()));

        }

        if(coreqMatcher.find()){
            String coreqs  = coreqMatcher.group(0);
            //System.out.println("Corequisite Requirements = " + coreqs);
            ArrayList<String> corequisites = new ArrayList<String>();
            Matcher courseSerieMatcher = courseSeriePattern.matcher(coreqs);

            while(courseSerieMatcher.find()){
                String courseSerie = courseSerieMatcher.group(0) ;

                Matcher letterCodeMatcher = Pattern.compile("[A-Za-z]{4}").matcher(courseSerie);
                if(letterCodeMatcher.find())
                    courseLetterCode = letterCodeMatcher.group(0);

                Matcher numericCodeMatcher =Pattern.compile("[0-9]{3}").matcher(courseSerie);

                while(numericCodeMatcher.find()){
                    corequisites.add(courseLetterCode + numericCodeMatcher.group(0));
                }

                coreqs = coreqs.replaceAll(courseSerie , "");


            }

            coreqs = coreqs.replaceAll("C:" , "");

            Matcher splitMatcher = Pattern.compile("(?:[A-Za-z]{4}[0-9]{3}){2}").matcher(coreqs);

            StringBuilder coreqsSb = new StringBuilder(coreqs);
                
            if(splitMatcher.find()){

                coreqsSb.insert((splitMatcher.end()+splitMatcher.start())/2,"#");
                coreqs = coreqsSb.toString();
            } 

           ArrayList<String> alternativeCoreqs = new ArrayList<String>(Arrays.asList(coreqs.split("#")));

           corequisites.addAll(alternativeCoreqs);

           requirements.put("coreqs" , corequisites); 

           //System.out.println("COREQUISITES = " + Arrays.toString(corequisites.toArray()));

        }

        return requirements;

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
