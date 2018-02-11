package com.rest.service;

import com.rest.domain.News;
import com.rest.domain.Topic;

import java.util.List;

public interface NewsService {
    List<News> findAllByOrderByUpdateAtDesc(int page, int size);
    List<News> findAllByStatusOrderByUpdateAtDesc(String status, int page, int size);
    News save(News news, List<Long> topics);
    News update(News news, List<Long> topics);
}
