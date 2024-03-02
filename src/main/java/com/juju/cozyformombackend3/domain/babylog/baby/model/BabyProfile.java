package com.juju.cozyformombackend3.domain.babylog.baby.model;

import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "baby_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BabyProfile {

	@OneToMany(mappedBy = "babyProfile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	List<Baby> babyList = new ArrayList<>();

	@OneToMany(mappedBy = "babyProfile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	List<GrowthDiary> growthDiaryList = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "baby_profile_id", nullable = false)
	private Long babyProfileId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "twins", nullable = false)
	private int twins;

	@Column(name = "pregnant_at", nullable = false)
	private LocalDateTime pregnantAt;

	@Column(name = "due_at", nullable = false)
	private LocalDateTime dueAt;

	private BabyProfile(User user, int twins, LocalDateTime pregnantAt, LocalDateTime dueAt) {
		this.user = user;
		this.twins = twins;
		this.pregnantAt = pregnantAt;
		this.dueAt = dueAt;
	}

	public static BabyProfile of(User user, int twins, LocalDateTime pregnantAt, LocalDateTime dueAt) {
		return new BabyProfile(user, twins, pregnantAt, dueAt);
	}
}