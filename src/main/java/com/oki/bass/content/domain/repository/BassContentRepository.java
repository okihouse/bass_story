package com.oki.bass.content.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oki.bass.content.domain.entity.Content;

public interface BassContentRepository extends JpaRepository<Content, Long>{

	@Query(value = "select c.title from Content c where c.registerDate > :today")
	List<String> findByRegisterDateStartDateAfter(@Param("today") Date today);

	@Query(value = "select new Map(c.url as url"
			+ ", c.title as title"
			+ ", c.name as name"
			+ ", c.thumbnail as thumbnail"
			+ ", c.category as category"
			+ ", c.registerDate as registerDate"
			+ ", c.contentNo as contentNo"
			+ ", b.profile as profile"
			+ ") from Content c inner join c.blog b "
			+ " where c.using = 'Y' and b.using = 'Y' "
			+ "group by c.url")
	Page<Content> findAll(Pageable pageRequest);

}
