package com.rafa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rafa.dto.CommentsDto;
import com.rafa.exceptions.PostNotFoundException;
import com.rafa.mapper.CommentMapper;
import com.rafa.model.Comment;
import com.rafa.model.NotificationEmail;
import com.rafa.model.Post;
import com.rafa.model.User;
import com.rafa.repository.CommentRepository;
import com.rafa.repository.PostRepository;
import com.rafa.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {
	
	private static final String POST_URL="";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	
	
	public void create(CommentsDto commentsDto) {
		
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(()-> new PostNotFoundException("this post does not exists"));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);
		
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
		
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername()+ " Commented on your post", user.getEmail(),message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post)
			.stream().map(commentMapper::mapToDto).collect(Collectors.toList());
		
	}

	public List<CommentsDto> getAllCommentsByUsername(String username) {
		User user = userRepository.findByUsername(username)
					.orElseThrow(()-> new UsernameNotFoundException(username));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
		
	}

}
