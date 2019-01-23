package services;


import http.HttpClient;
import http.jsonParsers.ConcordiaApiParser;
import models.ICourse;

import java.io.IOException;
import java.util.List;

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
        return ConcordiaApiParser.getCourses(httpResponse);
    }


}
