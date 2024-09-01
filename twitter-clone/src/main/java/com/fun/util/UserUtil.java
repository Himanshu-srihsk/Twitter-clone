package com.fun.util;

import com.fun.model.User;

public class UserUtil {
   public static final boolean isReqUser(User reqUser, User User2) {
	   return reqUser.getId().equals(User2.getId());
   }
   
   public static final boolean isFollowedByReqUser(User reqUser, User user2) {
	   return reqUser.getFollowings().contains(user2);
   }
}
