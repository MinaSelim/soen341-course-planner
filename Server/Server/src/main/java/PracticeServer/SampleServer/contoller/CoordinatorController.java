package PracticeServer.SampleServer.contoller;

import PracticeServer.SampleServer.entity.SequenceInfo;
import PracticeServer.SampleServer.service.CoordinatorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import skynet.coordinator.Coordinator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static PracticeServer.SampleServer.service.CoordinatorService.getJsonSequence;

@RestController
@RequestMapping("/sequence")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    /*
    Get request to receive all information for each given course in a list.
    Still has no relevant usage, can be properly implemented at a later time.
     */
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

    /*
    Post request to receive program name and courses taken from the front-end, to then use to generate
    a sequence and send it back to the UI.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object sequeceInfo(@RequestBody SequenceInfo info)
    {
        ArrayList<String> coursesTaken = new ArrayList<>(Arrays.asList(info.getCoursesTaken()));

        try {
            return getJsonSequence(Coordinator.getSequence(info.getProgramCode(), coursesTaken));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "Error";
    }

}