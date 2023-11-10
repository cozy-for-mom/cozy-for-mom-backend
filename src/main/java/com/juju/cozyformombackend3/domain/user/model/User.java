package com.juju.cozyformombackend3.domain.user.model;

import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.meal.model.MealRecord;
import com.juju.cozyformombackend3.domain.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.weight.model.WeightRecord;
import com.juju.cozyformombackend3.global.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {
				@UniqueConstraint(columnNames = {"nickname", "email"},
								name = "nickname_email_unique")})
@Entity
public class User extends BaseEntity {

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<Supplement> supplementList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<BloodSugarRecord> bloodSugarRecordList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<WeightRecord> weightRecordList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<MealRecord> mealRecordList = new ArrayList<>();

	@Column(name = "user_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "user_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "profile_image_url", nullable = false)
	private String profileImageUrl;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Column(name = "email", nullable = false)
	private String email;

	@Builder
	public User(UserType userType, String name, String nickname, String profileImageUrl, Date birth, String email) {
		this.userType = userType;
		this.name = name;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.birth = birth;
		this.email = email;
	}

	@Override
	public void delete() {

	}
}
