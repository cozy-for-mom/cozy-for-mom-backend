package com.juju.cozyformombackend3.domain.baby.model;


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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "baby")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Baby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long babyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "baby_profile_id")
	private BabyProfile babyProfile;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Baby(BabyProfile babyProfile, String name, Gender gender) {
		this.babyProfile = babyProfile;
		this.name = name;
		this.gender = gender;
	}

	public static Baby of(BabyProfile babyProfile, String name, Gender gender) {
		return new Baby(babyProfile, name, gender);
	}
}


