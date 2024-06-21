package com.juju.cozyformombackend3.global.auth.service.registration.apple;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleToken {

    @Setter
    @Getter
    public static class Request {
        private String code;
        private String client_id;
        private String client_secret;
        private String grant_type;
        //private String refresh_token;

        public static Request of(String code, String clientId, String clientSecret, String grantType
                /*, String refreshToken*/) {
            Request request = new Request();
            request.code = code;
            request.client_id = clientId;
            request.client_secret = clientSecret;
            request.grant_type = grantType;
            //  request.refresh_token = refreshToken;
            return request;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "code='" + code + '\'' +
                    ", client_id='" + client_id + '\'' +
                    ", client_secret='" + client_secret + '\'' +
                    ", grant_type='" + grant_type + '\'' +
                    '}';
        }
    }

    @Setter
    public static class Response {
        private String access_token;
        private String expires_in;
        private String id_token;
        private String refresh_token;
        private String token_type;
        private String error;

        public String getAccessToken() {
            return access_token;
        }

        public String getExpiresIn() {
            return expires_in;
        }

        public String getIdToken() {
            return id_token;
        }

        public String getRefreshToken() {
            return refresh_token;
        }

        public String getTokenType() {
            return token_type;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "access_token='" + access_token + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", id_token='" + id_token + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    ", token_type='" + token_type + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }
}
