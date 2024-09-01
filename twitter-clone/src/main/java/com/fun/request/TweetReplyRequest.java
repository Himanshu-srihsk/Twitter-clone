package com.fun.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TweetReplyRequest {
   private String content;
   private Long tweetId;
   private LocalDateTime createdAt;
   private String image;
   
}
