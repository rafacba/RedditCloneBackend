package com.rafa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.dto.PostRequest;
import com.rafa.dto.PostResponse;
import com.rafa.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<String>("Post created successfully",HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getpostsByUsername(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
	

}
