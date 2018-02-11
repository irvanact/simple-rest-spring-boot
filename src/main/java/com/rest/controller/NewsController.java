package com.rest.controller;

import com.rest.domain.News;
import com.rest.repository.NewsRepository;
import com.rest.service.NewsService;
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
@RequestMapping(value = {"/news"})
public class NewsController {

    final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Value("${partial_load.size}")
    private int size;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<News> getNews(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "status",defaultValue = "all") String status){
        if(status.equalsIgnoreCase("all")){
            return newsService.findAllByOrderByUpdateAtDesc(page,size);
        }else{
            return newsService.findAllByStatusOrderByUpdateAtDesc(status,page,size);
        }

    }
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> getOneNews(@PathVariable(value = "id") Long newsId){
        News news = newsRepository.findOne(newsId);
        if(news == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(news);
    }
    @RequestMapping(value = {""}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public News addNews(@RequestBody Map<String, Object> payload){
        List<Long> listTopic = new ArrayList<>();
        if(payload.get("topics") != null){
            listTopic = (List<Long>) payload.get("topics");
        }
        News news = new News();
        news.setStatus(payload.get("status").toString());
        news.setTitle(payload.get("title").toString());
        news.setContent(payload.get("content").toString());
        return newsService.save(news, listTopic);
    }
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public News updateNews(@PathVariable(value = "id") Long newsId, @RequestBody Map<String, Object> payload){
        List<Long> listTopic = new ArrayList<>();
        if(payload.get("topics") != null){
            listTopic = (List<Long>) payload.get("topics");
        }
        News news = newsRepository.findOne(newsId);
        if(news == null){
            throw new RuntimeException("Not found");
        }
        news.setStatus(payload.get("status").toString());
        news.setTitle(payload.get("title").toString());
        news.setContent(payload.get("content").toString());
        return newsService.update(news, listTopic);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> deleteNews(@PathVariable(value = "id") Long noteId) {
        News news = newsRepository.findOne(noteId);
        if(news == null) {
            return ResponseEntity.notFound().build();
        }
        newsRepository.delete(news);
        return ResponseEntity.ok().build();
    }

}
