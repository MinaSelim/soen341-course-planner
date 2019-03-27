package skynet.test;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import availability.AvailabilityProvider;
import services.CourseService;
import skynet.scheduler.common.SemesterSeasons;

public class AvailTest {

    CourseService service;

    @Before
    public void setup(){
        String user = "132";
        String pass = "6a388ea97bb3d994c699760a7ee01472";
        this.service = new CourseService(user, pass);
    }

    @SuppressWarnings("unused")
	@Test
    public void testAvail() throws IOException {

        HashMap<String, SemesterSeasons> lookup = AvailabilityProvider.getLookup(service);
        int i = 0;
    }

}
