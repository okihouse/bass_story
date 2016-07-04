package com.oki.bass.info.vo;

import java.util.Date;

import com.oki.config.vo.SuccessVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BassVersionVO extends SuccessVO{

	private String version;
	
	private Date issueDate;
	
}
