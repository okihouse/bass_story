package com.oki.bass.youtube.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oki.bass.type.BassType.YN;
import com.oki.bass.youtube.domain.entity.Youtube;
import com.oki.bass.youtube.vo.BassYoutubeContentVO.BassYoutubeContents;

public interface BassYoutubeRepository extends JpaRepository<Youtube, Long> {

	@Query(value = "select y.id from Youtube y where y.publishedDate > :today")
	List<String> findByPublishedDateStartDateAfter(@Param("today") Date today);
	
	@Query(value = "select new Map("
			+ "  y.youtubeNo as youtubeNo"
			+ ", y.id as id"
			+ ", y.title as title"
			+ ", y.thumbnail as thumbnail"
			+ ", y.videoId as videoId"
			+ ", y.duration as duration"
			+ ", y.publishedDate as publishedDate"
			+ ", y.profile as profile"
			+ ", c.title as name) from Youtube y inner join y.youtubeChannel c "
			+ "where y.view = :y "
			+ "group by y.id")
	Page<BassYoutubeContents> findByView(@Param("y") YN y, Pageable pageable);

}
