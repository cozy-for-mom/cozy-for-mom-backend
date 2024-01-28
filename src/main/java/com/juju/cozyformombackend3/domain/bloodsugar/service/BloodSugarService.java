package com.juju.cozyformombackend3.domain.bloodsugar.service;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.bloodsugar.repository.BloodSugarRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BloodSugarService {

    private final BloodSugarRepository bloodSugarRepository;

    @Transactional
    public SaveBloodSugarRecordResponse saveBloodSugarRecord(SaveBloodSugarRecordRequest request, User user) {
        Long savedRecordId = user.addBloodSugarRecord(request.getDate(), request.getType(), request.getLevel());
        return SaveBloodSugarRecordResponse.of(savedRecordId);
    }

    @Transactional
    public ModifyBloodSugarRecordResponse updateBloodSugarRecord(ModifyBloodSugarRecordRequest request, User user) {
        BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
        if (modifiedRecord.getUser() != user) {
            throw new IllegalArgumentException("본인의 혈당 기록만 수정할 수 있습니다.");
        }
        modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
        return ModifyBloodSugarRecordResponse.of(modifiedRecord.getBloodSugarId());
    }

    public void deleteBloodSugarRecord(Long recordId, User user) {
        BloodSugarRecord deletedRecord = bloodSugarRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
        if (deletedRecord.getUser() != user) {
            throw new IllegalArgumentException("본인의 혈당 기록만 삭제할 수 있습니다.");
        }
        bloodSugarRepository.delete(deletedRecord);
    }

    public FindDailyBloodSugarListResponse findDailyBloodSugarRecord(long userId, String date) {
        return FindDailyBloodSugarListResponse.of(bloodSugarRepository.searchAllByCreatedAt(userId, date));
    }

    public FindBloodSugarListResponse findBloodSugarRecord(long userId, String date, String type, Pageable pageable) {
//        List<FindPeriodicBloodSugar> findPeriodicRecordList = bloodSugarRepository.searchAllByPeriodType(userId, date, type, pageable);
        Slice<FindPeriodicBloodSugar> response = null;
        LocalDate localDate = LocalDate.parse(date);

        if (type.equals("daily")) {
            response = findAllByDailyType(userId, localDate, pageable);
            return FindBloodSugarListResponse.of("daily", response);
        } else if (type.equals("weekly")) {
            response = findAllByWeeklyType(userId, localDate, pageable);
            return FindBloodSugarListResponse.of("weekly", response);
        } else if (type.equals("monthly")) {
//            response = findAllByMonthlyType(userId, localDate, pageable);
//            response = bloodSugarRepository.searchAllByMonthlyType(userId, date, pageable);
        } else {
            throw new IllegalArgumentException("존재하지 않는 기간 타입입니다.");
        }
        return null;
    }

    private Slice<FindPeriodicBloodSugar> findAllByWeeklyType(long userId, LocalDate date, Pageable pageable) {
        Slice<FindPeriodicBloodSugar> bloodSugarList = bloodSugarRepository.searchAllByWeeklyType(userId, date, pageable);

        return bloodSugarList;
    }

    private Slice<FindPeriodicBloodSugar> findAllByDailyType(long userId, LocalDate date, Pageable pageable) {
        Slice<FindPeriodicBloodSugar> bloodSugarList = bloodSugarRepository.searchAllByDailyType(userId, date, pageable);

        return bloodSugarList;
    }
}
