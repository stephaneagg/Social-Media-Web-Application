package com.steph.user;

import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId) throws AccessDeniedException {

        return userService.updateUser(dto, userId, authenticatedUserId);
    }

    // SHOULD BE REMOVED IN FAVOR FOR AuthenticationController's REGISTER METHOD
//    @PostMapping
//    public UserProfileDTO addNewUser(@RequestBody CreateUserDTO createUserDTO) {
//        return userService.createUser(createUserDTO);
//    }
    //

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId,
                           @AuthenticationPrincipal(expression = "id") Integer authenticatedUserId)
            throws AccessDeniedException {

        userService.deleteUser(userId, authenticatedUserId);
    }

}
