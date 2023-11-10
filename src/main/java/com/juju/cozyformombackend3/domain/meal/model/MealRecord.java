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
import lombok.Builder;
import lombok.NoArgsConstructor;

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

	@Builder
	public MealRecord(User user, MealType mealType, String mealImageUrl) {
		this.user = user;
		this.mealType = mealType;
		this.mealImageUrl = mealImageUrl;
	}

	@Override
	public void delete() {
	}

}
