package com.rafa.mapper;

import com.rafa.dto.SubredditDto;
import com.rafa.model.Post;
import com.rafa.model.Subreddit;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel= "spring")
public interface SubredditMapper {
	
	
	@Mapping(target="numberOfPosts", expression= "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size();
	}
	
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "user", ignore = true)
	@InheritInverseConfiguration
	@Mapping(target="posts",ignore= true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
	
	
	
}
