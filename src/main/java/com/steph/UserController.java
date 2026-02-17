package com.steph;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        return List.of(
                new User(
                        1,
                        "Stephane",
                        "StrongPassword",
                        "stephanegg@gmail.com"
                ),
                new User(
                        2,
                        "Bianca",
                        "1234",
                        "biancatho@gmail.com"
                ),
                new User(
                        3,
                        "Jane",
                        "12345",
                        "janedoe@gmail.com"
                )
        );
    }
}
