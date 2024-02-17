package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageInfoDto {
	private Long imageId;
	private String imageUrl;
	private String description;

	public static ImageInfoDto of(CozyLogImage image) {
		return new ImageInfoDto(image.getId(), image.getCozyLogImageUrl(), image.getDescription());
	}
}
