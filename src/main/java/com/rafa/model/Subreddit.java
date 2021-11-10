package com.rafa.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message="Community name is required")
	private String name;
	
	@NotBlank(message="Description is required")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Post> posts;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

}
