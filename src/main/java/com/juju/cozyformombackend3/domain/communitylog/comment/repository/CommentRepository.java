package com.juju.cozyformombackend3.domain.communitylog.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.communitylog.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
