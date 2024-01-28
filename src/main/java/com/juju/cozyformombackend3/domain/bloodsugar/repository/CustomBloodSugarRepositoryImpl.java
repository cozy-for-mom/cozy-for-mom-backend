package com.juju.cozyformombackend3.domain.bloodsugar.repository;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.QFindDaliyBloodSugar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.juju.cozyformombackend3.domain.bloodsugar.model.QBloodSugarRecord.bloodSugarRecord;
import static com.juju.cozyformombackend3.global.repository.DateParser.getDateFromDateTime;

@RequiredArgsConstructor
public class CustomBloodSugarRepositoryImpl implements CustomBloodSugarRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt) {
        return jpaQueryFactory
                .select(new QFindDaliyBloodSugar(bloodSugarRecord.bloodSugarId, bloodSugarRecord.bloodSugarRecordType, bloodSugarRecord.level))
                .from(bloodSugarRecord)
                .where(bloodSugarRecord.user.userId.eq(userId),
                        getDateFromDateTime(bloodSugarRecord.createdAt).eq(createdAt))
                .orderBy(bloodSugarRecord.createdAt.asc())
                .fetch();
    }
}
