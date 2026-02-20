package com.steph;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // TODO: change runtime exception to something a bit more descriptive. Create an exception for this.
    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(id + " not found"));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
