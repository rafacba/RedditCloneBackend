package com.rafa.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.rafa.dto.PostRequest;
import com.rafa.dto.PostResponse;
import com.rafa.model.Post;
import com.rafa.model.Subreddit;
import com.rafa.model.User;
import com.rafa.model.Vote;
import com.rafa.model.VoteType;
import com.rafa.repository.CommentRepository;
import com.rafa.repository.VoteRepository;
import com.rafa.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired 
	private AuthService authService;
	
	
	
	@Mapping(target= "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target= "description", source= "postRequest.description")
	@Mapping(target= "subreddit", source= "subreddit")
	@Mapping(target= "user", source= "user")
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	@Mapping(target= "id", source= "postId")
	@Mapping(target= "subredditName", source= "subreddit.name")
	@Mapping(target= "userName", source= "user.username")
	@Mapping(target= "postName", source= "postName")
	@Mapping(target= "description", source= "description")
	@Mapping(target= "url", source= "url")
	@Mapping(target="commentCount", expression= "java(commentCount(post))")
	@Mapping(target="duration", expression= "java(getDuration(post))")
	@Mapping(target="upVote", expression = "java(isPostUpVoted(post))")
	@Mapping(target="downVote", expression = "java(isPostDownVoted(post))")
	public abstract PostResponse mapToDto(Post post);
	
	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}
	
	String getDuration(Post post) {		
		return new PrettyTime().format(post.getCreatedDate());
	}
	
	boolean isPostUpVoted(Post post) {
		return checkVoteType(post, VoteType.UPVOTE);
	}
	
	boolean isPostDownVoted(Post post) {
		return checkVoteType(post, VoteType.DOWNVOTE);
	}
	
	private boolean checkVoteType(Post post, VoteType voteType) {
		if(authService.isLoggedIn()) {
			Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
			return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
					.isPresent();
		}
		return false;
	}
	

}
