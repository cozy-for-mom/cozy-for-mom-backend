package com.juju.cozyformombackend3.domain.communitylog.scrap.model;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.domain.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrap")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private Long cozyLogId;

	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder
	public Scrap(User user, Long cozyLogId) {
		this.user = user;
		this.cozyLogId = cozyLogId;
	}
}
