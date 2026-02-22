package com.steph.post.DTOs;

// Object that gets passed from the front end to update an existing post with
// Only contains attributes that the front end normally has access to update
public class UpdatePostDTO {
    private String content;
    private String imageUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
