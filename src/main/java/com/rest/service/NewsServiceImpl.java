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
public class NewsServiceImpl implements NewsService{

    final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    NewsTopicMappingRepository newsTopicMappingRepository;



    public List<News> findAllByOrderByUpdateAtDesc(int page, int size){
        return  newsRepository.findAllByOrderByUpdateAtDesc(new PageRequest((page-1),size));
    }

    @Override
    public List<News> findAllByStatusOrderByUpdateAtDesc(String status, int page, int size) {
        return newsRepository.findAllByStatusOrderByUpdateAtDesc(status, new PageRequest((page-1),size));
    }

    @Override
    public News save(News news, List<Long> topics) {
        news.setCreateAt(new Date());
        news.setUpdateAt(new Date());
        newsRepository.save(news);
        if(topics != null){
            for (long t : topics){
                Topic topic = topicRepository.findOne(t);
                if(topic != null){
                    NewsTopicMapping newsTopicMapping = newsTopicMappingRepository.findByNewsAndTopic(news, topic);
                    if(newsTopicMapping == null){
                        newsTopicMappingRepository.save(new NewsTopicMapping(news, topic));
                    }
                }
            }
        }
        return news;
    }

    @Override
    public News update(News news, List<Long> topics) {

        news.setCreateAt(new Date());
        news.setUpdateAt(new Date());
        newsRepository.save(news);
        List<NewsTopicMapping> newsTopicMapping = newsTopicMappingRepository.findAllByNews(news);
        for(NewsTopicMapping n : newsTopicMapping){
            newsTopicMappingRepository.delete(n);
        }
        if(topics != null){
            for (long t : topics){
                Topic topic = topicRepository.findOne(t);
                if(topic != null){
                    NewsTopicMapping findNewsTopicMapping = newsTopicMappingRepository.findByNewsAndTopic(news, topic);
                    if(findNewsTopicMapping == null){
                        newsTopicMappingRepository.save(new NewsTopicMapping(news, topic));
                    }
                }
            }
        }
        return news;
    }


}
