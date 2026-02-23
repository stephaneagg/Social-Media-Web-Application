package com.steph.comment;

import com.steph.comment.DTOs.UpdateCommentDTO;
import com.steph.user.User;
import com.steph.post.Post;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many comments can belong to one User
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // Many Comments can belong to one Post
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Constructors //

    public Comment(){
    }

    public Comment(Integer id, User user, Post post, String content, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.content = content;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // Helpers //

    /**
     * Function take an instance of UpdateCommentDTO and updates the this comment with
     * its attrtibutes
     * @param updateCommentDTO object containing user attributes that should are updateable
     */
    public void updateComment(UpdateCommentDTO updateCommentDTO) {
        if (updateCommentDTO.getContent() != null) this.content = updateCommentDTO.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(user, comment.user) && Objects.equals(post, comment.post) && Objects.equals(content, comment.content) && Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, post, content, createdAt);
    }
}
