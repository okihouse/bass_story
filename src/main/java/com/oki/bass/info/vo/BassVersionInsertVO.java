package com.oki.bass.info.vo;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class BassVersionInsertVO {

	@NotEmpty(message = "{validation.notnull.message}")
	private String version;
	
}
