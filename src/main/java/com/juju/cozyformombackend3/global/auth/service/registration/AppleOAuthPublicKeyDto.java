package com.juju.cozyformombackend3.global.auth.service.registration;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleOAuthPublicKeyDto {
    private List<Key> keys;

    @Getter
    @Setter
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;

        @Override
        public String toString() {
            return "Key{" +
                   "kty='" + kty + '\'' +
                   ", kid='" + kid + '\'' +
                   ", use='" + use + '\'' +
                   ", alg='" + alg + '\'' +
                   ", n='" + n + '\'' +
                   ", e='" + e + '\'' +
                   '}';
        }
    }

    public Optional<Key> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
            .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
            .findFirst();
    }

    @Override
    public String toString() {
        return "AppleOAuthPublicKeyDto{" +
               "keys=" + keys +
               '}';
    }
}

