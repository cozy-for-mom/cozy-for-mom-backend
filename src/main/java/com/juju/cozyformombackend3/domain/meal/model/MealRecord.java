package com.juju.cozyformombackend3.domain.meal.model;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;
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
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "meal_record")
@Entity
public class MealRecord extends BaseEntity {

	@Column(name = "meal_id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mealId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "meal_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private MealType mealType;

	@Column(name = "meal_image_url", nullable = false)
	private String mealImageUrl;

	@Column(name = "record_at", nullable = false)
	private LocalDateTime recordAt;

	@Builder
	private MealRecord(User user, MealType mealType, String mealImageUrl, LocalDateTime recordAt) {
		this.user = user;
		this.mealType = mealType;
		this.mealImageUrl = mealImageUrl;
		this.recordAt = recordAt;
	}

	public static MealRecord of(User user, MealType mealType, String mealImageUrl, LocalDateTime recordAt) {
		return MealRecord.builder()
						.user(user)
						.mealType(mealType)
						.mealImageUrl(mealImageUrl)
						.recordAt(recordAt)
						.build();
	}

	@Override
	public void delete() {
	}

	public Long update(LocalDateTime datetime, MealType mealType, String mealImageUrl) {
		this.recordAt = datetime;
		this.mealType = mealType;
		this.mealImageUrl = mealImageUrl;

		return this.mealId;
	}
}
