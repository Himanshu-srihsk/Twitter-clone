package com.fun.service;

import java.util.List;

import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Like;
import com.fun.model.User;

public interface LikeService {
  public Like likeTweet(Long tweetId, User user) throws UserException, TweetException;
  public List<Like> getAllLikes(Long tweetId) throws TweetException;
}
