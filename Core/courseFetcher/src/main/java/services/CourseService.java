package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import availability.Availability;
import http.HttpClient;
import http.jsonParsers.ConcordiaApiParser;
import optimization.ConcurrentCourseFetcher;
import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

/*
    This service class is used to interact with the Concordia API.
 */
public class CourseService 
{
    @SuppressWarnings("unused")
    private static final String COURSE_DESCRIPTION = "https://opendata.concordia.ca/API/v1/course/description/filter/%s";
    /*
     *      1st %s (subject) 	- ex. SOEN
     *      2nd %s (catalog) 	- ex. 331
     *      3rd %s (career) 	- ex. UGRD || GRAD
     */
    private static final String COURSE_CATALOG = "https://opendata.concordia.ca/API/v1/course/catalog/filter/%s/%s/%s";
    @SuppressWarnings("unused")
	private static final String COURSE_SECTION = "https://opendata.concordia.ca/API/v1/course/section/filter/{subject}/{catalog}";
	private static final String COURSE_SCHEDULE = "https://opendata.concordia.ca/API/v1/course/schedule/filter/%s/%s/%s";
    private static final String COURSE_SESSION = "https://opendata.concordia.ca/API/v1/course/session/filter/%s/%s/%s";

    private HttpClient httpClient;

    /**
     * Initialize a CourseService instance by providing the credentials used to interact with the Concordia Open API.
     * @param username
     * @param pass
     */
    public CourseService(String username, String pass) 
    {
        this.httpClient = new HttpClient(username, pass);
    }

    public List<ICourse> getCoursesForProgram(String programCode, ArrayList<String> filter) throws IOException {

        String url = String.format(COURSE_CATALOG, programCode, "*", "UGRD");
        String httpResponse = httpClient.get(url);

        List<ICourse> courses = ConcordiaApiParser.getCourses(httpResponse);
        //attachPrereqs(courses);

        removeCoursesOutsideOfProgram(courses , filter);
        Course[] courseArray = new Course[courses.size()];
        courses.toArray(courseArray);
        
        Attach attch = new Attach(courseArray, this, filter);
        attch.attachPrerequisites();
        
        return courses;
    }

    public ICourse getCourse(String programCode, String classCode) throws IOException 
    {
    	if(programCode.equals(""))
    		return null;
        String url = String.format(COURSE_CATALOG, programCode, classCode, "UGRD");
        String httpResponse = httpClient.get(url);
        if(httpResponse.equals("[]"))
            return null;
        else
            return ConcordiaApiParser.getCourse(httpResponse);
    }

    /**
     * Retrieves Course information for the specified course codes.
     * Pass a list of VALID course codes. Method assumes codes have proper structure and format. (ex SOEN228)
     * @param codes
     * @return
     */
    public List<ICourse> getCourses(List<String> codes){
        return ConcurrentCourseFetcher.getCourses(codes, this);
    }


    @SuppressWarnings("unused")
	private void attachPrereqs(List<ICourse> courses) throws IOException 
    {

        HashMap<String, ICourse> lookup = new HashMap<String, ICourse>();

        for(ICourse c: courses)
            lookup.put(c.getCourseCode(), c);

        for(ICourse c: courses)
        {
            //  List<String> prereq = c.getPrerequisites();
            //   attach(c, prereq, lookup);
        }
    }

    @SuppressWarnings("unused")
	private void attach(ICourse course, List<String> prereq, HashMap<String, ICourse> lookup) throws IOException 
    {
        if(prereq == null || prereq.size() == 0 )
            return;
        else {
            for (String code : prereq) {

                if(!hasPrereqCourse(code, course)) {
                    ICourse prereqCourse = getCourse(code, lookup);
                    //  attach(prereqCourse, prereqCourse.getPrerequisites(), lookup);
                }
            }
        }
    }

    private ICourse getCourse(String code, HashMap<String, ICourse> lookup) throws IOException 
    {
        ICourse c = null;
        if (lookup.containsKey(code))
            c = lookup.get(code);
        else
            c = getNewCoureFromAPI(code);

        return c;
    }

    private ICourse getNewCoureFromAPI(String code) throws IOException 
    {
        int index = 0;
        while(!Character.isDigit(code.charAt(index)))
            index++;

        String program = code.substring(0, index);
        String codeNum = code.substring(index);
        
        if(program.equals("COMP") && codeNum.equals("232"))
        	System.out.println("");

        ICourse temp = getCourse(program, codeNum);
        return temp;
    }

    private boolean hasPrereqCourse(String code, ICourse course)
    {
        String[] pre =course.getPrerequisitesAsCourseCodes();

        for(String preCode: pre)
            if(preCode.equals(code));
        return false;
    }

    public List<Availability> getAvailablitity(int year) throws IOException {

        String url = String.format(COURSE_SESSION, "UGRD", "*", "*");
        String httpResponse = httpClient.get(url);

        return ConcordiaApiParser.getAvailability(year, httpResponse);

    }

    public List<SemesterSeasons> getSeasonForCourse(Course c) throws IOException {
        return getSeasonForCourse(c.getCourseSubject(), c.getCourseCatalog());
    }


        public List<SemesterSeasons> getSeasonForCourse(String programCode, String courseCode) throws IOException {
        	
        String url = String.format(COURSE_SCHEDULE, "*", programCode, courseCode);
        String httpResponse = httpClient.get(url);
        return ConcordiaApiParser.getSeasons(httpResponse, this);
    }
    
    private void removeCoursesOutsideOfProgram( List<ICourse> courses ,ArrayList<String> filter)
    {
    	int i = 0;
    	while( i<courses.size())
    	{
    		
    		if(!filter.contains(courses.get(i).getCourseCode()))
    		{
    			courses.remove(i);
    		}
    		else
    		{
    			i++;
    		}
    	}
    }
        
        
}
