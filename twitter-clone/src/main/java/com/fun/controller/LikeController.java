package com.fun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fun.dto.LikeDto;
import com.fun.dto.mapper.LikeDtoMapper;
import com.fun.exception.TweetException;
import com.fun.exception.UserException;
import com.fun.model.Like;
import com.fun.model.User;
import com.fun.service.LikeService;
import com.fun.service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {
	@Autowired
  private UserService userService;
	@Autowired
  private LikeService likeService;
	
	@PostMapping("/{tweetId}/like")
	public ResponseEntity<LikeDto>  likeTweet(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		Like like = likeService.likeTweet(tweetId, user);
		LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);
		return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);
	}
  
	@PostMapping("/tweet/{tweetId}")
	public ResponseEntity<List<LikeDto>>  getAllLikes(@PathVariable Long tweetId,
			@RequestHeader("Authorization") String jwt) throws UserException, TweetException{
		User user = userService.findUserProfileByJwt(jwt);
		List<Like> likes = likeService.getAllLikes(tweetId);
		List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);
		return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
	}
  
}
