package com.steph.user;

import com.steph.user.DTOs.CreateUserDTO;
import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import com.steph.user.DTOs.UserProfileDTOMapper;
import com.steph.exceptions.UserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileDTOMapper userProfileDTOMapper;

    public UserService( UserRepository userRepository, UserProfileDTOMapper userProfileDTOMapper) {
        this.userRepository = userRepository;
        this.userProfileDTOMapper = userProfileDTOMapper;
    }

    public List<UserProfileDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userProfileDTOMapper)
                .collect(Collectors.toList());
    }

    public UserProfileDTO getUserById(Integer id){
        return userRepository.findById(id)
                .map(userProfileDTOMapper)
                .orElseThrow(() -> new UserException(id + " not found"));
    }

    // SHOULD NOT BE USED ANYMORE. USER CREATION IS HANDLED BY AUTHENTICATION
    public UserProfileDTO createUser(CreateUserDTO createUserDTO) {
        // create new user and set all its attributes
        User user = new User();
        user.setUsername(createUserDTO.username());
        user.setEmail(createUserDTO.email());
        user.setPasswordHash(createUserDTO.password()); // TODO: HASH THE PASSWORD
        user.setDisplayName(createUserDTO.displayName());
        user.setBio(createUserDTO.bio());
        user.setProfileImageUrl(createUserDTO.profileImageUrl());

        // save the user
        userRepository.save(user);

        // mpa user to userProfileDTO and return
        return userProfileDTOMapper.apply(user);
    }
    //

    public UserProfileDTO updateUser(UpdateUserDTO updateUserDTO, Integer id) {
        // retrieve the user we want to update
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(id + " not found"));
        // update the user
        user.updateFromDTO(updateUserDTO);
        userRepository.save(user);

        // mpa user to userProfileDTO and return
        return userProfileDTOMapper.apply(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
