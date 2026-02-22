package com.steph.user;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "username_unique", columnNames = "username"),
                @UniqueConstraint(name= "email_unique", columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (unique = true, length = 50)
    private String username;

    @Column (unique = true, length = 255)
    private String email;

    @Column (name = "password_hash", nullable = false)
    private String passwordHash;

    @Column (name= "display_name", length = 100)
    private String displayName;

    @Column (columnDefinition = "TEXT")
    private String bio;

    @Column (name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column (name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Method is authomatically executed before a user is inserted
    // For now we just want to get the time the user is created
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    // CONSTRUCTORS //

    protected User() {
    }

    public User(Integer id, String username, String email, String passwordHash, String displayName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
    }

    // GETTERS //

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // SETTERS //

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // HELPERS //

    /**
     * Function take an instance of UpdateUserDTO and updates the this user with
     * its attrtibutes
     * @param updateUserDTO object containing user attributes that should are updateable
     */
    public void updateFromDTO(UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getDisplayName() != null) this.displayName = updateUserDTO.getDisplayName();
        if (updateUserDTO.getBio() != null) this.bio = updateUserDTO.getBio();
        if (updateUserDTO.getProfileImageUrl() != null) this.profileImageUrl = updateUserDTO.getProfileImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash) && Objects.equals(displayName, user.displayName) && Objects.equals(bio, user.bio) && Objects.equals(profileImageUrl, user.profileImageUrl) && Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, passwordHash, displayName, bio, profileImageUrl, createdAt);
    }
}
