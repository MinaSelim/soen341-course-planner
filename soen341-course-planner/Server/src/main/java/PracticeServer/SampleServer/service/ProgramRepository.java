package PracticeServer.SampleServer.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import PracticeServer.SampleServer.entity.Program;
import PracticeServer.SampleServer.entity.Course;

public interface ProgramRepository extends MongoRepository<Program, String> {

    public Program findByProgramId(String id);

}