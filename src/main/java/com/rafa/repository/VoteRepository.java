package com.rafa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.model.Post;
import com.rafa.model.User;
import com.rafa.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
