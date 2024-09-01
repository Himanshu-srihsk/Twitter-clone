package com.fun.service;

import java.util.List;

import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Tweet;
import com.fun.model.User;
import com.fun.request.TweetReplyRequest;

public interface TweetService {
   public Tweet createTweet(Tweet req, User user) throws UserException, TweetException;
   public List<Tweet> findAllTweet(); // to show all tweet on homepage
   public Tweet retweet(Long tweetId, User user) throws UserException, TweetException;
   public Tweet findById(Long tweetId) throws TweetException;
   public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException;
   public Tweet removeFromReTweet(Long tweetId, User user) throws TweetException, UserException;
   public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException;
   public List<Tweet> getUserTweet(User user);
   public List<Tweet> findByLikesContainsUser(User user);
}
