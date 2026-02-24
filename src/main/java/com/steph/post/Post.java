package com.steph.post;


import com.steph.post.DTOs.UpdatePostDTO;
import com.steph.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many Posts can belong to one user. But, a post always have a user
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content_text", length = 2000)
    private String contentText;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Method is authomatically executed before a post is inserted
    // For now we just want to get the time the post is created
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    // Constructors //

    protected Post(){
    }

    public Post(Integer id, User user, String contentText, String imageUrl, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.contentText = contentText;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters //

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // Helpers //

    /**
     * Function take an instance of UpdatePostDTO and updates the this post with
     * its attrtibutes
     * @param updatePostDTO object containing post attributes that should are updateable
     */
    public void updatePost(UpdatePostDTO updatePostDTO) {
        if (updatePostDTO.getContent() != null) this.contentText = updatePostDTO.getContent();
        if (updatePostDTO.getImageUrl() != null) this.imageUrl = updatePostDTO.getImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(user, post.user) && Objects.equals(contentText, post.contentText) && Objects.equals(imageUrl, post.imageUrl) && Objects.equals(createdAt, post.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, contentText, imageUrl, createdAt);
    }
}