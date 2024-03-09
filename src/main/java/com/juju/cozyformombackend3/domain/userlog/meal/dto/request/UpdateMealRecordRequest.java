package com.juju.cozyformombackend3.domain.userlog.meal.dto.request;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.domain.userlog.meal.model.MealType;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDateTime;
import com.juju.cozyformombackend3.global.validation.annotation.IsMealRecordType;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresent;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMealRecordRequest {

    @NotNull(message = "id 값을 입력해주세요.")
    @Min(1)
    private Long id;

    @NotNull
    @IsLocalDateTime
    @IsPastOrPresent
    private String datetime;

    @NotNull
    @IsMealRecordType
    private String type;

    private String mealImageUrl;

    public LocalDateTime getRecordAt() {
        return DateParser.stringDateTimeToLocalDateTime(datetime);
    }

    public MealType getMealType() {
        return MealType.ofType(this.type);
    }
}
