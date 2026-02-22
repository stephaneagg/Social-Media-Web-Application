package com.steph.user.DTOs;

// Object that gets passed from the front end to update an existing user with
// Only contains attributes that the front end normally has access to update
public class UpdateUserDTO {
    private String displayName;
    private String bio;
    private String profileImageUrl;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

