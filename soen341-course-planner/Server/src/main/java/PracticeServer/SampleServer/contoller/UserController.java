package PracticeServer.SampleServer.contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import PracticeServer.SampleServer.service.UserRepository;
import PracticeServer.SampleServer.entity.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;


    /*
        GET
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{userID}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("userID") String userID) {
        return repository.findByUserID(userID);
    }


    /*
        PUT
     */
    @RequestMapping(value = "/{userID}", method = RequestMethod.PUT)
    public void modifyUserByID(@PathVariable("userID") String userID, @Valid @RequestBody User users) {
        users.setUserID(userID);
        repository.save(users);
    }

    /*
        POST
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User users) {
        repository.save(users);
        return users;
    }

    /*
        DELETE
     */
    @RequestMapping(value = "/{userID}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String userID) {
        repository.delete(repository.findByUserID(userID));
    }
}