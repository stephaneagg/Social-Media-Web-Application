package com.steph.user;

import com.steph.config.JwtService;
import com.steph.user.DTOs.CreateUserDTO;
import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<UserProfileDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserProfileDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{userId}")
    public UserProfileDTO updateUser(
            @PathVariable Integer userId,
            @RequestBody UpdateUserDTO dto,
            @RequestHeader("Authorization") String authHeader) throws AccessDeniedException {

        // Extract the token from "Bearer <token>"
        String token = authHeader.replace("Bearer ", "");
        Integer authenticatedUserId = jwtService.extractUserId(token);

        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        return userService.updateUser(dto, userId);
    }

    // SHOULD BE REMOVED IN FAVOR FOR AuthenticationController's REGISTER METHOD
//    @PostMapping
//    public UserProfileDTO addNewUser(@RequestBody CreateUserDTO createUserDTO) {
//        return userService.createUser(createUserDTO);
//    }
    //

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
