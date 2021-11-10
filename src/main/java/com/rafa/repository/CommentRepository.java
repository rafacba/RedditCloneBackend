package com.rafa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.model.Comment;
import com.rafa.model.Post;
import com.rafa.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPost(Post post);
	
	List<Comment> findAllByUser(User user);

}
