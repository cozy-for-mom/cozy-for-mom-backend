package com.juju.cozyformombackend3.domain.user.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.like.model.Like;
import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;
import com.juju.cozyformombackend3.domain.user.dto.request.UpdateMyInfoRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.meal.model.MealRecord;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nickname", "email"},
        name = "nickname_email_unique")})
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_value", nullable = false)
    private String oauthValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_registration", nullable = false)
    private OAuth2Registration oauth2Registration;

    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "introduce", columnDefinition = "VARCHAR(255)")
    private String introduce;

    // @Temporal(TemporalType.DATE)
    private LocalDate birth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "recent_baby_profild_id")
    private Long recentBabyProfileId;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<Supplement> supplementList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<BloodSugarRecord> bloodSugarRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<WeightRecord> weightRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<MealRecord> mealRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<BabyProfile> babyProfileList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<CozyLog> cozyLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private final List<Scrap> scrapList = new ArrayList<>();

    @Builder
    public User(String oauthValue, OAuth2Registration oauth2Registration, String deviceToken, UserType userType,
        String name, String nickname, String profileImageUrl, String introduce, LocalDate birth, String email) {
        this.oauthValue = oauthValue;
        this.oauth2Registration = oauth2Registration;
        this.deviceToken = deviceToken;
        this.userType = userType;
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduce = introduce;
        this.birth = birth;
        this.email = email;
    }

    @Override
    public void delete() {

    }

    public void registerSupplement(Supplement supplement) {
        supplementList.add(supplement);
    }

    public void updateRecentBabyProfileId(Long recentBabyProfileId) {
        this.recentBabyProfileId = recentBabyProfileId;
    }

    public void update(UpdateMyInfoRequest request) {
        this.name = request.getName();
        this.nickname = request.getNickname();
        this.introduce = request.getIntroduce();
        this.profileImageUrl = request.getImage();
        this.birth = LocalDate.parse(request.getBirth());
        this.email = request.getEmail();
    }

    public void addBabyProfile(BabyProfile babyProfile) {
        this.babyProfileList.add(babyProfile);
        babyProfile.updateMom(this);
    }

    public void updateDeviceToken(String deviceToken) {
        if (!this.deviceToken.equals(deviceToken)) {
            this.deviceToken = deviceToken;
        }
    }

    public void updateOauthValue(String oauthValue) {
        if (!this.oauthValue.equals(oauthValue)) {
            this.oauthValue = oauthValue;
        }
    }
}
