package com.juju.cozyformombackend3.domain.communitylog.comment.model;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "parent_comment_id", nullable = false)
	private Long parentCommentId;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private CozyLog cozyLog;

	@Builder
	private Comment(String content, Long parentCommentId, User user, CozyLog cozyLog) {
		this.content = content;
		this.parentCommentId = parentCommentId;
		this.user = user;
		this.cozyLog = cozyLog;
	}

	public static Comment of(String content, Long parentCommentId, User user, CozyLog cozyLog) {
		return Comment.builder()
			.content(content)
			.parentCommentId(parentCommentId)
			.user(user)
			.cozyLog(cozyLog)
			.build();
	}

	@Override
	public void delete() {

	}

	public void updateCozyLog(CozyLog cozyLog) {
		this.cozyLog = cozyLog;
	}

	public void update(String comment) {
		this.content = comment;
	}
}
