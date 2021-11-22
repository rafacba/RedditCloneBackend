package com.rafa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rafa.dto.CommentsDto;
import com.rafa.model.Comment;
import com.rafa.model.Post;
import com.rafa.model.User;

@Mapper(componentModel= "spring")
public interface CommentMapper {
	
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="text", source= "commentsDto.text")
	@Mapping(target="createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target="post", source="post")
	Comment map(CommentsDto commentsDto,Post post, User user);
	
	@Mapping(target="postId", expression= "java(comment.getPost().getPostId())")
	@Mapping(target="username", expression= "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);

}
