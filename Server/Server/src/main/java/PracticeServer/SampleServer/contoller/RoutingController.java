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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import static PracticeServer.SampleServer.service.CoordinatorService.getJsonSequence;

@Controller
public class RoutingController{

    @RequestMapping(value={"/","/SignIn","SignInPage"}, method=RequestMethod.GET)
    public String index() {
       return "index.html";
    }
}