package PracticeServer.SampleServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import PracticeServer.SampleServer.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import PracticeServer.SampleServer.entity.User;
import PracticeServer.SampleServer.entity.Course;
import PracticeServer.SampleServer.entity.Program;
import org.springframework.boot.CommandLineRunner;
import PracticeServer.SampleServer.service.ProgramRepository;

@SpringBootApplication
public class SampleServerApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProgramRepository programRepository;

	public static void main(String[] args)
	{
		//Call to run the Server itself
		SpringApplication.run(SampleServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		userRepository.deleteAll();
		programRepository.deleteAll();

		initializePrograms();

		for (Program program : programRepository.findAll()) {
			System.out.println(program);
		}

		System.out.println();

		ArrayList<Course> courses = new ArrayList<Course>();
		courses.add(new Course("COMP", "248", 3.5));
		courses.add(new Course("COMP", "249", 3.0));
		courses.add(new Course("COMP", "352", 3.0));

		userRepository.save(new User("1433545", "password", courses, programRepository.findByProgramId("SOEN")));

		System.out.println("-------------------------------");
		for (User user : userRepository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

	}

	public void initializePrograms() {

		ArrayList<Course> coursesComp = new ArrayList<Course>();
		coursesComp.add(new Course("COMP", "232" , 3.0));
		coursesComp.add(new Course("COMP", "248" , 3.5));
		coursesComp.add(new Course("COMP", "228" , 3.0));
		coursesComp.add(new Course("COMP", "233" , 3.0));
		coursesComp.add(new Course("COMP", "249" , 3.5));
		coursesComp.add(new Course("ENCS", "282" , 3.0));
		coursesComp.add(new Course("COMP", "348" , 3.0));
		coursesComp.add(new Course("COMP", "352" , 3.0));
		coursesComp.add(new Course("COMP", "346" , 4.0));
		coursesComp.add(new Course("COMP", "335" , 3.0));
		coursesComp.add(new Course("COMP", "354" , 4.0));
		coursesComp.add(new Course("ENCS", "393" , 3.0));

		programRepository.save(new Program("COMP", coursesComp));

		ArrayList<Course> coursesSoen = new ArrayList<Course>();
		coursesSoen.add(new Course("ENGR", "201" , 3.0));
		coursesSoen.add(new Course("ENGR", "213" , 3.0));
		coursesSoen.add(new Course("ENGR", "233" , 3.0));
		coursesSoen.add(new Course("ENGR", "202" , 1.5));
		coursesSoen.add(new Course("ENGR", "371" , 4.0));
		coursesSoen.add(new Course("ENGR", "391" , 3.0));
		coursesSoen.add(new Course("ENGR", "301" , 3.0));
		coursesSoen.add(new Course("ENGR", "392" , 3.0));
		coursesSoen.add(new Course("SOEN", "228" , 3.0));
		coursesSoen.add(new Course("SOEN", "287" , 3.0));
		coursesSoen.add(new Course("SOEN", "331" , 3.0));
		coursesSoen.add(new Course("SOEN", "341" , 3.0));
		coursesSoen.add(new Course("SOEN", "342" , 3.0));
		coursesSoen.add(new Course("SOEN", "343" , 3.0));
		coursesSoen.add(new Course("SOEN", "384" , 3.0));
		coursesSoen.add(new Course("SOEN", "344" , 3.0));
		coursesSoen.add(new Course("SOEN", "345" , 3.0));
		coursesSoen.add(new Course("SOEN", "357" , 3.0));
		coursesSoen.add(new Course("SOEN", "390" , 3.0));
		coursesSoen.add(new Course("SOEN", "321" , 3.0));
		coursesSoen.add(new Course("SOEN", "490" , 3.0));
		coursesSoen.add(new Course("SOEN", "385" , 3.0));
		coursesSoen.add(new Course("COMP", "232" , 3.0));
		coursesSoen.add(new Course("COMP", "348" , 3.0));
		coursesSoen.add(new Course("COMP", "248" , 3.0));
		coursesSoen.add(new Course("COMP", "249" , 3.0));
		coursesSoen.add(new Course("COMP", "352" , 3.0));
		coursesSoen.add(new Course("COMP", "346" , 3.0));
		coursesSoen.add(new Course("COMP", "335" , 3.0));
		coursesSoen.add(new Course("ENCS", "282" , 3.0));

		programRepository.save(new Program("SOEN", coursesSoen));
	}

}

