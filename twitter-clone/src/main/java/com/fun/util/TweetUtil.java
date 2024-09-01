package com.fun.util;

import com.fun.model.Like;
import com.fun.model.Tweet;
import com.fun.model.User;

public class TweetUtil {
   public static final boolean isLikedByReqUser(User reqUser, Tweet tweet) {
	   for(Like like:tweet.getLikes()) {
		   if(like.getUser().getId().equals(reqUser.getId())) {
			   return true;
		   }
	   }
	   return false;
   }
   
   public static final boolean isRetweetedByReqUser(User reqUser, Tweet tweet) {
	   for(User user:tweet.getRetweetUser()) {
		   if(user.getId().equals(reqUser.getId())) {
			   return true;
		   }
	   }
	   return false;
   }
}
