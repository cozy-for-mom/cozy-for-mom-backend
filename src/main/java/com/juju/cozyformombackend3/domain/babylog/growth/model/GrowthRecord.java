package com.juju.cozyformombackend3.domain.babylog.growth.model;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.SaveGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.UpdateGrowth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "growth_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GrowthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "baby_id", nullable = false)
    private Long babyId;

    @ManyToOne()
    @JoinColumn(name = "growth_report_id")
    private GrowthReport growthReport;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "head_diameter")
    private Double headDiameter;

    @Column(name = "head_circum")
    private Double headCircum;

    @Column(name = "abdomen_circum")
    private Double abdomenCircum;

    @Column(name = "thigh_length")
    private Double thighLength;

    private GrowthRecord(Long babyId, LocalDate recordAt, Double weight, Double headDiameter, Double headCircum,
        Double abdomenCircum, Double thighLength) {
        this.babyId = babyId;
        this.weight = weight;
        this.headDiameter = headDiameter;
        this.headCircum = headCircum;
        this.abdomenCircum = abdomenCircum;
        this.thighLength = thighLength;
    }

    public static GrowthRecord of(Long babyId, LocalDate recordAt, Double weight, Double headDiameter,
        Double headCircum, Double abdomenCircum, Double thighLength) {
        return new GrowthRecord(babyId, recordAt, weight, headDiameter, headCircum, abdomenCircum, thighLength);
    }

    public static GrowthRecord of(Long babyId, LocalDate recordAt, SaveGrowth.Request.GrowthInfo growthInfo) {
        return new GrowthRecord(babyId, recordAt, growthInfo.getWeight(), growthInfo.getHeadDiameter(),
            growthInfo.getHeadCircum(), growthInfo.getAbdomenCircum(), growthInfo.getThighLength());
    }

    public void update(UpdateGrowth.Request.BabyInfo baby) {
        this.weight = baby.getGrowthInfo().getWeight();
        this.headDiameter = baby.getGrowthInfo().getHeadDiameter();
        this.headCircum = baby.getGrowthInfo().getHeadCircum();
        this.abdomenCircum = baby.getGrowthInfo().getAbdomenCircum();
        this.thighLength = baby.getGrowthInfo().getThighLength();
    }

    public void updateGrowthReport(GrowthReport growthReport) {
        this.growthReport = growthReport;
    }
}
