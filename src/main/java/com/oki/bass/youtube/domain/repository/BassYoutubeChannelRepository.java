package com.oki.bass.youtube.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oki.bass.type.BassType.YN;
import com.oki.bass.youtube.domain.entity.YoutubeChannel;

public interface BassYoutubeChannelRepository extends JpaRepository<YoutubeChannel, Long>{

	@Query(value = "select y.id from YoutubeChannel y where y.isUse = :isUse")
	List<String> findByIsUse(@Param("isUse") YN y);

	List<YoutubeChannel> findById(String playlistId);

}
