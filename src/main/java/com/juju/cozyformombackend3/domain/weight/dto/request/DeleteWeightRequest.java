package com.juju.cozyformombackend3.domain.weight.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class DeleteWeightRequest {

	private LocalDate date;
}