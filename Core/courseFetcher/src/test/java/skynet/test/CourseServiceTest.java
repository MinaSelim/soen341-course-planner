package skynet.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.AttachSeason;
import services.CourseService;
import skynet.scheduler.common.ICourse;
import skynet.scheduler.common.SemesterSeasons;

public class CourseServiceTest {

    private CourseService service;

    @Before
    public void setup(){

        String user = "132";
        String pass = "6a388ea97bb3d994c699760a7ee01472";
        this.service = new CourseService(user, pass);
    }


    /*@Test
    public void testSoenCourse() throws IOException {

        boolean match = true;
        String[] expected = getExpectedSOENCourse();

        //List<ICourse> courses = service.getCoursesForProgram("SOEN", courseFilter.getFilterForProgram("SOEN"));

        List<ICourse> courses = service.getCoursesForProgram("SOEN", new ArrayList<>());

        //check size if the same
        match = expected.length == courses.size();
        if(match){
            for(int i =0; i < expected.length; i++){

                ICourse course = courses.get(i);
                String obtained = course.getCourseCode();

                if(!expected[i].equals(obtained)) {
                    match = false;
                    break;
                }
            }
        }

        Assert.assertTrue(match);
    }*/

    @Test
    public void getCourse() throws IOException {

        String code = "SOEN228";
        String courseName = "System Hardware";
        ICourse c = service.getCourse("SOEN", "228");
        boolean match = code.equals(c.getCourseCode()) && courseName.equals(c.getCourseName());

        Assert.assertTrue(match);
    }

    private String[] getExpectedSOENCourse(){
        String[] courses = {
                "SOEN228","SOEN287","SOEN321","SOEN331","SOEN341","SOEN342","SOEN343","SOEN344","SOEN345","SOEN357",
                "SOEN384","SOEN385","SOEN387","SOEN390","SOEN422","SOEN423","SOEN487","SOEN490","SOEN298","SOEN491",
                "SOEN498","SOEN499"
        };
        return courses;
    }


    //@Test
    public void testConcurrentFetcher(){
        List<String> codes = new ArrayList<>();

        codes.add("COMP248");
        codes.add("COMP249");
        codes.add("SOEN228");
        codes.add("SOEN331");
        codes.add("COMP346");
        codes.add("COMP352");

        List<ICourse> courses = service.getCourses(codes);

        int i = 0;

    }


    //@Test
    public void testGetSeason() throws IOException {

        List<ICourse> courses = service.getCoursesForProgram("SOEN", new ArrayList<>());

        AttachSeason.attachSeasons(courses, service);
 
        //assertNotNull(courses.get(0).getCourseAvailability());
    }
}
