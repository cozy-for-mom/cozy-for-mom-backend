package com.juju.cozyformombackend3.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateMyInfoRequest {
	private String name;
	private String nickname;
	private String introduce;
	private String image;
	private String birth;
	private String email;
}
