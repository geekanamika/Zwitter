package com.example.zwitter.data.models;

import java.util.ArrayList;

public class Post {
    /*
    "post_id": {
      "replies": ["post_id", "post_id", "post_id"],
      "post_message": "hey this is test message",
      "date": 0,
      "user_id": "",
      "original_or_reply": true,
      "comments": ["post_id", "post_id", "post_id"],
      "likes": [
        {
          "uid": "8tyerygwie",
          "time": "9am"
        },
        {
          "uid": "8tyerygwie",
          "time": "9am"
        }
      ]
    },
     */

    private String postMessage;
    private long time;
    private String userId;
    private boolean originalOrReply;
    private long noOfLikes;
    private long noOfComments;
    private ArrayList<String> likesList;
    private ArrayList<String> commentsList;
    private ArrayList<String> replyList;

    public Post(String postMessage, long time,
                String userId, boolean originalOrReply) {
        this.postMessage = postMessage;
        this.time = time;
        this.userId = userId;
        this.originalOrReply = originalOrReply;
        this.noOfLikes = 0;
        this.noOfComments = 0;
        this.likesList = new ArrayList<>();
        this.commentsList = new ArrayList<>();
        this.replyList = new ArrayList<>();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isOriginalOrReply() {
        return originalOrReply;
    }

    public void setOriginalOrReply(boolean originalOrReply) {
        this.originalOrReply = originalOrReply;
    }

    public long getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(long noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public long getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(long noOfComments) {
        this.noOfComments = noOfComments;
    }

    public ArrayList<String> getLikesList() {
        return likesList;
    }

    public void setLikesList(ArrayList<String> likesList) {
        this.likesList = likesList;
    }

    public ArrayList<String> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(ArrayList<String> commentsList) {
        this.commentsList = commentsList;
    }

    public ArrayList<String> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<String> replyList) {
        this.replyList = replyList;
    }
}
