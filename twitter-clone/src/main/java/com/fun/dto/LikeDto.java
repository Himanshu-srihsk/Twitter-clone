package com.fun.dto;

import lombok.Data;

@Data
public class LikeDto {
   private Long id;
   private UserDto userDto;
   private TweetDto tweetdto;
   
}
