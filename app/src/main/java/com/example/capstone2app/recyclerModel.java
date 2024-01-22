package com.example.capstone2app;

public class recyclerModel {
    String postTitle;
    String postContent;
    int postID;

    public recyclerModel(String postTitle, String postContent, int postID) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postID = postID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }
    public int getPostId() {return postID; }
}
