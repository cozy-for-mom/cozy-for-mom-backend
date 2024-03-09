package com.juju.cozyformombackend3.domain.userlog.meal.dto.request;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.meal.model.MealRecord;
import com.juju.cozyformombackend3.domain.userlog.meal.model.MealType;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDateTime;
import com.juju.cozyformombackend3.global.validation.annotation.IsMealRecordType;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresent;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMealRecordRequest {

    @NotNull
    @IsLocalDateTime
    @IsPastOrPresent
    private String datetime;

    @NotNull
    @IsMealRecordType
    private String mealType;

    private String mealImageUrl;

    public LocalDateTime getRecordAt() {
        return DateParser.stringDateTimeToLocalDateTime(datetime);
    }

    public MealType getMealType() {
        return MealType.ofType(this.mealType);
    }

    public MealRecord toMealRecord(User user) {
        return MealRecord.of(user, getMealType(), mealImageUrl, getRecordAt());
    }
}
