package com.example.twitterclone.dto;

public class TweetDto {
    private String content;
    private Long parentTweetId;
    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getParentTweetId() { return parentTweetId; }
    public void setParentTweetId(Long parentTweetId) { this.parentTweetId = parentTweetId; }
}
