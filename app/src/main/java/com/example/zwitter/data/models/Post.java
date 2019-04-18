package com.example.zwitter.data.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    /*
    {
      "post_message": "hey this is test message",
      "date": 0,
      "user_id": "",
      "no_of_comments" : 0,
      "no_of_likes" : 0
    }
     */
    private String postId;
    private String profileDp;
    private String postMessage;
    private String postAuthorId;
    private long time;
    private String userName;
    private boolean originalOrReply;
    private String noOfLikes;
    private String noOfComments;
    public Map<String, Boolean> stars = new HashMap<>();

    // for firebase
    public Post() {

    }

    public Post(String postAuthorId , String postId, String dp, String postMessage, long time,
                String userName, boolean originalOrReply) {
        this.postAuthorId = postAuthorId;
        this.postId = postId;
        this.profileDp = dp;
        this.postMessage = postMessage;
        this.time = time;
        this.userName = userName;
        this.originalOrReply = originalOrReply;
        this.noOfLikes = "0";
        this.noOfComments = "0";
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("postAuthorId" , postAuthorId);
        result.put("postId", postId);
        result.put("userName", userName);
        result.put("profileDp", profileDp);
        result.put("postMessage", postMessage);
        result.put("time", time);
        result.put("noOfLikes", noOfLikes);
        result.put("noOfComments", noOfComments);
        result.put("originalOrReply", originalOrReply);
        result.put("stars", stars);
        return result;
    }

    public String getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(String postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getProfileDp() {
        return profileDp;
    }

    public void setProfileDp(String profileDp) {
        this.profileDp = profileDp;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isOriginalOrReply() {
        return originalOrReply;
    }

    public void setOriginalOrReply(boolean originalOrReply) {
        this.originalOrReply = originalOrReply;
    }

    public String getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(String noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(String noOfComments) {
        this.noOfComments = noOfComments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
