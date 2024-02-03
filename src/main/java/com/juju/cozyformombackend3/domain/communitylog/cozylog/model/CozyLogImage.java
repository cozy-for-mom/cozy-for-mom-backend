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

@Entity
@Table(name = "cozy_log_image")
public class CozyLogImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cozy_log_image_url", nullable = false, columnDefinition = "TEXT")
	private String cozyLogImageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	private CozyLog cozyLog;

	@Override
	public void delete() {

	}
}
