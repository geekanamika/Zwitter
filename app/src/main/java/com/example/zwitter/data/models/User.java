package com.example.zwitter.data.models;

import com.example.zwitter.utils.Constants;

public class User {

    private String userId;
    private String noOfPosts;
    private String noOfFollowing;
    private String userBio;
    private String profileDp;
    private String userName;
    private String noOfFollower;

    /*
        constructor used while sign up
     */
    public User(String userId, String userName, String profileDp) {
        this.userId = userId;
        this.userName = userName;
        this.profileDp = profileDp;
        this.userBio = Constants.DEFAULT_BIO;
        this.noOfPosts = "0";
        this.noOfFollower = "0";
        this.noOfFollowing = "0";
    }

    // for firebase
    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(String noOfPosts) {
        this.noOfPosts = noOfPosts;
    }

    public String getNoOfFollower() {
        return noOfFollower;
    }

    public void setNoOfFollower(String noOfFollower) {
        this.noOfFollower = noOfFollower;
    }

    public String getNoOfFollowing() {
        return noOfFollowing;
    }

    public void setNoOfFollowing(String noOfFollowing) {
        this.noOfFollowing = noOfFollowing;
    }
}
