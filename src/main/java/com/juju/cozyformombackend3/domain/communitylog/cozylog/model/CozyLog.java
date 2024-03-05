package com.juju.cozyformombackend3.domain.communitylog.cozylog.model;

import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.comment.model.Comment;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cozy_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CozyLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "mode", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private CozyLogMode mode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "view")
	private Long view = 0L;

	@OneToMany(mappedBy = "cozyLog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<CozyLogImage> cozyLogImageList = new ArrayList<>();

	@OneToMany(mappedBy = "cozyLog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<Comment> commentList = new ArrayList<>();

	@Builder
	private CozyLog(User user, String title, String content, List<CozyLogImage> imageList, CozyLogMode mode) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.mode = mode;
		this.cozyLogImageList.addAll(imageList);
	}

	public static CozyLog of(User user, String title, String content, List<CozyLogImage> imageList, CozyLogMode mode) {
		CozyLog newCozyLog = CozyLog.builder()
			.user(user)
			.title(title)
			.content(content)
			.imageList(imageList)
			.mode(mode)
			.build();
		imageList.forEach(image -> image.updateCozyLog(newCozyLog));

		return newCozyLog;
	}

	public void updateTextContent(String title, String content, CozyLogMode mode) {
		this.title = title;
		this.content = content;
		this.mode = mode;
	}

	public void updateImageList(List<CozyLogImage> imageList) {
		this.cozyLogImageList.clear();
		this.cozyLogImageList.addAll(imageList);
		imageList.forEach(image -> image.updateCozyLog(this));
	}

	public Comment addComment(Comment comment) {
		this.commentList.add(comment);
		comment.updateCozyLog(this);

		return comment;
	}

	@Override
	public void delete() {

	}
}
