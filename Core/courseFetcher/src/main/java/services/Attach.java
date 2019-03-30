package services;

import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Class that will attach prerequisites and corequisites to a list of courses.
 */
public class Attach 
{
    private Course[] courses;
    private CourseService service;
    private ArrayList<String> filter;

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
        if(prereq != null && prereq.length != 0)
        {
        	if(course.getCourseCode() == "ENGR391")
        		System.out.println();
            ICourse[] prerequisites = new ICourse[prereq.length];
            int counter = 0;
            for (String code : prereq) 
            {
                String parsedCode = parsePrereq(code);
                //fix spacing issue
                if(!parsedCode.equals(""))
                {
                    Course prereqCourse = (Course)getCourse(parsedCode, lookup);
                    if(prereqCourse != null) 
                    {
                        prerequisites[counter]= prereqCourse;
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
        attachCorequisites(course, course.getCorequisitesAsCourseCodes(), lookup);
    }

    private void attachCorequisites(Course course, String[] coreqs, HashMap<String, ICourse> lookup) throws IOException
    {
        if(coreqs != null && coreqs.length != 0)
        {
            ICourse[] corequisites = new ICourse[coreqs.length];
            int counter = 0;
            for (String code : coreqs)
            {
                String parsedCode = parsePrereq(code);
                //fix spacing issue
                if(!parsedCode.equals(""))
                {
                    Course coreqCourse = (Course)getCourse(parsedCode, lookup);
                    if(coreqCourse != null)
                    {
                        corequisites[counter]= coreqCourse;
                        counter++;
                    }
                }
            }
            if(counter == 0 )
                corequisites = Arrays.copyOf(corequisites, 0);
            else
                if(counter < coreqs.length)
                    corequisites = Arrays.copyOf(corequisites, counter);

            course.setCorequisites(corequisites);
        }
    }
    //if string contains the word 'or' it will only take the that pertains to the program code
    private String parsePrereq(String code)
    {
        if(code.contains("or1"))
        {
        	if(filter.contains(code.substring(0,code.indexOf("or1"))))
        		return code.substring(0,code.indexOf("or1"));
        	else if(filter.contains(code.substring(code.indexOf("or1"),code.indexOf("or2"))))
        		return code.substring(code.indexOf("or1"),code.indexOf("or2"));
        	else if(filter.contains(code.substring(code.indexOf("or2"),code.indexOf("or3"))))
        		return code.substring(code.indexOf("or2"),code.indexOf("or3"));
        }
        else if(code.contains("or"))
        {
            String[] equivalentPrereqs = code.split("or");
            if(equivalentPrereqs.length == 1)
                return equivalentPrereqs[0];
            else {
                for (String equivalentPrereq : equivalentPrereqs) {
                    if (filter.contains(equivalentPrereq))
                        return equivalentPrereq;
                }
                return equivalentPrereqs[1];
            }
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
        else {
            course = getNewCourseFromAPI(code);
            if (course != null){
                lookup.put(course.getCourseCode(), course);
                attach((Course) course, course.getPrerequisitesAsCourseCodes(), lookup);
            }
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
