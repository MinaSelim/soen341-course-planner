package skynet.test;

import availability.Availability;
import availability.AvailabilityProvider;
import org.junit.Before;
import org.junit.Test;
import services.CourseService;
import skynet.scheduler.common.SemesterSeasons;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AvailTest {

    CourseService service;

    @Before
    public void setup(){
        String user = "132";
        String pass = "6a388ea97bb3d994c699760a7ee01472";
        this.service = new CourseService(user, pass);
    }

    @Test
    public void testAvail() throws IOException {

        HashMap<String, SemesterSeasons> lookup = AvailabilityProvider.getLookup(service);
        int i = 0;
    }

}
