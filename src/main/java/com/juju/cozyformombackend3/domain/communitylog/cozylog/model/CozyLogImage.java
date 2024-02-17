package com.juju.cozyformombackend3.domain.communitylog.cozylog.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cozy_log_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CozyLogImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cozy_log_image_url", nullable = false, columnDefinition = "TEXT")
	private String cozyLogImageUrl;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private CozyLog cozyLog;

	private CozyLogImage(CozyLog cozyLog, String cozyLogImageUrl, String description) {
		this.cozyLogImageUrl = cozyLogImageUrl;
		this.description = description;
		this.cozyLog = cozyLog;
	}

	public static CozyLogImage of(CozyLog cozyLog, String cozyLogImageUrl, String description) {
		if (cozyLog == null) {
			return new CozyLogImage(null, cozyLogImageUrl, description);
		}
		return new CozyLogImage(cozyLog, cozyLogImageUrl, description);
	}

	public void updateCozyLog(CozyLog cozyLog) {
		this.cozyLog = cozyLog;
	}

	@Override
	public void delete() {

	}
}
