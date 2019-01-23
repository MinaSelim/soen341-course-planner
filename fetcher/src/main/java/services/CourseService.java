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
        //String httpResponse = httpClient.get(url);

        String httpResponse = "[\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032001\",\n" +
                "        \"title\": \"System Hardware\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"228\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: MATH203; MATH204\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032004\",\n" +
                "        \"title\": \"Web Programming\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"287\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Never Taken: SOEN387; Course Prerequisite: COMP248\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032004\",\n" +
                "        \"title\": \"Web Programming\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"287\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Never Taken: SOEN387; Course Prerequisite: COMP248\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032004\",\n" +
                "        \"title\": \"Web Programming\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"287\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Never Taken: SOEN387; Course Prerequisite: COMP248\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032004\",\n" +
                "        \"title\": \"Web Programming\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"287\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Never Taken: SOEN387; Course Prerequisite: COMP248\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032005\",\n" +
                "        \"title\": \"Information Systems Security\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"321\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: COMP346\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032005\",\n" +
                "        \"title\": \"Information Systems Security\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"321\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: COMP346\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032006\",\n" +
                "        \"title\": \"Introduction to Formal Methods for Software Engineering\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"331\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP249;  COMP238 or COMP232\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032006\",\n" +
                "        \"title\": \"Introduction to Formal Methods for Software Engineering\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"331\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP249;  COMP238 or COMP232\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032008\",\n" +
                "        \"title\": \"Software Process\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"341\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Prerequisite: COMP 352 or COEN 352; ENCS 282 previously or concurrently.\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032008\",\n" +
                "        \"title\": \"Software Process\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"341\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Prerequisite: COMP 352 or COEN 352; ENCS 282 previously or concurrently.\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032009\",\n" +
                "        \"title\": \"Software Requirements and Specifications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"342\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341; Never Taken: SOEN343, SOEN357, SOEN431, SOEN448, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032009\",\n" +
                "        \"title\": \"Software Requirements and Specifications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"342\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341; Never Taken: SOEN343, SOEN357, SOEN431, SOEN448, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032009\",\n" +
                "        \"title\": \"Software Requirements and Specifications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"342\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341; Never Taken: SOEN343, SOEN357, SOEN431, SOEN448, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032010\",\n" +
                "        \"title\": \"Software Architecture and Design I\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"343\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341; Course Corequisite: SOEN342; Never Taken: COEN421, COEN498M, SOEN344, SOEN345, SOEN431, SOEN448, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032010\",\n" +
                "        \"title\": \"Software Architecture and Design I\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"343\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341; Course Corequisite: SOEN342; Never Taken: COEN421, COEN498M, SOEN344, SOEN345, SOEN431, SOEN448, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032011\",\n" +
                "        \"title\": \"Software Architecture and Design II\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"344\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN343; Never Taken: SOEN390, SOEN448, SOEN449, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032011\",\n" +
                "        \"title\": \"Software Architecture and Design II\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"344\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN343; Never Taken: SOEN390, SOEN448, SOEN449, SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032012\",\n" +
                "        \"title\": \"Software Testing, Verification and Quality Assurance\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"345\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: SOEN343\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032012\",\n" +
                "        \"title\": \"Software Testing, Verification and Quality Assurance\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"345\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: SOEN343\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032013\",\n" +
                "        \"title\": \"User Interface Design\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"357\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN342; Never Taken: SOEN390\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032013\",\n" +
                "        \"title\": \"User Interface Design\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"357\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN342; Never Taken: SOEN390\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032015\",\n" +
                "        \"title\": \"Management, Measurement and Quality Control\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"384\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341, ENCS282\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032015\",\n" +
                "        \"title\": \"Management, Measurement and Quality Control\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"384\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"   Course Prerequisite: SOEN341, ENCS282\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032016\",\n" +
                "        \"title\": \"Control Systems and Applications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"385\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: ENGR213, ENGR233\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032016\",\n" +
                "        \"title\": \"Control Systems and Applications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"385\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: ENGR213, ENGR233\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032017\",\n" +
                "        \"title\": \"Web-Based Enterprise Application Design\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"387\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite SOEN287; COMP354 or SOEN341; Course Co-requisite: COMP353\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032017\",\n" +
                "        \"title\": \"Web-Based Enterprise Application Design\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"387\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite SOEN287; COMP354 or SOEN341; Course Co-requisite: COMP353\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032018\",\n" +
                "        \"title\": \"Software Engineering Team Design Project\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"390\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.50\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: SOEN344, SOEN357; Never Taken: SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032018\",\n" +
                "        \"title\": \"Software Engineering Team Design Project\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"390\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.50\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: SOEN344, SOEN357; Never Taken: SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032018\",\n" +
                "        \"title\": \"Software Engineering Team Design Project\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"390\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.50\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: SOEN344, SOEN357; Never Taken: SOEN490\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032020\",\n" +
                "        \"title\": \"Embedded Systems and Software\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"422\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032020\",\n" +
                "        \"title\": \"Embedded Systems and Software\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"422\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032020\",\n" +
                "        \"title\": \"Embedded Systems and Software\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"422\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032021\",\n" +
                "        \"title\": \"Distributed Systems\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"423\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032021\",\n" +
                "        \"title\": \"Distributed Systems\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"423\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032021\",\n" +
                "        \"title\": \"Distributed Systems\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"423\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Prerequisite: COMP346;\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032026\",\n" +
                "        \"title\": \"Web Services and Applications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"487\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Corequisite: SOEN387\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032026\",\n" +
                "        \"title\": \"Web Services and Applications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"487\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Corequisite: SOEN387\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032026\",\n" +
                "        \"title\": \"Web Services and Applications\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"487\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"Course Corequisite: SOEN387\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032027\",\n" +
                "        \"title\": \"Capstone Software Engineering Design Project\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"490\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \" ; Course Prerequisite: SOEN390\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"032027\",\n" +
                "        \"title\": \"Capstone Software Engineering Design Project\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"490\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \" ; Course Prerequisite: SOEN390\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"044220\",\n" +
                "        \"title\": \"SYSTEM HARDWARE LAB\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"298\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"1.00\",\n" +
                "        \"prerequisites\": \"   Course Corequisite: COMP228\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"044221\",\n" +
                "        \"title\": \"SOFTWARE ENGINEERING PROJECT\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"491\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"1.00\",\n" +
                "        \"prerequisites\": \"PREREQ SOEN491\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"047100\",\n" +
                "        \"title\": \"TOPICS/SOFTWARE ENGINEERING\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"498\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"3.00\",\n" +
                "        \"prerequisites\": \"PREREQ SOEN498\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"048957\",\n" +
                "        \"title\": \"TOPICS IN SOFTWARE ENGINEERING\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"499\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"PREREQ COMP498\",\n" +
                "        \"crosslisted\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"ID\": \"048957\",\n" +
                "        \"title\": \"TOPICS IN SOFTWARE ENGINEERING\",\n" +
                "        \"subject\": \"SOEN\",\n" +
                "        \"catalog\": \"499\",\n" +
                "        \"career\": \"UGRD\",\n" +
                "        \"classUnit\": \"4.00\",\n" +
                "        \"prerequisites\": \"PREREQ COMP498\",\n" +
                "        \"crosslisted\": null\n" +
                "    }\n" +
                "]";

        return ConcordiaApiParser.getCourses(httpResponse);
    }


}
