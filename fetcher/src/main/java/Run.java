
import http.jsonParsers.ConcordiaApiParser;
import models.ICourse;
import org.apache.commons.codec.binary.Base64;

import services.CourseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Run {

    public static void main(String[] args) throws IOException {

        //run sample code and tests here for now.

        String user = "132";
        String pass = "6a388ea97bb3d994c699760a7ee01472";


        CourseService service = new CourseService(user, pass);

        List<ICourse> courses = service.getCourseForProgram("SOEN");

        int i =0;
    }

}
