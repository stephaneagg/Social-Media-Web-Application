package com.steph.user;

import com.steph.user.DTOs.*;
import com.steph.exceptions.UserException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileDTOMapper userProfileDTOMapper;
    private final CurrentUserDTOMapper currentUserDTOMapper;

    public UserService(
            UserRepository userRepository,
            UserProfileDTOMapper userProfileDTOMapper,
            CurrentUserDTOMapper currentUserDTOMapper) {
        this.userRepository = userRepository;
        this.userProfileDTOMapper = userProfileDTOMapper;
        this.currentUserDTOMapper = currentUserDTOMapper;
    }

    public List<UserProfileDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userProfileDTOMapper)
                .collect(Collectors.toList());
    }

    public UserProfileDTO getUserById(Integer userId){
        return userRepository.findById(userId)
                .map(userProfileDTOMapper)
                .orElseThrow(() -> new UserException(userId + " not found"));
    }


    public CurrentUserDTO getCurrentUser(Integer authenticatedUserId) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow( () -> new UserException(authenticatedUserId + " not Found"));

        return currentUserDTOMapper.apply(user);
    }

    public UserProfileDTO updateUser(UpdateUserDTO updateUserDTO, Integer userId, Integer authenticatedUserId) throws AccessDeniedException {

        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        // retrieve the user we want to update
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(userId + " not found"));
        // update the user
        user.updateFromDTO(updateUserDTO);
        userRepository.save(user);

        // mpa user to userProfileDTO and return
        return userProfileDTOMapper.apply(user);
    }

    public void deleteUser(Integer userId, Integer authenticatedUserId) throws AccessDeniedException {

        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only delete your own profile");
        }

        userRepository.deleteById(userId);
    }
}
