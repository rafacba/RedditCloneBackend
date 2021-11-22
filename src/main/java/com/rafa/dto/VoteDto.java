package com.rafa.dto;

import com.rafa.model.VoteType;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	
	private VoteType voteType;
	private Long postId;

}
