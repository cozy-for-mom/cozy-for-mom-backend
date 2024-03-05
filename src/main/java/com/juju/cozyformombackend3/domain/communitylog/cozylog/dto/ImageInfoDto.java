package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema
public class ImageInfoDto {

	@Schema(example = "2")
	private Long imageId;

	@Schema(defaultValue = "www.dhfkwjdfhkwjhdfkj.com")
	private String imageUrl;

	@Schema(defaultValue = "이사진은 말이죵")
	private String description;

	public static ImageInfoDto of(CozyLogImage image) {
		return new ImageInfoDto(image.getId(), image.getImageUrl(), image.getDescription());
	}
}
