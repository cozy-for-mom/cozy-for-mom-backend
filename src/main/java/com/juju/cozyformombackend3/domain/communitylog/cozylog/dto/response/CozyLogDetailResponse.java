package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.ImageInfoDto;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CozyLogDetailResponse {
	private final Writer writer;
	private final String title;
	private final String content;
	private List<ImageInfoDto> imageList;
	private final String mode;
	private final String createdAt;
	private String updatedAt;
	private final Long scrapCount;
	private final Long viewCount;
	private final boolean isScraped;

	public static CozyLogDetailResponse of(CozyLog foundCozyLog, Long scrapCount, boolean isScraped) {
		return CozyLogDetailResponse.builder()
			.writer(new Writer(foundCozyLog.getUser().getId(), foundCozyLog.getUser().getNickname()))
			.title(foundCozyLog.getTitle())
			.content(foundCozyLog.getContent())
			.imageList(getImageDtoList(foundCozyLog.getCozyLogImageList()))
			.mode(String.valueOf(foundCozyLog.getMode()))
			.createdAt(dateTimeToStringFormatDateTime(foundCozyLog.getCreatedAt()))
			.updatedAt(dateTimeToStringFormatDateTime(foundCozyLog.getModifiedAt()))
			.scrapCount(scrapCount)
			.viewCount(foundCozyLog.getView())
			.isScraped(isScraped)
			.build();
	}

	private static List<ImageInfoDto> getImageDtoList(List<CozyLogImage> imageList) {
		return imageList.stream()
			.map(image -> ImageInfoDto.of(image))
			.toList();
	}

	@Getter
	@AllArgsConstructor
	private static class Writer {
		private Long id;
		private String nickname;
	}
}

/**
 * 				"writer": {
 * 					"id": 1,
 * 					"nickname": "쥬쥬"
 *                                }
 * 		    "title": "오늘은 태동을 느낀 날",
 * 				"content": "오늘은 미룽이가 ㅎㅎ 발차기질을 했다. 귀여워귀여워 귀여워귀여워 귀여워귀여워 귀여워귀여워 귀여워귀여워",
 * 				"imageList": [
 *              {"imageId": 1, "imageUrl": "imageurlsdfhksjdfhskjdhfksjh1", "description": "미룽이 초음파"},
 *              {"imageId": 2, "imageUrl": "imageurlsdfhksjdfhskjdhfksjh2", "description": "오늘 먹은 식단"},
 *              {"imageId": 3, "imageUrl": "imageurlsdfhksjdfhskjdhfksjh3", "description": "행복한 태교 시간"}
 * 		    ],
 * 				"mode": "PUBLIC",
 * 				"createdAt": "2023-10-28 11:11:11",
 * 				"updatedAt": "2023-10-28 11:31:11",
 * 				"crapCount": 10,
 * 				"viewCount": 231,
 * 				"isScraped": true
 */