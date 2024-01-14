package com.juju.cozyformombackend3.domain.user.model;

import com.juju.cozyformombackend3.domain.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecordType;
import com.juju.cozyformombackend3.domain.meal.model.MealRecord;
import com.juju.cozyformombackend3.domain.meal.model.MealType;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<BabyProfile> babyProfileList = new ArrayList<>();

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

	public Long registerSupplement(String supplementName, int targetCount) {
		Supplement supplement = Supplement.builder()
						.user(this)
						.supplementName(supplementName)
						.targetCount(targetCount)
						.build();
		supplementList.add(supplement);
		return supplement.getSupplementId();
	}

	public Long addBloodSugarRecord(LocalDate date, BloodSugarRecordType type, double level) {
		BloodSugarRecord bloodSugarRecord = BloodSugarRecord.builder()
						.user(this)
						.recordAt(date)
						.bloodSugarRecordType(type)
						.level(level)
						.build();

		if (bloodSugarRecordList.stream()
						.anyMatch(record -> record.getRecordAt().equals(date) && record.getBloodSugarRecordType().equals(type))) {
			throw new IllegalArgumentException("이미 해당 날짜에 해당 타입의 혈당 기록이 존재합니다.");
		} else {
			bloodSugarRecordList.add(bloodSugarRecord);
		}
		return bloodSugarRecord.getBloodSugarId();
	}

	public Long addMealRecord(LocalDateTime datetime, MealType mealType, String mealImageUrl) {
		MealRecord mealRecord = MealRecord.of(this, mealType, mealImageUrl, datetime);
		mealRecordList.add(mealRecord);

		return mealRecord.getMealId();
	}
}
