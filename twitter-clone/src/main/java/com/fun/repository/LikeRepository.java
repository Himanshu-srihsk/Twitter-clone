package com.fun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fun.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
	@Query("select l from Like l where l.user.id =:userId and l.tweet.id=:tweetId")
    public Like isLikeExist(@Param("userId") Long userId, @Param("tweetId") long tweetId);
	@Query("select l from Like l where l.tweet.id=:tweetId")
	public List<Like> findByTweetId(@Param("tweetId") Long tweetId);
}
