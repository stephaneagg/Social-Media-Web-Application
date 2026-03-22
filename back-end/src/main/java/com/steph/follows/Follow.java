package com.steph.follows;

import com.steph.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "follows")
public class Follow {

    @EmbeddedId
    private FollowId id; // The identity of this entity is this entire object -> PRIMARY KEY (followerId, followedId)

    @ManyToOne(optional = false)
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(optional = false)
    @MapsId("followedId")
    @JoinColumn(name = "followed_id")
    private User followed;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Method is authomatically executed before a post is inserted
    // For now we just want to get the time the post is created
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    // Constructors //

    protected Follow(){}

    public Follow(User follower, User followed) {
        this.id = new FollowId(follower.getId(), followed.getId());
        this.follower = follower;
        this.followed = followed;
    }

    // Getters and Setters //

    public FollowId getId() {
        return id;
    }

    public void setId(FollowId id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // Helpers //

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(id, follow.id) && Objects.equals(follower, follow.follower) && Objects.equals(followed, follow.followed) && Objects.equals(createdAt, follow.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, follower, followed, createdAt);
    }
}


