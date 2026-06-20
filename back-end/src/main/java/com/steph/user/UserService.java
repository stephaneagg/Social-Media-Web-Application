package com.steph.user;

import com.steph.upload.FileStorageService;
import com.steph.user.DTOs.*;
import com.steph.exceptions.UserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileDTOMapper userProfileDTOMapper;
    private final CurrentUserDTOMapper currentUserDTOMapper;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserProfileDTOMapper userProfileDTOMapper,
            CurrentUserDTOMapper currentUserDTOMapper,
            FileStorageService fileStorageService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileDTOMapper = userProfileDTOMapper;
        this.currentUserDTOMapper = currentUserDTOMapper;
        this.fileStorageService = fileStorageService;
        this.passwordEncoder = passwordEncoder;
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

        // Grab old image Urls in case of update
        String oldProfileImageURL = user.getProfileImageUrl();
        String oldCoverImageURL = user.getCoverImageUrl();

        // update the user
        user.updateFromDTO(updateUserDTO);
        userRepository.save(user);

        // Check if old images need to be deleted
        // Check ProfileImage
        if (oldProfileImageURL != null && !user.getProfileImageUrl().equals(oldProfileImageURL)) {
            fileStorageService.deleteFile(oldProfileImageURL);
        }
        // Check CoverImage
        if (oldCoverImageURL != null && !user.getCoverImageUrl().equals(oldCoverImageURL)) {
            fileStorageService.deleteFile(oldCoverImageURL);
        }

        // map user to userProfileDTO and return
        return userProfileDTOMapper.apply(user);
    }

    public void changePassword(ChangePasswordDTO dto, Integer userId, Integer authenticatedUserId) throws AccessDeniedException {
        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        // retrieve the user we want to update
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(userId + " not found"));

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                user.getPassword()
        )) {
            throw new UserException("Current password is incorrect");
        }

        if (dto.getNewPassword().length() < 8) {
            throw new UserException("New password must be at least 8 characters");
        }

        user.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);
    }

    public void deleteUser(Integer userId, DeleteUserDTO dto, Integer authenticatedUserId) throws AccessDeniedException {

        System.out.println("in service");

        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only delete your own profile");
        }

        // retrieve the user we want to update
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(userId + " not found"));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        )) {
            throw new UserException("Current password is incorrect");
        }

        userRepository.deleteById(userId);
    }
}
