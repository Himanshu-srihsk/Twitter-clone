package com.fun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Like;
import com.fun.model.Tweet;
import com.fun.model.User;
import com.fun.repository.LikeRepository;
import com.fun.repository.TweetRepository;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
	private LikeRepository likeRepository;
    @Autowired
	private TweetService tweetService;
    @Autowired
	private TweetRepository tweetRepository;
	
	@Override
	public Like likeTweet(Long tweetId, User user) throws UserException, TweetException {
		Like isLikeExist = likeRepository.isLikeExist(user.getId(), tweetId);
		if(isLikeExist!=null) {
			likeRepository.deleteById(isLikeExist.getId());
			return isLikeExist;
		}
		Tweet tweet = tweetService.findById(tweetId);
		Like like = new Like();
		like.setTweet(tweet);
		like.setUser(user);
		
		Like savedLike = likeRepository.save(like);
		tweet.getLikes().add(savedLike);
		tweetRepository.save(tweet);
		return savedLike;
	}

	@Override
	public List<Like> getAllLikes(Long tweetId) throws TweetException {
		Tweet tweet = tweetService.findById(tweetId);
		List<Like> likes = likeRepository.findByTweetId(tweetId);
		
		return likes;
	}

}
