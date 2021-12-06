package com.rafa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.dto.PostRequest;
import com.rafa.dto.PostResponse;
import com.rafa.exceptions.PostNotFoundException;
import com.rafa.exceptions.RedditException;
import com.rafa.exceptions.SubredditNotFoundException;
import com.rafa.mapper.PostMapper;
import com.rafa.model.Post;
import com.rafa.model.Subreddit;
import com.rafa.model.User;
import com.rafa.repository.PostRepository;
import com.rafa.repository.SubredditRepository;
import com.rafa.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;

	public void create(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
			.orElseThrow(()-> new RedditException("No existe subreddit"));
		User currentUser = authService.getCurrentUser();
		
		postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
		
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(()-> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}


}
