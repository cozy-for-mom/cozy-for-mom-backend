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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cozy_log")
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
	private User user;

	@OneToMany(mappedBy = "cozyLog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<CozyLogImage> cozyLogImageList = new ArrayList<>();

	@OneToMany(mappedBy = "cozyLog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<Comment> commentList = new ArrayList<>();

	@Override
	public void delete() {

	}
}
