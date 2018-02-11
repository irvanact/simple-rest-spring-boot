package com.rest.controller;


import com.rest.domain.Topic;
import com.rest.repository.TopicRepository;
import com.rest.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = {"/topic"})
public class TopicController {
    final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Value("${partial_load.size}")
    private int size;

    @Autowired
    TopicService topicService;

    @Autowired
    TopicRepository topicRepository;

    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Topic> getTopic(@RequestParam(value = "page",defaultValue = "1") int page){
        return topicService.findAllByOrderByUpdateAtDesc(page,size);
    }
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Topic> getOneTopic(@PathVariable(value = "id") Long topicId){
        Topic topic = topicRepository.findOne(topicId);
        if(topic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(topic);
    }
    @RequestMapping(value = {""}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic addTopic(@RequestBody Map<String, Object> payload){
        List<Long> listNews= new ArrayList<>();
        if(payload.get("news") != null){
            listNews = (List<Long>) payload.get("news");
        }
        Topic topic = new Topic();
        topic.setTopicName(payload.get("topic_name").toString());
        topic.setDescription(payload.get("description").toString());
        return topicService.save(topic, listNews);
    }
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic updateTopic(@PathVariable(value = "id") Long topicId, @RequestBody Map<String, Object> payload){
        List<Long> listNews = new ArrayList<>();
        if(payload.get("topics") != null){
            listNews = (List<Long>) payload.get("topics");
        }
        Topic topic = topicRepository.findOne(topicId);
        if(topic == null){
            throw new RuntimeException("Not found");
        }
        topic.setTopicName(payload.get("topic_name").toString());
        topic.setDescription(payload.get("description").toString());
        return topicService.update(topic, listNews);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Topic> deleteTopic(@PathVariable(value = "id") Long noteId) {
        Topic topic = topicRepository.findOne(noteId);
        if(topic == null) {
            return ResponseEntity.notFound().build();
        }
        topicRepository.delete(topic);
        return ResponseEntity.ok().build();
    }
}
