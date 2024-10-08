package com.fun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fun.dto.TweetDto;
import com.fun.dto.mapper.TweetDtoMapper;
import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Tweet;
import com.fun.model.User;
import com.fun.request.TweetReplyRequest;
import com.fun.response.ApiResponse;
import com.fun.service.TweetService;
import com.fun.service.UserService;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
	@Autowired
     private TweetService tweetService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet req,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createTweet(req, user);
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDto>(tweetDto, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/reply")
	public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.createdReply(req, user);
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDto>(tweetDto, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{tweetId}/retweet")
	public ResponseEntity<TweetDto> retweet(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.retweet(tweetId, user);
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDto>(tweetDto, HttpStatus.OK);
		
	}
	
	@GetMapping("/{tweetId}")
	public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		Tweet tweet = tweetService.findById(tweetId);
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		return new ResponseEntity<TweetDto>(tweetDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{tweetid}")
	public ResponseEntity<ApiResponse> deleteTweetById(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		tweetService.deleteTweetById(tweetId, user.getId());
		ApiResponse res= new ApiResponse();
		res.setMessage("tweet deleted successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TweetDto>> getAllTweets(
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.findAllTweet();
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TweetDto>> getUsersAllTweets(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		//User user = userService.findUserProfileByJwt(jwt);
		User user = userService.findUserById(userId);
		List<Tweet> tweets = tweetService.getUserTweet(user);
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TweetDto>> findTweetByLikesContainsUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Tweet> tweets = tweetService.findByLikesContainsUser(user);
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		return new ResponseEntity<>(tweetDtos, HttpStatus.OK);
		
	}
	
     
}
