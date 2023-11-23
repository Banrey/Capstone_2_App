package com.example.capstone2app;

public class recyclerModel {
    String postTitle;
    String postContent;

    public recyclerModel(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }
}
