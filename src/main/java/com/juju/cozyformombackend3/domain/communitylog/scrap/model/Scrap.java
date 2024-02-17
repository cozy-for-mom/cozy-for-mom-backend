package com.juju.cozyformombackend3.domain.communitylog.scrap.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.juju.cozyformombackend3.domain.user.model.User;

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
@Table(name = "scrap")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	// @ManyToOne(fetch = FetchType.LAZY)
	// private CozyLog cozyLog;
	private Long cozyLogId;

	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	public Scrap(User user, Long cozyLogId) {
		this.user = user;
		this.cozyLogId = cozyLogId;
	}
}
