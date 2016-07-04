package com.oki.bass.content.vo.detail;

import java.util.List;

import com.oki.bass.content.domain.entity.Content;
import com.oki.config.vo.SuccessVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BassContentVO extends SuccessVO {

	private List<Content> contents;
	
	private boolean isLast;
}
