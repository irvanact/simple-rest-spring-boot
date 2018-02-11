package com.rest.repository;

import com.rest.domain.News;
import com.rest.domain.NewsTopicMapping;
import com.rest.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NewsTopicMappingRepository extends JpaRepository<NewsTopicMapping, Long> {
    NewsTopicMapping findByNewsAndTopic(News news, Topic topic);
    List<NewsTopicMapping> findAllByNews(News news);
    List<NewsTopicMapping> findAllByTopic(Topic topic);
}
