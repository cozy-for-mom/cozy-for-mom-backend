package com.juju.cozyformombackend3.domain.supplement.service;

import com.juju.cozyformombackend3.domain.supplement.repository.SupplementRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SupplementRecordService {

	private final SupplementRecordRepository supplementRecordRepository;


}
