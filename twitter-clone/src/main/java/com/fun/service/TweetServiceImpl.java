package com.fun.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Tweet;
import com.fun.model.User;
import com.fun.repository.TweetRepository;
import com.fun.request.TweetReplyRequest;

@Service
public class TweetServiceImpl implements TweetService{
	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public Tweet createTweet(Tweet req, User user) throws UserException, TweetException {
		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImage(req.getImage());
		tweet.setUser(user);
		tweet.setReply(false);
		tweet.setTweet(true);
		tweet.setVideo(req.getVideo());
		
//		tweet.builder().content(req.getContent())
//		.createdAt(LocalDateTime.now()).image(req.getImage())
//		.user(user).isReply(false).video(req.getVideo()).build();
		return tweetRepository.save(tweet);
	}

	@Override
	public List<Tweet> findAllTweet() {
		
		return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
	}

	@Override
	public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
		Tweet tweet = findById(tweetId);
		if(tweet.getRetweetUser().contains(user)) {
			tweet.getRetweetUser().remove(user);
		}else {
			tweet.getRetweetUser().add(user);
		}
		return tweetRepository.save(tweet);
	}

	@Override
	public Tweet findById(Long tweetId) throws TweetException {
		Tweet tweet = tweetRepository.findById(tweetId)
				.orElseThrow(() -> new TweetException("Tweet not found with id"+tweetId));
		return tweet;
	}

	@Override
	public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {
		Tweet tweet = findById(tweetId);
		if(!userId.equals(tweet.getUser().getId())) {
			throw new UserException("You cannot delete another user's tweet");
		}
		tweetRepository.deleteById(tweet.getId());
		
	}

	@Override
	public Tweet removeFromReTweet(Long tweetId, User user) throws TweetException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException {
		
		Tweet replyFor = findById(req.getTweetId());
		Tweet tweet = new Tweet();
		tweet.setContent(req.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImage(req.getImage());
		tweet.setUser(user);
		tweet.setReply(true);
		tweet.setTweet(false);
		tweet.setReplyFor(replyFor);
		
		Tweet savedReply = tweetRepository.save(tweet);
		replyFor.getReplyTweets().add(savedReply);
		tweetRepository.save(replyFor);
		return replyFor;
	}

	@Override
	public List<Tweet> getUserTweet(User user) {
		
		return tweetRepository.findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Tweet> findByLikesContainsUser(User user) {
		
		return tweetRepository.findByLikesUser_Id(user.getId());
	}


}
