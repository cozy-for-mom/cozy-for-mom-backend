package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.Getter;

@Getter
public class CreateCozyLogRequest {
	private String title;
	private String content;
	private List<Image> imageList;
	private String mode;

	@Getter
	private class Image {
		private String imageUrl;
		private String description;
	}

	private List<CozyLogImage> getImageList() {
		return imageList.stream()
			.map(image -> CozyLogImage.of(null, image.imageUrl, image.description))
			.toList();
	}

	public CozyLog toEntity(User user) {
		return CozyLog.of(user, this.title, this.content, getImageList(), CozyLogMode.valueOf(this.mode));
	}
}
