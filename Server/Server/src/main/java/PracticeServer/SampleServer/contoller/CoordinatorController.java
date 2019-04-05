package PracticeServer.SampleServer.contoller;

import PracticeServer.SampleServer.service.CoordinatorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sequence")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllCourses()
    {
        List<String> course = new ArrayList<>();
        course.add("SOEN228");
        course.add("COMP248");
        course.add("COMP249");
        course.add("SOEN287");
        course.add("COMP352");
        course.add("COMP228");
        course.add("ENGR202");
        services.CourseService service = new services.CourseService("132","6a388ea97bb3d994c699760a7ee01472");

        return coordinatorService.getJson(service.getCourses(course));
    }

}
