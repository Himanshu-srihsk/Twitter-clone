package com.fun.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fun.dto.LikeDto;
import com.fun.dto.TweetDto;
import com.fun.dto.UserDto;
import com.fun.model.Like;
import com.fun.model.User;

public class LikeDtoMapper {
  public static LikeDto toLikeDto(Like like, User reqUser) {
	  UserDto userDto = UserDtoMapper.toUserDto(like.getUser());
	  UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
	  TweetDto tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);
	  
	  
	  LikeDto likeDto = new LikeDto();
	  likeDto.setId(like.getId());
	  likeDto.setUserDto(reqUserDto);
	  return likeDto;
  }
  
  public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser){
	  List<LikeDto> likeDtos = new ArrayList();
	  for(Like like:likes) {
		  UserDto userDto = UserDtoMapper.toUserDto(like.getUser());
		  TweetDto tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser); 
		  LikeDto likeDto = new LikeDto();
		  likeDto.setId(like.getId());
		  likeDto.setUserDto(userDto);
		  likeDtos.add(likeDto);
	  }
	  return likeDtos;
  }
}
