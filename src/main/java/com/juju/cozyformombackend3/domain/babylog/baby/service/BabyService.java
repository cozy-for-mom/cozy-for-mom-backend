package com.juju.cozyformombackend3.domain.babylog.baby.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.babylog.baby.dto.request.CreateBabyProfileRequest;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.request.ModifyBabyProfileRequest;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.CreateBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.GetBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.ModifyBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.RemoveBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BabyService {
    private final UserRepository userRepository;
    private final BabyProfileRepository babyProfileRepository;

    @Transactional
    public CreateBabyProfileResponse saveBabyProfile(Long userId, CreateBabyProfileRequest request) {
        User user = findUserById(userId);
        final BabyProfile saveBabyProfile = request.toBabyProfile(user);
        saveBabyProfile.addBabyList(request.toBabyList(saveBabyProfile));
        final BabyProfile savedBabyProfile = babyProfileRepository.save(saveBabyProfile);

        return CreateBabyProfileResponse.of(savedBabyProfile.getId(),
            savedBabyProfile.getBabyList().stream().map(baby -> baby.getId()).toList());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public ModifyBabyProfileResponse updateBabyProfile(Long userId, Long babyProfileId,
        ModifyBabyProfileRequest request) {
        final BabyProfile findBabyProfile = babyProfileRepository.findById(babyProfileId)
            .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY_PROFILE));
        findBabyProfile.update(request);
        findBabyProfile.getBabyList().forEach(baby -> {
            ModifyBabyProfileRequest.BabyDto dto = request.getBaby(baby.getId());
            baby.update(dto.getName(), dto.getGender());
        });

        return ModifyBabyProfileResponse.of(findBabyProfile.getId(),
            findBabyProfile.getBabyList().stream().map(baby -> baby.getId()).toList());
    }

    @Transactional
    public RemoveBabyProfileResponse deleteBabyProfile(Long userId, Long babyProfileId) {
        User user = findUserById(userId);
        if (user.getRecentBabyProfileId() == babyProfileId) {
            user.updateRecentBabyProfileId(null);
        }
        final BabyProfile deleteBabyProfile = babyProfileRepository.findById(babyProfileId)
            .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY_PROFILE));
        babyProfileRepository.delete(deleteBabyProfile);

        return RemoveBabyProfileResponse.of(deleteBabyProfile.getId(),
            deleteBabyProfile.getBabyList().stream().map(baby -> baby.getId()).toList());
    }

    public GetBabyProfileResponse getBabyProfile(Long userId, Long babyProfileId) {
        BabyProfile foundBabyProfile = babyProfileRepository.findById(babyProfileId)
            .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY_PROFILE));

        return GetBabyProfileResponse.of(foundBabyProfile);
    }
}
