package http.jsonParsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

		if(element.get("subject").getAsString().equals("COEN")
				&& element.get("catalog").getAsString().equals("346"))
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
				element.get("classUnit").getAsDouble(),
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


		//Special Case When Extra Pre-requisites appear at
		//end of the API response.
		int startOfExtraPrereq = 0;
		if(content.contains("Youmustcomplete1ofthefollowingrulesCoursePrerequisite:"))
		{
			startOfExtraPrereq = content.lastIndexOf(":")+1;
			String extraPrereq = content.substring(startOfExtraPrereq);
			int count = 1;
			while(extraPrereq.indexOf(",") != -1)
			{
				extraPrereq = extraPrereq.replaceFirst(",", "or"+Integer.toString(count++));
			}
			course.add(extraPrereq);
		}

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
			{
				if((!code.contains("or1")) &&
					code.indexOf("or") != code.lastIndexOf("or"))// multiple ors
				{
					code = getMultipleOrsCode(code);
				}
				course.add(code);
			}

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

	//this standarizes multiples ors to or1, or2 etc.
	private static String getMultipleOrsCode(String content)
	{
		int offset = 0;
		int counter = 1;
		StringBuilder string = new StringBuilder();
		boolean buildingString = true;
		while(buildingString)
		{
			string.append(content.substring(offset, content.indexOf("or", offset)));
			string.append("or" + counter++);
			offset = content.indexOf("or", offset) + 2;

			if(content.indexOf("or", offset) == -1)
			{
				string.append(content.substring(offset, content.length()));
				buildingString = false;
			}
		}
		return string.toString();
	}
}
