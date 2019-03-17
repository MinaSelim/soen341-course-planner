package skynet.test;

import availability.Availability;
import availability.AvailabilityProvider;
import org.junit.Before;
import org.junit.Test;
import services.CourseService;

import java.io.IOException;
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

        AvailabilityProvider.setLookup(service);
        int i = 0;
    }

}
