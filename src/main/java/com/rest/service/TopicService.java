package com.rest.service;

import com.rest.domain.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> findAllByOrderByUpdateAtDesc(int page, int size);
    Topic save(Topic topic, List<Long> news);
    Topic update(Topic topic, List<Long> news);
}
