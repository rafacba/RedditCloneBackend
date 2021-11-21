package com.rafa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.dto.SubredditDto;
import com.rafa.exceptions.RedditException;
import com.rafa.mapper.SubredditMapper;
import com.rafa.model.Subreddit;
import com.rafa.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	
	private final SubredditRepository subredditRepository;
	//private final AuthService authService;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(subreddit.getId());
		return subredditDto;
	}

	@Transactional(readOnly=true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
				.stream()
				.map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(()-> new RedditException("Subreddit not found with id - "+id));			
		return subredditMapper.mapSubredditToDto(subreddit);
	}

	//Cambio builder por mapper
	
//	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//		return Subreddit.builder()
//					.name("/r/" + subredditDto.getName())
//					.description(subredditDto.getDescription())
//					.user(authService.getCurrentUser())
//					.createdDate(Instant.now())
//					.build();
//		
//	}
//	
//	
//	private SubredditDto mapToDto(Subreddit subreddit) {
//		return  SubredditDto.builder()
//					.name(subreddit.getName())
//					.id(subreddit.getId())
//					.description(subreddit.getDescription())
//					.numberOfPosts(subreddit.getPosts().size())
//					.build();
//	}


}
