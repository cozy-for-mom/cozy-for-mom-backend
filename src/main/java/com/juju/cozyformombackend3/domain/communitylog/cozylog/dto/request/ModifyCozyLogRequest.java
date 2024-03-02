package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.ImageInfoDto;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;

import lombok.Getter;

@Getter
public class ModifyCozyLogRequest {
	private Long id;
	private String title;
	private String content;
	private List<ImageInfoDto> imageList;
	private String mode;

	public CozyLogMode getMode() {
		return CozyLogMode.valueOf(mode);
	}

	public List<Long> getImageIdList() {
		return imageList.stream()
			.map(image -> image.getImageId())
			.toList();
	}

	public List<CozyLogImage> getImageList() {
		return imageList.stream()
			.map(image -> CozyLogImage.of(null, image.getImageUrl(), image.getDescription()))
			.toList();
	}
}
