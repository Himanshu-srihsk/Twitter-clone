package com.fun.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
	
	
	private String image;
	private String video;
	
	private String content;
	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();
	@OneToMany
	private List<Tweet> replyTweets = new ArrayList<>();
	@ManyToMany
	private List<User> retweetUser = new ArrayList<>();
	
	@ManyToOne
	private Tweet replyFor;
	
	private boolean isReply;
	private boolean isTweet;
	@ManyToOne
	private User user;
	
	private LocalDateTime createdAt;
	
	
	
   
}
