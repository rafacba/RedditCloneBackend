package com.rafa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.model.Post;
import com.rafa.model.Subreddit;
import com.rafa.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllBySubreddit (Subreddit subreddit);
	
	List<Post> findByUser(User user);

}
