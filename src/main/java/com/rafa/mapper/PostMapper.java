package com.rafa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rafa.dto.PostRequest;
import com.rafa.dto.PostResponse;
import com.rafa.model.Post;
import com.rafa.model.Subreddit;
import com.rafa.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {
	
	
	@Mapping(target = "voteCount", ignore = true)
	@Mapping(target= "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target= "subreddit", source= "subreddit")
	@Mapping(target= "user", source= "user")
	@Mapping(target= "description", source= "postRequest.description")
	Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	@Mapping(target= "id", source= "postId")
	@Mapping(target= "postName", source= "postName")
	@Mapping(target= "description", source= "description")
	@Mapping(target= "url", source= "url")
	@Mapping(target= "subredditName", source= "subreddit.name")
	@Mapping(target= "userName", source= "user.username")
	PostResponse mapToDto(Post post);

}
