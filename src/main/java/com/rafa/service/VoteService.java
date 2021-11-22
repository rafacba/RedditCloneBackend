package com.rafa.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.dto.VoteDto;
import com.rafa.exceptions.PostNotFoundException;
import com.rafa.exceptions.RedditException;
import com.rafa.model.Post;
import com.rafa.model.Vote;
import com.rafa.model.VoteType;
import com.rafa.repository.PostRepository;
import com.rafa.repository.VoteRepository;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class VoteService {
	
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private AuthService authService;
	
	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(()-> new PostNotFoundException("Post not found with ID "+ voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new RedditException("You have already " + voteDto.getVoteType());
		}
		if(VoteType.UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() -1);
		}
		
		voteRepository.save(mapToVote(voteDto,post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
	

}
