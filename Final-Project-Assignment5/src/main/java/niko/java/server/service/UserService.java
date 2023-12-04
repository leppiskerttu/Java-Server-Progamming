// UserService.java
package niko.java.server.service;

import niko.java.server.model.User;
import niko.java.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        // Assuming you handle password hashing elsewhere if needed
        return userRepository.save(user);
    }
}
