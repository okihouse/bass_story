package com.oki.bass.youtube.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oki.config.vo.SuccessVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BassYoutubeContentVO extends SuccessVO {

	private List<BassYoutubeContents> youtubes = new ArrayList<>();
	
	private boolean isLast = true;
	
	@Data
	public static class BassYoutubeContents {
		
		private Long youtubeNo;
		
		private String id;
		
		private String title;
		
		private String thumbnail;
		
		private String videoId;
		
		private int duration;
		
		private Date publishedDate;
		
		private String name;
		
	}
	
}
