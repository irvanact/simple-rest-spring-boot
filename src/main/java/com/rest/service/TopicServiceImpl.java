package com.rest.service;

import com.rest.domain.News;
import com.rest.domain.NewsTopicMapping;
import com.rest.domain.Topic;
import com.rest.repository.NewsRepository;
import com.rest.repository.NewsTopicMappingRepository;
import com.rest.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsTopicMappingRepository newsTopicMappingRepository;

    @Override
    public List<Topic> findAllByOrderByUpdateAtDesc(int page, int size) {
        return  topicRepository.findAllByOrderByUpdateAtDesc(new PageRequest((page-1),size));
    }

    @Override
    public Topic save(Topic topic, List<Long> listNews) {
        topic.setCreateAt(new Date());
        topic.setUpdateAt(new Date());
        topicRepository.save(topic);
        if(listNews != null){
            for (long n : listNews){
                News news = newsRepository.findOne(n);
                if(news != null){
                    NewsTopicMapping newsTopicMapping = newsTopicMappingRepository.findByNewsAndTopic(news, topic);
                    if(newsTopicMapping == null){
                        newsTopicMappingRepository.save(new NewsTopicMapping(news, topic));
                    }
                }
            }
        }
        return topic;
    }

    @Override
    public Topic update(Topic topic, List<Long> listNews) {
        topic.setCreateAt(new Date());
        topic.setUpdateAt(new Date());
        topicRepository.save(topic);
        List<NewsTopicMapping> newsTopicMapping = newsTopicMappingRepository.findAllByTopic(topic);
        for(NewsTopicMapping n : newsTopicMapping){
            newsTopicMappingRepository.delete(n);
        }
        if(listNews != null){
            for (long n : listNews){
                News news = newsRepository.findOne(n);
                if(news != null){
                    NewsTopicMapping findNewsTopicMapping = newsTopicMappingRepository.findByNewsAndTopic(news, topic);
                    if(findNewsTopicMapping == null){
                        newsTopicMappingRepository.save(new NewsTopicMapping(news, topic));
                    }
                }
            }
        }
        return topic;
    }
}
