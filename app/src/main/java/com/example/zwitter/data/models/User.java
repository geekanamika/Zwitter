package com.example.zwitter.data.models;

import com.example.zwitter.utils.Constants;

import java.util.ArrayList;

public class User {

    private String userName;
    private String userBio;
    private String profileDp;
    private long noOfPosts;
    private long noOfFollower;
    private long noOfFollowing;
    private ArrayList<String> postIds;
    private ArrayList<String> followerIds;
    private ArrayList<String> followingIds;

    public User(String userId, String userName, String profileDp) {
        this.userName = userName;
        this.profileDp = profileDp;
        this.userBio = Constants.DEFAULT_BIO;
        this.noOfPosts = 0;
        this.noOfFollower = 0;
        this.noOfFollowing = 0;
        this.postIds = new ArrayList<>();
        this.followerIds = new ArrayList<>();
        this.followingIds = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getProfileDp() {
        return profileDp;
    }

    public void setProfileDp(String profileDp) {
        this.profileDp = profileDp;
    }

    public long getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(long noOfPosts) {
        this.noOfPosts = noOfPosts;
    }

    public long getNoOfFollower() {
        return noOfFollower;
    }

    public void setNoOfFollower(long noOfFollower) {
        this.noOfFollower = noOfFollower;
    }

    public long getNoOfFollowing() {
        return noOfFollowing;
    }

    public void setNoOfFollowing(long noOfFollowing) {
        this.noOfFollowing = noOfFollowing;
    }

    public ArrayList<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(ArrayList<String> postIds) {
        this.postIds = postIds;
    }

    public ArrayList<String> getFollowerIds() {
        return followerIds;
    }

    public void setFollowerIds(ArrayList<String> followerIds) {
        this.followerIds = followerIds;
    }

    public ArrayList<String> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(ArrayList<String> followingIds) {
        this.followingIds = followingIds;
    }
}
