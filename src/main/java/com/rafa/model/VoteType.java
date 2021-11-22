package com.rafa.model;

//import java.util.*;

//import com.rafa.exceptions.RedditException;

public enum VoteType {
	
	UPVOTE(1), DOWNVOTE(-1),;
	
	//private int direction;
	
	VoteType(int direction){
		
	}
	
//	public static VoteType lookup(Integer direction) {
//		return Arrays.stream(VoteType.values())
//					.filter(value-> value.getDirection().equals(direction))
//					.findAny()
//					.orElseThrow(()-> new RedditException("vote not found"));
//	}

}
