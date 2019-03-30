package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;


/**
 * Class that will attach prerequisites to a list of courses.
 */
public class Attach 
{
    private Course[] courses;
    private CourseService service;
    private ArrayList<String> filter;
    private final int COURSE_CODE_LENGTH = 7;

    public Attach(Course[] courses, CourseService service, ArrayList<String> progFilter)
    {
        this.service = service;
        this.courses = courses;
        this.filter = progFilter;
    }

    public void attachPrerequisites() throws IOException 
    {
        HashMap<String, ICourse> lookup = new HashMap<String, ICourse>();

        for(ICourse c: courses)
            lookup.put(c.getCourseCode(), c);

        for(Course c: courses)
        {
            String[] prerequisites = c.getPrerequisitesAsCourseCodes();
            attach(c, prerequisites, lookup);
        }

    }

    private void attach(Course course, String[] prereq, HashMap<String, ICourse> lookup) throws IOException 
    {
        if(prereq == null || prereq.length == 0)
            return;
        else 
        {
        	if(course.getCourseCode() == "ENGR391")
        		System.out.println();
            ICourse[] prerequisites = new ICourse[prereq.length];
            int counter = 0;
            for (String code : prereq) 
            {
                String parsedCode = parsePrereq(code);
                //fix spacing issue
                if(parsedCode.indexOf(" ") == -1) 
                {
                    Course prereqCourse = (Course)getCourse(parsedCode, lookup);
                    if(prereqCourse != null) 
                    {
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
    //if string contains the word 'or' it will only take the that pertains to the program code
    private String parsePrereq(String code)
    {
        if(code.contains("or1"))
        {
        	if(filter.contains(code.substring(0,COURSE_CODE_LENGTH)))
        		return code.substring(0,COURSE_CODE_LENGTH);
        	else if(filter.contains(code.substring(code.indexOf("or1") + 3, code.indexOf("or1") + 3 + COURSE_CODE_LENGTH)))
        		return code.substring(code.indexOf("or1") + 3,code.indexOf("or1") + 3 + COURSE_CODE_LENGTH);
        	else if(filter.contains(code.substring(code.indexOf("or2") + 3,code.indexOf("or2") + 3 + COURSE_CODE_LENGTH)))
        		return code.substring(code.indexOf("or2") + 3,code.indexOf("or2") + 3 + COURSE_CODE_LENGTH);
        	else if(filter.contains(code.substring(code.indexOf("or3") + 3,code.indexOf("or3") + 3 + COURSE_CODE_LENGTH)))
        		return code.substring(code.indexOf("or3") + 3,code.indexOf("or3") + 3 + COURSE_CODE_LENGTH);
        	throw new RuntimeException();
        }
        else if(code.contains("previouslyorconcurrently"))
        {
        		return code.substring(0,code.indexOf("previouslyorconcurrently")); // TOADD Coreqs
        }
        else if(code.contains("or"))
        {
        	if(filter.contains(code.substring(0,code.indexOf("or"))))
        		return code.substring(0,code.indexOf("or"));
        	else
        		return code.substring(code.indexOf("or")+2, (code.indexOf("or")+2+COURSE_CODE_LENGTH));
        }
        return code;
    }

    private ICourse getCourse(String code, HashMap<String, ICourse> lookup) throws IOException 
    {
        ICourse course = null;
        if (lookup.containsKey(code)) 
        {
            course = lookup.get(code);
        }
        else 
        {
            course = getNewCourseFromAPI(code);
            if(course != null)
                lookup.put(course.getCourseCode(), course);
        }
        return course;
    }

    private ICourse getNewCourseFromAPI(String code) throws IOException 
    {
        int index = 0;
        while(!Character.isDigit(code.charAt(index)))
            index++;

        String program = code.substring(0, index);
        String codeNum = code.substring(index);

        ICourse temp = service.getCourse(program, codeNum);
        return temp;
    }

}
