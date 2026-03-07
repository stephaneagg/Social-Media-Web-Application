package com.steph.user;

import com.steph.user.DTOs.CreateUserDTO;
import com.steph.user.DTOs.UpdateUserDTO;
import com.steph.user.DTOs.UserProfileDTO;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public UserProfileDTO updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable Integer id){
        return userService.updateUser(updateUserDTO, id);
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
