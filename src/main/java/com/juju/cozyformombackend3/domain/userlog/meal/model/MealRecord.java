package com.juju.cozyformombackend3.domain.userlog.meal.model;

import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "meal_record")
@Entity
public class MealRecord extends BaseEntity {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "meal_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "record_at", nullable = false)
    private LocalDateTime recordAt;

    @Builder
    private MealRecord(User user, MealType mealType, String imageUrl, LocalDateTime recordAt) {
        this.user = user;
        this.mealType = mealType;
        this.imageUrl = imageUrl;
        this.recordAt = recordAt;
    }

    public static MealRecord of(User user, MealType mealType, String imageUrl, LocalDateTime recordAt) {
        return MealRecord.builder()
            .user(user)
            .mealType(mealType)
            .imageUrl(imageUrl)
            .recordAt(recordAt)
            .build();
    }

    @Override
    public void delete() {
    }

    public void update(LocalDateTime datetime, MealType mealType, String imageUrl) {
        this.recordAt = datetime;
        this.mealType = mealType;
        this.imageUrl = imageUrl;
    }
}
