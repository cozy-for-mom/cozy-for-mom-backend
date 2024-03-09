package com.juju.cozyformombackend3.domain.user.dto.request;

import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateMyInfoRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String introduce;

    private String image;

    @IsLocalDate
    @IsPastOrPresentDate
    private String birth;

    private String email;
}
