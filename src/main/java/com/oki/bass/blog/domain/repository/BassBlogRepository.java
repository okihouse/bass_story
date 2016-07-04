package com.oki.bass.blog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oki.bass.blog.domain.entity.Blog;

public interface BassBlogRepository extends JpaRepository<Blog, Long>{

}
