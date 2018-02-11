package com.rest.repository;

import com.rest.domain.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findOne(@Param("topic_id") long topicId);
    List<Topic> findAllByOrderByUpdateAtDesc(Pageable pageable);
}
