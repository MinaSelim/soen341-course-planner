
import models.ICourse;
import services.CourseService;

import java.io.IOException;
import java.util.List;

public class Run {

    public static void main(String[] args) throws IOException {

        //run sample code and tests here for now.
        String user = "";
        String pass = "";
        CourseService service = new CourseService(user, pass);

        List<ICourse> courses = service.getCourseForProgram("SOEN");

        int i =0;

    }
}
