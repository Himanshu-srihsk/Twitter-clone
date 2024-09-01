package com.fun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fun.model.Tweet;
import com.fun.model.User;

public interface TweetRepository extends JpaRepository<Tweet, Long>{
   List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
   List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);
   List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);
   @Query("select t from Tweet t join t.likes l where l.user.id=:userId")
   List<Tweet> findByLikesUser_Id(Long userId);
}
