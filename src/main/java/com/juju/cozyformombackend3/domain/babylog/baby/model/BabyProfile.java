package com.juju.cozyformombackend3.domain.babylog.baby.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.dto.request.ModifyBabyProfileRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;
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

	// @OneToMany(mappedBy = "babyProfile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	// List<GrowthDiary> growthDiaryList = new ArrayList<>();

	@OneToMany(mappedBy = "babyProfile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	List<GrowthReport> growthReportList = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "twins", nullable = false)
	private int twins;

	@Column(name = "pregnant_at", nullable = false)
	private LocalDate pregnantAt;

	@Column(name = "due_at", nullable = false)
	private LocalDate dueAt;

	@Column(name = "image_url", columnDefinition = "TEXT")
	private String imageUrl;

	private BabyProfile(User user, int twins, LocalDate pregnantAt, LocalDate dueAt, String imageUrl) {
		this.user = user;
		this.twins = twins;
		this.pregnantAt = pregnantAt;
		this.dueAt = dueAt;
		this.imageUrl = imageUrl;
	}

	public static BabyProfile of(User user, int twins, LocalDate pregnantAt, LocalDate dueAt, String imageUrl) {
		return new BabyProfile(user, twins, pregnantAt, dueAt, imageUrl);
	}

	public void addBaby(List<Baby> babyList) {
		this.babyList.addAll(babyList);
	}

	public void update(ModifyBabyProfileRequest request) {
		this.dueAt = request.getDueAt();
		this.imageUrl = request.getProfileImageUrl();
	}
}