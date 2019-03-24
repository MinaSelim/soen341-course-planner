package PracticeServer.SampleServer.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import PracticeServer.SampleServer.entity.User;
import PracticeServer.SampleServer.entity.Course;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUserID(String userID);

}