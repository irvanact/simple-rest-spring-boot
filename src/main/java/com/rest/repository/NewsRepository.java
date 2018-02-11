package com.rest.repository;

import com.rest.domain.News;
import com.rest.domain.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByUpdateAtDesc(Pageable pageable);
    List<News> findAllByStatusOrderByUpdateAtDesc(@Param("status") String status, Pageable pageable);
    News getOne(@Param("news_id") long newsId);
}
