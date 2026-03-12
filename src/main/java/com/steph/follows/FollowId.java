package com.steph.follows;

import jakarta.persistence.Embeddable;

import java.util.Objects;


/*
This is a value object that has no identity on its own. This serves as the identity for the follows relationship


 */
@Embeddable
public class FollowId {

    private Integer followerId;
    private Integer followedId;

    // Constructors //

    protected FollowId(){}

    public FollowId(Integer followerId, Integer followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    // Getters and Setters //

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    // Helpers //

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(followerId, followId.followerId) && Objects.equals(followedId, followId.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followedId);
    }

    @Override
    public String toString() {
        return followerId + " follows " + followedId;
    }
}
