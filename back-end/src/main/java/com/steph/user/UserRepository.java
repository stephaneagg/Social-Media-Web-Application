package com.steph.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailOrUsername(String email, String username);

    List<User> findByDisplayNameContainingIgnoreCase(String query);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
