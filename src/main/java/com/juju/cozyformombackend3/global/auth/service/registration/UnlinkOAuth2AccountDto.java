package com.juju.cozyformombackend3.global.auth.service.registration;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnlinkOAuth2AccountDto {

    private String kakaoTargetIdType;
    private String kakaoTargetId;
    private String appleClientId;
    private String appleClientSecret;
    private String appleRefreshToken;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class KakaoResponse {
        private String id;
    }
}
