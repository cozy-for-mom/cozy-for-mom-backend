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

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image_url", nullable = false)
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
    public User(UserType userType, String name, String nickname, String profileImageUrl, String introduce,
        LocalDate birth, String email) {
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
}
