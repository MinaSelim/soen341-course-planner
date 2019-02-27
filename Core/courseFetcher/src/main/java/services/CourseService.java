package services;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import http.HttpClient;
import http.jsonParsers.ConcordiaApiParser;
import skynet.scheduler.common.ICourse;

/*
    This service class is used to interact with the Concordia API.
 */
public class CourseService {


    private static final String COURSE_DESCRIPTION = "https://opendata.concordia.ca/API/v1/course/description/filter/%s";



    /*
            1st %s (subject) - ex. SOEN
            2nd %s (catalog) - ex. 331
            3rd 5s (career) - ex. UGRD || GRAD
     */
    private static final String COURSE_CATALOG = "https://opendata.concordia.ca/API/v1/course/catalog/filter/%s/%s/%s";



    private static final String COURSE_SECTION = "https://opendata.concordia.ca/API/v1/course/section/filter/{subject}/{catalog}";
    private static final String COURSE_SCHEDULE = "https://opendata.concordia.ca/API/v1/course/schedule/filter/{courseid}/{subject}/{catalog}";


    private HttpClient httpClient;

    /**
     * Initialize a CourseService instance by providing the credentials used to interact with the Concordia Open API.
     * @param username
     * @param pass
     */
    public CourseService(String username, String pass) {
        this.httpClient = new HttpClient(username, pass);
    }





    public ICourse getCourse(String courseCode) throws IOException {

        String url = String.format(COURSE_DESCRIPTION, courseCode);
        String httpResponse = httpClient.get(url);

        //May need to hit other endpoints for more info.
        //send http reponse to a parser method

        return null;
    }


    public List<ICourse> getCourseForProgram(String programCode) throws IOException {

        String url = String.format(COURSE_CATALOG, programCode, "*", "UGRD");
        String httpResponse = httpClient.get(url);

        List<ICourse> courses = ConcordiaApiParser.getCourses(httpResponse);
        //attachPrereqs(courses);

        return  courses;

    }

    public ICourse getCourse(String programCode, String classCode) throws IOException {
        String url = String.format(COURSE_CATALOG, programCode, classCode, "UGRD");
        String httpResponse = httpClient.get(url);
        return ConcordiaApiParser.getCourse(httpResponse);
    }


    private void attachPrereqs(List<ICourse> courses) throws IOException {

        HashMap<String, ICourse> lookup = new HashMap();

        for(ICourse c: courses)
            lookup.put(c.getCourseCode(), c);

        for(ICourse c: courses){
          //  List<String> prereq = c.getPrerequisites();
         //   attach(c, prereq, lookup);
        }

    }

    private void attach(ICourse course, List<String> prereq, HashMap<String, ICourse> lookup) throws IOException {

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

    private ICourse getCourse(String code, HashMap<String, ICourse> lookup) throws IOException {

        ICourse c = null;
        if (lookup.containsKey(code))
            c = lookup.get(code);
        else
            c = getNewCoureFromAPI(code);

        return c;
    }

    private ICourse getNewCoureFromAPI(String code) throws IOException {

        int index = 0;
        while(!Character.isDigit(code.charAt(index)))
            index++;

        String program = code.substring(0, index);
        String codeNum = code.substring(index);

        ICourse temp = getCourse(program, codeNum);
        return temp;
    }

    private boolean hasPrereqCourse(String code, ICourse course){

        String[] pre =course.getPrerequisitesAsCourseCodes();

        for(String preCode: pre)
            if(preCode.equals(code));
        return false;
    }

}
