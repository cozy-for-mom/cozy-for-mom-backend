package com.juju.cozyformombackend3.domain.babylog.growth.model;

import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.UpdateGrowth;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "growth_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GrowthDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "baby_profile_id")
    // private BabyProfile babyProfile;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToOne
    @JoinColumn(name = "growth_report_id")
    private GrowthReport growthReport;

    private GrowthDiary(LocalDate recordAt, String growthImageUrl, String title,
                        String content, GrowthReport growthReport) {
        // this.babyProfile = babyProfile;
        this.imageUrl = growthImageUrl;
        this.title = title;
        this.content = content;
        this.growthReport = growthReport;
    }

    public static GrowthDiary of(LocalDate recordAt, String growthImageUrl, String title,
                                 String content) {
        return new GrowthDiary(recordAt, growthImageUrl, title, content, null);
    }

    public void update(UpdateGrowth.Request.GrowthDiaryDto growthDiaryDto) {
//        if (!id.equals(growthDiaryDto.getGrowthDiaryId())) {
//            throw new BusinessException(GrowthErrorCode.CONFLICT_NOT_MATCH_GROWTH_DIARY);
//        }
        this.imageUrl = growthDiaryDto.getGrowthImageUrl();
        this.title = growthDiaryDto.getTitle();
        this.content = growthDiaryDto.getContent();
    }

    public void updateGrowthReport(GrowthReport growthReport) {
        this.growthReport = growthReport;
    }
}
