package services;

import skynet.scheduler.common.ICourse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that will attach prerequisites to a list of courses.
 */
public class Attach {

    private ICourse[] courses;
    private CourseService service;

    public Attach(ICourse[] courses, CourseService service){
        this.service = service;
        this.courses = courses;
    }

    public void attachPrerequisites() throws IOException {

        HashMap<String, ICourse> lookup = new HashMap();

        for(ICourse c: courses)
            lookup.put(c.getCourseCode(), c);

        for(ICourse c: courses){
            String[] prerequisites = c.getPrerequisitesAsCourseCodes();
            attach(c, prerequisites, lookup);
        }

    }

    private void attach(ICourse course, String[] prereq, HashMap<String, ICourse> lookup) throws IOException {
        if(prereq == null || prereq.length == 0)
            return;
        else {
            ICourse[] prerequisites = new ICourse[prereq.length];
            int counter = 0;
            for (String code : prereq) {
                String parsedCode = parsePrereq(code);
                //fix spacing issue
                if(parsedCode.indexOf(" ") == -1) {
                    ICourse prereqCourse = getCourse(parsedCode, lookup);
                    if(prereqCourse != null) {
                        prerequisites[counter]= prereqCourse;
                        attach(prereqCourse, prereqCourse.getPrerequisitesAsCourseCodes(), lookup);
                        counter++;
                    }
                }
            }
            if(counter == 0 )
                prerequisites = Arrays.copyOf(prerequisites, 0);
            else
                if(counter < prereq.length)
                    prerequisites = Arrays.copyOf(prerequisites, counter);
            course.setPrerequisites(prerequisites);
        }
    }
    //if string contains the word 'or' it will only take the first one
    private String parsePrereq(String code){

        if(code.indexOf(" ") == -1)
            return code;
        else
        if(code.contains(" or"))
            return code.substring(0, code.indexOf(" or"));
        else
            return code;
    }

    private ICourse getCourse(String code, HashMap<String, ICourse> lookup) throws IOException {
        ICourse course = null;
        if (lookup.containsKey(code)) {
            course = lookup.get(code);
        }
        else {
            course = getNewCourseFromAPI(code);
            if(course != null)
                lookup.put(course.getCourseCode(), course);
        }
        return course;
    }

    private ICourse getNewCourseFromAPI(String code) throws IOException {

        int index = 0;
        while(!Character.isDigit(code.charAt(index)))
            index++;

        String program = code.substring(0, index);
        String codeNum = code.substring(index);

        ICourse temp = service.getCourse(program, codeNum);
        return temp;
    }

}
