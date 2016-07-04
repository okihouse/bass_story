package com.oki.bass.gcm.vo;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class BassGcmInsertVO {

	@NotEmpty(message = "{validation.notnull.message}")
	private String gcm;
	
}
